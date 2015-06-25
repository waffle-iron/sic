package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.CondicionIVA;
import sic.util.PersistenceUtil;

public class CondicionIVARepository {

    public List<CondicionIVA> getCondicionesIVA() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<CondicionIVA> typedQuery = em.createNamedQuery("CondicionIVA.buscarTodas", CondicionIVA.class);
        List<CondicionIVA> condicionesIVA = typedQuery.getResultList();
        em.close();
        return condicionesIVA;
    }

    public CondicionIVA getCondicionIVAPorNombre(String nombre) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<CondicionIVA> typedQuery = em.createNamedQuery("CondicionIVA.buscarPorNombre", CondicionIVA.class);
        typedQuery.setParameter("nombre", nombre);
        List<CondicionIVA> condicionesIVA = typedQuery.getResultList();
        em.close();
        if (condicionesIVA.isEmpty()) {
            return null;
        } else {
            return condicionesIVA.get(0);
        }
    }

    public void actualizar(CondicionIVA condicionIVA) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(condicionIVA);
        tx.commit();
        em.close();
    }

    public void guardar(CondicionIVA condicionIVA) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(condicionIVA);
        tx.commit();
        em.close();
    }
}
