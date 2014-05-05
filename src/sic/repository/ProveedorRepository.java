package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaProveedorCriteria;
import sic.modelo.Empresa;
import sic.modelo.Proveedor;
import sic.util.PersistenceUtil;

public class ProveedorRepository {

    public List<Proveedor> getProveedores(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Proveedor> typedQuery = em.createNamedQuery("Proveedor.buscarTodos", Proveedor.class);
        typedQuery.setParameter("empresa", empresa);
        List<Proveedor> proveedores = typedQuery.getResultList();
        em.close();
        return proveedores;
    }

    public Proveedor getProveedorPorCodigo(String codigo, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Proveedor> typedQuery = em.createNamedQuery("Proveedor.buscarPorCodigo", Proveedor.class);
        typedQuery.setParameter("codigo", codigo);
        typedQuery.setParameter("empresa", empresa);
        List<Proveedor> proveedores = typedQuery.getResultList();
        em.close();
        if (proveedores.isEmpty()) {
            return null;
        } else {
            return proveedores.get(0);
        }
    }

    public Proveedor getProveedorPorRazonSocial(String razonSocial, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Proveedor> typedQuery = em.createNamedQuery("Proveedor.buscarPorRazonSocial", Proveedor.class);
        typedQuery.setParameter("razonSocial", razonSocial);
        typedQuery.setParameter("empresa", empresa);
        List<Proveedor> proveedores = typedQuery.getResultList();
        em.close();
        if (proveedores.isEmpty()) {
            return null;
        } else {
            return proveedores.get(0);
        }
    }

    public Proveedor getProveedorPorId_Fiscal(String id_Fiscal, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Proveedor> typedQuery = em.createNamedQuery("Proveedor.buscarPorIdFiscal", Proveedor.class);
        typedQuery.setParameter("idFiscal", id_Fiscal);
        typedQuery.setParameter("empresa", empresa);
        List<Proveedor> proveedores = typedQuery.getResultList();
        em.close();
        if (proveedores.isEmpty()) {
            return null;
        } else {
            return proveedores.get(0);
        }
    }

    public List<Proveedor> buscarProveedores(BusquedaProveedorCriteria criteria) {
        String query = "SELECT p FROM Proveedor p WHERE p.empresa = :empresa AND p.eliminado = false";
        //Razon Social
        if (criteria.isBuscaPorRazonSocial() == true) {
            String[] terminos = criteria.getRazonSocial().split(" ");
            for (int i = 0; i < terminos.length; i++) {
                query += " AND p.razonSocial LIKE '%" + terminos[i] + "%'";
            }
        }
        //Id_Fiscal
        if (criteria.isBuscaPorId_Fiscal() == true) {
            query = query + " AND p.id_Fiscal = '" + criteria.getId_Fiscal() + "'";
        }
        //Codigo
        if (criteria.isBuscaPorCodigo() == true) {
            query = query + " AND p.codigo = '" + criteria.getCodigo() + "'";
        }
        //Localidad
        if (criteria.isBuscaPorLocalidad() == true) {
            query = query + " AND p.localidad = " + criteria.getLocalidad().getId_Localidad();
        }
        //Provincia
        if (criteria.isBuscaPorProvincia() == true) {
            query = query + " AND p.localidad.provincia = " + criteria.getProvincia().getId_Provincia();
        }
        //Pais
        if (criteria.isBuscaPorPais() == true) {
            query = query + " AND p.localidad.provincia.pais = " + criteria.getPais().getId_Pais();
        }
        query = query + " ORDER BY p.razonSocial ASC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Proveedor> typedQuery = em.createQuery(query, Proveedor.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<Proveedor> proveedores = typedQuery.getResultList();
        em.close();
        return proveedores;
    }

    public void guardar(Proveedor proveedor) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(proveedor));
        tx.commit();
        em.close();
    }

    public void actualizar(Proveedor proveedor) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(proveedor);
        tx.commit();
        em.close();
    }
}