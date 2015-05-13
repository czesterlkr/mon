package mon.web.rest;

import com.google.common.collect.Lists;
import mon.domain.Event;
import mon.domain.User;
import mon.repository.UserRepository;
import mon.service.UserEventService;
import mon.service.enums.EntranceStatus;
import mon.service.enums.ExitStatus;
import mon.web.rest.dto.SimpleUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/webservice")
public class WebServiceUserResource {

    private final Logger log = LoggerFactory.getLogger(WebServiceUserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserEventService userEventService;

    /**
     * GET  /usersWithoutFaceActivation -> get all users without face activation.
     */
    @RequestMapping(value = "/usersWithoutFaceActivation",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SimpleUserDTO> getAllUsersWithoutFaceActivation() {
        log.debug("REST WebService request to get all Users without face activation");
        List<User> users = userRepository.findAllByEmployeeFace(false);
        List<SimpleUserDTO> simpleUsersDTO = Lists.newArrayList();

        for (User user : users) {
            simpleUsersDTO.add(SimpleUserDTO.fromUser(user));
        }

        return simpleUsersDTO;
    }

    /**
     * GET  /usersIn-> get all users in
     */
    @RequestMapping(value = "/usersActivatedIn",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SimpleUserDTO> getAllActivatedUsersIn() {
        log.debug("REST WebService request to get all Users without face activation");
        List<User> users = userEventService.getActivatedUsersAndIn();
        List<SimpleUserDTO> simpleUsersDTO = Lists.newArrayList();

        for (User user : users) {
            simpleUsersDTO.add(SimpleUserDTO.fromUser(user));
        }

        return simpleUsersDTO;
    }

    /**
     * GET  /users -> get all users
     */
    @RequestMapping(value = "/usersActivatedNotIn",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SimpleUserDTO> getAllActivatedUsersNotIn() {
        log.debug("REST WebService request to get all Users with  face activation");
        List<User> users = userEventService.getActivatedUsersAndNotIn();
        List<SimpleUserDTO> simpleUsersDTO = Lists.newArrayList();

        for (User user : users) {
            simpleUsersDTO.add(SimpleUserDTO.fromUser(user));
        }

        return simpleUsersDTO;
    }


    /**
     * GET  /userEntrance/:login -> user entrance by "login"
     */
    @RequestMapping(value = "/usersEntrance",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> entranceUser(@RequestParam("id") Long id, HttpServletResponse response) {
        log.debug("REST request to User Entrance: {}", id);
        User user = userRepository.findOneById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntranceStatus entranceStatus = userEventService.entrance(user);
        return new ResponseEntity<User>(user, entranceStatus.getStatus());
    }

    /**
     * GET  /userExit/:login -> user exit by "login"
     */
    @RequestMapping(value = "/usersExit",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> exitUser(@RequestParam("id") Long id, HttpServletResponse response) {
        log.debug("REST request to User Exit: {}", id);
        User user = userRepository.findOneById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ExitStatus exitStatus = userEventService.exit(user);
        return new ResponseEntity<User>(user, exitStatus.getStatus());
    }
}
