package fr.bookswap.exchange.dto;

import fr.bookswap.entity.Exchange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeRequest {

    @NotNull
    private Long userBookId;

    @NotNull
    private Exchange.ExchangeType type;

    private String message;
}