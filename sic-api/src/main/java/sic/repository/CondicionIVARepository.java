package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.CondicionIVA;

public interface CondicionIVARepository extends PagingAndSortingRepository<CondicionIVA, Long>{

      CondicionIVA findByNombreIsAndEliminada(String nombre, boolean eliminada);

      List<CondicionIVA> findAllByAndEliminadaOrderByNombreAsc(boolean eliminada);
    
}
