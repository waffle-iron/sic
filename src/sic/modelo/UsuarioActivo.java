package sic.modelo;

import java.util.Date;

public class UsuarioActivo {

    private static UsuarioActivo INSTANCE = new UsuarioActivo();
    private Usuario usuario;
    private Date fechaHoraIngreso;

    private UsuarioActivo() {
    }

    public static UsuarioActivo getInstance() {
        return INSTANCE;
    }

    public Date getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(Date fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
