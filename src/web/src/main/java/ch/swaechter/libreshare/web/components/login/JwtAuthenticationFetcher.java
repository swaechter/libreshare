package ch.swaechter.libreshare.web.components.login;

import ch.swaechter.libreshare.web.components.account.AccountService;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Optional;

/**
 * Intercept HTTP requests and map a potential JWT authentication to the Micronaut authentication system.
 *
 * @author Simon Wächter
 */
@Singleton
public class JwtAuthenticationFetcher implements AuthenticationFetcher {

    /**
     * Prefix of the Authorization HTTP header value
     */
    private final static String BEARER_PREFIX = "Bearer ";

    /**
     * Account service to handle the JWT tokens.
     */
    private final AccountService accountService;

    /**
     * Create a new JWT HTTP request interceptor.
     *
     * @param accountService Account service to handle the JWT tokens
     */
    public JwtAuthenticationFetcher(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Intercept a HTTP request to find a potential JWT token. If valid, the JWT token is read and added to the
     * Micronaut authentication system.
     *
     * @param request Request with a potential JWT token
     * @return Potential authentication
     */
    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        Optional<String> optionalToken = request.getHeaders().getAuthorization();
        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            if (token.startsWith(BEARER_PREFIX)) {
                token = token.substring(BEARER_PREFIX.length());
                if (accountService.isValidToken(token)) {
                    String subject = accountService.getSubject(token);
                    return Flux.just(new JwtAuthentication(subject));
                }
            }
        }
        return Publishers.empty();
    }
}
