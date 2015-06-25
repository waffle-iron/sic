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

@Entity
@NamedQueries({
    @NamedQuery(name = "Provincia.buscarProvinciasDelPais", query = "SELECT p FROM Provincia p WHERE p.pais = :pais AND p.eliminada = false ORDER BY p.nombre ASC"),
    @NamedQuery(name = "Provincia.buscarPorNombre", query = "SELECT p FROM Provincia p WHERE p.pais = :pais AND p.eliminada = false AND p.nombre = :nombre ORDER BY p.nombre ASC")
})
public class Provincia implements Serializable {

    @Id
    @GeneratedValue
    private long id_Provincia;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Pais", referencedColumnName = "id_Pais")
    private Pais pais;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "provincia")
    private Set<Localidad> localidades;

    private boolean eliminada = false;

    public Provincia() {
    }

    public long getId_Provincia() {
        return id_Provincia;
    }

    public void setId_Provincia(long id_Provincia) {
        this.id_Provincia = id_Provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Set<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(Set<Localidad> localidades) {
        this.localidades = localidades;
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
        int hash = 3;
        hash = 97 * hash + (int) (this.id_Provincia ^ (this.id_Provincia >>> 32));
        hash = 97 * hash + Objects.hashCode(this.nombre);
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
        final Provincia other = (Provincia) obj;
        if (this.id_Provincia != other.id_Provincia) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
