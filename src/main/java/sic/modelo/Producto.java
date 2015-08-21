package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "producto")
@NamedQueries({
    @NamedQuery(name = "Producto.buscarPorId",
            query = "SELECT p FROM Producto p WHERE p.eliminado = false AND p.id_Producto = :id"),
    @NamedQuery(name = "Producto.buscarPorDescripcion",
            query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion AND p.empresa = :empresa AND p.eliminado = false"),
    @NamedQuery(name = "Producto.buscarPorCodigo",
            query = "SELECT p FROM Producto p WHERE p.codigo = :codigo AND p.empresa = :empresa AND p.eliminado = false"),
    @NamedQuery(name = "Producto.buscarPorRubro",
            query = "SELECT p FROM Producto p WHERE p.rubro = :rubro AND p.empresa = :empresa AND p.eliminado = false ORDER BY p.descripcion ASC"),
    @NamedQuery(name = "Producto.buscarPorProveedor",
            query = "SELECT p FROM Producto p WHERE p.proveedor = :proveedor AND p.empresa = :empresa AND p.eliminado = false ORDER BY p.descripcion ASC")
})
public class Producto implements Serializable {

    @Id
    @GeneratedValue
    private long id_Producto;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String descripcion;

    private double cantidad;

    private double cantMinima;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Medida", referencedColumnName = "id_Medida")
    private Medida medida;

    private double precioCosto;
    private double ganancia_porcentaje;
    private double ganancia_neto;
    private double precioVentaPublico;
    private double iva_porcentaje;
    private double iva_neto;
    private double impuestoInterno_porcentaje;
    private double impuestoInterno_neto;
    private double precioLista;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Rubro", referencedColumnName = "id_Rubro")
    private Rubro rubro;

    private boolean ilimitado;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaModificacion;

    @Column(nullable = false)
    private String estanteria;

    @Column(nullable = false)
    private String estante;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Proveedor", referencedColumnName = "id_Proveedor")
    private Proveedor proveedor;

    @Column(nullable = false)
    private String nota;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

    public Producto() {
    }

    public long getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(long id) {
        this.id_Producto = id;
    }

    public double getCantMinima() {
        return cantMinima;
    }

    public void setCantMinima(double cantMinima) {
        this.cantMinima = cantMinima;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getPrecioVentaPublico() {
        return precioVentaPublico;
    }

    public void setPrecioVentaPublico(double precioVentaPublico) {
        this.precioVentaPublico = precioVentaPublico;
    }

    public double getGanancia_neto() {
        return ganancia_neto;
    }

    public void setGanancia_neto(double ganancia_neto) {
        this.ganancia_neto = ganancia_neto;
    }

    public double getGanancia_porcentaje() {
        return ganancia_porcentaje;
    }

    public void setGanancia_porcentaje(double ganancia_porcentaje) {
        this.ganancia_porcentaje = ganancia_porcentaje;
    }

    public boolean isIlimitado() {
        return ilimitado;
    }

    public void setIlimitado(boolean ilimitado) {
        this.ilimitado = ilimitado;
    }

    public double getImpuestoInterno_neto() {
        return impuestoInterno_neto;
    }

    public void setImpuestoInterno_neto(double impuestoInterno_neto) {
        this.impuestoInterno_neto = impuestoInterno_neto;
    }

    public double getImpuestoInterno_porcentaje() {
        return impuestoInterno_porcentaje;
    }

    public void setImpuestoInterno_porcentaje(double impuestoInterno_porcentaje) {
        this.impuestoInterno_porcentaje = impuestoInterno_porcentaje;
    }

    public double getIva_neto() {
        return iva_neto;
    }

    public void setIva_neto(double iva_neto) {
        this.iva_neto = iva_neto;
    }

    public double getIva_porcentaje() {
        return iva_porcentaje;
    }

    public void setIva_porcentaje(double iva_porcentaje) {
        this.iva_porcentaje = iva_porcentaje;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(double precioLista) {
        this.precioLista = precioLista;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.id_Producto ^ (this.id_Producto >>> 32));
        hash = 59 * hash + Objects.hashCode(this.descripcion);
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
        final Producto other = (Producto) obj;
        if (this.id_Producto != other.id_Producto) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

}
