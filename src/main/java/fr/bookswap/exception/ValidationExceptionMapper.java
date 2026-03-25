package fr.bookswap.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ErrorResponse.FieldError> details = e.getConstraintViolations().stream()
                .map(cv -> {
                    String field = cv.getPropertyPath().toString();
                    if (field.contains(".")) {
                        field = field.substring(field.lastIndexOf('.') + 1);
                    }
                    return new ErrorResponse.FieldError(field, cv.getMessage());
                })
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(400, "VALIDATION_ERROR", "Les données envoyées sont invalides", details);
        return Response.status(400).entity(error).build();
    }
}