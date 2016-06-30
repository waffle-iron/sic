package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Localidad;
import sic.modelo.Provincia;
import sic.repository.ILocalidadRepository;
import sic.service.ILocalidadService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class LocalidadServiceImpl implements ILocalidadService {

    private final ILocalidadRepository localidadRepository;

    @Autowired
    public LocalidadServiceImpl(ILocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    @Override
    public List<Localidad> getLocalidadesDeLaProvincia(Provincia provincia) {
        return localidadRepository.getLocalidadesDeLaProvincia(provincia);
    }

    @Override
    @Transactional
    public void eliminar(Localidad localidad) {
        localidad.setEliminada(true);
        localidadRepository.actualizar(localidad);
    }

    @Override
    public Localidad getLocalidadPorNombre(String nombre, Provincia provincia) {
        return localidadRepository.getLocalidadPorNombre(nombre, provincia);
    }

    @Override
    public void validarOperacion(TipoDeOperacion operacion, Localidad localidad) {
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

    @Override
    @Transactional
    public void actualizar(Localidad localidad) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, localidad);
        localidadRepository.actualizar(localidad);
    }

    @Override
    @Transactional
    public void guardar(Localidad localidad) {
        this.validarOperacion(TipoDeOperacion.ALTA, localidad);
        localidadRepository.guardar(localidad);
    }
}
