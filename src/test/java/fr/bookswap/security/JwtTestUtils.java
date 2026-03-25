package fr.bookswap.security;

import io.smallrye.jwt.build.Jwt;

import java.util.Set;

public class JwtTestUtils {

    private static final String ISSUER = "https://echangelivre.fr";

    public static String generateToken(String username, String... roles) {
        return Jwt.issuer(ISSUER)
                .upn(username)
                .groups(Set.of(roles))
                .expiresIn(3600)
                .sign();
    }

    public static String userToken() {
        return generateToken("Otman", "USER");
    }

    public static String adminToken() {
        return generateToken("admin", "USER", "ADMIN");
    }
}
