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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.buscarTodas",
            query = "SELECT e FROM Empresa e WHERE e.eliminada = false ORDER BY e.nombre ASC"),
    @NamedQuery(name = "Empresa.buscarPorId",
            query = "SELECT e FROM Empresa e WHERE e.id_Empresa = :id AND e.eliminada = false"),
    @NamedQuery(name = "Empresa.buscarPorNombre",
            query = "SELECT e FROM Empresa e WHERE e.nombre LIKE :nombre AND e.eliminada = false ORDER BY e.nombre ASC"),
    @NamedQuery(name = "Empresa.buscarPorCUIP",
            query = "SELECT e FROM Empresa e WHERE e.cuip = :cuip AND e.eliminada = false")
})
public class Empresa implements Serializable {

    @Id
    @GeneratedValue
    private long id_Empresa;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String lema;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_CondicionIVA", referencedColumnName = "id_CondicionIVA")
    private CondicionIVA condicionIVA;

    private long cuip;

    private long ingresosBrutos;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioActividad;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;

    @Lob
    private byte[] logo;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Producto> productos;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Proveedor> proveedores;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Factura> facturas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Transportista> transportistas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Cliente> clientes;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<FormaDePago> formasDePago;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Medida> medidas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<Rubro> rubros;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "empresa")
    private Set<ConfiguracionDelSistema> configuraciones;

    private boolean eliminada;

    public Empresa() {
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }

    public Set<FormaDePago> getFormasDePago() {
        return formasDePago;
    }

    public void setFormasDePago(Set<FormaDePago> formasDePago) {
        this.formasDePago = formasDePago;
    }

    public CondicionIVA getCondicionIVA() {
        return condicionIVA;
    }

    public void setCondicionIVA(CondicionIVA condicionIVA) {
        this.condicionIVA = condicionIVA;
    }

    public long getCuip() {
        return cuip;
    }

    public void setCuip(long cuip) {
        this.cuip = cuip;
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

    public Date getFechaInicioActividad() {
        return fechaInicioActividad;
    }

    public void setFechaInicioActividad(Date fechaInicioActividad) {
        this.fechaInicioActividad = fechaInicioActividad;
    }

    public long getId_Empresa() {
        return id_Empresa;
    }

    public void setId_Empresa(long id_Empresa) {
        this.id_Empresa = id_Empresa;
    }

    public long getIngresosBrutos() {
        return ingresosBrutos;
    }

    public void setIngresosBrutos(long ingresosBrutos) {
        this.ingresosBrutos = ingresosBrutos;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
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

    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Set<Transportista> getTransportistas() {
        return transportistas;
    }

    public void setTransportistas(Set<Transportista> transportistas) {
        this.transportistas = transportistas;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Set<Medida> getMedidas() {
        return medidas;
    }

    public void setMedidas(Set<Medida> medidas) {
        this.medidas = medidas;
    }

    public Set<Rubro> getRubros() {
        return rubros;
    }

    public void setRubros(Set<Rubro> rubros) {
        this.rubros = rubros;
    }

    public Set<ConfiguracionDelSistema> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(Set<ConfiguracionDelSistema> configuraciones) {
        this.configuraciones = configuraciones;
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
        hash = 47 * hash + (int) (this.id_Empresa ^ (this.id_Empresa >>> 32));
        hash = 47 * hash + Objects.hashCode(this.nombre);
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
        final Empresa other = (Empresa) obj;
        if (this.id_Empresa != other.id_Empresa) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}
