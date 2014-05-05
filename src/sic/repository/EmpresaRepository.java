package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Empresa;
import sic.util.PersistenceUtil;

public class EmpresaRepository {

    public List<Empresa> getEmpresas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarTodas", Empresa.class);
        List<Empresa> empresas = typedQuery.getResultList();
        em.close();
        return empresas;
    }

    public Empresa getEmpresaPorNombre(String nombre) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarPorNombre", Empresa.class);
        typedQuery.setParameter("nombre", nombre);
        List<Empresa> empresas = typedQuery.getResultList();
        em.close();
        if (empresas.isEmpty()) {
            return null;
        } else {
            return empresas.get(0);
        }
    }

    public Empresa getEmpresaPorCUIP(long cuip) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarPorCUIP", Empresa.class);
        typedQuery.setParameter("cuip", cuip);
        List<Empresa> empresas = typedQuery.getResultList();
        em.close();
        if (empresas.isEmpty()) {
            return null;
        } else {
            return empresas.get(0);
        }
    }

    public void guardar(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(empresa));
        tx.commit();
        em.close();
    }

    public void actualizar(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(empresa);
        tx.commit();
        em.close();
    }
}