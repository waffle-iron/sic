package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonPedido;
import sic.util.FormatterFechaHora;
import sic.util.PersistenceUtil;

public class PedidoRepository {

    public List<RenglonPedido> getRenglonesDelPedido(long idPedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.buscarRenglonesDelPedido", Pedido.class);
        typedQuery.setParameter("idPedido", idPedido);
        List<Pedido> paraObtenerRenglones = typedQuery.getResultList();
        em.close();
        return paraObtenerRenglones.get(0).getRenglones();
    }

    public List<Pedido> buscarPedidosPorCriteria(BusquedaPedidoCriteria criteria) {
        String query = "SELECT p FROM Pedido p WHERE p.empresa = :empresa AND p.eliminado = false";
        //Fecha del pedido
        if (criteria.isBuscaPorFecha() == true) {
            FormatterFechaHora formateadorFecha = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_INTERNACIONAL);
            query += " AND p.fecha BETWEEN '" + formateadorFecha.format(criteria.getFechaDesde()) + "' AND '" + formateadorFecha.format(criteria.getFechaHasta()) + "'";
        }
        //Cliente
        if (criteria.isBuscaCliente() == true) {
            query += " AND p.cliente = " + criteria.getCliente().getId_Cliente();
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true) {
            query += " AND p.usuario = " + criteria.getUsuario().getId_Usuario();
        }
        //Nro de Pedido
        if (criteria.isBuscaPorNroPedido() == true) {
            query += " AND p.nroPedido = " + criteria.getNroPedido();
        }
        query += " ORDER BY p.fecha DESC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createQuery(query, Pedido.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<Pedido> pedidos = typedQuery.getResultList();
        em.close();
        return pedidos;
    }

    public long calcularNumeroPedido(long idEmpresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> typedQuery = em.createNamedQuery("Pedido.buscarCantidadNroPedidos", Long.class);
        typedQuery.setParameter("idEmpresa", idEmpresa);
        Long resultado = typedQuery.getSingleResult();
        em.close();
        if (resultado == 0) {
            return 1;
        } else {
            return resultado + 1;
        }
    }

    public List<Factura> getFacturasDelPedido(long nroPedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Factura> typedQuery = em.createNamedQuery("Pedido.buscarPedidoConFacturas", Factura.class);
        typedQuery.setParameter("nroPedido", nroPedido);
        List<Factura> facturas = typedQuery.getResultList();
        em.close();
        return facturas;
    }

    public void guardar(Pedido pedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(pedido));
        tx.commit();
        em.close();
    }

    public Pedido getPedidoPorId(long id_Pedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.buscarPorId", Pedido.class);
        typedQuery.setParameter("id", id_Pedido);
        List<Pedido> pedidos = typedQuery.getResultList();
        em.close();
        if (pedidos.isEmpty()) {
            return null;
        } else {
            return pedidos.get(0);
        }
    }

    public void actualizar(Pedido pedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pedido);
        tx.commit();
        em.close();
    }

    public Pedido getPedidoPorNumero(long nroPedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.buscarPorNumero", Pedido.class);
        typedQuery.setParameter("nroPedido", nroPedido);
        List<Pedido> pedidos = typedQuery.getResultList();
        em.close();
        if (pedidos.isEmpty()) {
            return null;
        } else {
            return pedidos.get(0);
        }
    }

    public Pedido getPedidoPorNumeroConFacturas(long nroPedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.buscarPorNumeroConFacturas", Pedido.class);
        typedQuery.setParameter("nroPedido", nroPedido);
        List<Pedido> pedidos = typedQuery.getResultList();
        em.close();
        if (pedidos.isEmpty()) {
            return null;
        } else {
            return pedidos.get(0);
        }
    }

    public Pedido getPedidoPorNumeroConRenglones(long nroPedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.buscarPorNumeroConRenglones", Pedido.class);
        typedQuery.setParameter("nroPedido", nroPedido);
        List<Pedido> pedidos = typedQuery.getResultList();
        em.close();
        if (pedidos.isEmpty()) {
            return null;
        } else {
            return pedidos.get(0);
        }
    }
}
