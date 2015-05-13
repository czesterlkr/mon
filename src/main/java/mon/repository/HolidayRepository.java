package mon.repository;

import mon.domain.Holiday;
import mon.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Holiday entity.
 */
public interface HolidayRepository extends JpaRepository<Holiday,Long> {

    Page<Holiday> findAllByUserLogin(String username, Pageable pageable);

}
