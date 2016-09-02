package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.ICondicionIVAService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class CondicionDeIVAServiceImpl implements ICondicionIVAService {

    private final ICondicionIVARepository condicionIVARepository;
    private static final Logger LOGGER = Logger.getLogger(CondicionDeIVAServiceImpl.class.getPackage().getName());

    @Autowired
    public CondicionDeIVAServiceImpl(ICondicionIVARepository condicionIVARepository) {
        try {
            this.condicionIVARepository = condicionIVARepository;

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<CondicionIVA> getCondicionesIVA() {
        try {
            return condicionIVARepository.getCondicionesIVA();

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVA);
        try {
            condicionIVARepository.actualizar(condicionIVA);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
        try {
            condicionIVARepository.guardar(condicionIVA);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(CondicionIVA condicionIVA) {
        condicionIVA.setEliminada(true);
        try {
            condicionIVARepository.actualizar(condicionIVA);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public CondicionIVA getCondicionIVAPorNombre(String nombre) {
        try {
            return condicionIVARepository.getCondicionIVAPorNombre(nombre);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public void validarOperacion(TipoDeOperacion operacion, CondicionIVA condicionIVA) {
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
