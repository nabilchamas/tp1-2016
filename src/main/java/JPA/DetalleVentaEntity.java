package JPA;



import javax.persistence.*;

/**
 * Created by nabil on 05/03/16.
 */
@Entity
@Table(name = "detalle_venta", schema = "public", catalog = "tp1")
public class DetalleVentaEntity {
    private long id;
    private String cantidad;

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

        DetalleVentaEntity that = (DetalleVentaEntity) o;

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
