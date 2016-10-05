package sic.controller;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.Usuario;
import sic.service.BusinessServiceException;
import sic.service.IUsuarioService;
import sic.util.Utilidades;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    
    private final IUsuarioService usuarioService;
    
    @Autowired
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/usuarios/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario getUsuarioPorId(@PathVariable long idUsuario) {
        return usuarioService.getUsuarioPorId(idUsuario);
    }
    
    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }
    
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario guardar(@RequestBody Usuario usuario) {
        usuarioService.guardar(usuario);
        return usuarioService.getUsuarioPorNombre(usuario.getNombre());
    }
    
    @DeleteMapping("/usuarios/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable long idUsuario) {
        usuarioService.eliminar(idUsuario);
    }
    
    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public Usuario getUsuarioPorNombreContrasenia(@RequestParam String nombre,
                                                  @RequestParam String password) {
        
        Usuario usuario = usuarioService.getUsuarioPorNombreContrasenia(nombre, Utilidades.encriptarConMD5(password));
        if (usuario == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_usuario_logInInvalido"));
        } else {
            return usuario;
        }
    }
    
}