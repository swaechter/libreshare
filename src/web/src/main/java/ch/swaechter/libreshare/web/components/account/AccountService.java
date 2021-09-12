package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.account.table.Account;
import ch.swaechter.libreshare.web.utils.string.StringUtils;
import io.micronaut.context.annotation.Context;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.Flowable;
import org.mindrot.jbcrypt.BCrypt;
import org.reactivestreams.Publisher;

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
     * Create a new account service.
     *
     * @param accountRepository Account repository to interact with the SQL database
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Create a new account.
     *
     * @param username     Account username
     * @param emailAddress Account email address
     * @param password     Raw account password
     */
    public void createAccount(String username, String emailAddress, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("An account with this name does already exist");
        }

        String passwordHash = getPasswordHash(password);
        Account account = new Account(UUID.randomUUID(), username, emailAddress, passwordHash);
        accountRepository.save(account);
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
        // Check the input data
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Flowable.empty();
        }

        // Get the account
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isEmpty()) {
            return Flowable.empty();
        }

        Account account = optionalAccount.get();

        // Check the password hash
        if (!isPasswordHashMatching(password, account.getPasswordHash())) {
            return Flowable.empty();
        }

        return Flowable.just(AuthenticationResponse.success(account.getId().toString(), new ArrayList<>()));
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
