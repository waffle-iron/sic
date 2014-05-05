package sic.modelo;

public class XMLFileConfig {

    private static String hostConexion;
    private static String bdConexion;
    private static int puertoConexion;

    public static String getBdConexion() {
        return bdConexion;
    }

    public static void setBdConexion(String bdConexion) {
        XMLFileConfig.bdConexion = bdConexion;
    }

    public static String getHostConexion() {
        return hostConexion;
    }

    public static void setHostConexion(String hostConexion) {
        XMLFileConfig.hostConexion = hostConexion;
    }

    public static int getPuertoConexion() {
        return puertoConexion;
    }

    public static void setPuertoConexion(int puertoConexion) {
        XMLFileConfig.puertoConexion = puertoConexion;
    }
}
