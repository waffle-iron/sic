package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Localidad;
import sic.modelo.Provincia;

public interface LocalidadRepository extends PagingAndSortingRepository<Localidad, Long> {
    
      Localidad findByNombreAndProvinciaAndEliminadaOrderByNombreAsc(String nombre, Provincia provincia, boolean eliminada);
      
      List<Localidad> findAllByAndProvinciaAndEliminada(Provincia provincia, boolean eliminada);
    
}
