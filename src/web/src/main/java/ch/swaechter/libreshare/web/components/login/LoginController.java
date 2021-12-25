package ch.swaechter.libreshare.web.components.login;

import ch.swaechter.libreshare.web.components.account.AccountService;
import ch.swaechter.libreshare.web.components.account.dto.AuthenticationDto;
import ch.swaechter.libreshare.web.components.account.dto.TokenDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Login endpoint to get a JWT token.
 *
 * @author Simon Wächter
 */
@Controller("/api")
@SecurityRequirement(name = "default")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
public class LoginController {

    /**
     * Account service to handle the JWT tokens.
     */
    private final AccountService accountService;

    /**
     * Create a new login controller.
     *
     * @param accountService Account service to handle the JWT tokens
     */
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Provide a username and password to get a JWT token.
     *
     * @param authenticationDto Username and password
     * @return JWT token
     */
    @Post("/login")
    @Tag(name = "Login")
    @Operation(operationId = "login")
    @ApiResponse(responseCode = "200", description = "JWT token")
    @ApiResponse(responseCode = "401", description = "Invalid login")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<TokenDto> login(@Body @Valid AuthenticationDto authenticationDto) {
        Optional<TokenDto> optionalTokenDto = accountService.login(authenticationDto);
        if (optionalTokenDto.isPresent()) {
            return HttpResponse.ok(optionalTokenDto.get());
        } else {
            return HttpResponse.unauthorized();
        }
    }
}
