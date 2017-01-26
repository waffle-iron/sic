package sic.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Producto;

public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long>,
        QueryDslPredicateExecutor<Producto>, ProductoRepositoryCustom {

    Producto findByCodigoAndEmpresaAndEliminado(String codigo, Empresa empresa, boolean eliminado);
    
    Producto findByDescripcionAndEmpresaAndEliminado(String descripcion, Empresa empresa, boolean eliminado);
    
}
