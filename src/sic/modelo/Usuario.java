package sic.modelo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.buscarTodos", query = "SELECT u FROM Usuario u WHERE u.eliminado = false ORDER BY u.nombre ASC"),
    @NamedQuery(name = "Usuario.buscarPorNombre", query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.buscarUsuariosAdministradores", query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.permisosAdministrador = true ORDER BY u.nombre ASC"),
    @NamedQuery(name = "Usuario.buscarPorNombreContrasenia", query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.nombre = :nombre AND u.password = :password")
})
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private long id_Usuario;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String password;
    private boolean permisosAdministrador;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "usuario")
    private Set<FacturaVenta> facturasVenta;
    private boolean eliminado;

    public Usuario() {
    }

    public Set<FacturaVenta> getFacturasVenta() {
        return facturasVenta;
    }

    public void setFacturasVenta(Set<FacturaVenta> facturasVenta) {
        this.facturasVenta = facturasVenta;
    }

    public long getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(long id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getPermisosAdministrador() {
        return permisosAdministrador;
    }

    public void setPermisosAdministrador(boolean permisosAdministrador) {
        this.permisosAdministrador = permisosAdministrador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof Usuario)) {
            return false;
        }

        Usuario usuario = (Usuario) o;
        if (id_Usuario != usuario.id_Usuario) {
            return false;
        }

        if (nombre != null ? !nombre.equals(usuario.nombre) : usuario.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_Usuario);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}
