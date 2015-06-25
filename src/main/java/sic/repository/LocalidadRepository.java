package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Localidad;
import sic.modelo.Provincia;
import sic.util.PersistenceUtil;

public class LocalidadRepository {

    public List<Localidad> getLocalidadesDeLaProvincia(Provincia provincia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Localidad> typedQuery = em.createNamedQuery("Localidad.buscarLocalidadesDeLaProvincia", Localidad.class);
        typedQuery.setParameter("provincia", provincia);
        List<Localidad> localidades = typedQuery.getResultList();
        em.close();
        return localidades;
    }

    public Localidad getLocalidadPorNombre(String nombre, Provincia provincia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Localidad> typedQuery = em.createNamedQuery("Localidad.buscarPorNombre", Localidad.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("provincia", provincia);
        List<Localidad> localidades = typedQuery.getResultList();
        em.close();
        if (localidades.isEmpty()) {
            return null;
        } else {
            return localidades.get(0);
        }
    }

    public void actualizar(Localidad localidad) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(localidad);
        tx.commit();
        em.close();
    }

    public void guardar(Localidad localidad) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(localidad));
        tx.commit();
        em.close();
    }
}