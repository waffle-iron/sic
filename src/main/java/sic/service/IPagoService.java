package sic.service;

import java.util.Date;
import java.util.List;
import sic.modelo.Factura;
import sic.modelo.Pago;

public interface IPagoService {

    List<Pago> getPagosDeLaFactura(Factura factura);

    double getSaldoAPagar(Factura factura);

    double getTotalPagado(Factura factura);

    void setFacturaEstadoDePago(Factura factura);

    List<Pago> getPagosEntreFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta);
    
    List<Factura> getFacturasEntreFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta);

    void guardar(Pago pago);

    void eliminar(Pago pago);

}
