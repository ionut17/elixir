package app.service.user;

import app.model.LoginCredentials;
import app.model.dto.UserDto;
import app.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    Page<UserDto> findAllByPage(int page);

    UserDto findByEmail(String email);

    User verifyCredentials(LoginCredentials credentials);

    Page<UserDto> searchByPage(String query, int page);

}
