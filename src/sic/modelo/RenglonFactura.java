package sic.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "RenglonFactura.getRenglonesDeLaFactura", query = "SELECT r FROM RenglonFactura r WHERE r.factura = :factura")
})
public class RenglonFactura implements Serializable {

    @Id
    @GeneratedValue
    private long id_RenglonFactura;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Factura", referencedColumnName = "id_Factura")
    private Factura factura;
    private long id_ProductoItem;
    @Column(nullable = false)
    private String codigoItem;
    @Column(nullable = false)
    private String descripcionItem;
    @Column(nullable = false)
    private String medidaItem;
    private double cantidad;
    private double precioUnitario;
    private double descuento_porcentaje;
    private double descuento_neto;
    private double iva_porcentaje;
    private double iva_neto;
    private double impuesto_porcentaje;
    private double impuesto_neto;
    private double ganancia_porcentaje;
    private double ganancia_neto;
    private double importe;

    public RenglonFactura() {
    }

    public long getId_ProductoItem() {
        return id_ProductoItem;
    }

    public void setId_ProductoItem(long id_ProductoItem) {
        this.id_ProductoItem = id_ProductoItem;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public double getGanancia_neto() {
        return ganancia_neto;
    }

    public void setGanancia_neto(double ganancia_neto) {
        this.ganancia_neto = ganancia_neto;
    }

    public double getImpuesto_neto() {
        return impuesto_neto;
    }

    public void setImpuesto_neto(double impuesto_neto) {
        this.impuesto_neto = impuesto_neto;
    }

    public double getIva_neto() {
        return iva_neto;
    }

    public void setIva_neto(double iva_neto) {
        this.iva_neto = iva_neto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
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

    public long getId_RenglonFactura() {
        return id_RenglonFactura;
    }

    public void setId_RenglonFactura(long id_RenglonFactura) {
        this.id_RenglonFactura = id_RenglonFactura;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getMedidaItem() {
        return medidaItem;
    }

    public void setMedidaItem(String medidaItem) {
        this.medidaItem = medidaItem;
    }

    public double getGanancia_porcentaje() {
        return ganancia_porcentaje;
    }

    public void setGanancia_porcentaje(double ganancia_porcentaje) {
        this.ganancia_porcentaje = ganancia_porcentaje;
    }

    public double getImpuesto_porcentaje() {
        return impuesto_porcentaje;
    }

    public void setImpuesto_porcentaje(double impuesto_porcentaje) {
        this.impuesto_porcentaje = impuesto_porcentaje;
    }

    public double getIva_porcentaje() {
        return iva_porcentaje;
    }

    public void setIva_porcentaje(double iva_porcentaje) {
        this.iva_porcentaje = iva_porcentaje;
    }
}
