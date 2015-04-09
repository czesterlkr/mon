package mon.repository;

import mon.domain.KindOfHoliday;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KindOfHoliday entity.
 */
public interface KindOfHolidayRepository extends JpaRepository<KindOfHoliday,Long> {

}
