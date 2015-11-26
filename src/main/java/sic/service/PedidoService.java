package sic.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private final EmpresaService empresaService = new EmpresaService();

    public long calcularNumeroPedido() {
        return pedidoRepository.calcularNumeroPedido(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
    }

    public List<Pedido> buscarConCriteria(BusquedaPedidoCriteria criteria) {
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
                    .getString("mensaje_pedido_cliente_vacio"));
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true && criteria.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_usuario_vacio"));
        }

        return pedidoRepository.buscarPedidosPorCriteria(criteria);
    }

    public List<Factura> getFacturasDelPedido(long nroPedido) {
        return pedidoRepository.getFacturasDelPedido(nroPedido);
    }

    public void validarPedido(Pedido pedido) {
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
    }

    public void guardar(Pedido pedido) {
        this.validarPedido(pedido);
        pedidoRepository.guardar(pedido);
    }

    public void actualizar(Pedido pedido) {
        pedidoRepository.actualizar(pedido);
    }

    public Pedido getPedidoPorId(long id_Pedido) {
        return pedidoRepository.getPedidoPorId(id_Pedido);
    }

    public Pedido getPedidoPorNumero(long nroPedido) {
        return pedidoRepository.getPedidoPorNumero(nroPedido);
    }

    public Pedido getPedidoPorNumeroConFacturas(long nroPedido) {
        return pedidoRepository.getPedidoPorNumeroConFacturas(nroPedido);
    }

    public Pedido getPedidoPorNumeroConRenglones(long nroPedido) {
        return pedidoRepository.getPedidoPorNumeroConRenglones(nroPedido);
    }

    public void eliminar(Pedido pedido) {
        pedido.setEliminado(true);
        pedidoRepository.actualizar(pedido);
    }

    public List<RenglonPedido> getRenglonesDelPedido(long idPedido) {
        return pedidoRepository.getRenglonesDelPedido(idPedido);
    }

    public HashMap<Long, RenglonFactura> getRenglonesUnificadosPorIdProducto(long nroPedido) {
        List<Factura> facturas = this.getFacturasDelPedido(nroPedido);
        List<RenglonFactura> renglonesDeFacturas = new ArrayList<>();
        HashMap<Long, RenglonFactura> Lista = new HashMap<>();
        if (!facturas.isEmpty()) {
            for (Factura factura : facturas) {
                renglonesDeFacturas.addAll(factura.getRenglones());
            }
            for (RenglonFactura renglon : renglonesDeFacturas) {
                if (Lista.containsKey(renglon.getId_ProductoItem())) {
                    Lista.get(renglon.getId_ProductoItem())
                            .setCantidad(Lista.get(renglon.getId_ProductoItem()).getCantidad() + renglon.getCantidad());
                } else {
                    Lista.put(renglon.getId_ProductoItem(), renglon);
                }
            }
        }
        renglonesDeFacturas.clear();
        return Lista;
    }
    
}
