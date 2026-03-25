package fr.bookswap.review;

import fr.bookswap.auth.UserRepository;
import fr.bookswap.book.BookRepository;
import fr.bookswap.entity.Book;
import fr.bookswap.entity.Review;
import fr.bookswap.entity.User;
import fr.bookswap.review.dto.ReviewRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ReviewService {

    @Inject
    ReviewRepository reviewRepository;

    @Inject
    BookRepository bookRepository;

    @Inject
    UserRepository userRepository;

    public List<Review> listByBook(Long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }
        return reviewRepository.findByBook(book);
    }

    @Transactional
    public Review create(Long bookId, ReviewRequest request, String username) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }

        User user = userRepository.findByUsername(username);
        if (reviewRepository.findByUserAndBook(user, book) != null) {
            throw new BadRequestException("Vous avez déjà laissé un avis sur ce livre");
        }

        Review review = new Review();
        review.user = user;
        review.book = book;
        review.rating = request.getRating();
        review.comment = request.getComment();
        reviewRepository.persist(review);

        return review;
    }

    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId);
        if (review == null) {
            throw new NotFoundException("Avis introuvable");
        }
        reviewRepository.delete(review);
    }
}