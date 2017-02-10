package app.controller;

import app.controller.common.BaseController;
import app.exceptions.ErrorMessagesProvider;
import app.exceptions.ErrorMessagesWrapper;
import app.model.LoginCredentials;
import app.model.dto.TokenDto;
import app.model.dto.UserDto;
import app.model.user.User;
import app.service.JwtService;
import app.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ionut on 06-Feb-17.
 */

@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper modelMapper;


    //-------------------Login user and return token--------------------------------------------------------

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAllUsers(@RequestBody LoginCredentials credentials) {
        User loggedUser = userService.verifyCredentials(credentials);
        if (loggedUser == null){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.INVALID_CREDENTIALS).getErrors(), HttpStatus.BAD_REQUEST);
        }

        TokenDto responseToken = new TokenDto();
        responseToken.setUser(modelMapper.map(loggedUser, UserDto.class));
        responseToken.setToken(jwtService.tokenFor(loggedUser));
        return new ResponseEntity<>(responseToken, HttpStatus.OK); //HttpStatus.NOT_FOUND
    }

}
