package sic.service;

import java.util.HashMap;
import java.util.List;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public interface IPedidoService {

    Pedido getPedidoPorId(Long id);
            
    void actualizar(Pedido pedido);

    List<Pedido> buscarConCriteria(BusquedaPedidoCriteria criteria);

    long calcularNumeroPedido(Empresa empresa);
    
    void actualizarEstadoPedido(TipoDeOperacion tipoDeOperacion, Pedido pedido);

    Pedido calcularTotalActualDePedido(Pedido pedido);

    boolean eliminar(Pedido pedido);

    List<Factura> getFacturasDelPedido(long id);

    Pedido getPedidoPorNumeroYEmpresa(long nroPedido, long idEmpresa);

    HashMap<Long, RenglonFactura> getRenglonesDeFacturasUnificadosPorNroPedido(long nroPedido);

    List<RenglonPedido> getRenglonesDelPedido(Long idPedido);

    byte[] getReportePedido(Pedido pedido);

    void guardar(Pedido pedido);

}
