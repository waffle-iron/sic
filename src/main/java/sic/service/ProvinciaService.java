package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.ProvinciaRepository;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.util.Validator;

public class ProvinciaService {

    private final ProvinciaRepository modeloProvincia = new ProvinciaRepository();

    public List<Provincia> getProvinciasDelPais(Pais pais) {
        return modeloProvincia.getProvinciasDelPais(pais);
    }

    public Provincia getProvinciaPorNombre(String nombre, Pais pais) {
        return modeloProvincia.getProvinciaPorNombre(nombre, pais);
    }

    private void validarOperacion(TipoDeOperacion operacion, Provincia provincia) {
        //Requeridos
        if (Validator.esVacio(provincia.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_provincia_vacio_nombre"));
        }
        if (provincia.getPais() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_provincia_pais_vacio"));
        }
        //Duplicados
        //Nombre
        Provincia provinciaDuplicada = this.getProvinciaPorNombre(provincia.getNombre(), provincia.getPais());
        if (operacion.equals(TipoDeOperacion.ALTA) && provinciaDuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_provincia_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (provinciaDuplicada != null && provinciaDuplicada.getId_Provincia() != provincia.getId_Provincia()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_provincia_duplicado_nombre"));
            }
        }
    }

    public void eliminar(Provincia provincia) {
        provincia.setEliminada(true);
        modeloProvincia.actualizar(provincia);
    }

    public void actualizar(Provincia provincia) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, provincia);
        modeloProvincia.actualizar(provincia);
    }

    public void guardar(Provincia provincia) {
        this.validarOperacion(TipoDeOperacion.ALTA, provincia);
        modeloProvincia.guardar(provincia);
    }
}