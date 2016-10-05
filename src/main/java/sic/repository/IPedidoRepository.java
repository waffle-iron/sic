package sic.repository;

import java.util.List;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Pedido;
import sic.modelo.RenglonPedido;

public interface IPedidoRepository {
    
    Pedido getPedidoPorId(Long id);

    void actualizar(Pedido pedido);

    long buscarMayorNroPedido(long idEmpresa);

    List<Pedido> buscarPedidosPorCriteria(BusquedaPedidoCriteria criteria);

    Pedido getPedidoPorNro(long nroPedido, long idEmpresa);

    void guardar(Pedido pedido);

}