package ch.swaechter.libreshare.web.components.event;

import ch.swaechter.libreshare.web.components.event.dto.EventDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Provide the REST functionality for the event endpoints.
 *
 * @author Simon Wächter
 */
@Controller("/api")
@SecurityRequirement(name = "default")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
public class EventController {

    /**
     * Event service to read the events
     */
    private final EventService eventService;

    /**
     * Create a new event REST controller.
     *
     * @param eventService Event service to read the vents
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Get the latest event. Older events are evicted after a given number of messages.
     *
     * @return List of current events
     */
    @Get("/events")
    @Tag(name = "Events")
    public List<EventDto> getEvents() {
        return eventService.getEvents();
    }
}
