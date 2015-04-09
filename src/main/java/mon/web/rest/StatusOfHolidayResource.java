package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import mon.domain.StatusOfHoliday;
import mon.repository.StatusOfHolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing StatusOfHoliday.
 */
@RestController
@RequestMapping("/api")
public class StatusOfHolidayResource {

    private final Logger log = LoggerFactory.getLogger(StatusOfHolidayResource.class);

    @Inject
    private StatusOfHolidayRepository statusOfHolidayRepository;

    /**
     * POST  /statusOfHolidays -> Create a new statusOfHoliday.
     */
    @RequestMapping(value = "/statusOfHolidays",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody StatusOfHoliday statusOfHoliday) throws URISyntaxException {
        log.debug("REST request to save StatusOfHoliday : {}", statusOfHoliday);
        if (statusOfHoliday.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statusOfHoliday cannot already have an ID").build();
        }
        statusOfHolidayRepository.save(statusOfHoliday);
        return ResponseEntity.created(new URI("/api/statusOfHolidays/" + statusOfHoliday.getId())).build();
    }

    /**
     * PUT  /statusOfHolidays -> Updates an existing statusOfHoliday.
     */
    @RequestMapping(value = "/statusOfHolidays",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody StatusOfHoliday statusOfHoliday) throws URISyntaxException {
        log.debug("REST request to update StatusOfHoliday : {}", statusOfHoliday);
        if (statusOfHoliday.getId() == null) {
            return create(statusOfHoliday);
        }
        statusOfHolidayRepository.save(statusOfHoliday);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /statusOfHolidays -> get all the statusOfHolidays.
     */
    @RequestMapping(value = "/statusOfHolidays",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StatusOfHoliday> getAll() {
        log.debug("REST request to get all StatusOfHolidays");
        return statusOfHolidayRepository.findAll();
    }

    /**
     * GET  /statusOfHolidays/:id -> get the "id" statusOfHoliday.
     */
    @RequestMapping(value = "/statusOfHolidays/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatusOfHoliday> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get StatusOfHoliday : {}", id);
        StatusOfHoliday statusOfHoliday = statusOfHolidayRepository.findOne(id);
        if (statusOfHoliday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statusOfHoliday, HttpStatus.OK);
    }

    /**
     * DELETE  /statusOfHolidays/:id -> delete the "id" statusOfHoliday.
     */
    @RequestMapping(value = "/statusOfHolidays/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete StatusOfHoliday : {}", id);
        statusOfHolidayRepository.delete(id);
    }
}
