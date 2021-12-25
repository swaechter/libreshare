package ch.swaechter.libreshare.web.client;

import ch.swaechter.libreshare.web.components.account.dto.AuthenticationDto;
import ch.swaechter.libreshare.web.components.account.dto.ReadAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.TokenDto;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;

import java.util.List;

/**
 * HTTP client to interact with the backend.
 *
 * @author Simon Wächter
 */
@Singleton
@Client("${libreshare.client.url}")
public interface LibreShareClient {

    /**
     * Login to get a new JWT token.
     *
     * @param authenticationDto Username and password
     * @return New JWT token
     */
    @Post("/api/login")
    TokenDto login(@Body AuthenticationDto authenticationDto);

    /**
     * Get the accounts.
     *
     * @param authorization JWT token
     * @return All accounts
     */
    @Get("/api/accounts")
    List<ReadAccountDto> getAccounts(@Header(name = "Authorization") String authorization);
}
