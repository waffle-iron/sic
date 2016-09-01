package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Pais;
import sic.repository.IPaisRepository;
import sic.service.IPaisService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class PaisServiceImpl implements IPaisService {

    private final IPaisRepository paisRepository;
    private static final Logger LOGGER = Logger.getLogger(PaisServiceImpl.class.getPackage().getName());


    @Autowired
    public PaisServiceImpl(IPaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    @Override
    public List<Pais> getPaises() {
        try {
            return paisRepository.getPaises();
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Pais getPaisPorNombre(String nombre) {
        try {
            return paisRepository.getPaisPorNombre(nombre);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
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

    @Override
    @Transactional
    public void eliminar(Pais pais) {
        pais.setEliminado(true);
        try {
            paisRepository.actualizar(pais);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, pais);
        try {
            paisRepository.actualizar(pais);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ALTA, pais);
        try {
            paisRepository.guardar(pais);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
