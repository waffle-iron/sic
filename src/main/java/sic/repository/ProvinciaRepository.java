package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.util.PersistenceUtil;

public class ProvinciaRepository {

    public List<Provincia> getProvinciasDelPais(Pais pais) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Provincia> typedQuery = em.createNamedQuery("Provincia.buscarProvinciasDelPais", Provincia.class);
        typedQuery.setParameter("pais", pais);
        List<Provincia> provincias = typedQuery.getResultList();
        em.close();
        return provincias;

    }

    public Provincia getProvinciaPorNombre(String nombreProvincia, Pais paisRelacionado) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Provincia> typedQuery = em.createNamedQuery("Provincia.buscarPorNombre", Provincia.class);
        typedQuery.setParameter("nombre", nombreProvincia);
        typedQuery.setParameter("pais", paisRelacionado);
        List<Provincia> provincias = typedQuery.getResultList();
        em.close();
        if (provincias.isEmpty()) {
            return null;
        } else {
            return provincias.get(0);
        }
    }

    public void actualizar(Provincia provincia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(provincia);
        tx.commit();
        em.close();
    }

    public void guardar(Provincia provincia) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(provincia));
        tx.commit();
        em.close();
    }
}