package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.PaisRepository;
import sic.modelo.Pais;
import sic.util.Validator;

public class PaisService {

    private final PaisRepository modeloPais = new PaisRepository();

    public List<Pais> getPaises() {
        return modeloPais.getPaises();
    }

    public Pais getPaisPorNombre(String nombre) {
        return modeloPais.getPaisPorNombre(nombre);
    }

    private void validarOperacion(TipoDeOperacion operacion, Pais pais) {
        //Obligatorios
        if (Validator.esVacio(pais.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pais_vacio_nombre"));
        }
        //Duplicados
        //Nombre
        Pais paisDuplicado = this.getPaisPorNombre(pais.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && paisDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pais_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (paisDuplicado != null && paisDuplicado.getId_Pais() != pais.getId_Pais()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_pais_duplicado_nombre"));
            }
        }
    }

    public void eliminar(Pais pais) {
        pais.setEliminado(true);
        modeloPais.actualizar(pais);
    }

    public void actualizar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, pais);
        modeloPais.actualizar(pais);
    }

    public void guardar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ALTA, pais);
        modeloPais.guardar(pais);
    }
}