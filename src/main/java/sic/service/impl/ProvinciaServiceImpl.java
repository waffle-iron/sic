package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.repository.IProvinciaRepository;
import sic.service.IProvinciaService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

    private final IProvinciaRepository provinciaRepository;
    private static final Logger LOGGER = Logger.getLogger(ProvinciaServiceImpl.class.getPackage().getName());

    @Autowired
    public ProvinciaServiceImpl(IProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    @Override
    public List<Provincia> getProvinciasDelPais(Pais pais) {
        try {
            return provinciaRepository.getProvinciasDelPais(pais);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Provincia getProvinciaPorNombre(String nombre, Pais pais) {
        try {
            return provinciaRepository.getProvinciaPorNombre(nombre, pais);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
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

    @Override
    @Transactional
    public void eliminar(Provincia provincia) {
        provincia.setEliminada(true);
        try {
            provinciaRepository.actualizar(provincia);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(Provincia provincia) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, provincia);
        try {
            provinciaRepository.actualizar(provincia);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Provincia provincia) {
        this.validarOperacion(TipoDeOperacion.ALTA, provincia);
        try {
            provinciaRepository.guardar(provincia);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
