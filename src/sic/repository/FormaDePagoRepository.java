package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.util.PersistenceUtil;

public class FormaDePagoRepository {

    public List<FormaDePago> getFormasDePago(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FormaDePago> typedQuery = em.createNamedQuery("FormaDePago.buscarTodas", FormaDePago.class);
        typedQuery.setParameter("empresa", empresa);
        List<FormaDePago> formasDePago = typedQuery.getResultList();
        em.close();
        return formasDePago;
    }

    public FormaDePago getFormaDePagoPorId(long id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FormaDePago> typedQuery = em.createNamedQuery("FormaDePago.buscarPorId", FormaDePago.class);
        typedQuery.setParameter("id", id);
        List<FormaDePago> formasDePago = typedQuery.getResultList();
        em.close();
        if (formasDePago.isEmpty()) {
            return null;
        } else {
            return formasDePago.get(0);
        }
    }

    public FormaDePago getFormaDePagoPredeterminado(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FormaDePago> typedQuery = em.createNamedQuery("FormaDePago.buscarPredeterminada", FormaDePago.class);
        typedQuery.setParameter("empresa", empresa);
        List<FormaDePago> formasDePago = typedQuery.getResultList();
        em.close();
        if (formasDePago.isEmpty()) {
            return null;
        } else {
            return formasDePago.get(0);
        }
    }

    public FormaDePago getFormaDePagoPorNombre(String nombre, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FormaDePago> typedQuery = em.createNamedQuery("FormaDePago.buscarPorNombre", FormaDePago.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("empresa", empresa);
        List<FormaDePago> formasDePago = typedQuery.getResultList();
        em.close();
        if (formasDePago.isEmpty()) {
            return null;
        } else {
            return formasDePago.get(0);
        }
    }

    public void guardar(FormaDePago formaDePago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(formaDePago));
        tx.commit();
        em.close();
    }

    public void actualizar(FormaDePago formaDePago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(formaDePago);
        tx.commit();
        em.close();
    }
}