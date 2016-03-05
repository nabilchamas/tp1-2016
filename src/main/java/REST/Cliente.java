package REST;

import EJB.ClienteService;
import JPA.ClienteEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
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
    public Response crearCliente(@PathParam("nombre") String nombre){
        ClienteEntity cliente = clienteService.crearCliente(nombre);

        return Response.status(200).entity(cliente).build();
    }

    @GET
    @Produces("application/json")
    public Response getClientes(){
        return Response.status(200).entity(clienteService.getClientes()).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response getCliente(@PathParam("id") Integer id){
        return Response.status(200).entity(clienteService.getCliente(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response borrarCliente(@PathParam("id") Integer id){
        return Response.status(200).entity(clienteService.deleteCliente(id)).build();
    }


    @PUT
    @Path("{id}")
    public Response actualizarCliente(@PathParam("id") Integer id,
                                      @QueryParam("nombre") String nombre){
        return Response.status(200).entity(clienteService.updateCliente(id, nombre)).build();
    }
}
