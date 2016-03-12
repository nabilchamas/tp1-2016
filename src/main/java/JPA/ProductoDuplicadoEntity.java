package JPA;

import javax.persistence.*;

/**
 * Created by nabil on 12/03/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "productoDuplicado.findByProducto", query ="select v from ProductoDuplicadoEntity v where v.producto=:producto")
})
@Table(name = "producto_duplicado", schema = "public", catalog = "tp1")
public class ProductoDuplicadoEntity {
    private long id;
    private Long cantidad;
    private ProductoEntity producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_producto")
    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
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

    @Basic
    @Column(name = "cantidad")
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
        if (cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }
}
