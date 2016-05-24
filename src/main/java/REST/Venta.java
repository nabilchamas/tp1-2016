package REST;


import EJB.VentaService;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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
    public Response crearVenta(@HeaderParam("accessToken") String accessToken,
                               @Context HttpRequest httpRequest,
                               @QueryParam("clienteId") Integer clienteId,
                               @QueryParam("productosId") List<Integer> productosId,
                               @QueryParam("cantidades") List<Integer> cantidades) {
        try {
            return Response.status(200).entity(ventaService.crearVenta(accessToken,
                    httpRequest, clienteId,
                    productosId, cantidades)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear la venta").build();
        }
    }


    @GET
    @Produces("application/json")
    public Response getVentas(@HeaderParam("accessToken") String accessToken,
                              @Context HttpRequest httpRequest) {
        return Response.status(200).entity(ventaService.getVentas(accessToken,
                httpRequest)).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getVenta(@HeaderParam("accessToken") String accessToken,
                             @Context HttpRequest httpRequest,
                             @PathParam("id") Integer id) {
        return Response.status(200).entity(ventaService.getVenta(accessToken,
                httpRequest, id)).build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteVenta(@HeaderParam("accessToken") String accessToken,
                                @Context HttpRequest httpRequest,
                                @PathParam("id") Integer id) {
        return Response.status(200).entity(ventaService.deleteVenta(accessToken,
                httpRequest, id)).build();
    }

}
