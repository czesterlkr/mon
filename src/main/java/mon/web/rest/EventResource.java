package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import mon.domain.Event;
import mon.domain.Holiday;
import mon.repository.EventRepository;
import mon.security.SecurityUtils;
import mon.service.util.EventFunctional;
import mon.web.rest.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Holiday.
 */
@RestController
@RequestMapping("/api")
public class EventResource {


    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    @Inject
    private EventRepository eventRepository;

    /**
     * POST  /events
     */
    @RequestMapping(value = "/events",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventDTO>> getAll()
        throws URISyntaxException {
        List<Event> events = eventRepository.findByUserLogin(SecurityUtils.getCurrentLogin());
        return new ResponseEntity<List<EventDTO>>(Lists.transform(events, EventFunctional.toEventDTO()), HttpStatus.OK);

    }

    /**
     * GET  /events/:id -> get the "id" event.
     */
    @RequestMapping(value = "/events/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Event> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Event : {}", id);
        Event event = eventRepository.findOne(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    /**
     * PUT  /events -> Updates an existing event.
     */
    @RequestMapping(value = "/events",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", event);
        eventRepository.save(event);
        return ResponseEntity.ok().build();
    }
}
