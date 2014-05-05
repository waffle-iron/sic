package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.LocalidadRepository;
import sic.modelo.Localidad;
import sic.modelo.Provincia;
import sic.util.Validator;

public class LocalidadService {

    private LocalidadRepository modeloLocalidad = new LocalidadRepository();

    public List<Localidad> getLocalidadesDeLaProvincia(Provincia provincia) {
        return modeloLocalidad.getLocalidadesDeLaProvincia(provincia);
    }

    public void eliminar(Localidad localidad) {
        localidad.setEliminada(true);
        modeloLocalidad.actualizar(localidad);
    }

    public Localidad getLocalidadPorNombre(String nombre, Provincia provincia) {
        return modeloLocalidad.getLocalidadPorNombre(nombre, provincia);
    }

    private void validarOperacion(TipoDeOperacion operacion, Localidad localidad) {
        //Requeridos
        if (Validator.esVacio(localidad.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_localidad_vacio_nombre"));
        }
        if (localidad.getProvincia() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_localidad_provincia_vacio"));
        }
        //Duplicados
        //Nombre
        Localidad localidadDuplicada = this.getLocalidadPorNombre(localidad.getNombre(), localidad.getProvincia());
        if (operacion.equals(TipoDeOperacion.ALTA) && localidadDuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_localidad_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (localidadDuplicada != null && localidadDuplicada.getId_Localidad() != localidad.getId_Localidad()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_localidad_duplicado_nombre"));
            }
        }
    }

    public void actualizar(Localidad localidad) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, localidad);
        modeloLocalidad.actualizar(localidad);
    }

    public void guardar(Localidad localidad) {
        this.validarOperacion(TipoDeOperacion.ALTA, localidad);
        modeloLocalidad.guardar(localidad);
    }
}