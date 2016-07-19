package sic.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "facturaventa")
@NamedQueries({
    @NamedQuery(name = "Factura.buscarPorTipoSerieNum",
            query = "SELECT f FROM FacturaVenta f LEFT JOIN FETCH f.renglones WHERE f.tipoFactura = :tipo AND f.numSerie = :serie AND f.numFactura = :num"),
    @NamedQuery(name = "Factura.buscarMayorNumFacturaSegunTipo",
            query = "SELECT max(fv.numFactura) FROM FacturaVenta fv WHERE fv.tipoFactura = :tipo AND fv.numSerie = :serie"),
    @NamedQuery(name = "Factura.buscarTopProductosMasVendidosPorAnio",
            query = "SELECT renglones.descripcionItem, sum(renglones.cantidad) as suma "
            + "FROM FacturaVenta factura INNER JOIN factura.renglones renglones "
            + "WHERE year(factura.fecha) = :anio AND factura.eliminada = false "
            + "GROUP BY renglones.descripcionItem ORDER BY sum(renglones.cantidad) DESC")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FacturaVenta extends Factura implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario")
    private Usuario usuario;

}
