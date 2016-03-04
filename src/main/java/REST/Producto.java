package REST;

import Beans.ProductoBean;
import Beans.ProveedorBean;
import EJB.ProductoService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by nabil on 27/02/16.
 */

@Path("/productos")
public class Producto {

    private static HashMap<Integer, ProductoBean> productos = ProductoBean.productos;
    private static HashMap<Integer, ProveedorBean> proveedores = ProveedorBean.proveedores;

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












}
