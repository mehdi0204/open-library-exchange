package fr.bookswap.auth;

import fr.bookswap.auth.dto.LoginRequest;
import fr.bookswap.auth.dto.RegisterRequest;
import fr.bookswap.auth.dto.TokenResponse;
import fr.bookswap.auth.dto.UpdateProfileRequest;
import fr.bookswap.entity.User;
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

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    public Response register(@Valid RegisterRequest request) {
        LOG.info("Inscription : " + request.getUsername());
        TokenResponse token = authService.register(request);
        return Response.status(201).entity(token).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(@Valid LoginRequest request) {
        LOG.info("Connexion : " + request.getUsername());
        TokenResponse token = authService.login(request);
        return Response.ok(token).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed("USER")
    public Response me(@Context SecurityContext ctx) {
        User user = authService.getProfile(ctx.getUserPrincipal().getName());
        return Response.ok(user).build();
    }

    @PUT
    @Path("/me")
    @RolesAllowed("USER")
    public Response updateMe(@Context SecurityContext ctx, @Valid UpdateProfileRequest request) {
        User user = authService.updateProfile(ctx.getUserPrincipal().getName(), request);
        return Response.ok(user).build();
    }
}
