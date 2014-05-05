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
    @NamedQuery(name = "Transportista.buscarTodos", query = "SELECT t FROM Transportista t WHERE t.empresa = :empresa AND t.eliminado = false ORDER BY t.nombre ASC"),
    @NamedQuery(name = "Transportista.buscarPorNombre", query = "SELECT t FROM Transportista t WHERE t.empresa = :empresa AND t.nombre = :nombre AND t.eliminado = false ORDER BY t.nombre ASC")
})
public class Transportista implements Serializable {

    @Id
    @GeneratedValue
    private long id_Transportista;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String direccion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;
    @Column(nullable = false)
    private String web;
    @Column(nullable = false)
    private String telefono;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "transportista")
    private Set<Factura> facturas;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;
    private boolean eliminado;

    public Transportista() {
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }

    public long getId_Transportista() {
        return id_Transportista;
    }

    public void setId_Transportista(long id_Transportista) {
        this.id_Transportista = id_Transportista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof Transportista)) {
            return false;
        }

        Transportista trans = (Transportista) o;
        if (id_Transportista != trans.id_Transportista) {
            return false;
        }

        if (nombre != null ? !nombre.equals(trans.nombre) : trans.nombre != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (int) (89 * hash + this.id_Transportista);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);

        return hash;
    }
}
