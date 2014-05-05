package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.FacturaRepository;
import sic.repository.PagoFacturaCompraRepository;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;

public class PagoFacturaDeCompraService {

    private PagoFacturaCompraRepository modeloPagoFacturaCompra = new PagoFacturaCompraRepository();
    private FacturaRepository modeloFactura = new FacturaRepository();

    public List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra) {
        return modeloPagoFacturaCompra.getPagosDeLaFactura(facturaCompra);
    }

    public double getTotalPagado(FacturaCompra facturaCompra) {
        double resultado = 0.0;
        for (PagoFacturaCompra pago : this.getPagosDeLaFactura(facturaCompra)) {
            resultado += pago.getMonto();
        }
        return resultado;
    }

    public double getSaldoAPagar(FacturaCompra facturaCompra) {
        double deuda = facturaCompra.getTotal();
        double pagado = this.getTotalPagado(facturaCompra);
        return deuda - pagado;
    }

    public void setFacturaEstadoDePago(FacturaCompra facturaCompra) {
        if (this.getTotalPagado(facturaCompra) >= facturaCompra.getTotal()) {
            facturaCompra.setPagada(true);
        } else {
            facturaCompra.setPagada(false);
        }
        modeloFactura.actualizar(facturaCompra);
    }

    private void validarOperacion(PagoFacturaCompra pago) {
        //Requeridos
        if (pago.getMonto() <= 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_mayorQueCero_monto"));
        }
        if (pago.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_fecha_vacia"));
        }
        if (pago.getFacturaCompra() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_factura_vacia"));
        }
    }

    public void eliminar(PagoFacturaCompra pago) {
        pago.setEliminado(true);
        modeloPagoFacturaCompra.actualizar(pago);
    }

    public void guardar(PagoFacturaCompra pago) {
        this.validarOperacion(pago);
        modeloPagoFacturaCompra.guardar(pago);
    }
}