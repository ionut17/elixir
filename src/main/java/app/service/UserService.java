package app.service;

import app.model.User;
import app.model.UserDto;
import app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> findAllUsers();

    public User findById(long id);

    public User addUser(UserDto user);

    public String removeUser(Long id);

}
