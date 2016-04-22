package JPA;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by nabil on 05/03/16.
 */
@Entity
@Table(name = "detalle_venta", schema = "public", catalog = "tp1")
public class DetalleVentaEntity {
    private long id;
    private long cantidad;
    @JsonIgnore
    private VentaEntity venta;
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
    @JoinColumn(name = "venta_id")
    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
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
    public long getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleVentaEntity that = (DetalleVentaEntity) o;

        if (id != that.id) return false;
        return cantidad == that.cantidad;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (cantidad ^ (cantidad >>> 32));
        return result;
    }

}
