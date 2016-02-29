package REST;

import Beans.ClienteBean;
import Beans.PagoBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */

@Path("/pagos")
public class Pago {

    private static HashMap<Integer, PagoBean> pagos = PagoBean.pagos;
    private static HashMap<Integer, ClienteBean> clientes = ClienteBean.clientes;

    @POST
    @Produces("application/json")
    public Response crearPago(@QueryParam("clienteId") Integer clienteId,
                              @QueryParam("monto") Integer monto,
                              @QueryParam("fecha") String fecha){
        if(!clientes.containsKey(clienteId)){
            return Response.status(200).entity("Cliente no existe.").build();
        }else {
            ClienteBean cliente = clientes.get(clienteId);
            Integer nuevoSaldo = cliente.getSaldo() - monto;
            cliente.setSaldo(nuevoSaldo);

            PagoBean pago = new PagoBean(cliente, monto, fecha);
            pagos.put(pago.getId(), pago);
            return Response.status(200).entity(pago).build();
        }
    }


    @GET
    @Produces("application/json")
    public Response getPagos(){
        return Response.status(200).entity(pagos).build();
    }
}
