package JPA;

import javax.persistence.*;

/**
 * Created by nabil on 12/03/16.
 */

public class ProductoDuplicadoEntity {
    private long id;
    private Long cantidad;
    private ProductoEntity producto;


    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoDuplicadoEntity that = (ProductoDuplicadoEntity) o;

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
