package sic.modelo.pojos.jpa;

import sic.modelo.Provincia;
import sic.modelo.Pais;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PaisTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManager() throws Exception {
        emf = Persistence.createEntityManagerFactory("SIC-Test-PU");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() throws SQLException {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }

    @Test
    public void shouldCreateAPais() throws Exception {
        Pais pais = new Pais();
        pais.setNombre("Argentina");

        //Provincia Corrientes
        Provincia provincia = new Provincia();
        provincia.setNombre("Corrientes");

        //Provincia Chaco
        Provincia provincia2 = new Provincia();
        provincia2.setNombre("Chaco");
        provincia2.setEliminada(true);
        
        //pais.addProvincia(provincia);
        //pais.addProvincia(provincia2);

        tx.begin();
        em.persist(pais);
        tx.commit();
        assertNotNull("ID should not be null", pais.getId_Pais());
    }    
}