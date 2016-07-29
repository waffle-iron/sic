package sic.service;

import java.util.List;
import sic.modelo.Factura;
import sic.modelo.Pago;

public interface IPagoService {
    
    List<Pago> getPagosDeLaFactura(Factura factura);
    
    double getSaldoAPagar(Factura factura);
    
    double getTotalPagado(Factura factura);
    
    void setFacturaEstadoDePago(Factura factura);
    
    void guardar(Pago pago);
    
    void eliminar(Pago pago);
    
}
