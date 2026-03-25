package fr.bookswap.book;

import fr.bookswap.auth.UserRepository;
import fr.bookswap.book.dto.BookRequest;
import fr.bookswap.book.dto.BookResponse;
import fr.bookswap.entity.Author;
import fr.bookswap.entity.Book;
import fr.bookswap.entity.Genre;
import fr.bookswap.entity.User;
import fr.bookswap.review.ReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.OptionalDouble;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository bookRepository;

    @Inject
    AuthorRepository authorRepository;

    @Inject
    GenreRepository genreRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    ReviewRepository reviewRepository;

    public List<BookResponse> listBooks(String title, String isbn, Long authorId, Long genreId) {
        List<Book> books;

        if (isbn != null) {
            Book book = bookRepository.findByIsbn(isbn);
            books = book != null ? List.of(book) : List.of();
        } else if (title != null) {
            books = bookRepository.findByTitle(title);
        } else {
            books = bookRepository.listAll();
        }

        if (authorId != null) {
            books = books.stream()
                    .filter(b -> b.authors.stream().anyMatch(a -> a.id.equals(authorId)))
                    .toList();
        }
        if (genreId != null) {
            books = books.stream()
                    .filter(b -> b.genres.stream().anyMatch(g -> g.id.equals(genreId)))
                    .toList();
        }

        return books.stream()
                .map(b -> BookResponse.from(b, getAverageRating(b)))
                .toList();
    }

    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }
        return BookResponse.from(book, getAverageRating(book));
    }

    @Transactional
    public BookResponse create(BookRequest request, String username) {
        User user = userRepository.findByUsername(username);

        Book book = new Book();
        book.isbn = request.getIsbn();
        book.title = request.getTitle();
        book.description = request.getDescription();
        book.publicationYear = request.getPublicationYear();
        book.coverUrl = request.getCoverUrl();
        book.createdBy = user;

        if (request.getAuthorIds() != null) {
            book.authors = request.getAuthorIds().stream()
                    .map(id -> authorRepository.findById(id))
                    .filter(a -> a != null)
                    .toList();
        }
        if (request.getGenreIds() != null) {
            book.genres = request.getGenreIds().stream()
                    .map(id -> genreRepository.findById(id))
                    .filter(g -> g != null)
                    .toList();
        }

        bookRepository.persist(book);
        return BookResponse.from(book, null);
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request, String username, boolean isAdmin) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }
        if (!isAdmin && !book.createdBy.username.equals(username)) {
            throw new ForbiddenException("Accès refusé");
        }

        book.isbn = request.getIsbn();
        book.title = request.getTitle();
        book.description = request.getDescription();
        book.publicationYear = request.getPublicationYear();
        book.coverUrl = request.getCoverUrl();

        if (request.getAuthorIds() != null) {
            book.authors = request.getAuthorIds().stream()
                    .map(aid -> authorRepository.findById(aid))
                    .filter(a -> a != null)
                    .toList();
        }
        if (request.getGenreIds() != null) {
            book.genres = request.getGenreIds().stream()
                    .map(gid -> genreRepository.findById(gid))
                    .filter(g -> g != null)
                    .toList();
        }

        return BookResponse.from(book, getAverageRating(book));
    }

    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }
        bookRepository.delete(book);
    }

    public List<Author> listAuthors() {
        return authorRepository.listAll();
    }

    public Author getAuthorById(Long id) {
        Author author = authorRepository.findById(id);
        if (author == null) {
            throw new NotFoundException("Auteur introuvable");
        }
        return author;
    }

    @Transactional
    public Author createAuthor(Author author) {
        authorRepository.persist(author);
        return author;
    }

    private Double getAverageRating(Book book) {
        List<fr.bookswap.entity.Review> reviews = reviewRepository.findByBook(book);
        OptionalDouble avg = reviews.stream().mapToInt(r -> r.rating).average();
        return avg.isPresent() ? avg.getAsDouble() : null;
    }
}