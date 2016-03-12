package JPA;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nabil on 04/03/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "compra.findAll", query = "select v from CompraEntity v")
})
@Table(name = "compra", schema = "public", catalog = "tp1")
public class CompraEntity {


    private long id;

    private ProveedorEntity proveedor;

    private List<DetalleCompraEntity> detalles = new ArrayList<DetalleCompraEntity>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "proveedor_id")
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    public List<DetalleCompraEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompraEntity> detalles) {
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

        CompraEntity that = (CompraEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
