package mon.service;

import com.google.common.collect.Lists;
import mon.domain.Event;
import mon.domain.User;
import mon.repository.EventRepository;
import mon.repository.UserRepository;
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

    @Inject
    private UserRepository userRepository;

    public List<User> getActivatedUsersAndNotIn() {
        List<User> users = userRepository.findAllByEmployeeFace(true);
        List<User> usersNotIn = Lists.newArrayList();

        for (User user : users) {
            List<Event> events = eventRepository.findByUserAndEndAtIsNull(user);
            if (events.isEmpty()) {
                usersNotIn.add(user);
            }
        }

        return usersNotIn;
    }

    public List<User> getActivatedUsersAndIn() {
        List<User> users = userRepository.findAllByEmployeeFace(true);
        List<User> usersNotIn = Lists.newArrayList();

        for (User user : users) {
            List<Event> events = eventRepository.findByUserAndEndAtIsNull(user);
            if (!events.isEmpty()) {
                usersNotIn.add(user);
            }
        }

        return usersNotIn;
    }

    public EntranceStatus entrance(User user) {
        user.getEmployee().setFace(true);
        List<Event> events = eventRepository.findByUserAndEndAtIsNull(user);

        if (!events.isEmpty()) {
            return EntranceStatus.ALREADY_IN;
        }

        Event event = Event.fromNow();
        event.setUser(user);
        eventRepository.save(event);
        userRepository.save(user);

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
