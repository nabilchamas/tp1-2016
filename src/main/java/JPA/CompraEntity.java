package JPA;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nabil on 04/03/16.
 */

public class CompraEntity {


    private long id;

    private ProveedorEntity proveedor;

    private List<DetalleCompraEntity> detalles = new ArrayList<DetalleCompraEntity>();


    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }


    public List<DetalleCompraEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompraEntity> detalles) {
        this.detalles = detalles;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompraEntity that = (CompraEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
