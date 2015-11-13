package sic.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Pedido;
import sic.modelo.RenglonPedido;
import sic.util.FormatterFechaHora;
import sic.util.PersistenceUtil;

public class PedidoRepository {

    public List<RenglonPedido> getRenglonesDelPedido(Pedido pedido) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<RenglonPedido> typedQuery = em.createNamedQuery("RenglonPedido.getRenglonesDelPedido", RenglonPedido.class);
        typedQuery.setParameter("pedido", pedido);
        List<RenglonPedido> renglonesPedido = typedQuery.getResultList();
        em.close();
        return renglonesPedido;
    }

    public List<Pedido> buscarPedido(BusquedaPedidoCriteria criteria) {
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
        if (criteria.isBuscaPorNumeroPedido() == true) {
            query += " AND p.nroPedido = " + criteria.getNumIdPedido();
        }
        query += " ORDER BY p.fecha ASC";
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

    public long calcularNumeroPedido(long empresa) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> typedQuery = em.createNamedQuery("Pedido.numeroDePedidos", Long.class);
        typedQuery.setParameter("empresa", empresa);
        Long resultado = typedQuery.getSingleResult();
        em.close();
        if (resultado == 0) {
            return 1;
        } else {
            return resultado + 1;
        }
    }

}
