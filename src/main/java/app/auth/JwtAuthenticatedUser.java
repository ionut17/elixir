package app.auth;

import app.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Ionut on 06-Feb-17.
 */
public class JwtAuthenticatedUser implements Authentication {

    private final User user;
    private final Map<String, GrantedAuthority> authorityMap;

    public JwtAuthenticatedUser(User user) {
        this.user = user;
        this.authorityMap = new HashMap<>();
        authorityMap.put("user", new SimpleGrantedAuthority("ROLE_USER"));
        authorityMap.put("student", new SimpleGrantedAuthority("ROLE_STUDENT"));
        authorityMap.put("lecturer", new SimpleGrantedAuthority("ROLE_LECTURER"));
        authorityMap.put("admin", new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(authorityMap.get("user"));
        grantedAuthorities.add(authorityMap.get(user.getType().toLowerCase())); //student, lecturer or admin
        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return user.getEmail();
    }

}
