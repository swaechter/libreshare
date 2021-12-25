package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * Provide the ability to change the account password.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema to change the password of an account")
public record ChangePasswordDto(

    @NotBlank
    @Schema(description = "New plaintext password of the account")
    String plaintextPassword
) {
}
