package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Empresa;
import sic.modelo.Rubro;
import sic.util.PersistenceUtil;

public class RubroRepository {

    public List<Rubro> getRubros(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Rubro> typedQuery = em.createNamedQuery("Rubro.buscarTodos", Rubro.class);
        typedQuery.setParameter("empresa", empresa);
        List<Rubro> rubros = typedQuery.getResultList();
        em.close();
        return rubros;
    }

    public Rubro getRubroPorNombre(String nombre, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Rubro> typedQuery = em.createNamedQuery("Rubro.buscarPorNombre", Rubro.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("empresa", empresa);
        List<Rubro> rubros = typedQuery.getResultList();
        em.close();
        if (rubros.isEmpty()) {
            return null;
        } else {
            return rubros.get(0);
        }
    }

    public void guardar(Rubro rubro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(rubro));
        tx.commit();
        em.close();
    }

    public void actualizar(Rubro rubro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(rubro);
        tx.commit();
        em.close();
    }
}