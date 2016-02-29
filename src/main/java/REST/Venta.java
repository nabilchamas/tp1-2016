package REST;


import Beans.ClienteBean;
import Beans.ProductoBean;
import Beans.VentaBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */

@Path("/ventas")
public class Venta {


    private static HashMap<Integer, ProductoBean> productos = ProductoBean.productos;
    private static HashMap<Integer, ClienteBean> clientes = Cliente.clientes;
    private static HashMap<Integer, VentaBean> ventas = VentaBean.ventas;

    @POST
    @Produces("application/json")
    public Response crearVenta(@QueryParam("clienteId") Integer clienteId,
                               @QueryParam("productosId")ArrayList<Integer> productosId,
                               @QueryParam("cantidades")ArrayList<Integer> cantidades){
        if(!clientes.containsKey(clienteId)){
            return Response.status(200).entity("Cliente no existe.").build();
        }else{
            ClienteBean cliente = clientes.get(clienteId);
            ArrayList<ProductoBean> productosVenta = new ArrayList<ProductoBean>();

            Integer totalVenta=0;
            for (int i = 0; i < productosId.size(); i++) {


                if(productos.containsKey(productosId.get(i))){
                    ProductoBean producto = productos.get(productosId.get(i));
                    productosVenta.add(producto);
                    totalVenta += producto.getPrecio() * cantidades.get(i);
                }else{
                    return Response.status(200).entity("Producto no existe.").build();
                }
            }

            VentaBean venta = new VentaBean();

            Integer nuevoSaldo = cliente.getSaldo() + totalVenta;
            cliente.setSaldo(nuevoSaldo);

            venta.setCliente(cliente);
            venta.setProductos(productosVenta);
            ventas.put(venta.getId(), venta);
            return Response.status(200).entity(venta).build();
        }
    }


    @GET
    @Produces("application/json")
    public Response getVentas(){
        return Response.status(200).entity(ventas).build();
    }

}
