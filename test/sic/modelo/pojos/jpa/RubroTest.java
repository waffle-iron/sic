package sic.modelo.pojos.jpa;

import sic.modelo.Rubro;
import sic.modelo.CondicionIVA;
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

public class RubroTest {

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

    public Rubro debeGuardarUnRubro() {
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

        Rubro rubro = new Rubro();
        rubro.setNombre("VARIOS");
        rubro.setEliminado(false);
        rubro.setEmpresa(empresa);

        tx.begin();
        em.persist(rubro);
        tx.commit();
        assertNotNull("El Id de la medida no debe ser nulo.", rubro.getId_Rubro());
        return rubro;
    }

    @Test
    public void shouldExecuteQueryXXX() {
        Rubro rubro = this.debeGuardarUnRubro();

        TypedQuery<Rubro> query = em.createNamedQuery("Rubro.buscarTodos", Rubro.class);
        query.setParameter("empresa", rubro.getEmpresa());
        //query.setParameter("nombre", "VARIOS");
        List<Rubro> resul = query.getResultList();
        assertNotNull(resul);
    }
}
