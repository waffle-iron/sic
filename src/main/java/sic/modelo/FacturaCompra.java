package sic.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "facturacompra")
@NamedQueries({
    @NamedQuery(name = "FacturaCompra.buscarPorTipoSerieNum",
            query = "SELECT f FROM FacturaCompra f WHERE f.tipoFactura= :tipo AND f.numSerie= :serie AND f.numFactura= :num")
})
public class FacturaCompra extends Factura implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_Proveedor", referencedColumnName = "id_Proveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "facturaCompra")
    private List<PagoFacturaCompra> pagosFacturaCompra;

    public FacturaCompra() {
    }

    public List<PagoFacturaCompra> getPagosFacturaCompra() {
        return pagosFacturaCompra;
    }

    public void setPagosFacturaCompra(List<PagoFacturaCompra> pagosFacturaCompra) {
        this.pagosFacturaCompra = pagosFacturaCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
