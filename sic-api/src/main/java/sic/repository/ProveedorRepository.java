package sic.repository;

import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Proveedor;

public interface ProveedorRepository extends PagingAndSortingRepository<Proveedor, Long>, QueryDslPredicateExecutor<Proveedor> {

      Proveedor findByCodigoAndEmpresaAndEliminado(String codigo, Empresa empresa, boolean eliminado);

      Proveedor findByIdFiscalAndEmpresaAndEliminado(String Idfiscal, Empresa empresa, boolean eliminado);

      Proveedor findByRazonSocialAndEmpresaAndEliminado(String razonSocial, Empresa empresa, boolean eliminado);

      List<Proveedor> findAllByAndEmpresaAndEliminadoOrderByRazonSocialAsc(Empresa empresa, boolean eliminado);
    
}
