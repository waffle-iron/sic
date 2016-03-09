package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Caja;
import sic.util.PersistenceUtil;

public class CajaRepository {

    public void guardar(Caja controlCaja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(controlCaja));
        tx.commit();
        em.close();
    }

    public Caja getControlCajaSinArqueo(Long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.cajaSinArqueo", Caja.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        List<Caja> ControlesCaja = typedQuery.getResultList();
        em.close();
        if (ControlesCaja.isEmpty()) {
            return null;
        } else {
            return ControlesCaja.get(0);
        }
    }

    public void actualizar(Caja controlCaja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(controlCaja);
        tx.commit();
        em.close();
    }

    public Caja getCajaPorID(long id_Caja, long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("ControlCaja.buscarCajaPorID", Caja.class);
        typedQuery.setParameter("id_caja", id_Caja);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        List<Caja> cajas = typedQuery.getResultList();
        em.close();
        if (cajas.isEmpty()) {
            return null;
        } else {
            return cajas.get(0);
        }
    }

//    public Caja getCajaPorNroYFormaDePago(long nroCaja, long idEmpresa, long idFormaDePago) {
//        EntityManager em = PersistenceUtil.getEntityManager();
//        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.cajaSinArqueoPorFormaDepago", Caja.class);
//        typedQuery.setParameter("nroCaja", nroCaja);
//        typedQuery.setParameter("id_Empresa", idEmpresa);
//        typedQuery.setParameter("id_FormaDePago", idFormaDePago);
//        List<Caja> cajas = typedQuery.getResultList();
//        em.close();
//        if (cajas.isEmpty()) {
//            return null;
//        } else {
//            return cajas.get(0);
//        }
//    }
    public Caja getCajaPorFormaDePago(long idEmpresa, long idFormaDePago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.cajaSinArqueoPorFormaDepago", Caja.class);
        typedQuery.setParameter("id_Empresa", idEmpresa);
        typedQuery.setParameter("id_FormaDePago", idFormaDePago);
        List<Caja> cajas = typedQuery.getResultList();
        em.close();
        if (cajas.isEmpty()) {
            return null;
        } else {
            return cajas.get(0);
        }
    }

    public int getUltimoNumeroDeCaja(long idEmpresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Integer> typedQuery = em.createNamedQuery("Caja.ultimoNumeroDeCaja", Integer.class);
        typedQuery.setParameter("id_Empresa", idEmpresa);
        Integer ultimoNumeroDeCaja = typedQuery.getSingleResult();
        em.close();
        if (ultimoNumeroDeCaja == null) {
            return 0;
        } else {
            return ultimoNumeroDeCaja;
        }
    }
}
