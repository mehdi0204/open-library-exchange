package fr.bookswap.book.dto;

import fr.bookswap.entity.Author;
import fr.bookswap.entity.Book;
import fr.bookswap.entity.Genre;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse {

    private Long id;
    private String isbn;
    private String title;
    private String description;
    private int publicationYear;
    private String coverUrl;
    private List<String> authors;
    private List<String> genres;
    private Double averageRating;

    public static BookResponse from(Book book, Double averageRating) {
        BookResponse response = new BookResponse();
        response.id = book.id;
        response.isbn = book.isbn;
        response.title = book.title;
        response.description = book.description;
        response.publicationYear = book.publicationYear;
        response.coverUrl = book.coverUrl;
        response.authors = book.authors.stream()
                .map(a -> a.firstName + " " + a.lastName)
                .toList();
        response.genres = book.genres.stream()
                .map(g -> g.name)
                .toList();
        response.averageRating = averageRating;
        return response;
    }
}