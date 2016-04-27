package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 05/03/16.
 */

public class VentaEntity {
    private long id;

    private ClienteEntity cliente;

    private List<DetalleVentaEntity> detalles = new ArrayList<DetalleVentaEntity>();


    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }


    public List<DetalleVentaEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaEntity> detalles) {
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

        VentaEntity that = (VentaEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
