package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Factura implements Serializable {

    @Id
    @GeneratedValue
    private long id_Factura;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private char tipoFactura;

    private long numSerie;

    private long numFactura;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_FormaDePago", referencedColumnName = "id_FormaDePago")
    private FormaDePago formaPago;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Transportista", referencedColumnName = "id_Transportista")
    private Transportista transportista;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "factura")
    private List<RenglonFactura> renglones;

    private double subTotal;
    private double recargo_porcentaje;
    private double recargo_neto;
    private double descuento_porcentaje;
    private double descuento_neto;
    private double subTotal_neto;
    private double iva_105_neto;
    private double iva_21_neto;
    private double impuestoInterno_neto;
    private double total;

    @Column(nullable = false)
    private String observaciones;

    private boolean pagada;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminada;

    public Factura() {
    }

    public List<RenglonFactura> getRenglones() {
        return renglones;
    }

    public void setRenglones(List<RenglonFactura> renglones) {
        this.renglones = renglones;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    public double getDescuento_neto() {
        return descuento_neto;
    }

    public void setDescuento_neto(double descuento_neto) {
        this.descuento_neto = descuento_neto;
    }

    public double getDescuento_porcentaje() {
        return descuento_porcentaje;
    }

    public void setDescuento_porcentaje(double descuento_porcentaje) {
        this.descuento_porcentaje = descuento_porcentaje;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public FormaDePago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaDePago formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public long getId_Factura() {
        return id_Factura;
    }

    public void setId_Factura(long id_Factura) {
        this.id_Factura = id_Factura;
    }

    public long getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(long numFactura) {
        this.numFactura = numFactura;
    }

    public long getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(long numSerie) {
        this.numSerie = numSerie;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getRecargo_neto() {
        return recargo_neto;
    }

    public void setRecargo_neto(double recargo_neto) {
        this.recargo_neto = recargo_neto;
    }

    public double getRecargo_porcentaje() {
        return recargo_porcentaje;
    }

    public void setRecargo_porcentaje(double recargo_porcentaje) {
        this.recargo_porcentaje = recargo_porcentaje;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public char getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(char tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public double getImpuestoInterno_neto() {
        return impuestoInterno_neto;
    }

    public void setImpuestoInterno_neto(double impuestoInterno_neto) {
        this.impuestoInterno_neto = impuestoInterno_neto;
    }

    public double getSubTotal_neto() {
        return subTotal_neto;
    }

    public void setSubTotal_neto(double subTotal_neto) {
        this.subTotal_neto = subTotal_neto;
    }

    public double getIva_105_neto() {
        return iva_105_neto;
    }

    public void setIva_105_neto(double iva_105_neto) {
        this.iva_105_neto = iva_105_neto;
    }

    public double getIva_21_neto() {
        return iva_21_neto;
    }

    public void setIva_21_neto(double iva_21_neto) {
        this.iva_21_neto = iva_21_neto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factura other = (Factura) obj;
        if (this.id_Factura != other.id_Factura) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (this.tipoFactura != other.tipoFactura) {
            return false;
        }
        if (this.numSerie != other.numSerie) {
            return false;
        }
        if (this.numFactura != other.numFactura) {
            return false;
        }
        if (!Objects.equals(this.renglones, other.renglones)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id_Factura ^ (this.id_Factura >>> 32));
        hash = 29 * hash + Objects.hashCode(this.fecha);
        hash = 29 * hash + this.tipoFactura;
        hash = 29 * hash + (int) (this.numSerie ^ (this.numSerie >>> 32));
        hash = 29 * hash + (int) (this.numFactura ^ (this.numFactura >>> 32));
        hash = 29 * hash + Objects.hashCode(this.renglones);
        return hash;
    }
}
