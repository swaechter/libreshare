package ch.swaechter.libreshare.web.configuration.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server exception that logs the exception message.
 *
 * @author Simon Wächter
 */
public class ServerException extends Exception {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerException.class);

    /**
     * Create and throw an exception with a message.
     *
     * @param message Exception message
     */
    public ServerException(String message) {
        super(message);
        logger.error("Server error: " + message);
    }

    /**
     * Create and rethrow a thrown exception with a new message.
     *
     * @param message   New exception message
     * @param throwable Thrown exception
     */
    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
        logger.error("Server error: " + message);
    }
}
