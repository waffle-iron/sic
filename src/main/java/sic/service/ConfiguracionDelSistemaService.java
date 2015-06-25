package sic.service;

import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.ConfiguracionDelSistemaRepository;
import sic.repository.XMLException;

public class ConfiguracionDelSistemaService {

    private final ConfiguracionDelSistemaRepository configuracionRepository = new ConfiguracionDelSistemaRepository();

    public void guardarXML(String pathEtiqueta, String valor) throws XMLException {
        configuracionRepository.guardarXMLconDOM(pathEtiqueta, valor);
    }

    public void leerXML() throws XMLException {
        configuracionRepository.leerXMLconDOM();
    }

    public ConfiguracionDelSistema getConfiguracionDelSistemaPorId(long id_ConfiguracionDelSistema) {
        return configuracionRepository.getConfiguracionDelSistemaPorId(id_ConfiguracionDelSistema);
    }

    public ConfiguracionDelSistema getConfiguracionDelSistemaPorEmpresa(Empresa empresa) {
        return configuracionRepository.getConfiguracionDelSistemaPorEmpresa(empresa);
    }

    public void guardar(ConfiguracionDelSistema cds) {        
        configuracionRepository.guardar(cds);
    }

    public void actualizar(ConfiguracionDelSistema cds) {        
        configuracionRepository.actualizar(cds);
    }
}
