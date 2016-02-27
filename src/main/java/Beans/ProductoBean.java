package Beans;

import java.util.HashMap;

/**
 * Created by nabil on 27/02/16.
 */
public class ProductoBean {
    public static HashMap<Integer, ProductoBean> productos = new HashMap<Integer, ProductoBean>();

    private Integer id;
    private static Integer idCounter=0;
    private String nombre;
    private Integer precio;
    private Integer cantidad;
    private ProveedorBean proveedor;

    public ProductoBean(String nombre, Integer precio, Integer cantidad, ProveedorBean proveedor) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.id = idCounter++;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ProveedorBean getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorBean proveedor) {
        this.proveedor = proveedor;
    }
}
