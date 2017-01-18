package sic.repository;

import java.util.List;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;

public interface IFacturaRepository {

    Factura getFacturaPorId(Long id_Factura);
    
    void actualizar(Factura factura);

    List<FacturaCompra> buscarFacturasCompra(BusquedaFacturaCompraCriteria criteria);

    List<FacturaVenta> buscarFacturasVenta(BusquedaFacturaVentaCriteria criteria);

    Factura getFacturaPorTipoSerieNum(char tipo, long serie, long num, long idEmpresa);
    
    List<Factura> getFacturasDelPedido(Long idPedido);
    
    long getMayorNumFacturaSegunTipo(String tipoDeFactura, long serie);
    
    double calcularTotalFacturadoVenta(BusquedaFacturaVentaCriteria criteria);
    
    double calcularTotalFacturadoCompra(BusquedaFacturaCompraCriteria criteria);
    
    double calcularIVA_Venta(BusquedaFacturaVentaCriteria criteria, Character[] tipoFacturasDiscriminadas);
    
    double calcularIVA_Compra(BusquedaFacturaCompraCriteria criteria, Character[] tipoFacturasDiscriminadas);
    
    double calcularGananciaTotal(BusquedaFacturaVentaCriteria criteria);

    Factura guardar(Factura factura);    
}
