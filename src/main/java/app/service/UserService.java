package app.service;

import app.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends BaseService<User>, UserDetailsService {

    public User findByEmail(String email);

    public List<User> findByType(String type);

}
