package ch.swaechter.libreshare.web.components.setup;

import ch.swaechter.libreshare.web.components.account.AccountService;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Context
public class SetupService implements ApplicationEventListener<ServerStartupEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SetupService.class);

    private final AccountService accountService;

    public SetupService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        // Create an initial account if no account exists
        if (accountService.getNumberOfAccounts() == 0) {
            try {
                logger.info("Creating initial account");
                String userName = "admin";
                String emailAddress = "admin@invalid.com";
                String password = generatePassword();
                accountService.createInitialAccount(userName, emailAddress, password);
                logger.info("Initial setup account created. Login name is {} and password {}", userName, password);
            } catch (Exception exception) {
                logger.error("Unable to create setup account: " + exception.getMessage());
            }
        }
    }

    private String generatePassword() {
        byte[] data = new byte[24];
        Random random = new SecureRandom();
        random.nextBytes(data);
        return Base64.getEncoder().encodeToString(Base64.getEncoder().encodeToString(data).getBytes(StandardCharsets.UTF_8));
    }
}
