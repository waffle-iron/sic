package sic.service;

import java.util.List;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;

public interface IPagoFacturaDeCompraService {

    void eliminar(PagoFacturaCompra pago);

    List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra);

    double getSaldoAPagar(FacturaCompra facturaCompra);

    double getTotalPagado(FacturaCompra facturaCompra);

    void guardar(PagoFacturaCompra pago);

    void setFacturaEstadoDePago(FacturaCompra facturaCompra);
    
}
