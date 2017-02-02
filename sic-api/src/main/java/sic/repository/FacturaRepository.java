package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.Pedido;

public interface FacturaRepository extends PagingAndSortingRepository<Factura, Long>, FacturaRepositoryCustom {

    Factura findByTipoFacturaAndNumSerieAndNumFacturaAndEmpresaAndEliminada(char tipo, long serie, long num, Empresa empresa, boolean eliminada);

    List<Factura> findAllByPedidoAndEliminada(Pedido pedido, boolean eliminada);

    Factura findTopByTipoFacturaAndNumSerieOrderByNumFactura(char tipoDeFactura, long numSerie);

}
