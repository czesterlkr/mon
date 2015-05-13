package mon.web.rest;

import com.codahale.metrics.annotation.Timed;
import mon.domain.Employee;
import mon.domain.Holiday;
import mon.domain.Project;
import mon.domain.User;
import mon.repository.ProjectRepository;
import mon.repository.UserRepository;
import mon.security.AuthoritiesConstants;
import mon.security.SecurityUtils;
import mon.service.UserService;
import mon.web.rest.dto.UserDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private ProjectRepository projectRepository;

    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return userRepository.findAll();
    }

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public User getUser(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to get User : {}", login);
        User user = userRepository.findOneByLogin(login);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }

    /**
     * PUT  /users -> Updates an existing user by login.
     */
    @RequestMapping(value = "/updateUser",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO) {

        User userHavingThisLogin = userRepository.findOneByLogin(userDTO.getLogin());
//        if (userHavingThisLogin != null && !userHavingThisLogin.getLogin().equals(SecurityUtils.getCurrentLogin())) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        Project project = null;
        if (userDTO.getEmployeeDTO().getProject() != null) {
            project = projectRepository.findOne(userDTO.getEmployeeDTO().getProject().getId());
        }
        if (userHavingThisLogin != null) {
            userService.updateSignleUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getEmployeeDTO().getSalary(), userHavingThisLogin, project);
        }
        return ResponseEntity.ok().build();
    }
    /**
     * PUT  /users -> Updates an existing user by login.
     *//*
    @RequestMapping(value = "/updateUser/{login}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> update(@PathVariable String login, HttpServletRequest request) {

        User userFromDatabase = userRepository.findOneByLogin(login);

        String fn = request.getParameter("firstName");
        String ln = request.getParameter("lastName");
//        String employee1 = (Employee) request.getParameter("employee");
        BigDecimal salary =  new BigDecimal("0");;
        *//*if (employee1.getSalary() != null)
            salary = employee1.getSalary();
        else
            salary = new BigDecimal("0");*//*

        userFromDatabase.setFirstName(fn);
        userFromDatabase.setLastName(ln);
        Employee e = userFromDatabase.getEmployee();
//        pe przyjmuje wartosc null
        e.setSalary(salary);

        log.debug("REST request to update User : {}", userFromDatabase);
        userRepository.save(userFromDatabase);

        return ResponseEntity.ok().build();
    }*/
}
