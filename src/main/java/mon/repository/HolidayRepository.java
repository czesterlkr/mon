package mon.repository;

import mon.domain.Holiday;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Holiday entity.
 */
public interface HolidayRepository extends JpaRepository<Holiday,Long> {

    @Query("select holiday from Holiday holiday where holiday.holiday_user.login = ?#{principal.username}")
    List<Holiday> findAllForCurrentUser();

}
