package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.buscarTodos",
            query = "SELECT u FROM Usuario u WHERE u.eliminado = false ORDER BY u.nombre ASC"),
    @NamedQuery(name = "Usuario.buscarPorNombre",
            query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.buscarUsuariosAdministradores",
            query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.permisosAdministrador = true ORDER BY u.nombre ASC"),
    @NamedQuery(name = "Usuario.buscarPorNombreContrasenia",
            query = "SELECT u FROM Usuario u WHERE u.eliminado = false AND u.nombre = :nombre AND u.password = :password")
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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "cliente")
    private Set<Pedido> pedidos;

    private boolean eliminado;

    public Usuario() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getPermisosAdministrador() {
        return permisosAdministrador;
    }

    public void setPermisosAdministrador(boolean permisosAdministrador) {
        this.permisosAdministrador = permisosAdministrador;
    }

    public Set<FacturaVenta> getFacturasVenta() {
        return facturasVenta;
    }

    public void setFacturasVenta(Set<FacturaVenta> facturasVenta) {
        this.facturasVenta = facturasVenta;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id_Usuario ^ (this.id_Usuario >>> 32));
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.password);
        hash = 29 * hash + (this.permisosAdministrador ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.facturasVenta);
        hash = 29 * hash + Objects.hashCode(this.pedidos);
        hash = 29 * hash + (this.eliminado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id_Usuario != other.id_Usuario) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.permisosAdministrador != other.permisosAdministrador) {
            return false;
        }
        if (!Objects.equals(this.facturasVenta, other.facturasVenta)) {
            return false;
        }
        if (!Objects.equals(this.pedidos, other.pedidos)) {
            return false;
        }
        if (this.eliminado != other.eliminado) {
            return false;
        }
        return true;
    }

}
