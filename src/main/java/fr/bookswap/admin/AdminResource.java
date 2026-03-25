package fr.bookswap.admin;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class AdminResource {

    @Inject
    AdminService adminService;

    @GET
    @Path("/users")
    public Response listUsers() {
        return Response.ok(adminService.listUsers()).build();
    }

    @PATCH
    @Path("/users/{id}/suspend")
    public Response suspendUser(@PathParam("id") Long id) {
        return Response.ok(adminService.suspendUser(id)).build();
    }

    @DELETE
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        adminService.deleteUser(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/reviews/{id}")
    public Response deleteReview(@PathParam("id") Long id) {
        adminService.deleteReview(id);
        return Response.noContent().build();
    }
}