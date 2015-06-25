package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaTransportistaCriteria;
import sic.modelo.Empresa;
import sic.modelo.Transportista;
import sic.util.PersistenceUtil;

public class TransportistaRepository {

    public List<Transportista> getTransportistas(Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Transportista> typedQuery = em.createNamedQuery("Transportista.buscarTodos", Transportista.class);
        typedQuery.setParameter("empresa", empresa);
        List<Transportista> transportistas = typedQuery.getResultList();
        em.close();
        return transportistas;
    }

    public Transportista getTransportistaPorNombre(String nombre, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Transportista> typedQuery = em.createNamedQuery("Transportista.buscarPorNombre", Transportista.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("empresa", empresa);
        List<Transportista> transportistas = typedQuery.getResultList();
        em.close();
        if (transportistas.isEmpty()) {
            return null;
        } else {
            return transportistas.get(0);
        }
    }

    public List<Transportista> busquedaPersonalizada(BusquedaTransportistaCriteria criteria) {
        String query = "SELECT t FROM Transportista t WHERE t.empresa = :empresa AND t.eliminado = false";
        //Nombre
        if (criteria.isBuscarPorNombre() == true) {
            String[] terminos = criteria.getNombre().split(" ");
            for (int i = 0; i < terminos.length; i++) {
                query += " AND t.nombre LIKE '%" + terminos[i] + "%'";
            }            
        }        
        //Localidad
        if (criteria.isBuscarPorLocalidad() == true) {
            query = query + " AND t.localidad = " + criteria.getLocalidad().getId_Localidad();
        }
        //Provincia
        if (criteria.isBuscarPorProvincia() == true) {
            query = query + " AND t.localidad.provincia = " + criteria.getProvincia().getId_Provincia();
        }
        //Pais
        if (criteria.isBuscarPorPais() == true) {
            query = query + " AND t.localidad.provincia.pais = " + criteria.getPais().getId_Pais();
        }
        query = query + " ORDER BY t.nombre ASC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Transportista> typedQuery = em.createQuery(query, Transportista.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        List<Transportista> transportistas = typedQuery.getResultList();
        em.close();
        return transportistas;
    }

    public void guardar(Transportista transportista) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(transportista));
        tx.commit();
        em.close();
    }

    public void actualizar(Transportista transportista) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(transportista);
        tx.commit();
        em.close();
    }
}