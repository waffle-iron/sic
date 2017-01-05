package sic.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facturacompra")
@NamedQueries({
    @NamedQuery(name = "FacturaCompra.buscarPorTipoSerieNum",
            query = "SELECT f FROM FacturaCompra f "
                    + "WHERE f.tipoFactura= :tipo AND f.numSerie= :serie AND f.numFactura= :num")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FacturaCompra extends Factura implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_Proveedor", referencedColumnName = "id_Proveedor")
    private Proveedor proveedor;

}
