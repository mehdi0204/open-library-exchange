package fr.bookswap.exception;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private List<FieldError> details;
    private String timestamp;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }

    public ErrorResponse(int status, String error, String message, List<FieldError> details) {
        this(status, error, message);
        this.details = details;
    }

    @Data
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}