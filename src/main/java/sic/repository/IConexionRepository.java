package sic.repository;

import sic.modelo.DatosConexion;

public interface IConexionRepository {

    void guardar(DatosConexion datosConexion) throws XMLException;
    
}
