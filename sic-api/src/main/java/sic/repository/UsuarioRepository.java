package sic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

      @Query("SELECT u FROM Usuario u WHERE u.id_Usuario = :idUsuario AND u.eliminado = :eliminado") 
      Usuario findOne(@Param("idUsuario") long idUsuario, @Param("eliminado") boolean eliminado);
    
      Usuario findByNombreAndEliminado(String nombre, boolean eliminado);

      Usuario findByNombreAndPasswordAndEliminado(String nombre, String password ,boolean eliminado);

      List<Usuario> findAllByAndEliminadoOrderByNombreAsc(boolean eliminado);

      List<Usuario> findAllByAndPermisosAdministradorAndEliminadoOrderByNombreAsc(boolean permisosAdministrador, boolean eliminado);
    
}
