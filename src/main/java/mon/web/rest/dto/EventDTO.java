package mon.web.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mon.domain.Event;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * Created by kalipiec on 2015-05-12.
 */
@Getter
@RequiredArgsConstructor
public class EventDTO {

    private final Long id;
    private final Long start;
    private final Long end;
    private final String title;

    public static EventDTO fromEvent(Event event) {
        Long id = event.getId();
        Long start = event.getStartAt().toDateTime().getMillis();
        Long end = event.getEndAt().toDateTime().getMillis();

        Period period = new Period(event.getStartAt(), event.getEndAt());
        String title = String.format("%dh %dm", period.getHours(), period.getMinutes());


        return new EventDTO(id, start, end, title);
    }
}
