package REST;

import EJB.PagoService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by nabil on 29/02/16.
 */

@Path("/pagos")
public class Pago {

    @EJB
    private PagoService pagoService;


    @POST
    @Produces("application/json")
    public Response crearPago(@QueryParam("clienteId") Integer clienteId,
                              @QueryParam("monto") String monto,
                              @QueryParam("fecha") String fecha){
        return Response.status(200).entity(pagoService.crearPago(clienteId, monto, fecha)).build();
    }


    @GET
    @Produces("application/json")
    public Response getPagos(){
        return Response.status(200).entity(pagoService.getPagos()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getPago(@PathParam("id") Integer id){
        return Response.status(200).entity(pagoService.getPago(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePago(@PathParam("id") Integer id){
        return Response.status(200).entity(pagoService.deletePago(id)).build();
    }


}
