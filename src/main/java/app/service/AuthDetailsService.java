package app.service;

import app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Ionut on 09-Feb-17.
 */
@Component("authDetailsService")
public class AuthDetailsService {

    @Autowired
    JwtService jwtService;

    private User authenticatedUser;

    AuthDetailsService(){
    }

    public User getAuthenticatedUser() {
        this.authenticatedUser = jwtService.verifyToken(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        return authenticatedUser;
    }
}
