package fr.bookswap.library;

import fr.bookswap.entity.UserBook;
import fr.bookswap.library.dto.UserBookRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibraryResource {

    @Inject
    LibraryService libraryService;

    @GET
    @Path("/library")
    @RolesAllowed("USER")
    public Response getMyLibrary(@QueryParam("status") UserBook.BookStatus status, @Context SecurityContext ctx) {
        return Response.ok(libraryService.getMyLibrary(ctx.getUserPrincipal().getName(), status)).build();
    }

    @POST
    @Path("/library")
    @RolesAllowed("USER")
    public Response addBook(@Valid UserBookRequest request, @Context SecurityContext ctx) {
        return Response.status(201).entity(libraryService.addBook(request, ctx.getUserPrincipal().getName())).build();
    }

    @PUT
    @Path("/library/{id}")
    @RolesAllowed("USER")
    public Response updateBook(@PathParam("id") Long id, @Valid UserBookRequest request, @Context SecurityContext ctx) {
        return Response.ok(libraryService.update(id, request, ctx.getUserPrincipal().getName())).build();
    }

    @DELETE
    @Path("/library/{id}")
    @RolesAllowed("USER")
    public Response removeBook(@PathParam("id") Long id, @Context SecurityContext ctx) {
        libraryService.remove(id, ctx.getUserPrincipal().getName());
        return Response.noContent().build();
    }

    @GET
    @Path("/users/{username}/library")
    @PermitAll
    public Response getPublicLibrary(@PathParam("username") String username) {
        return Response.ok(libraryService.getPublicLibrary(username)).build();
    }
}