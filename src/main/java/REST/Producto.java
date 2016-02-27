package REST;

import Beans.ProductoBean;
import Beans.ProveedorBean;

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

    @POST
    @Produces("application/json")
    public Response crearProducto(@QueryParam("nombre") String nombre,
                                  @QueryParam("precio") Integer precio,
                                  @QueryParam("cantidad") Integer cantidad,
                                  @QueryParam("proveedorId") Integer proveedorId){
        if(proveedores.containsKey(proveedorId)){
            ProveedorBean proveedor = proveedores.get(proveedorId);
            ProductoBean producto = new ProductoBean(nombre, precio, cantidad, proveedor);
            productos.put(producto.getId(), producto);
            return Response.status(200).entity(producto).build();
        }else {
            return Response.status(200).entity("El proveedor no existe.").build();
        }
    }

    @GET
    @Produces("application/json")
    public Response getProductos(){
        return Response.status(200).entity(productos).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProducto(@PathParam("id") Integer id){
        if(productos.containsKey(id)){
            ProductoBean producto = productos.get(id);
            return Response.status(200).entity(producto).build();
        }else{
            return Response.status(200).entity("El producto no existe.").build();
        }
    }


    @DELETE
    @Path("{id}")
    public Response borrarProducto(@PathParam("id") Integer id){
        if(productos.containsKey(id)){
            productos.remove(id);
            return Response.status(200).entity("Borrado exitoso.").build();
        }else{
            return Response.status(200).entity("El producto no existe.").build();
        }
    }


    @PUT
    @Path("{id}")
    public Response actualizarProducto(@PathParam("id") Integer id,
                                       @QueryParam("nombre") String nombre,
                                       @QueryParam("precio") Integer precio,
                                       @QueryParam("cantidad") Integer cantidad,
                                       @QueryParam("proveedorId") Integer proveedorId){
        if(productos.containsKey(id)){
            ProductoBean producto = productos.get(id);

            if(nombre!=null)
                producto.setNombre(nombre);

            if(precio!=null)
                producto.setPrecio(precio);

            if(cantidad!=null)
                producto.setCantidad(cantidad);

            if(proveedorId!=null){
                if(proveedores.containsKey(proveedorId)){
                    ProveedorBean proveedor = proveedores.get(proveedorId);
                    producto.setProveedor(proveedor);
                }else {
                    return Response.status(200).entity("El proveedor no existe.").build();
                }
            }

            return Response.status(200).entity("Actualizacion exitosa.").build();
        }else {
            return Response.status(200).entity("El producto no existe.").build();
        }
    }












}
