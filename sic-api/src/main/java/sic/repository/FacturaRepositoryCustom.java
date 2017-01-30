package sic.repository;

import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;

public interface FacturaRepositoryCustom {
    
    double calcularTotalFacturadoVenta(BusquedaFacturaVentaCriteria criteria); 
    
    double calcularTotalFacturadoCompra(BusquedaFacturaCompraCriteria criteria);
    
    double calcularIVA_Venta(BusquedaFacturaVentaCriteria criteria);
    
    double calcularIVA_Compra(BusquedaFacturaCompraCriteria criteria);
    
    double calcularGananciaTotal(BusquedaFacturaVentaCriteria criteria); 
    
}
