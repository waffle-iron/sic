package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "formadepago")
@NamedQueries({
    @NamedQuery(name = "FormaDePago.buscarTodas",
            query = "SELECT f FROM FormaDePago f WHERE f.empresa = :empresa AND f.eliminada = false ORDER BY f.nombre ASC"),
    @NamedQuery(name = "FormaDePago.buscarPorId",
            query = "SELECT f FROM FormaDePago f WHERE f.eliminada = false AND f.id_FormaDePago = :id"),
    @NamedQuery(name = "FormaDePago.buscarPorNombre",
            query = "SELECT f FROM FormaDePago f WHERE f.eliminada = false AND f.empresa = :empresa AND f.nombre = :nombre"),
    @NamedQuery(name = "FormaDePago.buscarPredeterminada",
            query = "SELECT f FROM FormaDePago f WHERE f.predeterminado = true and f.empresa = :empresa and f.eliminada = false")
})
public class FormaDePago implements Serializable {

    @Id
    @GeneratedValue
    private long id_FormaDePago;

    @Column(nullable = false)
    private String nombre;

    private boolean afectaCaja;

    private boolean predeterminado;

    @OneToMany(mappedBy = "formaPago")
    private Set<Factura> facturas;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminada;

    public FormaDePago() {
    }

    public long getId_FormaDePago() {
        return id_FormaDePago;
    }

    public void setId_FormaDePago(long id_FormaDePago) {
        this.id_FormaDePago = id_FormaDePago;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public boolean isAfectaCaja() {
        return afectaCaja;
    }

    public void setAfectaCaja(boolean afectaCaja) {
        this.afectaCaja = afectaCaja;
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id_FormaDePago ^ (this.id_FormaDePago >>> 32));
        hash = 59 * hash + Objects.hashCode(this.nombre);
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
        final FormaDePago other = (FormaDePago) obj;
        if (this.id_FormaDePago != other.id_FormaDePago) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}
