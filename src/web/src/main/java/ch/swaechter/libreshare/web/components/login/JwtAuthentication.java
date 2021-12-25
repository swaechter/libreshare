package ch.swaechter.libreshare.web.components.login;

import io.micronaut.security.authentication.Authentication;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JWT authentication that maps the JWT token to a Micronaut authentication.
 *
 * @author Simon Wächter
 */
public record JwtAuthentication(String name) implements Authentication {

    /**
     * Get the attributes of the authentication.
     *
     * @return Attributes of the authentication
     */
    @Override
    public Map<String, Object> getAttributes() {
        return new LinkedHashMap<>();
    }

    /**
     * Get the subject (Account ID) of the JWT token.
     *
     * @return subject (Account ID) of the JWT token
     */
    @Override
    public String getName() {
        return name;
    }
}
