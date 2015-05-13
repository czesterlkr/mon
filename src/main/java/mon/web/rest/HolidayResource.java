package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import mon.domain.Holiday;
import mon.domain.StatusOfHoliday;
import mon.domain.User;
import mon.repository.HolidayRepository;
import mon.repository.StatusOfHolidayRepository;
import mon.repository.UserRepository;
import mon.security.SecurityUtils;
import mon.security.UserNotActivatedException;
import mon.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Holiday.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    @Inject
    private HolidayRepository holidayRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private StatusOfHolidayRepository statusOfHolidayRepository;

    /**
     * POST  /holidays -> Create a new holiday.
     */
    @RequestMapping(value = "/holidays",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holiday);
        if (holiday.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new holiday cannot already have an ID").build();
        }
//        holiday.setUser(SecurityUtils.getCurrentUser());
        String login = SecurityUtils.getCurrentLogin();
        String lowercaseLogin = login.toLowerCase();
        User userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);

        holiday.setUser(userFromDatabase);
        StatusOfHoliday st = statusOfHolidayRepository.find("oczekiwanie");
        holiday.setHoliday_statusOfHoliday(st);

        holidayRepository.save(holiday);
        return ResponseEntity.created(new URI("/api/holidays/" + holiday.getId())).build();
    }

    /**
     * PUT  /holidays -> Updates an existing holiday.
     */
    @RequestMapping(value = "/holidays",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holiday);
        if (holiday.getId() == null) {
            return create(holiday);
        }
        holidayRepository.save(holiday);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /holidays -> get all the holidays.
     */
    @RequestMapping(value = "/holidays",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Holiday>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                @RequestParam(value = "per_page", required = false) Integer limit)
            throws URISyntaxException {
        Page<Holiday> page = holidayRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/holidays", offset, limit);
        return new ResponseEntity<List<Holiday>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /holidays -> get all the holidays.
     */
    @RequestMapping(value = "/holidaysForCurrentUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Holiday>> getAllForCurrentUser(@RequestParam(value = "page", required = false) Integer offset,
                                                              @RequestParam(value = "per_page", required = false) Integer limit)
            throws URISyntaxException {
        Page<Holiday> page = holidayRepository.findAllByUserLogin(SecurityUtils.getCurrentLogin(), PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/holidaysForCurrentUser", offset, limit);
        return new ResponseEntity<List<Holiday>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /holidays/:id -> get the "id" holiday.
     */
    @RequestMapping(value = "/holidays/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Holiday> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Holiday : {}", id);
        Holiday holiday = holidayRepository.findOne(id);
        if (holiday == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    /**
     * DELETE  /holidays/:id -> delete the "id" holiday.
     */
    @RequestMapping(value = "/holidays/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayRepository.delete(id);
    }
}
