package sic.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "Cliente.buscarTodos",
            query = "SELECT c FROM Cliente c WHERE c.empresa = :empresa AND c.eliminado = false ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorId",
            query = "SELECT c FROM Cliente c WHERE c.id_Cliente = :id AND c.eliminado = false"),
    @NamedQuery(name = "Cliente.buscarQueContengaRazonSocialNombreFantasiaIdFiscal",
            query = "SELECT c FROM Cliente c "
            + "WHERE (c.razonSocial LIKE :criteria OR c.nombreFantasia LIKE :criteria OR c.id_Fiscal LIKE :criteria) "
            + "AND c.empresa = :empresa AND c.eliminado = false "
            + "ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorRazonSocial",
            query = "SELECT c FROM Cliente c WHERE c.razonSocial = :razonSocial AND c.empresa = :empresa AND c.eliminado = false "
            + "ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorIdFiscal",
            query = "SELECT c FROM Cliente c WHERE c.id_Fiscal = :id_Fiscal AND c.eliminado = false AND c.empresa = :empresa"),
    @NamedQuery(name = "Cliente.buscarPredeterminado",
            query = "SELECT c FROM Cliente c WHERE c.predeterminado = true AND c.eliminado = false AND c.empresa = :empresa")
})
public class Cliente implements Serializable {

    @Id
    @GeneratedValue
    private long id_Cliente;

    @Column(nullable = false)
    private String razonSocial;

    private String nombreFantasia;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_CondicionIVA", referencedColumnName = "id_CondicionIVA")
    private CondicionIVA condicionIVA;

    @Column(nullable = false)
    private String id_Fiscal;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telPrimario;

    @Column(nullable = false)
    private String telSecundario;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

    private boolean predeterminado;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "cliente")
    private Set<FacturaVenta> facturasVenta;

    public Cliente() {
    }

    public Set<FacturaVenta> getFacturasVenta() {
        return facturasVenta;
    }

    public void setFacturasVenta(Set<FacturaVenta> facturasVenta) {
        this.facturasVenta = facturasVenta;
    }

    public CondicionIVA getCondicionIVA() {
        return condicionIVA;
    }

    public void setCondicionIVA(CondicionIVA condicionIVA) {
        this.condicionIVA = condicionIVA;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(long id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getId_Fiscal() {
        return id_Fiscal;
    }

    public void setId_Fiscal(String id_Fiscal) {
        this.id_Fiscal = id_Fiscal;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getTelPrimario() {
        return telPrimario;
    }

    public void setTelPrimario(String telPrimario) {
        this.telPrimario = telPrimario;
    }

    public String getTelSecundario() {
        return telSecundario;
    }

    public void setTelSecundario(String telSecundario) {
        this.telSecundario = telSecundario;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return razonSocial;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id_Cliente ^ (this.id_Cliente >>> 32));
        hash = 97 * hash + Objects.hashCode(this.razonSocial);
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
        final Cliente other = (Cliente) obj;
        if (this.id_Cliente != other.id_Cliente) {
            return false;
        }
        if (!Objects.equals(this.razonSocial, other.razonSocial)) {
            return false;
        }
        return true;
    }
}
