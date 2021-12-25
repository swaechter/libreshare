package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Provide the ability to update an existing account.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema to update an existing account")
public record UpdateAccountDto(

    @NotBlank
    @Schema(description = "Unique user name of the account that is used for logging in")
    String username,

    @Email
    @NotBlank
    @Schema(description = "Email of the address. Multiple accounts can share the same email address") String emailAddress
) {
}
