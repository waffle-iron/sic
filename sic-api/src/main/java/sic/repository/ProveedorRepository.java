package sic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.Empresa;
import sic.modelo.Proveedor;

public interface ProveedorRepository extends PagingAndSortingRepository<Proveedor, Long>, QueryDslPredicateExecutor<Proveedor> {

      @Query("SELECT p FROM Proveedor p WHERE p.id_Proveedor = :idProveedor AND p.eliminado = :eliminado")   
      Proveedor findOne(@Param("idProveedor") long idProveedor, @Param("eliminado") boolean eliminado);
    
      Proveedor findByCodigoAndEmpresaAndEliminado(String codigo, Empresa empresa, boolean eliminado);

      Proveedor findByIdFiscalAndEmpresaAndEliminado(String Idfiscal, Empresa empresa, boolean eliminado);

      Proveedor findByRazonSocialAndEmpresaAndEliminado(String razonSocial, Empresa empresa, boolean eliminado);

      List<Proveedor> findAllByAndEmpresaAndEliminadoOrderByRazonSocialAsc(Empresa empresa, boolean eliminado);
    
}
