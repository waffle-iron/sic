package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.Factura;
import sic.modelo.Pago;
import sic.repository.IPagoRepository;

@Repository
public class PagoRepositoryJPAImpl implements IPagoRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Pago> getPagosDeLaFactura(Factura factura) {
        TypedQuery<Pago> typedQuery = em.createNamedQuery("Pago.buscarPorFactura", Pago.class);
        typedQuery.setParameter("factura", factura);
        List<Pago> pagosFacturaCompra = typedQuery.getResultList();
        return pagosFacturaCompra;
    }

    @Override
    public void guardar(Pago pago) {
        em.persist(em.merge(pago));
    }

    @Override
    public void actualizar(Pago pago) {
        em.merge(pago);
    }
}
