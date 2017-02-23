package app.controller.user;

import app.controller.common.BaseController;
import app.model.Pager;
import app.model.dto.UserDto;
import app.model.user.User;
import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAllUsers(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        Map<String, Object> toReturn = new HashMap<>();
        int targetPage = page!=null? page.intValue() : 0;
        Page<UserDto> users;
        if (query!=null){
            users = userService.searchByPage(query, targetPage);
        } else{
            users = userService.findAllByPage(targetPage);
        }
        if (users==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (users.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(users);
        toReturn.put("content", users.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/users/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
