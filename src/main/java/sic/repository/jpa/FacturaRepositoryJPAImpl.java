package sic.repository.jpa;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.RenglonFactura;
import sic.repository.IFacturaRepository;
import sic.util.FormatterFechaHora;

@Repository
public class FacturaRepositoryJPAImpl implements IFacturaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RenglonFactura> getRenglonesDeLaFactura(Factura factura) {
        TypedQuery<RenglonFactura> typedQuery = em.createNamedQuery("RenglonFactura.getRenglonesDeLaFactura", RenglonFactura.class);
        typedQuery.setParameter("factura", factura);
        List<RenglonFactura> renglones = typedQuery.getResultList();
        return renglones;
    }

    @Override
    public FacturaVenta getFacturaVentaPorTipoSerieNum(String tipo, long serie, long num) {
        TypedQuery<FacturaVenta> typedQuery = em.createNamedQuery("Factura.buscarPorTipoSerieNum", FacturaVenta.class);
        typedQuery.setParameter("tipo", tipo.charAt(tipo.length() - 1));
        typedQuery.setParameter("serie", serie);
        typedQuery.setParameter("num", num);
        List<FacturaVenta> facturasVenta = typedQuery.getResultList();
        if (facturasVenta.isEmpty()) {
            return null;
        } else {
            return facturasVenta.get(0);
        }
    }

    @Override
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
        TypedQuery<FacturaCompra> typedQuery = em.createQuery(query, FacturaCompra.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<FacturaCompra> facturasCompra = typedQuery.getResultList();
        return facturasCompra;
    }

    @Override
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
        if (criteria.isBuscaSoloImpagas() == true) {
            query += " AND f.pagada = false";
        }
        query += " ORDER BY f.fecha ASC";
        TypedQuery<FacturaVenta> typedQuery = em.createQuery(query, FacturaVenta.class);
        typedQuery.setParameter("empresa", criteria.getEmpresa());
        //si es 0, recupera TODOS los registros
        if (criteria.getCantRegistros() != 0) {
            typedQuery.setMaxResults(criteria.getCantRegistros());
        }
        List<FacturaVenta> facturasVenta = typedQuery.getResultList();
        return facturasVenta;
    }

    @Override
    public long getMayorNumFacturaSegunTipo(String tipoDeFactura, long serie) {
        TypedQuery<Long> typedQuery = em.createNamedQuery("Factura.buscarMayorNumFacturaSegunTipo", Long.class);
        typedQuery.setParameter("tipo", tipoDeFactura.charAt(tipoDeFactura.length() - 1));
        typedQuery.setParameter("serie", serie);
        Long resultado = typedQuery.getSingleResult();
        if (resultado == null) {
            return 0;
        } else {
            return resultado;
        }
    }

    @Override
    public void guardar(Factura factura) {;
        em.persist(em.merge(factura));
    }

    @Override
    public void actualizar(Factura factura) {
        em.merge(factura);
    }

    @Override
    public List<Object[]> listarProductosMasVendidosPorAnio(int anio) {
        TypedQuery<Object[]> typedQuery = (TypedQuery<Object[]>) em.createNamedQuery("Factura.buscarTopProductosMasVendidosPorAnio");
        typedQuery.setParameter("anio", anio);
        typedQuery.setMaxResults(5);
        List<Object[]> resultado = typedQuery.getResultList();
        return resultado;
    }

    @Override
    public List<Factura> getFacturasPorFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        TypedQuery<Factura> typedQuery = em.createNamedQuery("Factura.buscarEntreFechasYFormaDePago", Factura.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("id_FormaDePago", id_FormaDePago);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Factura> facturas = typedQuery.getResultList();
        em.close();
        return facturas;
    }

    @Override
    public List<Factura> getFacturasPorFechas(long id_Empresa, Date desde, Date hasta) {
        TypedQuery<Factura> typedQuery = em.createNamedQuery("Factura.buscarEntreFechas", Factura.class);
        typedQuery.setParameter("id_Empresa", id_Empresa);
        typedQuery.setParameter("desde", desde);
        typedQuery.setParameter("hasta", hasta);
        List<Factura> facturas = typedQuery.getResultList();
        em.close();
        return facturas;
    }
}
