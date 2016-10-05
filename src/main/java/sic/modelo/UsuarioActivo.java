package sic.modelo;

public class UsuarioActivo {

    private static final UsuarioActivo INSTANCE = new UsuarioActivo();
    private Usuario usuario;    
    
    private UsuarioActivo() {}

    //Singleton
    public static UsuarioActivo getInstance() {
        return INSTANCE;
    }    

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
