package sic.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Pedido;

public interface PedidoRepository extends PagingAndSortingRepository<Pedido, Long>,
        QueryDslPredicateExecutor<Pedido> {
    
      Pedido findByNroPedidoAndEmpresaAndEliminado(long nroPedido, Empresa empresa, boolean eliminado);
      
      Pedido findTopByEmpresaAndEliminadoOrderByNroPedidoDesc(Empresa empresa, boolean eliminado);

}