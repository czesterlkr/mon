package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import mon.domain.User;
import mon.repository.UserRepository;
import mon.security.AuthoritiesConstants;
import mon.web.rest.dto.SimpleUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
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

    /**
     * GET  /users -> get all users without face activation.
     */
    @RequestMapping(value = "/usersWithoutFaceActivation",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SimpleUserDTO> getAllUsersWithoutFaceActivation() {
        log.debug("REST WebService request to get all Users without face activation");
        List<User> users = userRepository.findAllByEmployeeFace(true);
        List<SimpleUserDTO> simpleUsersDTO = Lists.newArrayList();

        for (User user : users) {
            simpleUsersDTO.add(SimpleUserDTO.fromUser(user));
        }

        return simpleUsersDTO;
    }

    /**
     * GET  /users -> get all users
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SimpleUserDTO> getAll() {
        log.debug("REST WebService request to get all Users F");
        List<User> users = userRepository.findAll();
        List<SimpleUserDTO> simpleUsersDTO = Lists.newArrayList();

        for (User user : users) {
            simpleUsersDTO.add(SimpleUserDTO.fromUser(user));
        }

        return simpleUsersDTO;
    }

    /**
     * GET  /userEntrance/:login -> user entrance by "login"
     */
    @RequestMapping(value = "/usersEntrance/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<String> entranceUser(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to User Entrance: {}", login);
        User user = userRepository.findOneByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
    }

    /**
     * GET  /userExit/:login -> user exit by "login"
     */
    @RequestMapping(value = "/usersExit/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<String> exitUser(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to User Exit: {}", login);
        User user = userRepository.findOneByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
    }
}
