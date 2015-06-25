package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;
import sic.util.PersistenceUtil;

public class PagoFacturaCompraRepository {

    public List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<PagoFacturaCompra> typedQuery = em.createNamedQuery("PagoFacturaCompra.buscarPorFactura", PagoFacturaCompra.class);
        typedQuery.setParameter("factura", facturaCompra);
        List<PagoFacturaCompra> pagosFacturaCompra = typedQuery.getResultList();
        em.close();
        return pagosFacturaCompra;
    }

    public void actualizar(PagoFacturaCompra pago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pago);
        tx.commit();
        em.close();
    }

    public void guardar(PagoFacturaCompra pago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(pago));
        tx.commit();
        em.close();
    }
}