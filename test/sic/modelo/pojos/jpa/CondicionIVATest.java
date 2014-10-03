package sic.modelo.pojos.jpa;

import sic.modelo.CondicionIVA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CondicionIVATest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManager() {
        emf = Persistence.createEntityManagerFactory("SIC-Test-PU");
        em = emf.createEntityManager();

    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }

    private CondicionIVA crearUnaCondicionIVA() {
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Responsable Inscripto");
        condicionIVA.setDiscriminaIVA(true);
        return condicionIVA;
    }

    @Test
    public void ejecutarConsultaBuscarPorNombre() {
        tx.begin();
        CondicionIVA condicion = this.crearUnaCondicionIVA();
        em.persist(condicion);
        tx.commit();

        TypedQuery<CondicionIVA> query = em.createNamedQuery("CondicionIVA.buscarPorNombre", CondicionIVA.class);
        query.setParameter("nombre", "Responsable Inscripto");
        List<CondicionIVA> resul = query.getResultList();
        assertNotNull(resul);
    }
}
