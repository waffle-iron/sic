package sic.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private final EmpresaService empresaService = new EmpresaService();

    private long calcularNumeroPedido() {
        return pedidoRepository.calcularNumeroPedido(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
    }

    public List<Pedido> buscarPedidos(BusquedaPedidoCriteria criteria) {
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedidos_fechas_busqueda_invalidas"));
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

        if (criteria.isBuscaPorNumeroPedido() == true && criteria.getNumPedido() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pedido_numeroPedido_vacio"));
        }
        return pedidoRepository.buscarPedido(criteria);
    }

}
