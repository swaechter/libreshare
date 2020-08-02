package ch.swaechter.libreshare.web.configuration.exceptions;

import ch.swaechter.libreshare.web.utils.exceptions.ClientException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ClientException.class, ExceptionHandler.class})
public class ClientExceptionHandler implements ExceptionHandler<ClientException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, ClientException exception) {
        return HttpResponse.serverError(new JsonError(exception.getMessage()));
    }
}
