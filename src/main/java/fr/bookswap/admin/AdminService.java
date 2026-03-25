package fr.bookswap.admin;

import fr.bookswap.auth.UserRepository;
import fr.bookswap.entity.Review;
import fr.bookswap.entity.User;
import fr.bookswap.review.ReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class AdminService {

    @Inject
    UserRepository userRepository;

    @Inject
    ReviewRepository reviewRepository;

    public List<User> listUsers() {
        return userRepository.listAll();
    }

    @Transactional
    public User suspendUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable");
        }
        user.active = false;
        return user;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable");
        }
        userRepository.delete(user);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id);
        if (review == null) {
            throw new NotFoundException("Avis introuvable");
        }
        reviewRepository.delete(review);
    }
}