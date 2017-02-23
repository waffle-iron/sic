package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Pais;

public interface PaisRepository extends PagingAndSortingRepository<Pais, Long> {
    
      Pais findByNombreIsAndEliminadoOrderByNombreAsc(String nombre, boolean eliminado);

      List<Pais> findAllByAndEliminadoOrderByNombreAsc(boolean eliminado);
    
}
