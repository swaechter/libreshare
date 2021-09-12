package ch.swaechter.libreshare.web.components.event;

import ch.swaechter.libreshare.web.components.event.dto.EventDto;
import ch.swaechter.libreshare.web.utils.collections.FixedArrayList;
import io.micronaut.context.annotation.Context;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service to manage the events.
 *
 * @author Simon Wächter
 */
@Context
public class EventService {

    /**
     * Default number of events before older events get evicted.
     */
    private static final Integer EVENT_BUFFER_SIZE = 100;

    /**
     * Fixed list that contains the event. If the list is full, older events will get evicted.
     */
    private final FixedArrayList<EventDto> events;

    /**
     * Create a new event service.
     */
    public EventService() {
        this.events = new FixedArrayList<>(EVENT_BUFFER_SIZE);
    }

    /**
     * Add a new event message.
     *
     * @param message Event message
     * @return Created event
     */
    public EventDto addEvent(String message) {
        EventDto eventDto = new EventDto(Instant.now(), message);
        events.add(eventDto);
        return eventDto;
    }

    /**
     * Get the current events.
     *
     * @return Current events
     */
    public List<EventDto> getEvents() {
        List<EventDto> copiedEvents = new ArrayList<>(events);
        Collections.reverse(copiedEvents);
        return copiedEvents;
    }
}
