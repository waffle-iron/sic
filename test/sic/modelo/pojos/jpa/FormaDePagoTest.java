package sic.modelo.pojos.jpa;

import sic.modelo.FormaDePago;
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

public class FormaDePagoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;
    private static String msjUnicoResultado = "Debe existir solo un resultado para la consulta.";

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

    private FormaDePago crearUnaFormaDePago() {
        FormaDePago formaDePago = new FormaDePago();
        formaDePago.setNombre("Contado");
        formaDePago.setAfectaCaja(true);
        formaDePago.setPredeterminado(true);
        
        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");            
        empresa.setEmail("");
        empresa.setTelefono("");        
        
        formaDePago.setEmpresa(empresa);        
        return formaDePago;
    }   
    
    @Test
    public void shouldExecuteQueryBuscarTodas() {
        tx.begin();
        FormaDePago formaDePago = this.crearUnaFormaDePago();
        em.persist(formaDePago);
        tx.commit();
        
        TypedQuery<FormaDePago> query = em.createNamedQuery("FormaDePago.buscarTodas", FormaDePago.class); 
        query.setParameter("empresa", formaDePago.getEmpresa());
        List<FormaDePago> formasDePago = query.getResultList(); 
        assertNotNull(formasDePago);
    }
    
    @Test
    public void shouldExecuteQueryBuscarPorId() {
        tx.begin();        
        em.persist(this.crearUnaFormaDePago());
        tx.commit();
        
        TypedQuery<FormaDePago> query = em.createNamedQuery("FormaDePago.buscarPorId", FormaDePago.class); 
        query.setParameter("id", 1L);
        List<FormaDePago> formasDePago = query.getResultList();
        assertEquals(msjUnicoResultado, formasDePago.size(), 1);
    }
    
    @Test
    public void shouldExecuteQueryBuscarPorNombre() {
        tx.begin();
        FormaDePago formaDePago = this.crearUnaFormaDePago();
        em.persist(formaDePago);
        tx.commit();
        
        TypedQuery<FormaDePago> query = em.createNamedQuery("FormaDePago.buscarPorNombre", FormaDePago.class); 
        query.setParameter("empresa", formaDePago.getEmpresa());
        query.setParameter("nombre", "Contado");
        List<FormaDePago> formasDePago = query.getResultList();
        assertEquals(msjUnicoResultado, formasDePago.size(), 1);
    }
    
    @Test
    public void shouldExecuteQueryBuscarPredeterminada() {
        tx.begin();
        FormaDePago formaDePago = this.crearUnaFormaDePago();
        em.persist(formaDePago);
        tx.commit();        
        
        TypedQuery<FormaDePago> query = em.createNamedQuery("FormaDePago.buscarPredeterminada", FormaDePago.class); 
        query.setParameter("empresa", formaDePago.getEmpresa());
        List<FormaDePago> formasDePago = query.getResultList();
        assertTrue("Puede existir solo una predeterminada o ninguna.", formasDePago.size() >= 0 && formasDePago.size() <= 1);
    }
}