package JPA;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 04/03/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "producto.findAll", query = "select v from ProductoEntity v")
})
@Table(name = "producto", schema = "public", catalog = "tp1")
public class ProductoEntity {
    private long id;
    private String nombre;
    private String precio;
    private String cantidad;

    private ProveedorEntity proveedor;
    private List<DetalleVentaEntity> detallesVenta = new ArrayList<DetalleVentaEntity>();
    private List<DetalleCompraEntity> detallesCompra = new ArrayList<DetalleCompraEntity>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    public List<DetalleCompraEntity> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompraEntity> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    public List<DetalleVentaEntity> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVentaEntity> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "proveedor_id")
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
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
    @Column(name = "precio")
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
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

        ProductoEntity that = (ProductoEntity) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (precio != null ? !precio.equals(that.precio) : that.precio != null) return false;
        if (cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }
}
