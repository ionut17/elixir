package app.service;

import app.model.User;
import app.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Logger logger;

    private static final String[] SUPPORTED_TYPES = new String[]{"student", "lecturer", "admin"};

    public UserServiceImpl() {

    }

    @Override
    public List<User> findAll() {
        return users.findAll();
    }

    @Override
    public User findById(long id) {
        return users.findOne(id);
    }

    @Override
    public User add(User userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//        logger.info(encodedPassword);
        return users.save(new User(userDto.getType(), encodedPassword, userDto.getFirstName(), userDto.getFirstName(), userDto.getEmail()));
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        try {
            users.delete(id);
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean entityExist(User entity) {
        return false;
    }

    @Override
    public User findByEmail(String email) {
        return users.findByEmail(email);
    }


    /**
     * UserDetailsService Implementation
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info(email);
        try {
            User user = users.findByEmail(email);
            if (user == null) {
                logger.info("User not found with the provided email");
                throw new UsernameNotFoundException("User not found");
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_USER"));

        for (String supportedType : SUPPORTED_TYPES) {
            if (user.getType().equals(supportedType)) {
                String role = "ROLE_" + supportedType.toUpperCase();
                logger.info(user.getEmail() + " " + role);
                authoritiesList.add(new SimpleGrantedAuthority(role));
            }
        }

        return authoritiesList;
    }

}
