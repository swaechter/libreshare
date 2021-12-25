package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.Converter;
import ch.swaechter.libreshare.web.components.account.dto.*;
import ch.swaechter.libreshare.web.components.account.table.Account;
import ch.swaechter.libreshare.web.components.settings.SettingsService;
import ch.swaechter.libreshare.web.configuration.exceptionhandling.ServerException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micronaut.context.annotation.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Manage all accounts and issue/check JWT tokens.
 *
 * @author Simon Wächter
 */
@Context
public class AccountService {

    /**
     * Name used to issue JWT tokens.
     */
    private static final String JWT_ISSUER = "Secasign-Box";

    /**
     * Validity duration of a JWT token in milliseconds.
     */
    private static final long JWT_VALIDITY_IN_MS = 120 * 60 * 1000;

    /**
     * Settings service used to get the settings and the server secret.
     */
    private final SettingsService settingsService;

    /**
     * Account repository to interact with the SQL database.
     */
    private final AccountRepository accountRepository;

    /**
     * Converter to convert DTO classes.
     */
    private final Converter converter;

    /**
     * Create a new account service.
     *
     * @param settingsService   Settings service used to get the settings and the server secret
     * @param accountRepository Account repository to interact with the SQL database
     * @param converter         Converter to convert DTO classes
     */
    public AccountService(SettingsService settingsService, AccountRepository accountRepository, Converter converter) {
        this.settingsService = settingsService;
        this.accountRepository = accountRepository;
        this.converter = converter;
    }

    /**
     * Create a new account.
     *
     * @param createAccountDto Account to create
     * @return Created account
     * @throws ServerException Exception in case of a validation/database problem
     */
    public ReadAccountDto createAccount(CreateAccountDto createAccountDto) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(createAccountDto.username());
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("An account with this name does already exist");
        }

        UUID id = UUID.randomUUID();
        String username = createAccountDto.username();
        String emailAddress = createAccountDto.emailAddress();
        String passwordHash = getPasswordHash(createAccountDto.plaintextPassword());
        Account account = new Account(id, username, emailAddress, passwordHash);

        accountRepository.save(account);

        return new ReadAccountDto(id, username, emailAddress);
    }

    /**
     * Get all accounts.
     *
     * @return All accounts
     */
    public List<ReadAccountDto> getAccounts() {
        List<Account> accountList = accountRepository.findAllOrderByUsername();
        return converter.accountsToReadAccountDtos(accountList);
    }

    /**
     * Get an account by ID.
     *
     * @param id ID of the account
     * @return Optional account
     * @throws ServerException Exception in case the account was not found or a database problem
     */
    public ReadAccountDto getAccountById(UUID id) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Unable to find an account for the given ID");
        }

        Account account = optionalAccount.get();

        return converter.accountToReadAccountDto(account);
    }

    /**
     * Update an account by ID:
     *
     * @param id               ID of the account
     * @param updateAccountDto Updated account
     * @throws ServerException Exception in case the account was not found or a database problem
     */
    public void updateAccount(UUID id, UpdateAccountDto updateAccountDto) throws ServerException {
        Optional<Account> optionalAccountById = accountRepository.findById(id);
        if (optionalAccountById.isEmpty()) {
            throw new ServerException("Unable to find an account for the given ID");
        }

        Optional<Account> optionalAccountByUserName = accountRepository.findByUsername(updateAccountDto.username());
        if (optionalAccountByUserName.isPresent() && !id.equals(optionalAccountByUserName.get().getId())) {
            throw new ServerException("Another account with this name already exists");
        }

        Account existingAccount = optionalAccountById.get();
        Account account = converter.updateAccountDtoToAccount(updateAccountDto);

        account.setId(existingAccount.getId());
        account.setPasswordHash(existingAccount.getPasswordHash());

        accountRepository.update(account);
    }

    /**
     * Change the password of an account.
     *
     * @param id                ID of the account
     * @param changePasswordDto Changed password
     * @throws ServerException Exception in case the account was not found or a database problem
     */
    public void changeAccountPassword(UUID id, ChangePasswordDto changePasswordDto) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Unable to find an account for the given ID");
        }

        Account account = optionalAccount.get();

        account.setPasswordHash(getPasswordHash(changePasswordDto.plaintextPassword()));

        accountRepository.update(account);
    }

    /**
     * Delete an account by ID:
     *
     * @param id ID of the account
     * @throws ServerException Exception in case the account was not found or a database problem
     */
    public void deleteAccountById(UUID id) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Unable to find an account for the given ID");
        }

        List<Account> accountList = accountRepository.findAllOrderByUsername();
        if (accountList.size() == 1) {
            throw new ServerException("Unable to delete the last account");
        }

        accountRepository.deleteById(id);
    }

    /**
     * Get the number of accounts.
     *
     * @return Number of accounts
     */
    public long getNumberOfAccounts() {
        return accountRepository.count();
    }

    /**
     * Perform a login via username and password to get a new JWT token.
     *
     * @param authenticationDto Username and password
     * @return Optional JWT token if the login was successful
     */
    public Optional<TokenDto> login(AuthenticationDto authenticationDto) {
        // Get the account
        Optional<Account> optionalAccount = accountRepository.findByUsername(authenticationDto.username());
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }

        Account account = optionalAccount.get();

        // Check the password hash
        if (!isPasswordHashMatching(authenticationDto.password(), account.getPasswordHash())) {
            return Optional.empty();
        }

        // Calculate the expiration date
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime() + JWT_VALIDITY_IN_MS);

        // Issue the token
        String secret = settingsService.getSettings().getServer().getSecret();
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8));
        String token = JWT.create().withIssuer(JWT_ISSUER).withSubject(account.getId().toString()).withExpiresAt(expirationDate).sign(algorithm);
        return Optional.of(new TokenDto(token));
    }

    /**
     * Check if a JWT token is valid (Cryptographically and not expired).
     *
     * @param token JWT token
     * @return Status of the action or false in case of an exception
     */
    public boolean isValidToken(String token) {
        try {
            String secret = settingsService.getSettings().getServer().getSecret();
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Get the subject from a JWT token.
     *
     * @param token JWT token
     * @return JWT subject
     */
    public String getSubject(String token) {
        String secret = settingsService.getSettings().getServer().getSecret();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
        DecodedJWT decodedJwt = verifier.verify(token);
        return decodedJwt.getSubject();
    }

    /**
     * Generate a password hash of the raw password.
     *
     * @param rawPassword Raw password to hash
     * @return Hashed output value
     */
    public String getPasswordHash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * Check if the hashing of a raw password results in the password hash
     *
     * @param rawPassword    Raw password
     * @param hashedPassword Hash of the password the value is compared against
     * @return Status of the check
     */
    private boolean isPasswordHashMatching(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
