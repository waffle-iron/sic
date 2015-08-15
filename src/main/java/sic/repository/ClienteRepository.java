package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaClienteCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.util.PersistenceUtil;

public class ClienteRepository {

    public List<Cliente> getClientes(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarTodos", Cliente.class);
        typedQuery.setParameter("empresa", empresa);
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        return clientes;

    }

    public Cliente getClientePorId(long id_Cliente) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarPorId", Cliente.class);
        typedQuery.setParameter("id", id_Cliente);
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }
    }

    public Cliente getClientePorRazonSocial(String razonSocial, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarPorRazonSocial", Cliente.class);
        typedQuery.setParameter("razonSocial", razonSocial);
        typedQuery.setParameter("empresa", empresa);
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }
    }

    public List<Cliente> getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(String criteria, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarQueContengaRazonSocialNombreFantasiaIdFiscal", Cliente.class);
        typedQuery.setParameter("empresa", empresa);
        typedQuery.setParameter("criteria", "%" + criteria + "%");
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        return clientes;
    }

    public Cliente getClientePorId_Fiscal(String id_Fiscal, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarPorIdFiscal", Cliente.class);
        typedQuery.setParameter("id_Fiscal", id_Fiscal);
        typedQuery.setParameter("empresa", empresa);
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }
    }

    public Cliente getClientePredeterminado(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createNamedQuery("Cliente.buscarPredeterminado", Cliente.class);
        typedQuery.setParameter("empresa", empresa);
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }
    }

    public List<Cliente> buscarClientes(BusquedaClienteCriteria criteria) {
        String query = "SELECT c FROM Cliente c WHERE c.empresa = :empresa AND c.eliminado = false";

        //OR entre razonSocial y nombreFantasia
        if (criteria.buscaPorRazonSocial() && criteria.buscaPorNombreFantasia()) {
            String[] terminos = criteria.getRazonSocial().split(" ");
            for (String termino : terminos) {
                query += " AND (c.razonSocial LIKE '%" + termino + "%'" + " OR c.nombreFantasia LIKE '%" + termino + "%')";
            }
        } else {
            if (criteria.buscaPorRazonSocial() == true) {
                String[] terminos = criteria.getRazonSocial().split(" ");
                for (String termino : terminos) {
                    query += " AND c.razonSocial LIKE '%" + termino + "%'";
                }
            }
            if (criteria.buscaPorNombreFantasia() == true) {
                String[] terminos = criteria.getNombreFantasia().split(" ");
                for (String termino : terminos) {
                    query += " AND c.nombreFantasia LIKE '%" + termino + "%'";
                }
            }
        }
        if (criteria.buscaPorId_Fiscal() == true) {
            query = query + " AND c.id_Fiscal = '" + criteria.getId_Fiscal() + "'";
        }
        if (criteria.buscaPorLocalidad() == true) {
            query = query + " AND c.localidad = " + criteria.getLocalidad().getId_Localidad();
        }
        if (criteria.buscaPorProvincia() == true) {
            query = query + " AND c.localidad.provincia = " + criteria.getProvincia().getId_Provincia();
        }
        if (criteria.buscaPorPais() == true) {
            query = query + " AND c.localidad.provincia.pais = " + criteria.getPais().getId_Pais();
        }
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Cliente> typedQuery = em.createQuery(query, Cliente.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        List<Cliente> clientes = typedQuery.getResultList();
        em.close();
        return clientes;
    }

    public void guardar(Cliente cliente) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(cliente));
        tx.commit();
        em.close();
    }

    public void actualizar(Cliente cliente) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(cliente);
        tx.commit();
        em.close();
    }
}
