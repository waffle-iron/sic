package sic.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "facturaventa")
@NamedQueries({
    @NamedQuery(name = "Factura.buscarPorTipoSerieNum",
            query = "SELECT f FROM FacturaVenta f WHERE f.tipoFactura = :tipo AND f.numSerie = :serie AND f.numFactura = :num"),
    @NamedQuery(name = "Factura.buscarMayorNumFacturaSegunTipo",
            query = "SELECT max(fv.numFactura) FROM FacturaVenta fv WHERE fv.tipoFactura = :tipo AND fv.numSerie = :serie"),
    @NamedQuery(name = "Factura.buscarTopProductosMasVendidosPorAnio",
            query = "SELECT renglones.descripcionItem, sum(renglones.cantidad) as suma "
            + "FROM FacturaVenta factura INNER JOIN factura.renglones renglones "
            + "WHERE year(factura.fecha) = :anio AND factura.eliminada = false "
            + "GROUP BY renglones.descripcionItem ORDER BY sum(renglones.cantidad) DESC")
})
public class FacturaVenta extends Factura implements Serializable {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario")
    private Usuario usuario;

    public FacturaVenta() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
