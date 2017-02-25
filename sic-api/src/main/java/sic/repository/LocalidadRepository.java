package sic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.Localidad;
import sic.modelo.Provincia;

public interface LocalidadRepository extends PagingAndSortingRepository<Localidad, Long> {
    
      @Query("SELECT l FROM Localidad l WHERE l.id_Localidad = :idLocalidad AND l.eliminada = :eliminada")
      Localidad findOne(@Param("idLocalidad") long idLocalidad, @Param("eliminada") boolean eliminada);
      
      Localidad findByNombreAndProvinciaAndEliminadaOrderByNombreAsc(String nombre, Provincia provincia, boolean eliminada);
      
      List<Localidad> findAllByAndProvinciaAndEliminada(Provincia provincia, boolean eliminada);
    
}
