package REST;

import Beans.ClienteBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by nabil on 26/02/16.
 */


@Path("/clientes")
public class Cliente {

    private static HashMap<Integer, ClienteBean> clientes = ClienteBean.clientes;



    @POST
    @Path("{nombre}")
    @Produces("application/json")
    public Response crearCliente(@PathParam("nombre") String nombre){
        ClienteBean cliente = new ClienteBean(nombre);
        clientes.put(cliente.getId(), cliente);

        return Response.status(200).entity(cliente).build();
    }

    @GET
    @Produces("application/json")
    public Response getClientes(){
        return Response.status(200).entity(clientes).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response getCliente(@PathParam("id") Integer id){
        if(clientes.containsKey(id)){
            ClienteBean cliente = clientes.get(id);
            return Response.status(200).entity(cliente).build();
        }else{
            return Response.status(200).entity("Cliente no existe.").build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response borrarCliente(@PathParam("id") Integer id){
        if(clientes.containsKey(id)) {
            clientes.remove(id);
            return Response.status(200).entity("Borrado exitoso.").build();
        }else{
            return Response.status(200).entity("Cliente no existe.").build();
        }
    }


    @PUT
    @Path("{id}")
    public Response actualizarCliente(@PathParam("id") Integer id,
                                      @QueryParam("nombre") String nombre){
        if(clientes.containsKey(id)) {
            ClienteBean cliente = clientes.get(id);
            cliente.setNombre(nombre);
            return Response.status(200).entity("Actualizacion exitosa.").build();
        }else{
            return Response.status(200).entity("Cliente no existe.").build();
        }
    }
}
