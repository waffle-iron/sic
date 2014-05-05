package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "PagoFacturaCompra.buscarPorFactura", query = "SELECT p FROM PagoFacturaCompra p WHERE p.facturaCompra = :factura AND p.eliminado = false ORDER BY p.fecha ASC")
})
public class PagoFacturaCompra implements Serializable {

    @Id
    @GeneratedValue
    private long id_PagoFacturaCompra;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Factura", referencedColumnName = "id_Factura")
    private FacturaCompra facturaCompra;
    private double monto;
    @Column(nullable = false)
    private String nota;
    private boolean eliminado;

    public PagoFacturaCompra() {
    }

    public FacturaCompra getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(FacturaCompra facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public long getId_PagoFacturaCompra() {
        return id_PagoFacturaCompra;
    }

    public void setId_PagoFacturaCompra(long id_PagoFacturaCompra) {
        this.id_PagoFacturaCompra = id_PagoFacturaCompra;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
