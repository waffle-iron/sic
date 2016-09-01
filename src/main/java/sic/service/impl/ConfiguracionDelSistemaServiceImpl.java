package sic.service.impl;

import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.IConfiguracionDelSistemaRepository;
import sic.service.IConfiguracionDelSistemaService;
import sic.service.ServiceException;
import sic.util.Utilidades;

@Service
public class ConfiguracionDelSistemaServiceImpl implements IConfiguracionDelSistemaService {

    private final IConfiguracionDelSistemaRepository configuracionRepository;
    private static final Logger LOGGER = Logger.getLogger(ConfiguracionDelSistemaServiceImpl.class.getPackage().getName());

    @Autowired
    public ConfiguracionDelSistemaServiceImpl(IConfiguracionDelSistemaRepository configuracionRepository) {
        try {
            this.configuracionRepository = configuracionRepository;
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public ConfiguracionDelSistema getConfiguracionDelSistemaPorId(long id_ConfiguracionDelSistema) {
        try {
            return configuracionRepository.getConfiguracionDelSistemaPorId(id_ConfiguracionDelSistema);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public ConfiguracionDelSistema getConfiguracionDelSistemaPorEmpresa(Empresa empresa) {
        try {
            return configuracionRepository.getConfiguracionDelSistemaPorEmpresa(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(ConfiguracionDelSistema cds) {
        try {
            configuracionRepository.guardar(cds);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(ConfiguracionDelSistema cds) {
        try {
            configuracionRepository.actualizar(cds);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
