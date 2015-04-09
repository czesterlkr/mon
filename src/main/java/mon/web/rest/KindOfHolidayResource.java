package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import mon.domain.KindOfHoliday;
import mon.repository.KindOfHolidayRepository;
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
 * REST controller for managing KindOfHoliday.
 */
@RestController
@RequestMapping("/api")
public class KindOfHolidayResource {

    private final Logger log = LoggerFactory.getLogger(KindOfHolidayResource.class);

    @Inject
    private KindOfHolidayRepository kindOfHolidayRepository;

    /**
     * POST  /kindOfHolidays -> Create a new kindOfHoliday.
     */
    @RequestMapping(value = "/kindOfHolidays",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody KindOfHoliday kindOfHoliday) throws URISyntaxException {
        log.debug("REST request to save KindOfHoliday : {}", kindOfHoliday);
        if (kindOfHoliday.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new kindOfHoliday cannot already have an ID").build();
        }
        kindOfHolidayRepository.save(kindOfHoliday);
        return ResponseEntity.created(new URI("/api/kindOfHolidays/" + kindOfHoliday.getId())).build();
    }

    /**
     * PUT  /kindOfHolidays -> Updates an existing kindOfHoliday.
     */
    @RequestMapping(value = "/kindOfHolidays",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody KindOfHoliday kindOfHoliday) throws URISyntaxException {
        log.debug("REST request to update KindOfHoliday : {}", kindOfHoliday);
        if (kindOfHoliday.getId() == null) {
            return create(kindOfHoliday);
        }
        kindOfHolidayRepository.save(kindOfHoliday);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /kindOfHolidays -> get all the kindOfHolidays.
     */
    @RequestMapping(value = "/kindOfHolidays",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KindOfHoliday> getAll() {
        log.debug("REST request to get all KindOfHolidays");
        return kindOfHolidayRepository.findAll();
    }

    /**
     * GET  /kindOfHolidays/:id -> get the "id" kindOfHoliday.
     */
    @RequestMapping(value = "/kindOfHolidays/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KindOfHoliday> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get KindOfHoliday : {}", id);
        KindOfHoliday kindOfHoliday = kindOfHolidayRepository.findOne(id);
        if (kindOfHoliday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kindOfHoliday, HttpStatus.OK);
    }

    /**
     * DELETE  /kindOfHolidays/:id -> delete the "id" kindOfHoliday.
     */
    @RequestMapping(value = "/kindOfHolidays/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete KindOfHoliday : {}", id);
        kindOfHolidayRepository.delete(id);
    }
}
