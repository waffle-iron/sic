package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Rubro;

public interface RubroRepository extends PagingAndSortingRepository<Rubro, Long> {

//    Rubro getRubroPorId(Long id_Rubro);
//    
//    void actualizar(Rubro rubro);
//
      Rubro findByNombreAndEmpresaAndEliminado(String nombre, Empresa empresa, boolean eliminado); 
//    Rubro getRubroPorNombre(String nombre, Empresa empresa);
//
      List<Rubro> findAllByAndEmpresaAndEliminado(Empresa empresa, boolean eliminado);
//    List<Rubro> getRubros(Empresa empresa);
//
//    Rubro guardar(Rubro rubro);
    
}
