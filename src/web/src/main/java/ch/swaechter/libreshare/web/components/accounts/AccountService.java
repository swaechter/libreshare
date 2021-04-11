package ch.swaechter.libreshare.web.components.accounts;

import io.micronaut.context.annotation.Context;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.ArrayList;

@Context
public class AccountService implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        if (username != null && username.equals("username") && password != null && password.equals("password")) {
            return Flowable.just(new UserDetails(username, new ArrayList<>()));
        } else {
            return Flowable.empty();
        }
    }
}
