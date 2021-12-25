package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * Provide the ability to obtain a JWT token.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema to obtain a JWT token")
public record AuthenticationDto(

    @NotBlank
    @Schema(description = "Name of the account", required = true)
    String username,

    @NotBlank
    @Schema(description = "Password of the account", required = true)
    String password
) {
}
