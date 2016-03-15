package REST;

import Beans.ProductoBean;
import EJB.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.List;

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

    @POST
    @Path("/enviarJson")
    @Consumes("application/json")
    public Response crearProductos(List<ProductoBean> productosBean){
        productoService.crearProductos(productosBean);
        return Response.status(200).entity("Productos creados.").build();
    }

    @GET
    @Produces("application/json")
    public Response getProductos(){
        productoService.crearFileProductos();

        File file = new File("/home/nabil/Desktop/productos");
        return Response.status(200).entity(file).build();
//        Response.ResponseBuilder response = Response.ok((Object) file);
//        //response.header("Content-Disposition", "attachment; filename=\"productos\"");
//        return response.build();
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
//    @Path("/file/write")
//    @Produces("text/plain")
//    public Response getProductosFile(){
//        File file = new File("/home/nabil/Desktop/write");
//
//        FileOutputStream fop = null;
//        //File file;
//        String content = "This is the text content";
//
//        try {
//
//            //file = new File("/home/nabil/Desktop/hola");
//            fop = new FileOutputStream(file);
//
//            // if file doesnt exists, then create it
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            // get the content in bytes
//            byte[] contentInBytes = content.getBytes();
//
//            fop.write(contentInBytes);
//            fop.flush();
//            fop.close();
//
//            System.out.println("Done");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        finally {
////            try {
////                if (fop != null) {
////                    fop.close();
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//        Response.ResponseBuilder response = Response.ok((Object) file);
//        response.header("Content-Disposition",
//                "attachment; filename=\"file_from_server.log\"");
//        return response.build();
//    }

    @GET
    @Path("/descargarFile")
    @Produces("text/plain")
    public Response appendFile(){
        productoService.crearFileProductos();

        File file = new File("/home/nabil/Desktop/productos");
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"productos\"");
        return response.build();
    }


//    @GET
//    @Path("/si")
//    @Produces("application/json")
//    public Response getProductoByNombre(@QueryParam("nombre") String nombre){
//        return Response.status(200).entity(productoService.getProductoByNombre(nombre)).build();
//

}
