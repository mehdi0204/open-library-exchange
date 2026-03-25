package fr.bookswap.library.dto;

import fr.bookswap.entity.UserBook.BookCondition;
import fr.bookswap.entity.UserBook.BookStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserBookRequest {

    @NotNull
    private Long bookId;

    @NotNull
    private BookStatus status;

    private BookCondition bookCondition;

    private boolean availableForExchange;

    private boolean availableForLoan;
}