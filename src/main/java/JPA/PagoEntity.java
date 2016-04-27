package JPA;

import javax.persistence.*;

/**
 * Created by nabil on 15/03/16.
 */

public class PagoEntity {
    private long id;
    private String monto;
    private String fecha;

    private ClienteEntity cliente;

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagoEntity that = (PagoEntity) o;

        if (id != that.id) return false;
        if (monto != null ? !monto.equals(that.monto) : that.monto != null) return false;
        return fecha != null ? fecha.equals(that.fecha) : that.fecha == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (monto != null ? monto.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        return result;
    }
}
