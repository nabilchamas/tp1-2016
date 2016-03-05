package REST;

import EJB.CompraService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Created by nabil on 29/02/16.
 */

@Path("/compras")
public class Compra {


    @EJB
    private CompraService compraService;

    @POST
    @Produces("application/json")
    public Response crearCompra(@QueryParam("proveedorId") Integer proveedorId){
        return Response.status(200).entity(compraService.crearCompra(proveedorId)).build();

    }

    @GET
    @Produces("application/json")
    public Response getCompras(){
        return Response.status(200).entity(compraService.getCompras()).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getCompra(@PathParam("id") Integer id){
        return Response.status(200).entity(compraService.getCompra(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCompra(@PathParam("id") Integer id){
        return Response.status(200).entity(compraService.deleteCompra(id)).build();
    }
}
