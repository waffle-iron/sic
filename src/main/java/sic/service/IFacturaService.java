package sic.service;

import java.util.Date;
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

    double calcularImpInterno_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones);

    double calcularIva_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones, double iva_porcentaje);

    long calcularNumeroFactura(String tipoDeFactura, long serie);

    double calcularRecargo_neto(double subtotal, double recargo_porcentaje);

    public List<RenglonFactura> convertirRenglonesPedidoARenglonesFactura(Pedido pedido, String tipoComprobante);

    public RenglonFactura getRenglonFacturaPorRenglonPedido(RenglonPedido renglon, String tipoComprobante);

    public List<Factura> getFacturasPorFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta);

    //**************************************************************************
    //Calculos
    double calcularSubTotal(List<RenglonFactura> renglones);

    double calcularSubTotal_neto(double subtotal, double recargo_neto, double descuento_neto);

    double calcularTotal(double subTotal, double descuento_neto, double recargo_neto, double iva105_neto, double iva21_neto, double impInterno_neto);

    double calcularTotalFacturado(List<FacturaVenta> facturas);

    double calcularTotalFacturas(List<FacturaVenta> facturas);

    double calcularVuelto(double importeAPagar, double importeAbonado);

    //**************************************************************************
    //Division de Factura
    List<FacturaVenta> dividirFactura(FacturaVenta factura, int[] indices);

    void eliminar(Factura factura);

    FacturaVenta getFacturaVentaPorTipoSerieNum(String tipo, long serie, long num);

    FacturaCompra getFacturaCompraPorTipoSerieNum(String tipo, long serie, long num);

    List<RenglonFactura> getRenglonesDeLaFactura(Factura factura);

    //**************************************************************************
    //Reportes
    JasperPrint getReporteFacturaVenta(Factura factura) throws JRException;

    char[] getTipoFacturaCompra(Empresa empresa, Proveedor proveedor);

    String[] getTipoFacturaVenta(Empresa empresa, Cliente cliente);

    char[] getTiposFacturaSegunEmpresa(Empresa empresa);

    public String getTipoFactura(Factura factura);

    void guardar(Factura factura);

    public Movimiento getTipoMovimiento(Factura factura);

    //**************************************************************************
    //Estadisticas
    List<Object[]> listarProductosMasVendidosPorAnio(int anio);

    boolean validarCantidadMaximaDeRenglones(int cantidad);

    public RenglonFactura calcularRenglon(String tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje);

}
