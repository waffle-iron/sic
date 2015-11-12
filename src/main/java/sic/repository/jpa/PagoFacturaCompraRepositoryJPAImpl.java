package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;
import sic.repository.IPagoFacturaCompraRepository;

@Repository
public class PagoFacturaCompraRepositoryJPAImpl implements IPagoFacturaCompraRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<PagoFacturaCompra> getPagosDeLaFactura(FacturaCompra facturaCompra) {        
        TypedQuery<PagoFacturaCompra> typedQuery = em.createNamedQuery("PagoFacturaCompra.buscarPorFactura", PagoFacturaCompra.class);
        typedQuery.setParameter("factura", facturaCompra);
        List<PagoFacturaCompra> pagosFacturaCompra = typedQuery.getResultList();        
        return pagosFacturaCompra;
    }

    @Override
    public void actualizar(PagoFacturaCompra pago) {        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pago);
        tx.commit();        
    }

    @Override
    public void guardar(PagoFacturaCompra pago) {        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(pago));
        tx.commit();        
    }
}