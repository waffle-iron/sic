package sic.modelo.pojos.jpa;

import sic.modelo.Provincia;
import sic.modelo.Pais;
import sic.modelo.Localidad;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProvinciaTest {

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
    public void shouldCreateProvinciasWithSameNameInDifferentPaises() throws Exception {
        Pais pais1 = new Pais();
        pais1.setNombre("Paraguay");

        Pais pais2 = new Pais();
        pais2.setNombre("Brasil");

        //Provincia Asunción
        Provincia provincia = new Provincia();
        provincia.setNombre("Asunción");
        provincia.setPais(pais1);

        //Provincia Encarnación
        Provincia provincia2 = new Provincia();
        provincia2.setNombre("Asunción");
        provincia2.setPais(pais2);

        tx.begin();
        em.persist(provincia);
        em.persist(provincia2);
        tx.commit();
        assertNotNull("ID should not be null", provincia.getId_Provincia());
        assertNotNull("ID should not be null", provincia2.getId_Provincia());
    }

    @Test
    public void shouldExecuteQueryXXX() {
        tx.begin();
        Pais pais = new Pais();
        pais.setNombre("USA");

        Provincia provincia = new Provincia();
        provincia.setNombre("Dallas");
        //pais.addProvincia(provincia);

        Localidad localidad1 = new Localidad();
        localidad1.setNombre("Plano");
        localidad1.setCodigoPostal("");

        Localidad localidad2 = new Localidad();
        localidad2.setNombre("Resistencia");
        localidad2.setCodigoPostal("");

        //provincia.addLocalidad(localidad1);
        //provincia.addLocalidad(localidad2);

        em.persist(localidad1);
        em.persist(localidad2);
        tx.commit();

        TypedQuery<Pais> query = em.createNamedQuery("Pais.buscarPorNombre", Pais.class);
        //query.setParameter("pais", provincia.getPais());
        query.setParameter("nombre", "USA");
        List<Pais> resul = query.getResultList();
        assertNotNull(resul);
    }
}