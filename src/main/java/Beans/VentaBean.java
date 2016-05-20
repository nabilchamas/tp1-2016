package Beans;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */
public class VentaBean {

    public static HashMap<Integer, VentaBean> ventas = new HashMap<Integer, VentaBean>();


    private static Integer idCounter=0;
    private ClienteBean cliente;
    private ArrayList<ProductoBean> productos;






    public ClienteBean getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ProductoBean> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoBean> productos) {
        this.productos = productos;
    }
}
