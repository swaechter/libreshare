package ch.swaechter.libreshare.web.utils.exceptions;

public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable error) {
        super(message, error);
    }
}
