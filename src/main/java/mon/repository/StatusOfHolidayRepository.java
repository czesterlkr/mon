package mon.repository;

import mon.domain.StatusOfHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the StatusOfHoliday entity.
 */
public interface StatusOfHolidayRepository extends JpaRepository<StatusOfHoliday,Long> {

    @Query("SELECT s FROM StatusOfHoliday s WHERE s.name LIKE LOWER(:name)")
    public StatusOfHoliday find(@Param("name") String name);
}
