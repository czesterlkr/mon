package mon.repository;

import mon.domain.StatusOfHoliday;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StatusOfHoliday entity.
 */
public interface StatusOfHolidayRepository extends JpaRepository<StatusOfHoliday,Long> {

}
