package sic.modelo;

/**
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
public class DatosConexion {

    private String host;
    private String nombreBaseDeDatos;
    private int puerto;

    public DatosConexion() {
    }

    public DatosConexion(String host, String nombreBaseDeDatos, int puerto) {
        this.host = host;
        this.nombreBaseDeDatos = nombreBaseDeDatos;
        this.puerto = puerto;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getNombreBaseDeDatos() {
        return nombreBaseDeDatos;
    }

    public void setNombreBaseDeDatos(String nombreBaseDeDatos) {
        this.nombreBaseDeDatos = nombreBaseDeDatos;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
