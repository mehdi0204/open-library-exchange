package fr.bookswap.book;

import fr.bookswap.book.dto.BookRequest;
import fr.bookswap.book.dto.BookResponse;
import fr.bookswap.entity.Author;
import fr.bookswap.review.ReviewService;
import fr.bookswap.review.dto.ReviewRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger LOG = Logger.getLogger(BookResource.class);

    @Inject
    BookService bookService;

    @Inject
    ReviewService reviewService;

    // --- Books ---

    @GET
    @Path("/books")
    @PermitAll
    public Response listBooks(
            @QueryParam("title") String title,
            @QueryParam("isbn") String isbn,
            @QueryParam("author") Long authorId,
            @QueryParam("genre") Long genreId) {
        LOG.info("GET /api/books");
        List<BookResponse> books = bookService.listBooks(title, isbn, authorId, genreId);
        return Response.ok(books).build();
    }

    @GET
    @Path("/books/{id}")
    @PermitAll
    public Response getBook(@PathParam("id") Long id) {
        return Response.ok(bookService.getById(id)).build();
    }

    @POST
    @Path("/books")
    @RolesAllowed("USER")
    public Response createBook(@Valid BookRequest request, @Context SecurityContext ctx) {
        LOG.info("POST /api/books par " + ctx.getUserPrincipal().getName());
        BookResponse book = bookService.create(request, ctx.getUserPrincipal().getName());
        return Response.status(201).entity(book).build();
    }

    @PUT
    @Path("/books/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    public Response updateBook(@PathParam("id") Long id, @Valid BookRequest request, @Context SecurityContext ctx) {
        boolean isAdmin = ctx.isUserInRole("ADMIN");
        BookResponse book = bookService.update(id, request, ctx.getUserPrincipal().getName(), isAdmin);
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/books/{id}")
    @RolesAllowed("ADMIN")
    public Response deleteBook(@PathParam("id") Long id) {
        LOG.info("DELETE /api/books/" + id);
        bookService.delete(id);
        return Response.noContent().build();
    }

    // --- Reviews ---

    @GET
    @Path("/books/{id}/reviews")
    @PermitAll
    public Response getReviews(@PathParam("id") Long bookId) {
        return Response.ok(reviewService.listByBook(bookId)).build();
    }

    @POST
    @Path("/books/{id}/reviews")
    @RolesAllowed("USER")
    public Response addReview(@PathParam("id") Long bookId, @Valid ReviewRequest request, @Context SecurityContext ctx) {
        LOG.info("POST /api/books/" + bookId + "/reviews par " + ctx.getUserPrincipal().getName());
        return Response.status(201).entity(reviewService.create(bookId, request, ctx.getUserPrincipal().getName())).build();
    }

    // --- Authors ---

    @GET
    @Path("/authors")
    @PermitAll
    public Response listAuthors() {
        return Response.ok(bookService.listAuthors()).build();
    }

    @GET
    @Path("/authors/{id}")
    @PermitAll
    public Response getAuthor(@PathParam("id") Long id) {
        return Response.ok(bookService.getAuthorById(id)).build();
    }

    @POST
    @Path("/authors")
    @RolesAllowed("USER")
    public Response createAuthor(@Valid Author author) {
        return Response.status(201).entity(bookService.createAuthor(author)).build();
    }
}
