package sic.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Empresa;
import sic.modelo.EmpresaActiva;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.repository.IPedidoRepository;
import sic.service.EstadoPedido;
import sic.service.IFacturaService;
import sic.service.IPedidoService;
import sic.service.IProductoService;
import sic.service.BusinessServiceException;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;

@Service
public class PedidoServiceImpl implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IFacturaService facturaService;
    private final IProductoService productoService;
    private static final Logger LOGGER = Logger.getLogger(PedidoServiceImpl.class.getPackage().getName());

    @Autowired
    public PedidoServiceImpl(IFacturaService facturaService,
            IPedidoRepository pedidoRepository, IProductoService productoService) {
        this.facturaService = facturaService;
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    @Override
    public Pedido getPedidoPorId(Long id) {
        return this.pedidoRepository.getPedidoPorId(id);
    }

    private void validarPedido(Pedido pedido) {
        //Entrada de Datos
        //Requeridos
        if (pedido.getFecha() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_fecha_vacia"));
        }
        if (pedido.getRenglones().isEmpty()) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_renglones_vacio"));
        }
        if (pedido.getEmpresa() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_empresa_vacia"));
        }
        if (pedido.getUsuario() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_usuario_vacio"));
        }
        //Duplicados       
        if (pedidoRepository.getPedidoPorNro(pedido.getNroPedido(), pedido.getEmpresa().getId_Empresa()) != null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_duplicado"));
        }
    }

    private List<Pedido> calcularTotalActualDePedidos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            this.calcularTotalActualDePedido(pedido);
        }
        return pedidos;
    }

    private Pedido actualizarSubTotalRenglonesPedido(Pedido pedido) {
        double porcentajeDescuento;
        for (RenglonPedido renglonPedido : pedido.getRenglones()) {
            porcentajeDescuento = (1 - (renglonPedido.getDescuento_porcentaje() / 100));
            renglonPedido.setSubTotal(renglonPedido.getCantidad() * renglonPedido.getProducto().getPrecioLista() * porcentajeDescuento);
        }
        return pedido;
    }

    @Override
    @Transactional
    public void actualizarEstadoPedido(TipoDeOperacion tipoDeOperacion, Pedido pedido) {
        if (pedido != null) {
            if (tipoDeOperacion == TipoDeOperacion.ELIMINACION) {
                if (this.getFacturasDelPedido(pedido.getNroPedido()).isEmpty()) {
                    pedido.setEstado(EstadoPedido.ABIERTO);
                } else {
                    pedido.setEstado(EstadoPedido.ACTIVO);
                }
            }
            if (tipoDeOperacion == TipoDeOperacion.ALTA) {
                if (facturaService.convertirRenglonesPedidoARenglonesFactura(pedido, "Factura A").isEmpty()) {
                    pedido.setEstado(EstadoPedido.CERRADO);
                } else {
                    pedido.setEstado(EstadoPedido.ACTIVO);
                }
            }
            this.actualizar(pedido);
        }
    }

    @Override
    public Pedido calcularTotalActualDePedido(Pedido pedido) {
        double porcentajeDescuento;
        double totalActual = 0;
        for (RenglonPedido renglonPedido : pedidoRepository.getRenglonesDelPedido(pedido.getId_Pedido())) {
            porcentajeDescuento = (1 - (renglonPedido.getDescuento_porcentaje() / 100));
            renglonPedido.setSubTotal((renglonPedido.getProducto().getPrecioLista() * renglonPedido.getCantidad() * porcentajeDescuento));
            totalActual += renglonPedido.getSubTotal();
        }
        pedido.setTotalActual(totalActual);
        return pedido;
    }

    @Override
    public long calcularNumeroPedido(Empresa empresa) {
        return 1 + pedidoRepository.buscarMayorNroPedido(empresa.getId_Empresa());
    }

    @Override
    public List<Factura> getFacturasDelPedido(long id_pedido) {
        List<Factura> facturasSinEliminar = new ArrayList<>();
        for (Factura factura : pedidoRepository.getPedidoPorNumeroConFacturas(id_pedido).getFacturas()) {
            if (!factura.isEliminada()) {
                facturasSinEliminar.add(factura);
            }
        }
        return facturasSinEliminar;
    }

    @Override
    @Transactional
    public void guardar(Pedido pedido) {
        this.validarPedido(pedido);
        pedido.setNroPedido(this.calcularNumeroPedido(pedido.getEmpresa()));
        pedidoRepository.guardar(pedido);
        LOGGER.warn("El Pedido " + pedido + " se guardó correctamente.");
    }

    @Override
    public List<Pedido> buscarConCriteria(BusquedaPedidoCriteria criteria) {
        //Fecha
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_fechas_busqueda_invalidas"));
        }
        if (criteria.isBuscaPorFecha() == true) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(criteria.getFechaDesde());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            criteria.setFechaDesde(cal.getTime());
            cal.setTime(criteria.getFechaHasta());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            criteria.setFechaHasta(cal.getTime());
        }
        //Cliente
        if (criteria.isBuscaCliente() == true && criteria.getCliente() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_razonSocial"));
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true && criteria.getUsuario() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_usuario_vacio_nombre"));
        }
        List<Pedido> pedidos = pedidoRepository.buscarPedidosPorCriteria(criteria);
        return this.calcularTotalActualDePedidos(pedidos);
    }

    @Override
    @Transactional
    public void actualizar(Pedido pedido) {
        this.validarPedido(pedido);
        pedidoRepository.actualizar(pedido);
    }

    @Override
    public Pedido getPedidoPorNumeroYEmpresa(long nroPedido, long idEmpresa) {
        return pedidoRepository.getPedidoPorNro(nroPedido, idEmpresa);
    }

    @Override
    public Pedido getPedidoPorNumeroConFacturas(long nroPedido) {
        return pedidoRepository.getPedidoPorNumeroConFacturas(nroPedido);
    }

    @Override
    public Pedido getPedidoPorIdConRenglones(long idPedido) {
        return pedidoRepository.getPedidoPorIdConRenglones(idPedido);
    }

    @Override
    @Transactional
    public boolean eliminar(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.ABIERTO) {
            pedido.setEliminado(true);
            pedidoRepository.actualizar(pedido);
        }
        return pedido.isEliminado();
    }

    @Override
    public List<RenglonPedido> getRenglonesDelPedido(Long idPedido) {
        return pedidoRepository.getRenglonesDelPedido(idPedido);
    }

    @Override
    public Pedido getPedidoPorNumeroConRenglonesActualizandoSubtotales(long nroPedido) {
        Pedido pedidoConRenglones = pedidoRepository.getPedidoPorNro(nroPedido, EmpresaActiva.getInstance().getEmpresa().getId_Empresa());
        pedidoConRenglones.setRenglones(pedidoRepository.getRenglonesDelPedido(pedidoConRenglones.getId_Pedido()));
        return this.actualizarSubTotalRenglonesPedido(pedidoConRenglones);
    }

    @Override
    public HashMap<Long, RenglonFactura> getRenglonesDeFacturasUnificadosPorNroPedido(long nroPedido) {
        List<Factura> facturas = this.getFacturasDelPedido(nroPedido);
        List<RenglonFactura> renglonesDeFacturas = new ArrayList<>();
        HashMap<Long, RenglonFactura> listaRenglonesUnificados = new HashMap<>();
        if (!facturas.isEmpty()) {
            for (Factura factura : facturas) {
                renglonesDeFacturas.addAll(facturaService.getRenglonesDeLaFactura(factura));
            }
            for (RenglonFactura renglon : renglonesDeFacturas) {
                if (listaRenglonesUnificados.containsKey(renglon.getId_ProductoItem())) {
                    listaRenglonesUnificados.get(renglon.getId_ProductoItem())
                            .setCantidad(listaRenglonesUnificados
                                    .get(renglon.getId_ProductoItem()).getCantidad() + renglon.getCantidad());
                } else {
                    listaRenglonesUnificados.put(renglon.getId_ProductoItem(), renglon);
                }
            }
        }
        return listaRenglonesUnificados;
    }

    @Override
    public byte[] getReportePedido(Pedido pedido) {
        ClassLoader classLoader = PedidoServiceImpl.class.getClassLoader();
        InputStream isFileReport = classLoader.getResourceAsStream("sic/vista/reportes/Pedido.jasper");
        Map params = new HashMap();
        params.put("pedido", pedido);
        params.put("logo", Utilidades.convertirByteArrayIntoImage(pedido.getEmpresa().getLogo()));
        List<RenglonPedido> renglones = this.getRenglonesDelPedido(pedido.getId_Pedido());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(renglones);
        try {
            return JasperExportManager.exportReportToPdf(JasperFillManager.fillReport(isFileReport, params, ds));
        } catch (JRException ex) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_error_reporte"), ex);
        }
    }

    @Override
    public RenglonPedido convertirRenglonFacturaARenglonPedido(RenglonFactura renglonFactura) {
        RenglonPedido nuevoRenglon = new RenglonPedido();
        nuevoRenglon.setCantidad(renglonFactura.getCantidad());
        nuevoRenglon.setDescuento_porcentaje(renglonFactura.getDescuento_porcentaje());
        nuevoRenglon.setDescuento_neto(renglonFactura.getDescuento_neto());
        Producto producto = productoService.getProductoPorId(renglonFactura.getId_ProductoItem());
        nuevoRenglon.setProducto(producto);
        nuevoRenglon.setSubTotal(renglonFactura.getImporte());
        return nuevoRenglon;
    }

    @Override
    public List<RenglonPedido> convertirRenglonesFacturaARenglonesPedido(List<RenglonFactura> renglonesDeFactura) {
        List<RenglonPedido> renglonesPedido = new ArrayList();
        for (RenglonFactura renglonFactura : renglonesDeFactura) {
            renglonesPedido.add(this.convertirRenglonFacturaARenglonPedido(renglonFactura));
        }
        return renglonesPedido;
    }
}
