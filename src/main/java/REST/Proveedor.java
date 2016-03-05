package REST;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import EJB.ProveedorService;

/**
 * Created by nabil on 27/02/16.
 */

@Path("/proveedores")
public class Proveedor {


    @EJB
    private ProveedorService proveedorService;

    @POST
    @Path("{nombre}")
    @Produces("application/json")
    public Response crearProveedor(@PathParam("nombre") String nombre){
        return Response.status(200).entity(proveedorService.crearProveedor(nombre)).build();
    }

    @GET
    @Produces("application/json")
    public Response getProveedores(){
        return Response.status(200).entity(proveedorService.getProveedores()).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProveedor(@PathParam("id") Integer id){
        return Response.status(200).entity(proveedorService.getProveedor(id)).build();
    }


    @DELETE
    @Path("{id}")
    public Response borrarProveedor(@PathParam("id") Integer id){
        return Response.status(200).entity(proveedorService.deleteProveedor(id)).build();
    }


    @PUT
    @Path("{id}")
    public Response actualizarProveedor(@PathParam("id") Integer id,
                                        @QueryParam("nombre") String nombre){
        return Response.status(200).entity(proveedorService.updateProveedor(id, nombre)).build();
    }
}
