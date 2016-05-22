package REST;

import EJB.ClienteService;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by nabil on 26/02/16.
 */


@Path("/clientes")
public class Cliente {

    @EJB
    private ClienteService clienteService;



    @POST
    @Path("{nombre}")
    @Produces("application/json")
    public Response crearCliente(@PathParam("nombre") String nombre,
                                 @Context HttpRequest httpRequest,
                                 @HeaderParam("accessToken") String accessToken){
        return Response.status(200).entity(clienteService.crearCliente(accessToken,
                httpRequest, nombre)).build();
    }

    @GET
    @Produces("application/json")
    public Response getClientes(@Context HttpRequest httpRequest,
                                @HeaderParam("accessToken")String accessToken){
        return Response.status(200).entity(clienteService.getClientes(accessToken,
                httpRequest)).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response getCliente(@PathParam("id") Integer id,
                               @Context HttpRequest httpRequest,
                               @HeaderParam("accessToken") String accessToken){
        return Response.status(200).entity(clienteService.getCliente(accessToken,
                httpRequest, id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response borrarCliente(@PathParam("id") Integer id,
                                  @Context HttpRequest httpRequest,
                                  @HeaderParam("accessToken") String accessToken){
        return Response.status(200).entity(clienteService.deleteCliente(accessToken,
                httpRequest, id)).build();
    }


    @PUT
    @Path("{id}")
    public Response actualizarCliente(@PathParam("id") Integer id,
                                      @QueryParam("nombre") String nombre,
                                      @Context HttpRequest httpRequest,
                                      @HeaderParam("accessToken") String accessToken){
        return Response.status(200).entity(clienteService.updateCliente(accessToken,
                httpRequest, id, nombre)).build();
    }
}
