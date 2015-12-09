package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "renglonfactura")
@NamedQueries({
    @NamedQuery(name = "RenglonFactura.getRenglonesDeLaFactura",
            query = "SELECT r FROM RenglonFactura r WHERE r.factura = :factura")
})
public class RenglonFactura implements Serializable {

    @Id
    @GeneratedValue
    private long id_RenglonFactura;

    @ManyToOne
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RenglonFactura other = (RenglonFactura) obj;
        if (this.id_RenglonFactura != other.id_RenglonFactura) {
            return false;
        }
        if (this.id_ProductoItem != other.id_ProductoItem) {
            return false;
        }
        if (!Objects.equals(this.codigoItem, other.codigoItem)) {
            return false;
        }
        if (!Objects.equals(this.descripcionItem, other.descripcionItem)) {
            return false;
        }
        if (!Objects.equals(this.medidaItem, other.medidaItem)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantidad) != Double.doubleToLongBits(other.cantidad)) {
            return false;
        }
        if (Double.doubleToLongBits(this.precioUnitario) != Double.doubleToLongBits(other.precioUnitario)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descuento_porcentaje) != Double.doubleToLongBits(other.descuento_porcentaje)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descuento_neto) != Double.doubleToLongBits(other.descuento_neto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.iva_porcentaje) != Double.doubleToLongBits(other.iva_porcentaje)) {
            return false;
        }
        if (Double.doubleToLongBits(this.iva_neto) != Double.doubleToLongBits(other.iva_neto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.impuesto_porcentaje) != Double.doubleToLongBits(other.impuesto_porcentaje)) {
            return false;
        }
        if (Double.doubleToLongBits(this.impuesto_neto) != Double.doubleToLongBits(other.impuesto_neto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ganancia_porcentaje) != Double.doubleToLongBits(other.ganancia_porcentaje)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ganancia_neto) != Double.doubleToLongBits(other.ganancia_neto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.importe) != Double.doubleToLongBits(other.importe)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id_RenglonFactura ^ (this.id_RenglonFactura >>> 32));
        hash = 89 * hash + (int) (this.id_ProductoItem ^ (this.id_ProductoItem >>> 32));
        hash = 89 * hash + Objects.hashCode(this.codigoItem);
        hash = 89 * hash + Objects.hashCode(this.descripcionItem);
        hash = 89 * hash + Objects.hashCode(this.medidaItem);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.cantidad) ^ (Double.doubleToLongBits(this.cantidad) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.precioUnitario) ^ (Double.doubleToLongBits(this.precioUnitario) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.descuento_porcentaje) ^ (Double.doubleToLongBits(this.descuento_porcentaje) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.descuento_neto) ^ (Double.doubleToLongBits(this.descuento_neto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.iva_porcentaje) ^ (Double.doubleToLongBits(this.iva_porcentaje) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.iva_neto) ^ (Double.doubleToLongBits(this.iva_neto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.impuesto_porcentaje) ^ (Double.doubleToLongBits(this.impuesto_porcentaje) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.impuesto_neto) ^ (Double.doubleToLongBits(this.impuesto_neto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.ganancia_porcentaje) ^ (Double.doubleToLongBits(this.ganancia_porcentaje) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.ganancia_neto) ^ (Double.doubleToLongBits(this.ganancia_neto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.importe) ^ (Double.doubleToLongBits(this.importe) >>> 32));
        return hash;
    }

}
