package app.service;

import app.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends BaseService<User>, UserDetailsService {

    User findByEmail(String email);

    List<User> findByType(String type);

}
