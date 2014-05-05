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
    @NamedQuery(name = "CondicionIVA.buscarTodas", query = "SELECT c FROM CondicionIVA c WHERE c.eliminada = false ORDER BY c.nombre ASC"),
    @NamedQuery(name = "CondicionIVA.buscarPorNombre", query = "SELECT c FROM CondicionIVA c WHERE c.nombre LIKE :nombre AND c.eliminada = false")
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof CondicionIVA)) {
            return false;
        }

        CondicionIVA condicionIVA = (CondicionIVA) o;
        if (id_CondicionIVA != condicionIVA.id_CondicionIVA) {
            return false;
        }

        if (nombre != null ? !nombre.equals(condicionIVA.nombre) : condicionIVA.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_CondicionIVA);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}
