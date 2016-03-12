package REST;

import EJB.ProductoService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by nabil on 27/02/16.
 */

@Path("/productos")
public class Producto {


    @EJB
    private ProductoService productoService;

    @POST
    @Produces("application/json")
    public Response crearProducto(@QueryParam("nombre") String nombre,
                                  @QueryParam("precio") String precio,
                                  @QueryParam("cantidad") String cantidad,
                                  @QueryParam("proveedorId") Integer proveedorId){
        return Response.status(200).entity(
                productoService.crearProducto(nombre, precio, cantidad, proveedorId)).build();
    }

    @GET
    @Produces("application/json")
    public Response getProductos(){
        return Response.status(200).entity(productoService.getProductos()).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProducto(@PathParam("id") Integer id){
        return Response.status(200).entity(productoService.getProducto(id)).build();
    }


    @DELETE
    @Path("{id}")
    public Response borrarProducto(@PathParam("id") Integer id){
        return Response.status(200).entity(productoService.deleteProducto(id)).build();
    }


    @PUT
    @Path("{id}")
    public Response actualizarProducto(@PathParam("id") Integer id,
                                       @QueryParam("nombre") String nombre,
                                       @QueryParam("precio") String precio,
                                       @QueryParam("cantidad") String cantidad,
                                       @QueryParam("proveedorId") Integer proveedorId){
        return Response.status(200).entity(productoService.updateProducto(id, nombre, precio,
                cantidad, proveedorId)).build();
    }


//    @GET
//    @Path("/si")
//    @Produces("application/json")
//    public Response getProductoByNombre(@QueryParam("nombre") String nombre){
//        return Response.status(200).entity(productoService.getProductoByNombre(nombre)).build();
//    }












}
