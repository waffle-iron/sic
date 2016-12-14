package sic.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
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
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.repository.IPedidoRepository;
import sic.modelo.EstadoPedido;
import sic.modelo.Movimiento;
import sic.service.IFacturaService;
import sic.service.IPedidoService;
import sic.service.BusinessServiceException;
import sic.service.ServiceException;
import sic.modelo.TipoDeOperacion;
import sic.util.Utilidades;

@Service
public class PedidoServiceImpl implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IFacturaService facturaService;    
    private static final Logger LOGGER = Logger.getLogger(PedidoServiceImpl.class.getPackage().getName());

    @Autowired
    public PedidoServiceImpl(IFacturaService facturaService, IPedidoRepository pedidoRepository) {
        this.facturaService = facturaService;
        this.pedidoRepository = pedidoRepository;        
    }

    @Override
    public Pedido getPedidoPorId(Long idPedido) {
        Pedido pedido = this.pedidoRepository.getPedidoPorId(idPedido);
        if (pedido == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_no_existente"));
        }
        return pedido;
    }
    
    private void validarPedido(TipoDeOperacion operacion, Pedido pedido) {
        //Entrada de Datos
        //Requeridos
        if (pedido.getFecha() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_fecha_vacia"));
        }        
        if (pedido.getRenglones() == null || pedido.getRenglones().isEmpty()) {  
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
        //Validar Estado
        EstadoPedido estado = pedido.getEstado();
        if ((estado != EstadoPedido.ABIERTO) && (estado != EstadoPedido.ACTIVO) && (estado != EstadoPedido.CERRADO)) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaja_estado_no_valido"));
        }
        if (operacion == TipoDeOperacion.ALTA) {
            //Duplicados       
            if (pedidoRepository.getPedidoPorNro(pedido.getNroPedido(), pedido.getEmpresa().getId_Empresa()) != null) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_pedido_duplicado"));
            }
        }
        if (operacion == TipoDeOperacion.ACTUALIZACION) {
            //Duplicados       
            if (pedidoRepository.getPedidoPorNro(pedido.getNroPedido(), pedido.getEmpresa().getId_Empresa()) == null) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_pedido_no_existente"));
            }
        }
        
    }

    private List<Pedido> calcularTotalActualDePedidos(List<Pedido> pedidos) {
        pedidos.stream().forEach((pedido) -> {
            this.calcularTotalActualDePedido(pedido);
        });
        return pedidos;
    }

    @Override
    public Pedido actualizarEstadoPedido(Pedido pedido, List<Factura> facturas) {
        for (Factura f : facturas) {
            pedido.setEstado(EstadoPedido.ACTIVO);
            if (this.getFacturasDelPedido(pedido.getId_Pedido()).isEmpty()) {
                pedido.setEstado(EstadoPedido.ABIERTO);
            }
            if (facturaService.getRenglonesPedidoParaFacturar(pedido, "Factura " + f.getTipoFactura()).isEmpty()) {
                pedido.setEstado(EstadoPedido.CERRADO);
            }
        }
        return pedido;
    }

    @Override
    public Pedido calcularTotalActualDePedido(Pedido pedido) {
        double porcentajeDescuento;
        double totalActual = 0;
        for (RenglonPedido renglonPedido : this.getRenglonesDelPedido(pedido.getId_Pedido())) {
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
    public List<Factura> getFacturasDelPedido(long idPedido) {
        List<Factura> facturasSinEliminar = new ArrayList<>();
        List<Factura> facturasDePedido = facturaService.getFacturasDelPedido(idPedido);
        if (facturasDePedido != null) {
            facturasDePedido.stream().filter((Factura f) -> !f.isEliminada()).forEach((f) -> {
                facturasSinEliminar.add(f);
            });
        }
        return facturasSinEliminar;
    }

    @Override
    @Transactional
    public Pedido guardar(Pedido pedido) {
        this.validarPedido(TipoDeOperacion.ALTA , pedido);
        pedido.setNroPedido(this.calcularNumeroPedido(pedido.getEmpresa()));
        pedido = pedidoRepository.guardar(pedido);
        LOGGER.warn("El Pedido " + pedido + " se guardó correctamente.");
        return pedido;
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
        //Empresa
        if (criteria.getEmpresa() == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
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
        this.validarPedido(TipoDeOperacion.ACTUALIZACION , pedido);
        pedidoRepository.actualizar(pedido);
    }

    @Override
    public Pedido getPedidoPorNumeroYEmpresa(long nroPedido, long idEmpresa) {
        return pedidoRepository.getPedidoPorNro(nroPedido, idEmpresa);
    }

    @Override
    @Transactional
    public boolean eliminar(long idPedido) {
        Pedido pedido = this.getPedidoPorId(idPedido);
        if (pedido == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_no_existente"));
        }
        if (pedido.getEstado() == EstadoPedido.ABIERTO) {
            pedido.setEliminado(true);
            pedidoRepository.actualizar(pedido);
        }
        return pedido.isEliminado();
    }

    @Override
    public List<RenglonPedido> getRenglonesDelPedido(Long idPedido) {       
        return this.getPedidoPorId(idPedido).getRenglones();
    }

    @Override
    public HashMap<Long, RenglonFactura> getRenglonesDeFacturasUnificadosPorNroPedido(long nroPedido) {
        List<RenglonFactura> renglonesDeFacturas = new ArrayList<>();
//        this.getFacturasDelPedido(nroPedido).stream().forEach((f) -> {
          for (Factura f : this.getFacturasDelPedido(nroPedido)) {
            for (RenglonFactura r : f.getRenglones()) {
//            f.getRenglones().stream().forEach((r) -> {
                renglonesDeFacturas.add(facturaService.calcularRenglon("Factura " + f.getTipoFactura(),
                        Movimiento.VENTA, r.getCantidad(),r.getId_ProductoItem(), r.getDescuento_porcentaje()));
            }}
//            });
//        });       
        HashMap<Long, RenglonFactura> listaRenglonesUnificados = new HashMap<>();
        if (!renglonesDeFacturas.isEmpty()) {
            for (RenglonFactura renglon : renglonesDeFacturas) {
//            renglonesDeFacturas.stream().forEach((renglon) -> {
                if (listaRenglonesUnificados.containsKey(renglon.getId_ProductoItem())) {
                    listaRenglonesUnificados.get(renglon.getId_ProductoItem())
                            .setCantidad(listaRenglonesUnificados
                            .get(renglon.getId_ProductoItem()).getCantidad() + renglon.getCantidad());
                } else {
                    listaRenglonesUnificados.put(renglon.getId_ProductoItem(), renglon);
                }}
//            });
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
  
}
