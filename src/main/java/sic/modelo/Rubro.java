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
@Table(name = "rubro")
@NamedQueries({
    @NamedQuery(name = "Rubro.buscarTodos",
            query = "SELECT r FROM Rubro r WHERE r.eliminado = false AND r.empresa = :empresa ORDER BY r.nombre ASC"),
    @NamedQuery(name = "Rubro.buscarPorNombre",
            query = "SELECT r FROM Rubro r WHERE r.eliminado = false AND r.nombre LIKE :nombre AND r.empresa = :empresa ORDER BY r.nombre ASC")
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
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id_Rubro ^ (this.id_Rubro >>> 32));
        hash = 53 * hash + Objects.hashCode(this.nombre);
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
        final Rubro other = (Rubro) obj;
        if (this.id_Rubro != other.id_Rubro) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
