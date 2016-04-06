package sic.repository;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaCajaCriteria;
import sic.modelo.Caja;
import sic.util.FormatterFechaHora;
import sic.util.PersistenceUtil;

public class CajaRepository {

    public void guardar(Caja caja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(caja));
        tx.commit();
        em.close();
    }

    public Caja getUltimaCaja(long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.getUltimaCaja", Caja.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        List<Caja> ControlesCaja = typedQuery.getResultList();
        em.close();
        if (ControlesCaja.isEmpty()) {
            return null;
        } else {
            return ControlesCaja.get(0);
        }
    }

    public List<Caja> getCajas(long id_Empresa, Date desde, Date hasta) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.getCajas", Caja.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Caja> ControlesCaja = typedQuery.getResultList();
        em.close();
        if (ControlesCaja.isEmpty()) {
            return null;
        } else {
            return ControlesCaja;
        }
    }

    public void actualizar(Caja caja) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(caja);
        tx.commit();
        em.close();
    }

    public Caja getCajaPorID(long id_Caja, long id_Empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createNamedQuery("Caja.buscarCajaPorID", Caja.class);
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
        TypedQuery<Integer> typedQuery = em.createNamedQuery("Caja.getUltimoNumeroDeCaja", Integer.class);
        typedQuery.setParameter("id_Empresa", idEmpresa);
        Integer ultimoNumeroDeCaja = typedQuery.getSingleResult();
        em.close();
        if (ultimoNumeroDeCaja == null) {
            return 0;
        } else {
            return ultimoNumeroDeCaja;
        }
    }

    public List<Caja> getCajasCriteria(BusquedaCajaCriteria criteria) {
        String query = "SELECT c FROM Caja c WHERE c.empresa = :empresa AND c.eliminada = false";
        if (criteria.isBuscaPorUsuario() == true) {
            query += " AND (c.usuario.id_Usuario = " + criteria.getUsuario().getId_Usuario() + " OR c.usuarioCierra.id_Usuario = " + criteria.getUsuario().getId_Usuario() + ")";
        }
        if (criteria.isBuscaPorFecha() == true) {
            FormatterFechaHora formateadorFecha = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_INTERNACIONAL);
            query += " AND c.fechaApertura BETWEEN '" + formateadorFecha.format(criteria.getFechaDesde()) + "' AND '" + formateadorFecha.format(criteria.getFechaHasta()) + "'";
        }
        query += " ORDER BY c.fechaApertura DESC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Caja> typedQuery = em.createQuery(query, Caja.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        if (criteria.getCantidadDeRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantidadDeRegistros());
        }
        List<Caja> cajas = typedQuery.getResultList();
        em.close();
        return cajas;
    }
}
