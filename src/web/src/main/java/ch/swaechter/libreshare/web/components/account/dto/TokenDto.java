package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represent a JWT token.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema that represents a JWT token")
public record TokenDto(

    @Schema(description = "JWT token", required = true)
    String token
) {
}
