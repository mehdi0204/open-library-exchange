package fr.bookswap.exchange;

import fr.bookswap.entity.Exchange;
import fr.bookswap.exchange.dto.ExchangeRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

@Path("/api/exchanges")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("USER")
public class ExchangeResource {

    private static final Logger LOG = Logger.getLogger(ExchangeResource.class);

    @Inject
    ExchangeService exchangeService;

    @GET
    public Response getMyExchanges(@QueryParam("status") Exchange.ExchangeStatus status, @Context SecurityContext ctx) {
        LOG.info("GET /api/exchanges par " + ctx.getUserPrincipal().getName());
        return Response.ok(exchangeService.getMyExchanges(ctx.getUserPrincipal().getName(), status)).build();
    }

    @POST
    public Response create(@Valid ExchangeRequest request, @Context SecurityContext ctx) {
        LOG.info("POST /api/exchanges par " + ctx.getUserPrincipal().getName());
        Exchange exchange = exchangeService.create(request, ctx.getUserPrincipal().getName());
        return Response.status(201).entity(exchange).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id, @Context SecurityContext ctx) {
        return Response.ok(exchangeService.getById(id, ctx.getUserPrincipal().getName())).build();
    }

    @PATCH
    @Path("/{id}/accept")
    public Response accept(@PathParam("id") Long id, @Context SecurityContext ctx) {
        LOG.info("PATCH /api/exchanges/" + id + "/accept par " + ctx.getUserPrincipal().getName());
        return Response.ok(exchangeService.accept(id, ctx.getUserPrincipal().getName())).build();
    }

    @PATCH
    @Path("/{id}/refuse")
    public Response refuse(@PathParam("id") Long id, @Context SecurityContext ctx) {
        LOG.info("PATCH /api/exchanges/" + id + "/refuse par " + ctx.getUserPrincipal().getName());
        return Response.ok(exchangeService.refuse(id, ctx.getUserPrincipal().getName())).build();
    }
}
