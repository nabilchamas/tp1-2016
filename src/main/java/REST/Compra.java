package REST;

import EJB.CompraService;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by nabil on 29/02/16.
 */

@Path("/compras")
public class Compra {


    @EJB
    private CompraService compraService;

    @POST
    @Produces("application/json")
    public Response crearCompra(@HeaderParam("accessToken") String accessToken,
                                @Context HttpRequest httpRequest,
                                @QueryParam("proveedorId") Integer proveedorId,
                                @QueryParam("productosId") List<Integer> productosId,
                                @QueryParam("cantidades") List<Integer> cantidades) {
        try {
            return Response.status(200).entity(compraService.crearCompra(accessToken, httpRequest,
                    proveedorId, productosId, cantidades)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo Crear La compra").build();
        }

    }

    @GET
    @Produces("application/json")
    public Response getCompras(@HeaderParam("accessToken") String accessToken,
                               @Context HttpRequest httpRequest) {
        return Response.status(200).entity(compraService.getCompras(accessToken, httpRequest)).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getCompra(@HeaderParam("accessToken") String accessToken,
                              @Context HttpRequest httpRequest,
                              @PathParam("id") Integer id) {
        return Response.status(200).entity(compraService.getCompra(accessToken, httpRequest, id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCompra(@HeaderParam("accessToken") String accessToken,
                                 @Context HttpRequest httpRequest,
                                 @PathParam("id") Integer id) {
        return Response.status(200).entity(compraService.deleteCompra(accessToken, httpRequest, id)).build();
    }
}
