package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.Empresa;
import sic.modelo.Producto;
import sic.util.PersistenceUtil;

public class ProductoRepository {

    public List<Producto> BuscarProductos(BusquedaProductoCriteria criteria) {
        String query = "SELECT p FROM Producto p WHERE p.empresa = :empresa AND p.eliminado = false";
        //Codigo        
        if (criteria.isBuscarPorCodigo() == true) {
            query = query + " AND p.codigo = '" + criteria.getCodigo() + "'";
        }
        //Descripcion
        if (criteria.isBuscarPorDescripcion() == true) {
            String[] terminos = criteria.getDescripcion().split(" ");
            for (int i = 0; i < terminos.length; i++) {
                query += " AND p.descripcion LIKE '%" + terminos[i] + "%'";
            }
        }
        //Rubro
        if (criteria.isBuscarPorRubro() == true) {
            query = query + " AND p.rubro = " + criteria.getRubro().getId_Rubro();
        }
        //Proveedor
        if (criteria.isBuscarPorProveedor()) {
            query = query + " AND p.proveedor = " + criteria.getProveedor().getId_Proveedor();
        }
        //Faltantes
        if (criteria.isListarSoloFaltantes() == true) {
            query = query + " AND p.cantidad <= p.cantMinima AND p.ilimitado = 0";
        }
        query = query + " ORDER BY p.descripcion ASC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Producto> typedQuery = em.createQuery(query, Producto.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<Producto> productos = typedQuery.getResultList();
        em.close();
        return productos;
    }

    public Producto getProductoPorId(long id_Producto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Producto> typedQuery = em.createNamedQuery("Producto.buscarPorId", Producto.class);
        typedQuery.setParameter("id", id_Producto);
        List<Producto> productos = typedQuery.getResultList();
        em.close();
        if (productos.isEmpty()) {
            return null;
        } else {
            return productos.get(0);
        }
    }

    public Producto getProductoPorDescripcion(String descripcion, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Producto> typedQuery = em.createNamedQuery("Producto.buscarPorDescripcion", Producto.class);
        typedQuery.setParameter("descripcion", descripcion);
        typedQuery.setParameter("empresa", empresa);
        List<Producto> productos = typedQuery.getResultList();
        em.close();
        if (productos.isEmpty()) {
            return null;
        } else {
            return productos.get(0);
        }
    }

    public List<Producto> getProductoPorDescripcionQueContenga(String descripcion, int cantRegistros, Empresa empresa) {
        String query = "SELECT p FROM Producto p WHERE p.empresa = :empresa AND p.eliminado = false";
        String[] terminos = descripcion.split(" ");
        for (int i = 0; i < terminos.length; i++) {
            query += " AND p.descripcion LIKE '%" + terminos[i] + "%'";
        }
        query += " ORDER BY p.descripcion ASC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Producto> typedQuery = em.createQuery(query, Producto.class);
        typedQuery.setParameter("empresa", empresa);
        //si es 0, recupera TODOS los registros
        if (cantRegistros != 0) {
            typedQuery.setMaxResults(cantRegistros);
        }
        List<Producto> productos = typedQuery.getResultList();
        em.close();
        return productos;
    }

    public Producto getProductoPorCodigo(String codigo, Empresa empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Producto> typedQuery = em.createNamedQuery("Producto.buscarPorCodigo", Producto.class);
        typedQuery.setParameter("codigo", codigo);
        typedQuery.setParameter("empresa", empresa);
        List<Producto> productos = typedQuery.getResultList();
        em.close();
        if (productos.isEmpty()) {
            return null;
        } else {
            return productos.get(0);
        }
    }

    public void guardar(Producto producto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(producto));
        tx.commit();
        em.close();
    }

    public void actualizar(Producto producto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(producto);
        tx.commit();
        em.close();
    }

    public void actualizarMultiplesProductos(List<Producto> productos) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (Producto producto : productos) {
            em.merge(producto);
        }
        tx.commit();
        em.close();
    }
}