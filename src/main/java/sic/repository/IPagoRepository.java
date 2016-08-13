package sic.repository;

import java.util.List;
import sic.modelo.Factura;
import sic.modelo.Pago;

public interface IPagoRepository {

    void actualizar(Pago pago);

    List<Pago> getPagosDeLaFactura(Factura factura);

    void guardar(Pago pago);
}
