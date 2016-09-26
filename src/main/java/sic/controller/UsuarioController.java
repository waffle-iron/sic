package sic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.Usuario;
import sic.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    
    private final IUsuarioService usuarioService;
    
    @Autowired
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario getUsuarioPorId(@PathVariable("id") long id) {
        return usuarioService.getUsuarioPorId(id);
    }
    
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario guardar(@RequestBody Usuario usuario) {
        usuarioService.guardar(usuario);
        return usuarioService.getUsuarioPorId(usuario.getId_Usuario());
    }
    
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") long id) {
        usuarioService.eliminar(usuarioService.getUsuarioPorId(id));
    }
    
    @GetMapping("/usuarios/activo")
    @ResponseStatus(HttpStatus.OK)
    public Usuario getUsuarioActivo() {
        return usuarioService.getUsuarioActivo().getUsuario();
    }
    
    @GetMapping("/usuarios/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }
    
}