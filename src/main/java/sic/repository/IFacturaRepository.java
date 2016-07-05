package sic.repository;

import java.util.Date;
import java.util.List;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.RenglonFactura;

public interface IFacturaRepository {

    void actualizar(Factura factura);

    List<FacturaCompra> buscarFacturasCompra(BusquedaFacturaCompraCriteria criteria);

    List<FacturaVenta> buscarFacturasVenta(BusquedaFacturaVentaCriteria criteria);

    FacturaVenta getFacturaVentaPorTipoSerieNum(char tipo, long serie, long num);

    long getMayorNumFacturaSegunTipo(String tipoDeFactura, long serie);

    List<RenglonFactura> getRenglonesDeLaFactura(Factura factura);

    void guardar(Factura factura);

    List<Object[]> listarProductosMasVendidosPorAnio(int anio);

    List<Factura> getFacturasPorFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta);

    FacturaCompra getFacturaCompraPorTipoSerieNum(char tipo, long serie, long num);
}
