package app.service;

import app.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {

    public User findByEmail(String email);

}
