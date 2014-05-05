package sic.modelo;

import java.io.Serializable;
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

@Entity
@NamedQueries({
    @NamedQuery(name = "Rubro.buscarTodos", query = "SELECT r FROM Rubro r WHERE r.eliminado = false AND r.empresa = :empresa ORDER BY r.nombre ASC"),
    @NamedQuery(name = "Rubro.buscarPorNombre", query = "SELECT r FROM Rubro r WHERE r.eliminado = false AND r.nombre LIKE :nombre AND r.empresa = :empresa ORDER BY r.nombre ASC")
})
public class Rubro implements Serializable {

    @Id
    @GeneratedValue
    private long id_Rubro;
    @Column(nullable = false)
    private String nombre;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "rubro")
    private Set<Producto> productos;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;
    private boolean eliminado;

    public Rubro() {
    }

    public long getId_Rubro() {
        return id_Rubro;
    }

    public void setId_Rubro(long id_Rubro) {
        this.id_Rubro = id_Rubro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
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

        if (!(o instanceof Rubro)) {
            return false;
        }

        Rubro rubro = (Rubro) o;
        if (id_Rubro != rubro.id_Rubro) {
            return false;
        }

        if (nombre != null ? !nombre.equals(rubro.nombre) : rubro.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_Rubro);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}
