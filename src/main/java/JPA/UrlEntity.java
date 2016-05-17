package JPA;

import javax.persistence.*;

/**
 * Created by nabil on 17/05/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "url.findByRol", query = "select v from UrlEntity v where v.rol=:rol")
})
@Table(name = "url", schema = "public", catalog = "tp1")
public class UrlEntity {
    private long id;
    private String nombre;
    private String metodo;
    private String rol;

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
    @Column(name = "metodo")
    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    @Basic
    @Column(name = "rol")
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlEntity urlEntity = (UrlEntity) o;

        if (id != urlEntity.id) return false;
        if (nombre != null ? !nombre.equals(urlEntity.nombre) : urlEntity.nombre != null) return false;
        if (metodo != null ? !metodo.equals(urlEntity.metodo) : urlEntity.metodo != null) return false;
        if (rol != null ? !rol.equals(urlEntity.rol) : urlEntity.rol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (metodo != null ? metodo.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        return result;
    }
}
