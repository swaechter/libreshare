package ch.swaechter.libreshare.web.configuration.exceptionhandling;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ServerException.class, ExceptionHandler.class})
public class ServerExceptionHandler implements ExceptionHandler<ServerException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest httpRequest, ServerException exception) {
        return HttpResponse.serverError(new JsonError(exception.getMessage()));
    }
}
