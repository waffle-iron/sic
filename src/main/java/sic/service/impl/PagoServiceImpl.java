package sic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Factura;
import sic.modelo.Pago;
import sic.repository.IFacturaRepository;
import sic.repository.IPagoRepository;
import sic.service.IPagoService;
import sic.service.ServiceException;

@Service
public class PagoServiceImpl implements IPagoService {

    @PersistenceContext
    private EntityManager em;

    private final IPagoRepository pagoRepository;
    private final IFacturaRepository facturaRepository;

    @Autowired
    public PagoServiceImpl(IPagoRepository pagoRepository,
            IFacturaRepository facturaRepository) {

        this.pagoRepository = pagoRepository;
        this.facturaRepository = facturaRepository;
    }

    @Override
    public List<Pago> getPagosDeLaFactura(Factura factura) {
        return this.pagoRepository.getPagosDeLaFactura(factura);
    }

    @Override
    public double getSaldoAPagar(Factura factura) {
        double saldo = factura.getTotal();
        for (Pago pago : this.getPagosDeLaFactura(factura)) {
                saldo = saldo - pago.getMonto();
        }
        if (saldo < 0) {
            saldo = 0.0;
        }
        return saldo;
    }

    @Override
    public double getTotalPagado(Factura factura) {
        double pagado = 0.0;
        for (Pago pago : this.getPagosDeLaFactura(factura)) {
            pagado = pagado + pago.getMonto();
        }
        return pagado;
    }

    @Override
    @Transactional
    public void setFacturaEstadoDePago(Factura factura) {
        if (this.getTotalPagado(factura) >= factura.getTotal()) {
            factura.setPagada(true);
        } else {
            factura.setPagada(false);
        }
        facturaRepository.actualizar(factura);
    }

    @Override
    public List<Pago> getPagosEntreFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        TypedQuery<Pago> typedQuery = em.createNamedQuery("Pago.buscarPagosEntreFechasYFormaDePago", Pago.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("id_FormaDePago", id_FormaDePago);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Pago> Pagos = typedQuery.getResultList();
        return Pagos;
    }

    @Override
    public List<Factura> getFacturasEntreFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        List<Pago> pagos = this.getPagosEntreFechasYFormaDePago(id_Empresa, id_FormaDePago, desde, hasta);
        List<Factura> facturas = new ArrayList<>();
        for (Pago pago : pagos) {
            facturas.add(pago.getFactura());
        }
        return facturas;
    }

    @Override
    @Transactional
    public void guardar(Pago pago) {
        this.validarOperacion(pago);
        pagoRepository.guardar(pago);
    }

    @Override
    @Transactional
    public void eliminar(Pago pago) {
        pago.setEliminado(true);
        pagoRepository.actualizar(pago);
    }

    private void validarOperacion(Pago pago) {
        //Requeridos
        if (pago.getMonto() <= 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_mayorQueCero_monto"));
        }
        if (pago.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_fecha_vacia"));
        }
        if (pago.getFactura() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_factura_vacia"));
        }
        if (pago.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_empresa_vacia"));
        }
        if (pago.getFormaDePago() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pago_formaDePago_vacia"));
        }
    }

}
