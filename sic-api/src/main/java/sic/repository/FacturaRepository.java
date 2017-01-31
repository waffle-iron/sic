package sic.repository;

import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.Pedido;

public interface FacturaRepository<T extends Factura> extends PagingAndSortingRepository<Factura, Long>, QueryDslPredicateExecutor<Factura> , FacturaRepositoryCustom {

      Factura findByTipoFacturaAndNumSerieAndNumFacturaAndEmpresaAndEliminada(char tipo, long serie, long num, Empresa empresa, boolean eliminada);
    
      List<Factura> findAllByPedidoAndEliminada(Pedido pedido, boolean eliminada);
      
      Factura findTopByTipoFacturaAndNumSerieOrderByNumFactura(String tipoDeFactura, long numSerie); // no borrar aún
       
}
