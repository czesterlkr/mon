package mon.service;

import mon.domain.Event;
import mon.domain.User;
import mon.repository.EventRepository;
import mon.service.enums.EntranceStatus;
import mon.service.enums.ExitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by kalipiec on 2015-04-14.
 */
@Service
@Transactional
public class UserEventService {

    private final Logger log = LoggerFactory.getLogger(UserEventService.class);

    @Inject
    private EventRepository eventRepository;

    public EntranceStatus entrance(User user) {
        List<Event> events = eventRepository.findByUserAndEndAtIsNull(user);

        if (!events.isEmpty()) {
            return EntranceStatus.ALREADY_IN;
        }

        Event event = Event.fromNow();
        event.setUser(user);
        eventRepository.save(event);

        return EntranceStatus.OK;
    }

    public ExitStatus exit(User user) {
        Event event = eventRepository.findOneByUserAndEndAtIsNull(user);
        if (event == null) {
            return ExitStatus.NOT_IN;
        }
        event.end();
        eventRepository.save(event);
        return ExitStatus.OK;
    }

}
