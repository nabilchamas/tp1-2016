package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by nabil on 04/03/16.
 */

public class DetalleCompraEntity {
    private long id;
    private String cantidad;


    private CompraEntity compra;

    private ProductoEntity producto;


    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }


    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        DetalleCompraEntity that = (DetalleCompraEntity) o;

        if (id != that.id) return false;
        return cantidad != null ? cantidad.equals(that.cantidad) : that.cantidad == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }
}
