package REST;

import Beans.ProveedorBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by nabil on 27/02/16.
 */

@Path("/proveedores")
public class Proveedor {

    private static HashMap<Integer, ProveedorBean> proveedores = ProveedorBean.proveedores;

    @POST
    @Path("{nombre}")
    @Produces("application/json")
    public Response crearProveedor(@PathParam("nombre") String nombre){
        ProveedorBean proveedor = new ProveedorBean(nombre);
        proveedores.put(proveedor.getId(), proveedor);

        return Response.status(200).entity(proveedor).build();
    }

    @GET
    @Produces("application/json")
    public Response getProveedores(){
        return Response.status(200).entity(proveedores).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProveedor(@PathParam("id") Integer id){
        if(proveedores.containsKey(id)){
            ProveedorBean proveedor = proveedores.get(id);
            return Response.status(200).entity(proveedor).build();
        }else {
            return Response.status(200).entity("Proveedor no existe.").build();
        }
    }


    @DELETE
    @Path("{id}")
    public Response borrarProveedor(@PathParam("id") Integer id){
        if(proveedores.containsKey(id)){
            proveedores.remove(id);
            return Response.status(200).entity("Borrado exitoso.").build();
        }else {
            return Response.status(200).entity("Proveedor no existe.").build();
        }
    }


    @PUT
    @Path("{id}")
    public Response actualizarProveedor(@PathParam("id") Integer id,
                                        @QueryParam("nombre") String nombre){
        if(proveedores.containsKey(id)){
            ProveedorBean proveedor = proveedores.get(id);
            proveedor.setNombre(nombre);
            return Response.status(200).entity("Actualizacion exitosa.").build();
        }else {
            return Response.status(200).entity("Proveedor no existe.").build();
        }
    }
}
