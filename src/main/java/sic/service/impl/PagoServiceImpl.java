package sic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
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
    private static final Logger LOGGER = Logger.getLogger(PagoServiceImpl.class.getPackage().getName());

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
        this.setFacturaEstadoDePago(pago.getFactura());
        LOGGER.warn("El Pago: " + pago.toString() + " se guardó correctamente.");
    }

    @Override
    @Transactional
    public void eliminar(Pago pago) {
        pago.setEliminado(true);
        pagoRepository.actualizar(pago);
        this.setFacturaEstadoDePago(pago.getFactura());
        LOGGER.warn("El Pago: " + pago.toString() + " se eliminó correctamente.");
    }

    @Override
    public void pagoMultiplesFacturasVenta(List<FacturaVenta> facturasVenta, double monto, FormaDePago formaDePago, String nota, Date fechaYHora) {
        List<Factura> facturas = new ArrayList<>();
        facturas.addAll(facturasVenta);
        this.pagarFacturas(facturas, monto, formaDePago, nota, fechaYHora);
    }
    
    @Override
    public void pagoMultiplesFacturasCompra(List<FacturaCompra> facturasCompra, double monto, FormaDePago formaDePago, String nota, Date fechaYHora) {
        List<Factura> facturas = new ArrayList<>();
        facturas.addAll(facturasCompra);
        this.pagarFacturas(facturas, monto, formaDePago, nota, fechaYHora);
    }

    @Override
    public double calcularTotalAdeudadoFacturasVenta(List<FacturaVenta> facturasVenta) {
        List<Factura> facturas = new ArrayList<>();
        facturas.addAll(facturasVenta);
        return this.calcularTotalAdeudadoFacturas(facturas);
    }
    
    @Override
    public double calcularTotalAdeudadoFacturasCompra(List<FacturaCompra> facturasCompra) {
        List<Factura> facturas = new ArrayList<>();
        facturas.addAll(facturasCompra);
        return this.calcularTotalAdeudadoFacturas(facturas);
    }

    private double calcularTotalAdeudadoFacturas(List<Factura> facturas) {
        double total = 0.0;
        for (Factura factura : facturas) {
            total += this.getSaldoAPagar(factura);
        }
        return total;
    }

    private void pagarFacturas(List<Factura> facturas, double monto, FormaDePago formaDePago, String nota, Date fechaYHora) {
        List<Factura> facturasOrdenadas = this.ordenarFacturasPorFechaDesc(facturas);
        int longitudListaFacturas = facturasOrdenadas.size() - 1;
        for (int i = longitudListaFacturas; i >= 0; i--) {
            if (monto > 0) {
                Factura factura = facturasOrdenadas.get(i);
                factura.setPagos(this.getPagosDeLaFactura(factura));
                Pago nuevoPago = Pago.builder()
                        .formaDePago(formaDePago)
                        .factura(factura)
                        .fecha(fechaYHora)
                        .empresa(factura.getEmpresa())
                        .nota(nota)
                        .build();
                double saldoAPagar = this.getSaldoAPagar(factura);
                if (saldoAPagar <= monto) {
                    monto = monto - saldoAPagar;
                    nuevoPago.setMonto(saldoAPagar);
                } else {
                    nuevoPago.setMonto(monto);
                    monto = 0;
                }
                this.guardar(nuevoPago);
            }
        }
    }

    private List<Factura> ordenarFacturasPorFechaDesc(List<Factura> facturas) {
        Factura facturaAux;
        for (int i = 0; i < facturas.size() - 1; i++) {
            for (int j = 0; j < facturas.size() - 1; j++) {
                if (facturas.get(j).getFecha().before(facturas.get(j + 1).getFecha())) {
                    facturaAux = facturas.get(j);
                    facturas.set(j, facturas.get(j + 1));
                    facturas.set(j + 1, facturaAux);
                }
            }
        }
        return facturas;
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

    @Transactional
    private void setFacturaEstadoDePago(Factura factura) {
        double totalFactura = Math.floor(factura.getTotal() * 100) / 100;
        if (this.getTotalPagado(factura) >= totalFactura) {
            factura.setPagada(true);
        } else {
            factura.setPagada(false);
        }
        facturaRepository.actualizar(factura);
    }

}
