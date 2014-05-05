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
    @NamedQuery(name = "Medida.buscarTodas", query = "SELECT m FROM Medida m WHERE m.eliminada = false AND m.empresa = :empresa ORDER BY m.nombre ASC"),
    @NamedQuery(name = "Medida.buscarPorNombre", query = "SELECT m FROM Medida m WHERE m.eliminada = false AND m.nombre LIKE :nombre AND m.empresa = :empresa ORDER BY m.nombre ASC")
})
public class Medida implements Serializable {

    @Id
    @GeneratedValue
    private long id_Medida;
    @Column(nullable = false)
    private String nombre;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "medida")
    private Set<Producto> productos;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;
    private boolean eliminada;

    public Medida() {
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public long getId_Medida() {
        return id_Medida;
    }

    public void setId_Medida(long id_Medida) {
        this.id_Medida = id_Medida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof Medida)) {
            return false;
        }

        Medida medida = (Medida) o;
        if (id_Medida != medida.id_Medida) {
            return false;
        }

        if (nombre != null ? !nombre.equals(medida.nombre) : medida.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_Medida);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}
