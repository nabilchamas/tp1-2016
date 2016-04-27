package JPA;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by nabil on 05/03/16.
 */

public class DetalleVentaEntity {
    private long id;
    private long cantidad;

    private VentaEntity venta;
    private ProductoEntity producto;



    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }


    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public long getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleVentaEntity that = (DetalleVentaEntity) o;

        if (id != that.id) return false;
        return cantidad == that.cantidad;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (cantidad ^ (cantidad >>> 32));
        return result;
    }

}
