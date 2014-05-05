package sic.modelo.pojos.jpa;

import sic.modelo.CondicionIVA;
import sic.modelo.Medida;
import sic.modelo.Empresa;
import java.sql.SQLException;
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

public class MedidaTest {

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

    public Medida debeGuardarUnaMedida() {
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCuip(123);

        //Condicion IVA
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Responsable Inscripto");
        condicionIVA.setDiscriminaIVA(true);

        empresa.setCondicionIVA(condicionIVA);
        empresa.setEmail("");
        empresa.setTelefono("");

        Medida medida = new Medida();
        medida.setNombre("DOCENA");
        medida.setEliminada(false);
        medida.setEmpresa(empresa);

        tx.begin();
        em.persist(medida);
        tx.commit();
        assertNotNull("El Id de la medida no debe ser nulo.", medida.getId_Medida());
        return medida;
    }

    @Test
    public void shouldExecuteQueryXXX() {
        Medida medida = this.debeGuardarUnaMedida();

        TypedQuery<Medida> query = em.createNamedQuery("Medida.buscarPorNombre", Medida.class);
        query.setParameter("empresa", medida.getEmpresa());
        query.setParameter("nombre", "DOCENA");
        List<Medida> resul = query.getResultList();
        assertNotNull(resul);
    }
}