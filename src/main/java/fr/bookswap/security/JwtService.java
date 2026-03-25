package fr.bookswap.security;

import fr.bookswap.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.time.Instant;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "jwt.expiration.seconds", defaultValue = "3600")
    long expirationSeconds;

    public String generateToken(User user) {
        return Jwt.issuer(issuer)
                .upn(user.username)
                .groups(user.roles)
                .expiresAt(Instant.now().plusSeconds(expirationSeconds))
                .sign();
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}