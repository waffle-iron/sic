package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "localidad")
@NamedQueries({
    @NamedQuery(name = "Localidad.buscarTodas",
            query = "SELECT l FROM Localidad l WHERE l.eliminada = false ORDER BY l.nombre ASC"),
    @NamedQuery(name = "Localidad.buscarLocalidadesDeLaProvincia",
            query = "SELECT l FROM Localidad l WHERE l.provincia = :provincia AND l.eliminada = false ORDER BY l.nombre ASC"),
    @NamedQuery(name = "Localidad.buscarPorNombre",
            query = "SELECT l FROM Localidad l WHERE l.provincia = :provincia AND l.eliminada = false AND l.nombre = :nombre ORDER BY l.nombre ASC")
})
public class Localidad implements Serializable {

    @Id
    @GeneratedValue
    private long id_Localidad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String codigoPostal;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Provincia", referencedColumnName = "id_Provincia")
    private Provincia provincia;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "localidad")
    private Set<Proveedor> proveedores;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "localidad")
    private Set<Transportista> transportistas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "localidad")
    private Set<Empresa> empresas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "localidad")
    private Set<Cliente> clientes;

    private boolean eliminada = false;

    public Localidad() {
    }

    public long getId_Localidad() {
        return id_Localidad;
    }

    public void setId_Localidad(long id_Localidad) {
        this.id_Localidad = id_Localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public Set<Transportista> getTransportistas() {
        return transportistas;
    }

    public void setTransportistas(Set<Transportista> transportistas) {
        this.transportistas = transportistas;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id_Localidad ^ (this.id_Localidad >>> 32));
        hash = 67 * hash + Objects.hashCode(this.nombre);
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
        final Localidad other = (Localidad) obj;
        if (this.id_Localidad != other.id_Localidad) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
