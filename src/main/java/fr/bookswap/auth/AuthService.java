package fr.bookswap.auth;

import fr.bookswap.auth.dto.LoginRequest;
import fr.bookswap.auth.dto.RegisterRequest;
import fr.bookswap.auth.dto.TokenResponse;
import fr.bookswap.auth.dto.UpdateProfileRequest;
import fr.bookswap.entity.User;
import fr.bookswap.security.JwtService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtService jwtService;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà pris");
        }
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }

        User user = new User();
        user.username = request.getUsername();
        user.email = request.getEmail();
        user.passwordHash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.roles = Set.of("USER");
        userRepository.persist(user);

        String token = jwtService.generateToken(user);
        return new TokenResponse(token, "Bearer", jwtService.getExpirationSeconds());
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null || !BCrypt.checkpw(request.getPassword(), user.passwordHash)) {
            throw new BadRequestException("Identifiants invalides");
        }
        if (!user.active) {
            throw new BadRequestException("Ce compte est suspendu");
        }

        String token = jwtService.generateToken(user);
        return new TokenResponse(token, "Bearer", jwtService.getExpirationSeconds());
    }

    public User getProfile(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable");
        }
        return user;
    }

    @Transactional
    public User updateProfile(String username, UpdateProfileRequest request) {
        User user = getProfile(username);

        if (request.getUsername() != null && !request.getUsername().equals(username)) {
            if (userRepository.findByUsername(request.getUsername()) != null) {
                throw new BadRequestException("Ce nom d'utilisateur est déjà pris");
            }
            user.username = request.getUsername();
        }
        if (request.getEmail() != null) {
            if (userRepository.findByEmail(request.getEmail()) != null) {
                throw new BadRequestException("Cet email est déjà utilisé");
            }
            user.email = request.getEmail();
        }
        return user;
    }
}