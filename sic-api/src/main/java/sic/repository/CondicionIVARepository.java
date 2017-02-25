package sic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.CondicionIVA;

public interface CondicionIVARepository extends PagingAndSortingRepository<CondicionIVA, Long>{
    
      @Query("SELECT c FROM CondicionIVA c WHERE c.id_CondicionIVA = :idCondicionIVA AND c.eliminada = :eliminada")
      CondicionIVA findOne(@Param("idCondicionIVA") long idCondicionIVA, @Param("eliminada") boolean eliminada);

      CondicionIVA findByNombreIsAndEliminada(String nombre, boolean eliminada);

      List<CondicionIVA> findAllByAndEliminadaOrderByNombreAsc(boolean eliminada);
    
}
