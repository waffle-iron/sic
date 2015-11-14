package sic.service;

import sic.modelo.DatosConexion;
import sic.repository.XMLException;

public interface IConexionService {

    void guardar(DatosConexion datosConexion) throws XMLException;
    
}
