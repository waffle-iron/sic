package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Empresa;
import sic.modelo.Rubro;
import sic.repository.IRubroRepository;
import sic.service.IRubroService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class RubroServiceImpl implements IRubroService {

    private final IRubroRepository rubroRepository;
    private static final Logger LOGGER = Logger.getLogger(RubroServiceImpl.class.getPackage().getName());

    @Autowired
    public RubroServiceImpl(IRubroRepository rubroRepository) {
        this.rubroRepository = rubroRepository;
    }

    @Override
    public List<Rubro> getRubros(Empresa empresa) {
        try {
            return rubroRepository.getRubros(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Rubro getRubroPorNombre(String nombre, Empresa empresa) {
        try {
            return rubroRepository.getRubroPorNombre(nombre, empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
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

    @Override
    @Transactional
    public void actualizar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, rubro);
        try {
            rubroRepository.actualizar(rubro);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ALTA, rubro);
        try {
            rubroRepository.guardar(rubro);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(Rubro rubro) {
        rubro.setEliminado(true);
        try {
            rubroRepository.actualizar(rubro);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
