package sic.modelo.pojos.jpa;

import sic.modelo.PagoFacturaCompra;
import sic.modelo.FacturaCompra;
import java.sql.SQLException;
import java.util.Date;
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

public class PagoFacturaCompraTest {

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

    public PagoFacturaCompra guardarUnPago() {
        FacturaCompra fcompra = new FacturaCompra();
        fcompra.setFecha(new Date());
        fcompra.setFechaVencimiento(new Date());
        fcompra.setObservaciones("");
        fcompra.setTipoFactura('A');
        
        PagoFacturaCompra pago = new PagoFacturaCompra();
        pago.setFecha(new Date());
        pago.setMonto(144);
        pago.setNota("");
        pago.setFacturaCompra(fcompra);
        
        tx.begin();
        em.persist(pago);
        tx.commit();
        return pago;
    }

    @Test
    public void shouldExecuteQueryXXX() {
        PagoFacturaCompra pago = this.guardarUnPago();
        TypedQuery<PagoFacturaCompra> query = em.createNamedQuery("PagoFacturaCompra.buscarPorFactura", PagoFacturaCompra.class);
        query.setParameter("factura", pago.getFacturaCompra());
        List<PagoFacturaCompra> resul = query.getResultList();
        assertNotNull(resul);
    }
}
