package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Pais;
import sic.modelo.Provincia;

public interface ProvinciaRepository extends PagingAndSortingRepository<Provincia, Long> {
    
      Provincia findByNombreAndPaisAndEliminadaOrderByNombreAsc(String nombre, Pais pais, boolean eliminada);
      
      List<Provincia> findAllByAndPaisAndEliminada(Pais pais, boolean eliminada);

}
