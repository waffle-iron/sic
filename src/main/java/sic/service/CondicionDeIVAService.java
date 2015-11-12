package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.jpa.CondicionIVARepositoryJPAImpl;
import sic.modelo.CondicionIVA;
import sic.util.Validator;

public class CondicionDeIVAService {

    private final CondicionIVARepositoryJPAImpl modeloCondicionIVA = new CondicionIVARepositoryJPAImpl();

    public List<CondicionIVA> getCondicionesIVA() {
        return modeloCondicionIVA.getCondicionesIVA();
    }

    public void actualizar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVA);
        modeloCondicionIVA.actualizar(condicionIVA);
    }

    public void guardar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
        modeloCondicionIVA.guardar(condicionIVA);
    }

    public void eliminar(CondicionIVA condicionIVA) {
        condicionIVA.setEliminada(true);
        modeloCondicionIVA.actualizar(condicionIVA);
    }

    public CondicionIVA getCondicionIVAPorNombre(String nombre) {
        return modeloCondicionIVA.getCondicionIVAPorNombre(nombre);
    }

    private void validarOperacion(TipoDeOperacion operacion, CondicionIVA condicionIVA) {
        //Requeridos
        if (Validator.esVacio(condicionIVA.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_requerido"));
        }
        //Duplicados
        CondicionIVA condicionIVADuplicada = this.getCondicionIVAPorNombre(condicionIVA.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && condicionIVADuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_duplicado"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (condicionIVADuplicada != null && condicionIVADuplicada.getId_CondicionIVA() != condicionIVA.getId_CondicionIVA()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_condicionIVA_nombre_duplicado"));
            }
        }
    }
}