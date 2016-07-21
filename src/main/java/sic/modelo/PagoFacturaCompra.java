package sic.modelo;

import java.io.Serializable;
import java.util.Date;
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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pagofacturacompra")
@NamedQueries({
    @NamedQuery(name = "PagoFacturaCompra.buscarPorFactura",
            query = "SELECT p FROM PagoFacturaCompra p "
                    + "WHERE p.facturaCompra = :factura AND p.eliminado = false "
                    + "ORDER BY p.fecha ASC")
})
@Data
@EqualsAndHashCode(of = {"fecha", "facturaCompra"})
public class PagoFacturaCompra implements Serializable {

    @Id
    @GeneratedValue
    private long id_PagoFacturaCompra;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_Factura", referencedColumnName = "id_Factura")
    private FacturaCompra facturaCompra;

    private double monto;

    @Column(nullable = false)
    private String nota;

    private boolean eliminado;

}
