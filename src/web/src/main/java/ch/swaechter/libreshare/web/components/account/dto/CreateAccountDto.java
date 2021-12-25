package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Provide the ability to create a new account.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema to create a new account")
public record CreateAccountDto(

    @NotBlank
    @Schema(description = "Unique username of the account that is used for logging in")
    String username,

    @Email
    @NotBlank
    @Schema(description = "Email of the address. Multiple accounts can share the same email address")
    String emailAddress,

    @NotBlank
    @Schema(description = "New plaintext password")
    String plaintextPassword
) {
}
