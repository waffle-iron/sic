package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.ControlCaja;
import sic.util.PersistenceUtil;

public class ControlCajaRepository {

    public void guardar(ControlCaja controlCaja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(controlCaja));
        tx.commit();
        em.close();
    }

    public ControlCaja getControlCajaSinArqueo(long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<ControlCaja> typedQuery = em.createNamedQuery("ControlCaja.cajaSinArqueo", ControlCaja.class);
        typedQuery.setParameter("idEmpresa", id_Empresa);
        List<ControlCaja> ControlesCaja = typedQuery.getResultList();
        em.close();
        if (ControlesCaja.isEmpty()) {
            return null;
        } else {
            return ControlesCaja.get(0);
        }
    }

    public void actualizar(ControlCaja controlCaja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(controlCaja);
        tx.commit();
        em.close();
    }

    public ControlCaja getCajaPorNro(long nroCaja, long idEmpresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<ControlCaja> typedQuery = em.createNamedQuery("ControlCaja.buscarPorNumero", ControlCaja.class);
        typedQuery.setParameter("nroCaja", nroCaja);
        typedQuery.setParameter("idEmpresa", idEmpresa);
        List<ControlCaja> cajas = typedQuery.getResultList();
        em.close();
        if (cajas.isEmpty()) {
            return null;
        } else {
            return cajas.get(0);
        }
    }

    public ControlCaja getCajaPorNroYFormaDePago(long nroCaja, long idEmpresa, long idFormaDePago) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<ControlCaja> typedQuery = em.createNamedQuery("ControlCaja.cajaSinArqueoPorFormaDepago", ControlCaja.class);
        typedQuery.setParameter("nroCaja", nroCaja);
        typedQuery.setParameter("idEmpresa", idEmpresa);
        typedQuery.setParameter("idFormaDePago", idFormaDePago);
        List<ControlCaja> cajas = typedQuery.getResultList();
        em.close();
        if (cajas.isEmpty()) {
            return null;
        } else {
            return cajas.get(0);
        }
    }
}
