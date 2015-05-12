package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import mon.domain.Event;
import mon.repository.EventRepository;
import mon.security.SecurityUtils;
import mon.service.util.EventFunctional;
import mon.web.rest.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
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
}
