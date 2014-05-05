package sic.service;

import sic.repository.ConfiguracionRepository;
import sic.repository.XMLException;

public class ConfiguracionService {

    private ConfiguracionRepository modeloConfiguracion = new ConfiguracionRepository();

    public void guardarXML(String pathEtiqueta, String valor) throws XMLException {
        modeloConfiguracion.guardarXMLconDOM(pathEtiqueta, valor);
    }

    public void leerXML() throws XMLException {
        modeloConfiguracion.leerXMLconDOM();
    }
}
