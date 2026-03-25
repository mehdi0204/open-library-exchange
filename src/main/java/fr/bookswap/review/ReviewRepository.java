package fr.bookswap.review;

import fr.bookswap.entity.Book;
import fr.bookswap.entity.Review;
import fr.bookswap.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ReviewRepository implements PanacheRepository<Review> {

    public List<Review> findByBook(Book book) {
        return find("book", book).list();
    }

    public Review findByUserAndBook(User user, Book book) {
        return find("user = ?1 and book = ?2", user, book).firstResult();
    }
}