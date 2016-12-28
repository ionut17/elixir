package app.service.user;

import app.model.user.User;
import app.service.common.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findByEmail(String email);

    List<User> findByType(String type);

}
