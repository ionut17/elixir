package app.service.user;

import app.model.dto.UserDto;
import app.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    UserDto findByEmail(String email);

}
