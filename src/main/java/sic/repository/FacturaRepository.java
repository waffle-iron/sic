package sic.repository;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.RenglonFactura;
import sic.util.FormatterFechaHora;
import sic.util.PersistenceUtil;

public class FacturaRepository {

    public List<RenglonFactura> getRenglonesDeLaFactura(Factura factura) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<RenglonFactura> typedQuery = em.createNamedQuery("RenglonFactura.getRenglonesDeLaFactura", RenglonFactura.class);
        typedQuery.setParameter("factura", factura);
        List<RenglonFactura> renglones = typedQuery.getResultList();
        em.close();
        return renglones;
    }

    public FacturaVenta getFacturaVentaPorTipoSerieNum(char tipo, long serie, long num) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FacturaVenta> typedQuery = em.createNamedQuery("Factura.buscarPorTipoSerieNum", FacturaVenta.class);
        typedQuery.setParameter("tipo", tipo);
        typedQuery.setParameter("serie", serie);
        typedQuery.setParameter("num", num);
        List<FacturaVenta> facturasVenta = typedQuery.getResultList();
        em.close();
        if (facturasVenta.isEmpty()) {
            return null;
        } else {
            return facturasVenta.get(0);
        }
    }

    public List<FacturaCompra> buscarFacturasCompra(BusquedaFacturaCompraCriteria criteria) {
        String query = "SELECT f FROM FacturaCompra f WHERE f.empresa = :empresa AND f.eliminada = false";
        //Fecha Factura
        if (criteria.isBuscaPorFecha() == true) {
            FormatterFechaHora formateadorFecha = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_INTERNACIONAL);
            query += " AND f.fecha BETWEEN '" + formateadorFecha.format(criteria.getFechaDesde()) + "' AND '" + formateadorFecha.format(criteria.getFechaHasta()) + "'";
        }
        //Proveedor
        if (criteria.isBuscaPorProveedor() == true) {
            query += " AND f.proveedor = " + criteria.getProveedor().getId_Proveedor();
        }
        //Nro de Factura
        if (criteria.isBuscaPorNumeroFactura() == true) {
            query += " AND f.numSerie = " + criteria.getNumSerie() + " AND f.numFactura = " + criteria.getNumFactura();
        }
        //Inpagas
        if (criteria.isBuscarSoloInpagas() == true) {
            query += " AND f.pagada = false";
        }
        query += " ORDER BY f.fecha ASC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FacturaCompra> typedQuery = em.createQuery(query, FacturaCompra.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<FacturaCompra> facturasCompra = typedQuery.getResultList();
        em.close();
        return facturasCompra;
    }

    public List<FacturaVenta> buscarFacturasVenta(BusquedaFacturaVentaCriteria criteria) {
        String query = "SELECT f FROM FacturaVenta f WHERE f.empresa = :empresa AND f.eliminada = false";
        //Fecha
        if (criteria.isBuscaPorFecha() == true) {
            FormatterFechaHora formateadorFecha = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_INTERNACIONAL);
            query += " AND f.fecha BETWEEN '" + formateadorFecha.format(criteria.getFechaDesde()) + "' AND '" + formateadorFecha.format(criteria.getFechaHasta()) + "'";
        }
        //Cliente
        if (criteria.isBuscaCliente() == true) {
            query += " AND f.cliente = " + criteria.getCliente().getId_Cliente();
        }
        //Tipo de Factura
        if (criteria.isBuscaPorTipoFactura() == true) {
            query += " AND f.tipoFactura = '" + criteria.getTipoFactura() + "'";
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true) {
            query += " AND f.usuario = " + criteria.getUsuario().getId_Usuario();
        }
        //Nro de Factura
        if (criteria.isBuscaPorNumeroFactura() == true) {
            query += " AND f.numSerie = " + criteria.getNumSerie() + " AND f.numFactura = " + criteria.getNumFactura();
        }
        //Pedido
        if (criteria.isBuscarPorPedido() == true) {
            query += " AND f.pedido.nroPedido = " + criteria.getNroPedido();
        }
        //Inpagas
        if (criteria.isBuscaSoloInpagas() == true) {
            query += " AND f.pagada = false";
        }
        //Pagadas
        if (criteria.isBuscaSoloPagadas() == true) {
            query += " AND f.pagada = true";
        }
        query += " ORDER BY f.fecha DESC";
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<FacturaVenta> typedQuery = em.createQuery(query, FacturaVenta.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<FacturaVenta> facturasVenta = typedQuery.getResultList();
        em.close();
        return facturasVenta;
    }

    public long buscarMayorNumFacturaSegunTipo(char tipoDeFactura, long serie) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> typedQuery = em.createNamedQuery("Factura.buscarMayorNumFacturaSegunTipo", Long.class);
        typedQuery.setParameter("tipo", tipoDeFactura);
        typedQuery.setParameter("serie", serie);
        Long resultado = typedQuery.getSingleResult();
        em.close();
        if (resultado == null) {
            return 0;
        } else {
            return resultado;
        }
    }

    public void guardar(Factura factura) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(factura));
        tx.commit();
        em.close();
    }

    public void actualizar(Factura factura) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(factura);
        tx.commit();
        em.close();
    }

    public List<Object[]> listarProductosMasVendidosPorAnio(int anio) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object[]> typedQuery = (TypedQuery<Object[]>) em.createNamedQuery("Factura.buscarTopProductosMasVendidosPorAnio");
        typedQuery.setParameter("anio", anio);
        typedQuery.setMaxResults(5);
        List<Object[]> resultado = typedQuery.getResultList();
        em.close();
        return resultado;
    }

    public List<Object> getFacturasPorFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object> typedQuery = (TypedQuery<Object>) em.createNamedQuery("Factura.buscarEntreFechasYFormaDePago");
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("id_FormaDePago", id_FormaDePago);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Object> facturas = typedQuery.getResultList();
        em.close();
        return facturas;
    }

    public List<Object> getFacturasPorFechas(long id_Empresa, Date desde, Date hasta) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object> typedQuery = (TypedQuery<Object>) em.createNamedQuery("Factura.buscarEntreFechas");
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Object> facturas = typedQuery.getResultList();
        em.close();
        return facturas;
    }
}
