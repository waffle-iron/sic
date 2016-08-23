package sic.service;

import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public interface IFacturaService {

    List<FacturaCompra> buscarFacturaCompra(BusquedaFacturaCompraCriteria criteria);

    List<FacturaVenta> buscarFacturaVenta(BusquedaFacturaVentaCriteria criteria);

    double calcularDescuento_neto(double subtotal, double descuento_porcentaje);

    double calcularGananciaTotal(List<FacturaVenta> facturas);

    double calcularIVA_Venta(List<FacturaVenta> facturas);

    double calcularIVA_Compra(List<FacturaCompra> facturas);

    double calcularImpInterno_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones);

    double calcularIva_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones, double iva_porcentaje);

    long calcularNumeroFactura(String tipoDeFactura, long serie);

    double calcularRecargo_neto(double subtotal, double recargo_porcentaje);

    List<RenglonFactura> convertirRenglonesPedidoARenglonesFactura(Pedido pedido, String tipoComprobante);

    RenglonFactura getRenglonFacturaPorRenglonPedido(RenglonPedido renglon, String tipoComprobante);

    RenglonFactura calcularRenglon(String tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje);

    boolean validarFacturasParaPagoMultiple(List<Factura> facturas, Movimiento movimiento);
    
    boolean validarFacturasParaPagoMultiplePorPagadas(List<Factura> facturas);
    
    boolean validarFacturasParaPagoMultiplePorClienteProveedor(List<Factura> facturas, Movimiento movimiento);

    //**************************************************************************
    //Calculos
    double calcularSubTotal(List<RenglonFactura> renglones);

    double calcularSubTotal_neto(double subtotal, double recargo_neto, double descuento_neto);

    double calcularTotal(double subTotal, double descuento_neto, double recargo_neto, double iva105_neto, double iva21_neto, double impInterno_neto);

    double calcularTotalFacturadoVenta(List<FacturaVenta> facturas);

    double calcularTotalFacturadoCompra(List<FacturaCompra> facturas);

    double calcularVuelto(double importeAPagar, double importeAbonado);

    double calcularImporte(double cantidad, double precioUnitario, double descuento_neto);

    double calcularIVA_neto(Movimiento movimiento, Producto producto, double descuento_neto);

    double calcularImpInterno_neto(Movimiento movimiento, Producto producto, double descuento_neto);

    double calcularPrecioUnitario(Movimiento movimiento, String tipoDeFactura, Producto producto);

    //**************************************************************************
    //Division de Factura
    List<FacturaVenta> dividirFactura(FacturaVenta factura, int[] indices);

    void eliminar(Factura factura);

    FacturaVenta getFacturaVentaPorTipoSerieNum(char tipo, long serie, long num);

    FacturaCompra getFacturaCompraPorTipoSerieNum(char tipo, long serie, long num);

    List<RenglonFactura> getRenglonesDeLaFactura(Factura factura);

    //**************************************************************************
    //Reportes
    JasperPrint getReporteFacturaVenta(Factura factura) throws JRException;

    String[] getTipoFacturaCompra(Empresa empresa, Proveedor proveedor);

    String[] getTipoFacturaVenta(Empresa empresa, Cliente cliente);

    char[] getTiposFacturaSegunEmpresa(Empresa empresa);

    String getTipoFactura(Factura factura);

    void guardar(Factura factura);
    
    void actualizar(Factura factura);
    
    List<Factura> ordenarFacturasPorFechaAsc(List<Factura> facturas);

    Movimiento getTipoMovimiento(Factura factura);

    //**************************************************************************
    //Estadisticas
    List<Object[]> listarProductosMasVendidosPorAnio(int anio);

    boolean validarCantidadMaximaDeRenglones(int cantidad);

}
