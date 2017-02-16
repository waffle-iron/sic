package sic.repository;

import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Transportista;

public interface TransportistaRepository extends PagingAndSortingRepository<Transportista, Long>, QueryDslPredicateExecutor<Transportista> {
    
      Transportista findByNombreAndEmpresaAndEliminado(String nombre, Empresa empresa, boolean eliminado);

      List<Transportista> findAllByAndEmpresaAndEliminado(Empresa empresa, boolean eliminado);

    
}
