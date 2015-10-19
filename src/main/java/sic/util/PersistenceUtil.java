package sic.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import sic.modelo.XMLFileConfig;

public class PersistenceUtil {

    private static EntityManagerFactory emf = null;
    private static final Logger log = Logger.getLogger(PersistenceUtil.class.getPackage().getName());

    public static EntityManager getEntityManager() {
        if (emf == null) {
            String url = "jdbc:mysql://" + XMLFileConfig.getHostConexion()
                    + ":" + XMLFileConfig.getPuertoConexion()
                    + "/" + XMLFileConfig.getBdConexion();
            Map properties = new HashMap();
            properties.put("javax.persistence.jdbc.url", url);
            String username = System.getenv("SIC_USERNAME");
            String password = System.getenv("SIC_PASSWORD");
            properties.put("javax.persistence.jdbc.user", username);
            properties.put("javax.persistence.jdbc.password", password);
            try {
                emf = Persistence.createEntityManagerFactory("SIC-PU", properties);
            } catch (Exception ex) {
                log.error(ex.getMessage() + " - " + ex.getCause().getMessage());
            }
        }
        return emf.createEntityManager();
    }
}
