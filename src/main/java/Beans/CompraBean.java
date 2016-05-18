package Beans;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */
public class CompraBean {

    public static HashMap<Integer, CompraBean> compras= new HashMap<Integer, CompraBean>();


    private static Integer idCounter=0;
    private ProveedorBean proveedor;
    private ArrayList<ProductoBean> productos;





    public ProveedorBean getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorBean proveedor) {
        this.proveedor = proveedor;
    }

    public ArrayList<ProductoBean> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoBean> productos) {
        this.productos = productos;
    }
}
