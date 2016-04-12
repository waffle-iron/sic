package sic.repository;

import java.util.List;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;

public interface IPagoFacturaCompraRepository {

    void actualizar(PagoFacturaCompra pago);

    List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra);

    void guardar(PagoFacturaCompra pago);
    
}
