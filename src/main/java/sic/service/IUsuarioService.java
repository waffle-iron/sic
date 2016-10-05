package sic.service;

import java.util.List;
import sic.modelo.Usuario;

public interface IUsuarioService {
   
    Usuario getUsuarioPorId(long idUsuario);

    void actualizar(Usuario usuario);

    void eliminar(long idUsuario);

    Usuario getUsuarioPorNombre(String nombre);

    Usuario getUsuarioPorNombreContrasenia(String nombre, String contrasenia);

    List<Usuario> getUsuarios();

    List<Usuario> getUsuariosAdministradores();

    void guardar(Usuario usuario);
    
}
