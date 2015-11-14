package sic.service.impl;

import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.repository.XMLException;
import sic.modelo.DatosConexion;
import sic.repository.IConexionRepository;
import sic.service.IConexionService;
import sic.service.ServiceException;
import sic.util.Validator;

@Service
public class ConexionServiceImpl implements IConexionService {

    private final IConexionRepository conexionRepository;

    @Autowired
    public ConexionServiceImpl(IConexionRepository conexionRepository) {
        this.conexionRepository = conexionRepository;
    }

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

    @Override
    public void guardar(DatosConexion datosConexion) throws XMLException {
        this.validar(datosConexion);
        conexionRepository.guardar(datosConexion);
    }
}
