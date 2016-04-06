package sic.repository;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Gasto;
import sic.util.PersistenceUtil;

public class GastoRepository {

    public void guardar(Gasto gasto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(gasto));
        tx.commit();
        em.close();
    }

    public List<Object> getGastosPorFecha(long id_Empresa, Date desde, Date hasta) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object> typedQuery = (TypedQuery<Object>) em.createNamedQuery("Gasto.getGastosSinArqueoPorFecha");
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Object> gastos = typedQuery.getResultList();
        em.close();
        if (gastos.isEmpty()) {
            return null;
        } else {
            return gastos;
        }
    }

    public List<Object> getGastosPorFechaYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object> typedQuery = (TypedQuery<Object>) em.createNamedQuery("Gasto.getGastosSinArqueoPorFormaDePagoYFecha");
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("id_FormaDePago", id_FormaDePago);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Object> gastos = typedQuery.getResultList();
        em.close();
        return gastos;
    }

    public void actualizar(Gasto gasto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(gasto);
        tx.commit();
        em.close();
    }

    public Gasto getCajaPorID(long id_Gasto, long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Gasto> typedQuery = em.createNamedQuery("Gasto.getGastoPorId", Gasto.class);
        typedQuery.setParameter("id_Gasto", id_Gasto);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        List<Gasto> gastos = typedQuery.getResultList();
        em.close();
        if (gastos.isEmpty()) {
            return null;
        } else {
            return gastos.get(0);
        }
    }

    public int getUltimoNumeroDeGasto(long idEmpresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Integer> typedQuery = em.createNamedQuery("Gasto.getUltimoNumeroDeGasto", Integer.class);
        typedQuery.setParameter("id_Empresa", idEmpresa);
        Integer ultimoNumeroDeGasto = typedQuery.getSingleResult();
        em.close();
        if (ultimoNumeroDeGasto == null) {
            return 0;
        } else {
            return ultimoNumeroDeGasto;
        }
    }

}
