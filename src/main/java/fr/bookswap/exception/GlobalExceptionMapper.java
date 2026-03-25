package fr.bookswap.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException wae) {
            int status = wae.getResponse().getStatus();
            ErrorResponse error = new ErrorResponse(status, "ERROR", e.getMessage());
            return Response.status(status).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse(500, "INTERNAL_SERVER_ERROR", "Une erreur interne est survenue");
        return Response.serverError().entity(error).build();
    }
}