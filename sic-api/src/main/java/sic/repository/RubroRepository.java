package sic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.Empresa;
import sic.modelo.Rubro;

public interface RubroRepository extends PagingAndSortingRepository<Rubro, Long> {
    
      @Query("SELECT r FROM Rubro r WHERE r.id_Rubro = :idRubro AND r.eliminado = :eliminado")
      Rubro findOne(@Param("idRubro") long idRubro, @Param("eliminado") boolean eliminado);

      Rubro findByNombreAndEmpresaAndEliminado(String nombre, Empresa empresa, boolean eliminado); 

      List<Rubro> findAllByAndEmpresaAndEliminado(Empresa empresa, boolean eliminado);
    
}
