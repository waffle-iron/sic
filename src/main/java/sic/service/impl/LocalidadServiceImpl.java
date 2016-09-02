package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Localidad;
import sic.modelo.Provincia;
import sic.repository.ILocalidadRepository;
import sic.service.ILocalidadService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class LocalidadServiceImpl implements ILocalidadService {

    private final ILocalidadRepository localidadRepository;
    private static final Logger LOGGER = Logger.getLogger(LocalidadServiceImpl.class.getPackage().getName());

    @Autowired
    public LocalidadServiceImpl(ILocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    @Override
    public List<Localidad> getLocalidadesDeLaProvincia(Provincia provincia) {
        try {
            return localidadRepository.getLocalidadesDeLaProvincia(provincia);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(Localidad localidad) {
        localidad.setEliminada(true);
        try {
            localidadRepository.actualizar(localidad);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Localidad getLocalidadPorNombre(String nombre, Provincia provincia) {
        try {
            return localidadRepository.getLocalidadPorNombre(nombre, provincia);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
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
        try {
            localidadRepository.actualizar(localidad);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Localidad localidad) {
        this.validarOperacion(TipoDeOperacion.ALTA, localidad);
        try {
            localidadRepository.guardar(localidad);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
