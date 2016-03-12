package JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by nabil on 04/03/16.
 */
@Entity
@Table(name = "detalle_compra", schema = "public", catalog = "tp1")
public class DetalleCompraEntity {
    private long id;
    private String cantidad;

    @JsonIgnore
    private CompraEntity compra;

    private ProductoEntity producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "compra_id")
    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
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
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleCompraEntity that = (DetalleCompraEntity) o;

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
