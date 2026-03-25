package fr.bookswap.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class BookRequest {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    private String description;
    private int publicationYear;
    private String coverUrl;
    private List<Long> authorIds;
    private List<Long> genreIds;
}