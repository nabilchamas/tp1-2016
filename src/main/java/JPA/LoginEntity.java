package JPA;

import javax.persistence.*;

/**
 * Created by nabil on 17/05/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "login.findByUsuario", query = "select v from LoginEntity v where v.usuario=:usuario")
})
@Table(name = "login", schema = "public", catalog = "tp1")
public class LoginEntity {
    private String usuario;
    private String password;
    private String rol;
    private long id;
    private String accessToken;

    @Basic
    @Column(name = "usuario")
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "rol")
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginEntity that = (LoginEntity) o;

        if (id != that.id) return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (rol != null ? !rol.equals(that.rol) : that.rol != null) return false;
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usuario != null ? usuario.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        return result;
    }
}
