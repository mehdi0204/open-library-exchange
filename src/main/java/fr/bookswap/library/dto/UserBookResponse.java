package fr.bookswap.library.dto;

import fr.bookswap.entity.UserBook;
import lombok.Data;

@Data
public class UserBookResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private String bookIsbn;
    private UserBook.BookStatus status;
    private UserBook.BookCondition bookCondition;
    private boolean availableForExchange;
    private boolean availableForLoan;

    public static UserBookResponse from(UserBook userBook) {
        UserBookResponse response = new UserBookResponse();
        response.id = userBook.id;
        response.bookId = userBook.book.id;
        response.bookTitle = userBook.book.title;
        response.bookIsbn = userBook.book.isbn;
        response.status = userBook.status;
        response.bookCondition = userBook.bookCondition;
        response.availableForExchange = userBook.availableForExchange;
        response.availableForLoan = userBook.availableForLoan;
        return response;
    }
}