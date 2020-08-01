package ch.swaechter.libreshare.web.components.authentication;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.endpoints.LoginController;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;

@Validated
@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthenticationController {

    private final LoginController loginController;

    public AuthenticationController(Authenticator authenticator, LoginHandler loginHandler, ApplicationEventPublisher eventPublisher) {
        this.loginController = new LoginController(authenticator, loginHandler, eventPublisher);
    }

    @Post("/login")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccessRefreshToken.class)))
    public Single<MutableHttpResponse<?>> login(@Valid @Body UsernamePasswordCredentials usernamePasswordCredentials, HttpRequest<?> request) {
        return loginController.login(usernamePasswordCredentials, request);
    }
}
