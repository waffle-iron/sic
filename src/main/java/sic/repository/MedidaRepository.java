package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.util.PersistenceUtil;

public class MedidaRepository {

    public List<Medida> getUnidadMedidas(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Medida> typedQuery = em.createNamedQuery("Medida.buscarTodas", Medida.class);
        typedQuery.setParameter("empresa", empresa);
        List<Medida> medidas = typedQuery.getResultList();
        em.close();
        return medidas;
    }

    public Medida getMedidaPorNombre(String nombreMedida, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Medida> typedQuery = em.createNamedQuery("Medida.buscarPorNombre", Medida.class);
        typedQuery.setParameter("nombre", nombreMedida);
        typedQuery.setParameter("empresa", empresa);
        List<Medida> medidas = typedQuery.getResultList();
        em.close();
        if (medidas.isEmpty()) {
            return null;
        } else {
            return medidas.get(0);
        }
    }

    public void guardar(Medida medida) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(medida));
        tx.commit();
        em.close();
    }

    public void actualizar(Medida medida) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(medida);
        tx.commit();
        em.close();
    }
}