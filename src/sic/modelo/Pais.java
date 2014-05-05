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
    @NamedQuery(name = "Pais.buscarTodos", query = "SELECT p FROM Pais p WHERE p.eliminado = false ORDER BY p.nombre ASC"),
    @NamedQuery(name = "Pais.buscarPorNombre", query = "SELECT p FROM Pais p WHERE p.eliminado = false AND p.nombre LIKE :nombre ORDER BY p.nombre ASC")
})
public class Pais implements Serializable {

    @Id
    @GeneratedValue
    private long id_Pais;
    @Column(nullable = false)
    private String nombre;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pais")
    private Set<Provincia> provincias;
    private boolean eliminado = false;

    public Pais() {
    }

    public long getId_Pais() {
        return id_Pais;
    }

    public void setId_Pais(long id_Pais) {
        this.id_Pais = id_Pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Set<Provincia> provincias) {
        this.provincias = provincias;
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

        if (!(o instanceof Pais)) {
            return false;
        }

        Pais pais = (Pais) o;
        if (id_Pais != pais.id_Pais) {
            return false;
        }

        if (nombre != null ? !nombre.equals(pais.nombre) : pais.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_Pais);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}