package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Pais;
import sic.util.PersistenceUtil;

public class PaisRepository {

    public List<Pais> getPaises() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pais> typedQuery = em.createNamedQuery("Pais.buscarTodos", Pais.class);
        List<Pais> paises = typedQuery.getResultList();
        em.close();
        return paises;
    }

    public Pais getPaisPorNombre(String nombre) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pais> typedQuery = em.createNamedQuery("Pais.buscarPorNombre", Pais.class);
        typedQuery.setParameter("nombre", nombre);
        List<Pais> paises = typedQuery.getResultList();
        em.close();
        if (paises.isEmpty()) {
            return null;
        } else {
            return paises.get(0);
        }
    }

    public void actualizar(Pais pais) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pais);
        tx.commit();
        em.close();
    }

    public void guardar(Pais pais) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(pais);
        tx.commit();
        em.close();
    }
}