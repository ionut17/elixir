package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserByType(@PathVariable("type") String type) {
        List<User> users = userService.findByType(type);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (users.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userService.entityExist(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.add(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    //-------------------Update a User--------------------------------------------------------

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());

        userService.update(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    //-------------------Delete a User--------------------------------------------------------

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
