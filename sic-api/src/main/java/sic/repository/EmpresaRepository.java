package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;

public interface EmpresaRepository extends PagingAndSortingRepository<Empresa, Long>{
    
      Empresa findByCuipAndEliminada(long cuip, boolean eliminada);

      Empresa findByNombreIsAndEliminadaOrderByNombreAsc(String nombre, boolean eliminada);

      List<Empresa> findAllByAndEliminadaOrderByNombreAsc(boolean eliminada); 
    
}
