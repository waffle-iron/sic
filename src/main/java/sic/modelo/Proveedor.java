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
@Table(name = "proveedor")
@NamedQueries({
    @NamedQuery(name = "Proveedor.buscarTodos",
            query = "SELECT p FROM Proveedor p WHERE p.empresa = :empresa AND p.eliminado = false ORDER BY p.razonSocial ASC"),
    @NamedQuery(name = "Proveedor.buscarPorCodigo",
            query = "SELECT p FROM Proveedor p WHERE p.codigo = :codigo AND p.empresa = :empresa AND p.eliminado = false"),
    @NamedQuery(name = "Proveedor.buscarPorRazonSocial",
            query = "SELECT p FROM Proveedor p WHERE p.razonSocial = :razonSocial AND p.empresa = :empresa AND p.eliminado = false"),
    @NamedQuery(name = "Proveedor.buscarPorIdFiscal",
            query = "SELECT p FROM Proveedor p WHERE p.id_Fiscal = :idFiscal AND p.empresa = :empresa AND p.eliminado = false")
})
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue
    private long id_Proveedor;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_CondicionIVA", referencedColumnName = "id_CondicionIVA")
    private CondicionIVA condicionIVA;

    @Column(nullable = false)
    private String id_Fiscal;

    @Column(nullable = false)
    private String telPrimario;

    @Column(nullable = false)
    private String telSecundario;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String web;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "proveedor")
    private Set<Producto> productos;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "proveedor")
    private Set<FacturaCompra> facturasCompra;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

    public Proveedor() {
    }

    public CondicionIVA getCondicionIVA() {
        return condicionIVA;
    }

    public void setCondicionIVA(CondicionIVA condicionIVA) {
        this.condicionIVA = condicionIVA;
    }

    public String getId_Fiscal() {
        return id_Fiscal;
    }

    public void setId_Fiscal(String id_Fiscal) {
        this.id_Fiscal = id_Fiscal;
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

    public long getId_Proveedor() {
        return id_Proveedor;
    }

    public void setId_Proveedor(long id) {
        this.id_Proveedor = id;
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

    public String getTelPrimario() {
        return telPrimario;
    }

    public void setTelPrimario(String telPrimario) {
        this.telPrimario = telPrimario;
    }

    public String getTelSecundario() {
        return telSecundario;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setTelSecundario(String telSecundario) {
        this.telSecundario = telSecundario;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return razonSocial;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Set<FacturaCompra> getFacturasCompra() {
        return facturasCompra;
    }

    public void setFacturasCompra(Set<FacturaCompra> facturasCompra) {
        this.facturasCompra = facturasCompra;
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
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id_Proveedor ^ (this.id_Proveedor >>> 32));
        hash = 79 * hash + Objects.hashCode(this.razonSocial);
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
        final Proveedor other = (Proveedor) obj;
        if (this.id_Proveedor != other.id_Proveedor) {
            return false;
        }
        if (!Objects.equals(this.razonSocial, other.razonSocial)) {
            return false;
        }
        return true;
    }

}
