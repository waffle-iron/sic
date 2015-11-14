package sic.service;

import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.XMLException;

public interface IConfiguracionDelSistemaService {

    void actualizar(ConfiguracionDelSistema cds);

    ConfiguracionDelSistema getConfiguracionDelSistemaPorEmpresa(Empresa empresa);

    ConfiguracionDelSistema getConfiguracionDelSistemaPorId(long id_ConfiguracionDelSistema);

    void guardar(ConfiguracionDelSistema cds);

    void guardarXML(String pathEtiqueta, String valor) throws XMLException;

    void leerXML() throws XMLException;
    
}
