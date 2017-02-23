package app.service.user;

import app.model.LoginCredentials;
import app.model.dto.UserDto;
import app.model.user.User;
import app.repository.AdminRepository;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.UserRepository;
import app.service.AuthDetailsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("userService")
public class UserServiceImpl implements UserService {

    private static final String[] SUPPORTED_TYPES = new String[]{"student", "lecturer", "admin"};

    //Main repository
    @Autowired
    private UserRepository users;

    //Secondary repositories
    @Autowired
    private StudentRepository students;
    @Autowired
    private LecturerRepository lecturers;
    @Autowired
    private AdminRepository admins;

    //Other dependencies
    @Autowired
    private AuthDetailsService authDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Logger logger;

    public UserServiceImpl() {
    }

    @Override
    public List<UserDto> findAll() {
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(users.findAll(), listType);
    }

    @Override
    public Page<UserDto> findAllByPage(int page){
        Type listType = new TypeToken<Page<UserDto>>() {}.getType();
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
            case "lecturer":
                return null;
            case "admin":
                return modelMapper.map(users.findAll(new PageRequest(page, 10)), listType);
        }
        return modelMapper.map(users.findAll(new PageRequest(page, 10)), listType);
    }

    @Override
    public UserDto findByEmail(String email) {
        return modelMapper.map(users.findByEmail(email), UserDto.class);
    }

    @Override
    public Page<UserDto> searchByPage(String query, int page) {
        Type listType = new TypeToken<Page<UserDto>>() {}.getType();
        return modelMapper.map(users.findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(query, query, query, new PageRequest(page, 10)), listType);
    }


    /**
     * Authentication Login
     */
    public User verifyCredentials(LoginCredentials credentials) {
        User user = users.findByEmail(credentials.getEmail());
        String encoded = passwordEncoder.encode(credentials.getPassword());
        if (user!=null){
            if (passwordEncoder.matches(credentials.getPassword(), user.getPassword())){
                return user;
            }
        }
        return null;
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
            throw new UsernameNotFoundException("IUser not found");
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
