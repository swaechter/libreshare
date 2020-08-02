package ch.swaechter.libreshare.web.configuration.exceptions;

import ch.swaechter.libreshare.web.utils.exceptions.SystemException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {SystemException.class, ExceptionHandler.class})
public class SystemExceptionHandler implements ExceptionHandler<SystemException, HttpResponse<?>> {

    private static final Logger logger = LoggerFactory.getLogger(SystemExceptionHandler.class);

    @Override
    public HttpResponse<?> handle(HttpRequest request, SystemException exception) {
        logger.error("System exception: " + exception.getMessage());
        return HttpResponse.serverError(new JsonError(exception.getMessage()));
    }
}
