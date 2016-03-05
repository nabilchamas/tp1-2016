package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 04/03/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "cliente.findAll", query = "select v from ClienteEntity v")
})
@Table(name = "cliente", schema = "public", catalog = "tp1")
public class ClienteEntity {

    private long id;
    private String nombre;
    private String saldo;

    @JsonIgnore
    private List<VentaEntity> ventas = new ArrayList<VentaEntity>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    public List<VentaEntity> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaEntity> ventas) {
        this.ventas = ventas;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "saldo")
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
        if (saldo != null ? !saldo.equals(that.saldo) : that.saldo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (saldo != null ? saldo.hashCode() : 0);
        return result;
    }
}
