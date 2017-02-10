package app.service;

import app.model.user.User;
import app.repository.UserRepository;
import app.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Component("jwtService")
public class JwtService {

    private static final String ISSUER = "elixir.jwt";
    private String secretKeyValue = "secretkeygoeshere";
    private Key key = MacProvider.generateKey();

    @Autowired
    UserRepository userRepository;

    public String tokenFor(User user) {
        byte[] secretKey = secretKeyValue.getBytes();
        Date expiration = Date.from(LocalDateTime.now().plusSeconds(3600).toInstant(UTC));
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public User verifyToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return userRepository.findByEmail(claims.getBody().getSubject().toString());
    }

}
