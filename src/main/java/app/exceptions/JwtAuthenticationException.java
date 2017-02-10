package app.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Ionut on 06-Feb-17.
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
