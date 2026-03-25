package fr.bookswap.library;

import fr.bookswap.auth.UserRepository;
import fr.bookswap.book.BookRepository;
import fr.bookswap.entity.Book;
import fr.bookswap.entity.User;
import fr.bookswap.entity.UserBook;
import fr.bookswap.library.dto.UserBookRequest;
import fr.bookswap.library.dto.UserBookResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class LibraryService {

    @Inject
    LibraryRepository libraryRepository;

    @Inject
    BookRepository bookRepository;

    @Inject
    UserRepository userRepository;

    public List<UserBookResponse> getMyLibrary(String username, UserBook.BookStatus status) {
        User user = userRepository.findByUsername(username);
        List<UserBook> userBooks = libraryRepository.findByUser(user);

        if (status != null) {
            userBooks = userBooks.stream()
                    .filter(ub -> ub.status == status)
                    .toList();
        }

        return userBooks.stream().map(UserBookResponse::from).toList();
    }

    public List<UserBookResponse> getPublicLibrary(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable");
        }
        return libraryRepository.findByUser(user).stream()
                .map(UserBookResponse::from)
                .toList();
    }

    @Transactional
    public UserBookResponse addBook(UserBookRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Book book = bookRepository.findById(request.getBookId());
        if (book == null) {
            throw new NotFoundException("Livre introuvable");
        }

        UserBook userBook = new UserBook();
        userBook.user = user;
        userBook.book = book;
        userBook.status = request.getStatus();
        userBook.bookCondition = request.getBookCondition();
        userBook.availableForExchange = request.isAvailableForExchange();
        userBook.availableForLoan = request.isAvailableForLoan();
        libraryRepository.persist(userBook);

        return UserBookResponse.from(userBook);
    }

    @Transactional
    public UserBookResponse update(Long id, UserBookRequest request, String username) {
        UserBook userBook = libraryRepository.findById(id);
        if (userBook == null) {
            throw new NotFoundException("Entrée introuvable dans la bibliothèque");
        }
        if (!userBook.user.username.equals(username)) {
            throw new ForbiddenException("Accès refusé");
        }

        userBook.status = request.getStatus();
        userBook.bookCondition = request.getBookCondition();
        userBook.availableForExchange = request.isAvailableForExchange();
        userBook.availableForLoan = request.isAvailableForLoan();

        return UserBookResponse.from(userBook);
    }

    @Transactional
    public void remove(Long id, String username) {
        UserBook userBook = libraryRepository.findById(id);
        if (userBook == null) {
            throw new NotFoundException("Entrée introuvable dans la bibliothèque");
        }
        if (!userBook.user.username.equals(username)) {
            throw new ForbiddenException("Accès refusé");
        }
        libraryRepository.delete(userBook);
    }
}