package ch.swaechter.libreshare.web.components.setup;

import ch.swaechter.libreshare.web.components.account.AccountService;
import ch.swaechter.libreshare.web.components.account.dto.CreateAccountDto;
import ch.swaechter.libreshare.web.components.event.EventService;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for setting up the initial configuration and account.
 *
 * @author Simon Wächter
 */
@Context
public class SetupService implements ApplicationEventListener<ServerStartupEvent> {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SetupService.class);

    /**
     * Event service to publish new events.
     */
    private final EventService eventService;

    /**
     * Account service to create an initial account.
     */
    private final AccountService accountService;

    /**
     * Create a new setup service.
     *
     * @param eventService   Event service to publish new events
     * @param accountService Account service to create an initial account
     */
    public SetupService(EventService eventService, AccountService accountService) {
        this.eventService = eventService;
        this.accountService = accountService;
    }

    /**
     * Create the initial account on server startup if none exists.
     *
     * @param event Micronaut event
     */
    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        // Create an initial account if no account exists
        if (accountService.getNumberOfAccounts() == 0) {
            try {
                logger.info("Creating initial account");
                CreateAccountDto createAccountDto = new CreateAccountDto("admin", "admin@invalid.com", "123456aAbB");
                accountService.createAccount(createAccountDto);
                logger.info("Initial setup account created. Login name is {} and password {}", createAccountDto.username(), createAccountDto.plaintextPassword());
                eventService.addEvent("Initial setup account " + createAccountDto.username() + " was created");
            } catch (Exception exception) {
                logger.error("Unable to create setup account: " + exception.getMessage());
            }
        }
    }
}
