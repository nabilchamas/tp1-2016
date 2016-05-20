package REST;


import EJB.VentaService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by nabil on 29/02/16.
 */

@Path("/ventas")
public class Venta {


    @EJB
    private VentaService ventaService;

    @POST
    @Produces("application/json")
    public Response crearVenta(@QueryParam("clienteId") Integer clienteId,
                               @QueryParam("productosId") List<Integer> productosId,
                               @QueryParam("cantidades") List<Integer> cantidades){
        try {
            return Response.status(200).entity(ventaService.crearVenta(clienteId,
                    productosId, cantidades)).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear la venta").build();
        }
    }


    @GET
    @Produces("application/json")
    public Response getVentas(){
        return Response.status(200).entity(ventaService.getVentas()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getVenta(@PathParam("id") Integer id){
        return Response.status(200).entity(ventaService.getVenta(id)).build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteVenta(@PathParam("id") Integer id){
        return Response.status(200).entity(ventaService.deleteVenta(id)).build();
    }

}
