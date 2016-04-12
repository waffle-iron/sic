package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;
import sic.repository.IFacturaRepository;
import sic.repository.IPagoFacturaCompraRepository;
import sic.service.IPagoFacturaDeCompraService;
import sic.service.ServiceException;

@Service
public class PagoFacturaDeCompraServiceImpl implements IPagoFacturaDeCompraService {

    private final IPagoFacturaCompraRepository pagoFacturaCompraRepository;
    private final IFacturaRepository facturaRepository;

    @Autowired
    public PagoFacturaDeCompraServiceImpl(IPagoFacturaCompraRepository pagoFacturaCompraRepository,
            IFacturaRepository facturaRepository) {

        this.pagoFacturaCompraRepository = pagoFacturaCompraRepository;
        this.facturaRepository = facturaRepository;
    }

    @Override
    public List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra) {
        return pagoFacturaCompraRepository.getPagosDeLaFactura(facturaCompra);
    }

    @Override
    public double getTotalPagado(FacturaCompra facturaCompra) {
        double resultado = 0.0;
        for (PagoFacturaCompra pago : this.getPagosDeLaFactura(facturaCompra)) {
            resultado += pago.getMonto();
        }
        return resultado;
    }

    @Override
    public double getSaldoAPagar(FacturaCompra facturaCompra) {
        double deuda = facturaCompra.getTotal();
        double pagado = this.getTotalPagado(facturaCompra);
        return deuda - pagado;
    }

    @Override
    @Transactional
    public void setFacturaEstadoDePago(FacturaCompra facturaCompra) {
        if (this.getTotalPagado(facturaCompra) >= facturaCompra.getTotal()) {
            facturaCompra.setPagada(true);
        } else {
            facturaCompra.setPagada(false);
        }
        facturaRepository.actualizar(facturaCompra);
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

    @Override
    @Transactional
    public void eliminar(PagoFacturaCompra pago) {
        pago.setEliminado(true);
        pagoFacturaCompraRepository.actualizar(pago);
    }

    @Override
    @Transactional
    public void guardar(PagoFacturaCompra pago) {
        this.validarOperacion(pago);
        pagoFacturaCompraRepository.guardar(pago);
    }
}
