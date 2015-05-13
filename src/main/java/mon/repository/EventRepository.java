package mon.repository;

import mon.domain.Event;
import mon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kalipiec on 2015-04-14.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserAndEndAtIsNull(User user);

    List<Event> findByUserLogin(String login);

    Event findOneByUserAndEndAtIsNull(User user);
}
