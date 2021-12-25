package ch.swaechter.libreshare.web.components.login;

import ch.swaechter.libreshare.web.client.LibreshareClient;
import ch.swaechter.libreshare.web.components.account.dto.AuthenticationDto;
import ch.swaechter.libreshare.web.components.account.dto.ReadAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.TokenDto;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the login and authentication functionality.
 *
 * @author Simon Wächter
 */
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    /**
     * HTTP client used for the backend communication.
     */
    @Inject
    private LibreshareClient client;

    /**
     * Test the login and authentication functionality.
     */
    @Test
    public void testLogin() {
        // Trigger an authenticated endpoint with an invalid token
        assertThrows(HttpClientResponseException.class, () -> client.getAccounts("INVALID"), "Unauthorized");

        // Trigger the unauthenticated login endpoint with invalid credentials
        assertThrows(HttpClientResponseException.class, () -> client.login(new AuthenticationDto("invalid", "invalid")), "Unauthorized");

        // Trigger the unauthenticated login endpoint with proper credentials
        TokenDto tokenDto = client.login(new AuthenticationDto("admin", "123456aAbB"));
        assertNotNull(tokenDto);

        // Use the token to trigger an authenticated endpoint
        List<ReadAccountDto> accounts = client.getAccounts("Bearer " + tokenDto.token());
        assertEquals(0, accounts.size());
    }
}
