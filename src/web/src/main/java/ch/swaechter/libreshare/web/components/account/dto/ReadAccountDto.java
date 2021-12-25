package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * Provide the ability to read an existing account.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema that represents an existing account")
public record ReadAccountDto(

    @Schema(description = "Unique ID of the account")
    UUID id,

    @Schema(description = "Unique user name of the account that is used for logging in")
    String username,

    @Schema(description = "Email of the address. Multiple accounts can share the same email address")
    String emailAddress
) {
}
