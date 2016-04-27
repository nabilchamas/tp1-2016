package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 04/03/16.
 */

public class ClienteEntity {

    private long id;
    private String nombre;
    private String saldo;


    private List<VentaEntity> ventas = new ArrayList<VentaEntity>();


    public List<VentaEntity> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaEntity> ventas) {
        this.ventas = ventas;
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


    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClienteEntity that = (ClienteEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        return saldo != null ? saldo.equals(that.saldo) : that.saldo == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (saldo != null ? saldo.hashCode() : 0);
        return result;
    }
}
