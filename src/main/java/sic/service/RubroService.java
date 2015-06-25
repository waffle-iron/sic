package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.RubroRepository;
import sic.modelo.Empresa;
import sic.modelo.Rubro;
import sic.util.Validator;

public class RubroService {

    private final RubroRepository modeloRubro = new RubroRepository();

    public List<Rubro> getRubros(Empresa empresa) {
        return modeloRubro.getRubros(empresa);
    }

    public Rubro getRubroPorNombre(String nombre, Empresa empresa) {
        return modeloRubro.getRubroPorNombre(nombre, empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Rubro rubro) {
        //Requeridos
        if (Validator.esVacio(rubro.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle(
                    "Mensajes").getString("mensaje_rubro_nombre_vacio"));
        }
        //Duplicados
        //Nombre
        Rubro rubroDuplicado = this.getRubroPorNombre(rubro.getNombre(), rubro.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && rubroDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_rubro_nombre_duplicado"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (rubroDuplicado != null && rubroDuplicado.getId_Rubro() != rubro.getId_Rubro()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_rubro_nombre_duplicado"));
            }
        }
    }

    public void actualizar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, rubro);
        modeloRubro.actualizar(rubro);
    }

    public void guardar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ALTA, rubro);
        modeloRubro.guardar(rubro);
    }

    public void eliminar(Rubro rubro) {
        rubro.setEliminado(true);
        modeloRubro.actualizar(rubro);
    }
}