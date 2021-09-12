package ch.swaechter.libreshare.web.configuration.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ServerException.class);

    public ServerException(String message) {
        super(message);
        logger.error("Server error: " + message);
    }

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
        logger.error("Server error: " + message);
    }
}
