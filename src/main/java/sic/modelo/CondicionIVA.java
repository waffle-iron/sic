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
@Table(name = "condicioniva")
@NamedQueries({
    @NamedQuery(name = "CondicionIVA.buscarTodas",
            query = "SELECT c FROM CondicionIVA c WHERE c.eliminada = false ORDER BY c.nombre ASC"),
    @NamedQuery(name = "CondicionIVA.buscarPorNombre",
            query = "SELECT c FROM CondicionIVA c WHERE c.nombre LIKE :nombre AND c.eliminada = false")
})
public class CondicionIVA implements Serializable {

    @Id
    @GeneratedValue
    private long id_CondicionIVA;

    @Column(nullable = false)
    private String nombre;

    private boolean discriminaIVA;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "condicionIVA")
    private Set<Proveedor> proveedores;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "condicionIVA")
    private Set<Empresa> empresas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "condicionIVA")
    private Set<Cliente> clientes;

    private boolean eliminada;

    public CondicionIVA() {
    }

    public long getId_CondicionIVA() {
        return id_CondicionIVA;
    }

    public void setId_CondicionIVA(long id) {
        this.id_CondicionIVA = id;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDiscriminaIVA() {
        return discriminaIVA;
    }

    public void setDiscriminaIVA(boolean discriminaIVA) {
        this.discriminaIVA = discriminaIVA;
    }

    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
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

    @Override
    public String toString() {
        if (discriminaIVA) {
            return nombre + " (discrimina IVA)";
        } else {
            return nombre + " (no discrimina IVA)";
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.id_CondicionIVA ^ (this.id_CondicionIVA >>> 32));
        hash = 47 * hash + Objects.hashCode(this.nombre);
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
        final CondicionIVA other = (CondicionIVA) obj;
        if (this.id_CondicionIVA != other.id_CondicionIVA) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}
