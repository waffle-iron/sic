package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Pais;
import sic.modelo.Provincia;

public interface ProvinciaRepository extends PagingAndSortingRepository<Provincia, Long> {
    
//    Provincia getProvinciaPorId(Long id_Provincia);
//
//    void actualizar(Provincia provincia);
//
      Provincia findByNombreAndPaisAndEliminadaOrderByNombreAsc(String nombre, Pais pais, boolean eliminada);
//    Provincia getProvinciaPorNombre(String nombreProvincia, Pais paisRelacionado);
//
//    List<Provincia> getProvinciasDelPais(Pais pais);
      
      List<Provincia> findAllByAndPaisAndEliminada(Pais pais, boolean eliminada);
//
//    Provincia guardar(Provincia provincia);
    
}
