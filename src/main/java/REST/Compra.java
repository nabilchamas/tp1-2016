package REST;

import Beans.CompraBean;
import Beans.ProductoBean;
import Beans.ProveedorBean;
import EJB.CompraService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */

@Path("/compras")
public class Compra {

    private static HashMap<Integer, CompraBean> compras = CompraBean.compras;
    private static HashMap<Integer, ProveedorBean> proveedores = ProveedorBean.proveedores;
    private static HashMap<Integer, ProductoBean> productos = ProductoBean.productos;

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
}
