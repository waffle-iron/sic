package sic.modelo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class FacturaCompra extends Factura implements Serializable {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Proveedor", referencedColumnName = "id_Proveedor")
    private Proveedor proveedor;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "facturaCompra")
    private Set<PagoFacturaCompra> pagosFacturaCompra;

    public FacturaCompra() {
    }

    public Set<PagoFacturaCompra> getPagosFacturaCompra() {
        return pagosFacturaCompra;
    }

    public void setPagosFacturaCompra(Set<PagoFacturaCompra> pagosFacturaCompra) {
        this.pagosFacturaCompra = pagosFacturaCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
