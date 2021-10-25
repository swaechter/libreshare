package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.Converter;
import ch.swaechter.libreshare.web.components.account.dto.ChangePasswordDto;
import ch.swaechter.libreshare.web.components.account.dto.CreateAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.ReadAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.UpdateAccountDto;
import ch.swaechter.libreshare.web.components.account.table.Account;
import ch.swaechter.libreshare.web.configuration.exceptionhandling.ServerException;
import ch.swaechter.libreshare.web.utils.string.StringUtils;
import io.micronaut.context.annotation.Context;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

/**
 * Manage all accounts.
 *
 * @author Simon Wächter
 */
@Context
public class AccountService implements AuthenticationProvider {

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
     * @param accountRepository Account repository to interact with the SQL database
     * @param converter         Converter to convert DTO classes
     */
    public AccountService(AccountRepository accountRepository, Converter converter) {
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
        Optional<Account> optionalAccount = accountRepository.findByUsername(createAccountDto.getUsername());
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("An account with this name does already exist");
        }

        UUID id = UUID.randomUUID();
        String username = createAccountDto.getUsername();
        String emailAddress = createAccountDto.getEmailAddress();
        String passwordHash = getPasswordHash(createAccountDto.getPlaintextPassword());
        Account account = new Account(id, username, emailAddress, passwordHash);

        accountRepository.save(account);

        return new ReadAccountDto(id, username, emailAddress);
    }

    public List<ReadAccountDto> getAccounts() {
        List<Account> accountList = accountRepository.findAllOrderByUsername();
        return converter.accountsToReadAccountDtos(accountList);
    }

    public ReadAccountDto getAccountById(UUID id) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Der angegebene Benutzer konnte nicht gefunden werden");
        }

        Account account = optionalAccount.get();

        return converter.accountToReadAccountDto(account);
    }

    public void updateAccount(UUID id, UpdateAccountDto updateAccountDto) throws ServerException {
        Optional<Account> optionalAccountById = accountRepository.findById(id);
        if (optionalAccountById.isEmpty()) {
            throw new ServerException("Der angegebene Benutzer konnte nicht gefunden werden");
        }

        Optional<Account> optionalAccountByUserName = accountRepository.findByUsername(updateAccountDto.getUsername());
        if (optionalAccountByUserName.isPresent() && !id.equals(optionalAccountByUserName.get().getId())) {
            throw new ServerException("Es existiert bereits ein anderer Benutzer mit diesem Namen");
        }

        Account existingAccount = optionalAccountById.get();
        Account account = converter.updateAccountDtoToAccount(updateAccountDto);

        account.setId(existingAccount.getId());
        account.setPasswordHash(existingAccount.getPasswordHash());

        accountRepository.update(account);
    }

    public void changeAccountPassword(UUID id, ChangePasswordDto changePasswordDto) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Der angegebene Benutzer konnte nicht gefunden werden");
        }

        Account account = optionalAccount.get();

        account.setPasswordHash(getPasswordHash(changePasswordDto.getPlaintextPassword()));

        accountRepository.update(account);
    }

    public void deleteAccountById(UUID id) throws ServerException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new ServerException("Der Benutzer Account konnte nicht gefunden werden");
        }

        List<Account> accountList = accountRepository.findAllOrderByUsername();
        if (accountList.size() == 1) {
            throw new ServerException("Der letzte Benutzer kann nicht gelöscht werden");
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
     * Authenticate a client based on the HTTP request.
     *
     * @param httpRequest           HTTP request
     * @param authenticationRequest Authentication that was extracted from the HTTP request
     * @return Failed or successful login
     */
    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            // Check the input data
            String username = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                emitter.error(AuthenticationResponse.exception());
                return;
            }

            // Get the account
            Optional<Account> optionalAccount = accountRepository.findByUsername(username);
            if (optionalAccount.isEmpty()) {
                emitter.error(AuthenticationResponse.exception());
                return;
            }

            Account account = optionalAccount.get();

            // Check the password hash
            if (!isPasswordHashMatching(password, account.getPasswordHash())) {
                emitter.error(AuthenticationResponse.exception());
                return;
            }

            emitter.next(AuthenticationResponse.success(account.getId().toString(), new ArrayList<>()));
            emitter.complete();
        }, FluxSink.OverflowStrategy.ERROR);
    }

    /**
     * Generate a new password.
     *
     * @return Generated password
     */
    public String generatePassword() {
        byte[] data = new byte[24];
        Random random = new SecureRandom();
        random.nextBytes(data);
        return Base64.getEncoder().encodeToString(Base64.getEncoder().encodeToString(data).getBytes(StandardCharsets.UTF_8));
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
     * Check if an input hash results in the password hash
     *
     * @param rawPassword    Raw password
     * @param hashedPassword Hash of the password the value is compared gaainst
     * @return Status of the check
     */
    private boolean isPasswordHashMatching(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
