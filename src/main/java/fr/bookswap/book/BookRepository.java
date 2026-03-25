package fr.bookswap.book;

import fr.bookswap.entity.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public List<Book> findByTitle(String title) {
        return find("lower(title) like lower(?1)", "%" + title + "%").list();
    }

    public Book findByIsbn(String isbn) {
        return find("isbn", isbn).firstResult();
    }
}