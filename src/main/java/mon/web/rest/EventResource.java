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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.sql.Timestamp;
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
     * POST  /events
     */
    @RequestMapping(value = "/event/update",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateEvent(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "start") Long start,
                                            @RequestParam(value = "end") Long end)
            throws URISyntaxException {
        Event eventToUpdate = eventRepository.findOneById(id);

        Timestamp timestampStart = new Timestamp(start);
        Timestamp timestampEnd = new Timestamp(end);

//
//        CONVERT TO LOCALDATATIME
//

        return ResponseEntity.ok().build();
    }
}
