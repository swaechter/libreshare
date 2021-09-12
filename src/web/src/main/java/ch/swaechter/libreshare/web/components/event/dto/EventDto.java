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
public class EventDto {

    /**
     * Date time when the event happened.
     */
    @Schema(description = "Date time when the event happened")
    private Instant date;

    /**
     * Message of the event.
     */
    @Schema(description = "Message of the event")
    private String message;

    /**
     * Define a new event.
     *
     * @param date    Event date
     * @param message Event message
     */
    public EventDto(Instant date, String message) {
        this.date = date;
        this.message = message;
    }

    /**
     * Get the current event date.
     *
     * @return Current event date
     */
    public Instant getDate() {
        return date;
    }

    /**
     * Set the event date.
     *
     * @param date New event date
     */
    public void setDate(Instant date) {
        this.date = date;
    }

    /**
     * Get the current event message.
     *
     * @return Current event message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the new event message.
     *
     * @param message New event message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
