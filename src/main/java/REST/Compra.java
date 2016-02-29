package REST;

import Beans.CompraBean;
import Beans.ProductoBean;
import Beans.ProveedorBean;

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

    @POST
    @Produces("application/json")
    public Response crearCompra(@QueryParam("proveedorId") Integer proveedorId,
                                @QueryParam("productosId") ArrayList<Integer> productosId){

        if(!proveedores.containsKey(proveedorId)){
            return Response.status(200).entity("Proveedor no existe.").build();
        }else{
            ProveedorBean proveedor = proveedores.get(proveedorId);
            ArrayList<ProductoBean> productosCompra = new ArrayList<ProductoBean>();

            for (int i = 0; i < productosId.size(); i++) {
                if(productos.containsKey(productosId.get(i))){
                    ProductoBean producto = productos.get(productosId.get(i));
                    productosCompra.add(producto);
                }else{
                    return Response.status(200).entity("Producto no existe.").build();
                }
            }

            CompraBean compra = new CompraBean();
            compra.setProveedor(proveedor);
            compra.setProductos(productosCompra);
            compras.put(compra.getId(), compra);
            return Response.status(200).entity(compra).build();
        }

    }

    @GET
    @Produces("application/json")
    public Response getCompras(){
        return Response.status(200).entity(compras).build();
    }
}
