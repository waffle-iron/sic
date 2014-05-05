package sic.repository;

import sic.modelo.DatosConexion;

public class ConexionRepository {

    public void guardar(DatosConexion datosConexion) throws XMLException {
        ConfiguracionRepository modeloConfig = new ConfiguracionRepository();
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/HOST", datosConexion.getHost());
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/BD", datosConexion.getNombreBaseDeDatos());
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/PORT", String.valueOf(datosConexion.getPuerto()));
    }
}