package ch.swaechter.libreshare.web.components.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Represent a user-centric event that happened in the system, e.g. someone uploaded a new file or deleted an existing
 * directory etc.
 *
 * @author Simon Wächter
 */
@Introspected
@Schema(description = "Schema that represents a user-centric event")
public record EventDto(

    @Schema(description = "Date time when the event happened")
    Instant date,

    @Schema(description = "Message of the event")
    String message
) {
}
