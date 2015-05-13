package mon.service.util;

import com.google.common.base.Function;
import mon.domain.Event;
import mon.web.rest.dto.EventDTO;

/**
 * Created by kalipiec on 2015-05-12.
 */
public class EventFunctional {

    public static Function<Event, EventDTO> toEventDTO() {
        return new Function<Event, EventDTO>() {
            @Override
            public EventDTO apply(Event input) {
                return EventDTO.fromEvent(input);
            }
        };
    }
}
