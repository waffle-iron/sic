package sic.service;

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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.repository.PedidoRepository;
import sic.util.Utilidades;

public class PedidoService {

    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private final EmpresaService empresaService = new EmpresaService();

    private void validarPedido(Pedido pedido) {
        //Entrada de Datos
        //Requeridos
        if (pedido.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_fecha_vacia"));
        }
        if (pedido.getRenglones().isEmpty()) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_renglones_vacio"));
        }
        if (pedido.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_empresa_vacia"));
        }
        if (pedido.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_usuario_vacio"));
        }
        //Duplicados
        if (pedidoRepository.getPedidoPorNro(pedido.getNroPedido(), pedido.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
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

    public Pedido calcularTotalActualDePedido(Pedido pedido) {
        double porcentajeDescuento;
        double totalActual = 0;
        for (RenglonPedido renglonPedido : pedidoRepository.getRenglonesDelPedido(pedido.getNroPedido())) {
            porcentajeDescuento = (1 - (renglonPedido.getDescuento_porcentaje() / 100));
            renglonPedido.setSubTotal((renglonPedido.getProducto().getPrecioLista() * renglonPedido.getCantidad() * porcentajeDescuento));
            totalActual += renglonPedido.getSubTotal();
        }
        pedido.setTotalActual(totalActual);
        return pedido;
    }

    public long calcularNumeroPedido() {
        return 1 + pedidoRepository.buscarMayorNroPedido(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
    }

    public List<Factura> getFacturasDelPedido(long nroPedido) {
        return pedidoRepository.getFacturasDelPedido(nroPedido);
    }

    public void guardar(Pedido pedido) {
        this.validarPedido(pedido);
        pedidoRepository.guardar(pedido);
    }

    public List<Pedido> buscarConCriteria(BusquedaPedidoCriteria criteria) {
        //Fecha
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
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
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_razonSocial"));
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true && criteria.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_usuario_vacio_nombre"));
        }
        List<Pedido> pedidos = pedidoRepository.buscarPedidosPorCriteria(criteria);
        return this.calcularTotalActualDePedidos(pedidos);
    }

    public void actualizar(Pedido pedido) {
        pedidoRepository.actualizar(pedido);
    }

    public Pedido getPedidoPorNumero(long nroPedido, long idEmpresa) {
        return pedidoRepository.getPedidoPorNro(nroPedido, idEmpresa);
    }

    public Pedido getPedidoPorNumeroConFacturas(long nroPedido) {
        return pedidoRepository.getPedidoPorNumeroConFacturas(nroPedido);
    }

    public Pedido getPedidoPorNumeroConRenglones(long nroPedido, long idEmpresa) {
        return pedidoRepository.getPedidoPorNumeroConRenglones(nroPedido, idEmpresa);
    }

    public void eliminar(Pedido pedido) {
        pedido.setEliminado(true);
        pedidoRepository.actualizar(pedido);
    }

    public List<RenglonPedido> getRenglonesDelPedido(long nroPedido) {
        return pedidoRepository.getRenglonesDelPedido(nroPedido);
    }

    public Pedido getPedidoPorNumeroConRenglonesActualizandoSubtotales(long nroPedido, long idEmpresa) {
        return this.actualizarSubTotalRenglonesPedido(this.getPedidoPorNumeroConRenglones(nroPedido, idEmpresa));
    }

    public HashMap<Long, RenglonFactura> getRenglonesDeFacturasUnificadosPorNroPedido(long nroPedido) {
        List<Factura> facturas = this.getFacturasDelPedido(nroPedido);
        List<RenglonFactura> renglonesDeFacturas = new ArrayList<>();
        HashMap<Long, RenglonFactura> listaRenglonesUnificados = new HashMap<>();
        if (!facturas.isEmpty()) {
            for (Factura factura : facturas) {
                renglonesDeFacturas.addAll(factura.getRenglones());
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

    public JasperPrint getReportePedido(Pedido pedido) throws JRException {
        ClassLoader classLoader = PedidoService.class.getClassLoader();
        InputStream isFileReport = classLoader.getResourceAsStream("sic/vista/reportes/Pedido.jasper");
        Map params = new HashMap();
        params.put("pedido", pedido);
        params.put("logo", Utilidades.convertirByteArrayIntoImage(pedido.getEmpresa().getLogo()));
        List<RenglonPedido> renglones = this.getRenglonesDelPedido(pedido.getNroPedido());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(renglones);
        return JasperFillManager.fillReport(isFileReport, params, ds);
    }
}
