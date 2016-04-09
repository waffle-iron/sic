package sic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.IConfiguracionDelSistemaRepository;
import sic.service.IConfiguracionDelSistemaService;

@Service
public class ConfiguracionDelSistemaServiceImpl implements IConfiguracionDelSistemaService {

    private final IConfiguracionDelSistemaRepository configuracionRepository;

    @Autowired
    public ConfiguracionDelSistemaServiceImpl(IConfiguracionDelSistemaRepository configuracionRepository) {
        this.configuracionRepository = configuracionRepository;
    }

    @Override
    public ConfiguracionDelSistema getConfiguracionDelSistemaPorId(long id_ConfiguracionDelSistema) {
        return configuracionRepository.getConfiguracionDelSistemaPorId(id_ConfiguracionDelSistema);
    }

    @Override
    public ConfiguracionDelSistema getConfiguracionDelSistemaPorEmpresa(Empresa empresa) {
        return configuracionRepository.getConfiguracionDelSistemaPorEmpresa(empresa);
    }

    @Override
    @Transactional
    public void guardar(ConfiguracionDelSistema cds) {
        configuracionRepository.guardar(cds);
    }

    @Override
    @Transactional
    public void actualizar(ConfiguracionDelSistema cds) {
        configuracionRepository.actualizar(cds);
    }
}
