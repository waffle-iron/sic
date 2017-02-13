package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

      Usuario findByNombreAndEliminado(String nombre, boolean eliminado);

      Usuario findByNombreAndPasswordAndEliminado(String nombre, String password ,boolean eliminado);

      List<Usuario> findAllByAndEliminadoOrderByNombreAsc(boolean eliminado);

      List<Usuario> findAllByAndPermisosAdministradorAndEliminadoOrderByNombreAsc(boolean permisosAdministrador, boolean eliminado);
    
}
