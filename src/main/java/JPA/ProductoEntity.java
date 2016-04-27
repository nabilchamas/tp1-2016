package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 04/03/16.
 */

public class ProductoEntity {
    private long id;
    private String nombre;
    private String precio;
    private String cantidad;

    private ProveedorEntity proveedor;


    private List<DetalleVentaEntity> detallesVenta = new ArrayList<DetalleVentaEntity>();


    private List<DetalleCompraEntity> detallesCompra = new ArrayList<DetalleCompraEntity>();


    public List<DetalleCompraEntity> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompraEntity> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }


    public List<DetalleVentaEntity> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVentaEntity> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }


    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoEntity that = (ProductoEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (precio != null ? !precio.equals(that.precio) : that.precio != null) return false;
        return cantidad != null ? cantidad.equals(that.cantidad) : that.cantidad == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }
}
