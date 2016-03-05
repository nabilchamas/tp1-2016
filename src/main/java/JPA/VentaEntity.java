package JPA;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 05/03/16.
 */
@Entity
@Table(name = "venta", schema = "public", catalog = "tp1")
public class VentaEntity {
    private long id;

    private ClienteEntity cliente;

    private List<DetalleVentaEntity> detalles = new ArrayList<DetalleVentaEntity>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    public List<DetalleVentaEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaEntity> detalles) {
        this.detalles = detalles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
