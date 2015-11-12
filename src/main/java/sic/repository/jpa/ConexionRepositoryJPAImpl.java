package sic.repository.jpa;

import org.springframework.stereotype.Repository;
import sic.modelo.DatosConexion;
import sic.repository.IConexionRepository;
import sic.repository.XMLException;

@Repository
public class ConexionRepositoryJPAImpl implements IConexionRepository {

    @Override
    public void guardar(DatosConexion datosConexion) throws XMLException {
        ConfiguracionDelSistemaRepositoryJPAImpl modeloConfig = new ConfiguracionDelSistemaRepositoryJPAImpl();
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/HOST", datosConexion.getHost());
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/BD", datosConexion.getNombreBaseDeDatos());
        modeloConfig.guardarXMLconDOM("/CONFIGURACION/CONEXION/PORT", String.valueOf(datosConexion.getPuerto()));
    }
}