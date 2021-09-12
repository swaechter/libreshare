package ch.swaechter.libreshare.web.configuration.exceptionhandling;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

/**
 * Handler to transform the server exception into a HTTP internal server error.
 *
 * @author Simon Wächter
 */
@Produces
@Singleton
@Requires(classes = {ServerException.class, ExceptionHandler.class})
public class ServerExceptionHandler implements ExceptionHandler<ServerException, HttpResponse<?>> {

    /**
     * Transform the thrown server exception into a HTTP internal server response.
     *
     * @param httpRequest HTTP request
     * @param exception   Thrown exception
     * @return HTTP response
     */
    @Override
    public HttpResponse<?> handle(HttpRequest httpRequest, ServerException exception) {
        return HttpResponse.serverError(new JsonError(exception.getMessage()));
    }
}
