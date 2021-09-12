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

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Context
public class AccountService implements AuthenticationProvider {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createInitialAccount(String userName, String emailAddress, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUserName(userName);
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("An account with this name does already exist");
        }

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setUserName(userName);
        account.setEmailAddress(emailAddress);
        account.setPasswordHash(getPasswordHash(password));
        accountRepository.save(account);
    }

    public long getNumberOfAccounts() {
        return accountRepository.count();
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Flowable.empty();
        }

        // Get the account
        Optional<Account> optionalAccount = accountRepository.findByUserName(username);
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

    private String getPasswordHash(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    private boolean isPasswordHashMatching(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
