package sic.service;

import java.util.ResourceBundle;
import sic.repository.ConexionRepository;
import sic.repository.XMLException;
import sic.modelo.DatosConexion;
import sic.util.Validator;

public class ConexionService {

    private ConexionRepository modeloConexion = new ConexionRepository();

    private void validar(DatosConexion datosConexion) {
        //Requeridos
        if (Validator.esVacio(datosConexion.getHost())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_conexion_host_vacio"));
        }
        if (Validator.esVacio(datosConexion.getNombreBaseDeDatos())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_conexion_bd_vacio"));
        }
    }

    public void guardar(DatosConexion datosConexion) throws XMLException {
        this.validar(datosConexion);
        modeloConexion.guardar(datosConexion);
    }
}
