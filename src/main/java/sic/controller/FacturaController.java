package sic.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import sic.modelo.Cliente;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.modelo.Usuario;
import sic.service.BusinessServiceException;
import sic.service.IClienteService;
import sic.service.IEmpresaService;
import sic.service.IFacturaService;
import sic.service.IPedidoService;
import sic.service.IProductoService;
import sic.service.IProveedorService;
import sic.service.IUsuarioService;
import sic.service.Movimiento;

@RestController
@RequestMapping("/api/v1")
public class FacturaController {
    
    private final IFacturaService facturaService;
    private final IEmpresaService empresaService;
    private final IProveedorService proveedorService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;
    private final IPedidoService pedidoService;
    private final IProductoService productoService;
    
    @Autowired
    public FacturaController(IFacturaService facturaService, IEmpresaService empresaService,
                             IProveedorService proveedorService, IClienteService clienteService,
                             IUsuarioService usuarioService, IPedidoService pedidoService,
                             IProductoService productoService) {
        this.facturaService = facturaService;
        this.empresaService = empresaService;
        this.proveedorService = proveedorService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }
    
    @GetMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Factura getFacturaPorId(@PathVariable("id") long id) {
        return facturaService.getFacturaPorId(id);
    }
    
    @PostMapping("/facturas")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Factura> guardar(@RequestBody Factura factura,
                                 @RequestParam(value = "indices", required = false) int[] indices) {
        List<Factura> facturas =  new ArrayList<>();
        if (indices.length > 0 && factura instanceof FacturaVenta) {
            facturas.addAll(facturaService.dividirFactura((FacturaVenta) factura, indices));
            facturaService.guardar(facturas);
        } else {
            facturas.add(factura);
            facturaService.guardar(factura);
        }
        facturas.add(0, facturaService.getFacturaCompraPorTipoSerieNum(facturas.get(0).getTipoFactura(), facturas.get(0).getNumSerie(), facturas.get(0).getNumFactura()));
        if (facturas.size() > 1) {
            facturas.add(1, facturaService.getFacturaCompraPorTipoSerieNum(facturas.get(1).getTipoFactura(), facturas.get(1).getNumSerie(), facturas.get(1).getNumFactura()));
        }
        return facturas;
    }
    
    @PostMapping("/facturas/pedidos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Factura guardar(@PathVariable("id") long id,
                           @RequestBody Factura factura) {
        facturaService.guardar(factura, pedidoService.getPedidoPorId(id)); 
        return facturaService.getFacturaVentaPorTipoSerieNum(factura.getTipoFactura(), factura.getNumSerie(), factura.getNumFactura());
    }    
    
    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) {
        facturaService.eliminar(facturaService.getFacturaPorId(id));
    }
    
    @GetMapping("/facturass/{id}/renglones")
    @ResponseStatus(HttpStatus.OK)
    public List<RenglonFactura> getRenglonesDeLaFactura(@PathVariable("id") long id) {
        return facturaService.getRenglonesDeLaFactura(id);
    }
    
    @GetMapping("/facturas/compra/busqueda/criteria")
    @ResponseStatus(HttpStatus.OK)
    public List<FacturaCompra> buscarFacturaCompra(@RequestParam(value = "idEmpresa") Long idEmpresa,
                                                   @RequestParam(value = "desde", required = false) Long desde,
                                                   @RequestParam(value = "hasta", required = false) Long hasta,
                                                   @RequestParam(value = "idProveedor", required = false) Long idProveedor,
                                                   @RequestParam(value = "nroSerie", required = false) Integer nroSerie,
                                                   @RequestParam(value = "nroFactura", required = false) Integer nroFactura,
                                                   @RequestParam(value = "soloImpagas", required = false) Boolean soloImpagas,
                                                   @RequestParam(value = "soloPagas", required = false) Boolean soloPagas) {
        Calendar fechaDesde = Calendar.getInstance();
        Calendar fechaHasta = Calendar.getInstance();
        if ((desde != null) && (hasta != null)) {
            fechaDesde.setTimeInMillis(desde);            
            fechaHasta.setTimeInMillis(hasta);
        }
        Proveedor proveedor = proveedorService.getProveedorPorId(idProveedor);
        if ((soloImpagas != null) && (soloPagas != null)) {
            if ((soloImpagas == true) && (soloPagas == true)) {
                soloImpagas = false;
                soloPagas = false;
            }
        }
        BusquedaFacturaCompraCriteria criteria = BusquedaFacturaCompraCriteria.builder()
                                                 .empresa(empresaService.getEmpresaPorId(idEmpresa))
                                                 .buscaPorFecha((desde != null) && (hasta != null))
                                                 .fechaDesde(fechaDesde.getTime())
                                                 .fechaHasta(fechaHasta.getTime())
                                                 .buscaPorProveedor(idProveedor != null)
                                                 .proveedor(proveedor)
                                                 .buscaPorNumeroFactura((nroSerie != null) && (nroFactura != null))
                                                 .numSerie((nroSerie != null) ? nroSerie : 0)
                                                 .numFactura((nroFactura != null) ? nroFactura : 0)
                                                 .buscarSoloInpagas(soloImpagas)
                                                 .buscaSoloPagadas(soloPagas)
                                                 .cantRegistros(0)
                                                 .build();
        return facturaService.buscarFacturaCompra(criteria);
    }
    
    @GetMapping("/facturas/venta/busqueda/criteria")
    @ResponseStatus(HttpStatus.OK)
    public List<FacturaVenta> buscarFacturaVenta(@RequestParam(value = "idEmpresa") Long idEmpresa,
                                                 @RequestParam(value = "desde", required = false) Long desde,
                                                 @RequestParam(value = "hasta", required = false) Long hasta,
                                                 @RequestParam(value = "idCliente", required = false) Long idCliente,
                                                 @RequestParam(value = "nroSerie", required = false) Integer nroSerie,
                                                 @RequestParam(value = "nroFactura", required = false) Integer nroFactura,
                                                 @RequestParam(value = "tipoFactura", required = false) Character tipoFactura,
                                                 @RequestParam(value = "idUsuario", required = false) Long idUsuario,
                                                 @RequestParam(value = "nroPedido", required = false) Long nroPedido,
                                                 @RequestParam(value = "soloImpagas", required = false) Boolean soloImpagas,
                                                 @RequestParam(value = "soloPagas", required = false) Boolean soloPagas) {
        Calendar fechaDesde = Calendar.getInstance();
        Calendar fechaHasta = Calendar.getInstance();
        if ((desde != null) && (hasta != null)) {
            fechaDesde.setTimeInMillis(desde);
            fechaHasta.setTimeInMillis(hasta);
        }
        Cliente cliente = clienteService.getClientePorId(idCliente);
        Usuario usuario = usuarioService.getUsuarioPorId(idUsuario);
        if ((soloImpagas != null) && (soloPagas != null)) {
            if ((soloImpagas == true) && (soloPagas == true)) {
                soloImpagas = false;
                soloPagas = false;
            }
        }
        BusquedaFacturaVentaCriteria criteria = BusquedaFacturaVentaCriteria.builder()
                                                 .empresa(empresaService.getEmpresaPorId(idEmpresa))
                                                 .buscaPorFecha((desde != null) && (hasta != null))
                                                 .fechaDesde(fechaDesde.getTime())
                                                 .fechaHasta(fechaHasta.getTime())
                                                 .buscaCliente(cliente != null)
                                                 .cliente(cliente)
                                                 .buscaUsuario(usuario != null)
                                                 .usuario(usuario)
                                                 .buscaPorNumeroFactura((nroSerie != null) && (nroFactura != null))
                                                 .numSerie((nroSerie != null)? nroSerie : 0)
                                                 .numFactura((nroFactura != null) ? nroFactura : 0)
                                                 .buscarPorPedido(nroPedido != null)
                                                 .nroPedido((nroPedido != null) ? nroPedido : 0)
                                                 .buscaPorTipoFactura(tipoFactura != null)
                                                 .tipoFactura((tipoFactura != null) ? tipoFactura : '-')
                                                 .buscaSoloImpagas(soloImpagas)
                                                 .buscaSoloPagadas(soloPagas)
                                                 .cantRegistros(0)
                                                 .build();
        return facturaService.buscarFacturaVenta(criteria);
    }
    
    @GetMapping("/facturas/tipos/empresa/{idEmpresa}/proveedor/{idProveedor}")
    @ResponseStatus(HttpStatus.OK)
    public String[] getTipoFacturaCompra(@PathVariable("idEmpresa") long idEmpresa, @PathVariable("idProveedor") long idProveedor) {
        return facturaService.getTipoFacturaCompra(empresaService.getEmpresaPorId(idEmpresa), proveedorService.getProveedorPorId(idProveedor));
    }
    
    @GetMapping("/facturas/tipos/empresa/{idEmpresa}/cliente/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public String[] getTipoFacturaVenta(@PathVariable("idEmpresa") long idEmpresa, @PathVariable("idCliente") long idCliente) {
        return facturaService.getTipoFacturaVenta(empresaService.getEmpresaPorId(idEmpresa), clienteService.getClientePorId(idCliente));
    }
    
    @GetMapping("/facturas/tipos/empresa/{idEmpresa}")
    @ResponseStatus(HttpStatus.OK)
    public char[] getTiposFacturaSegunEmpresa(@PathVariable("idEmpresa") long idEmpresa) {
        return facturaService.getTiposFacturaSegunEmpresa(empresaService.getEmpresaPorId(idEmpresa));
    }
    
    @GetMapping("/facturas/venta/tipo-serie-numero")
    @ResponseStatus(HttpStatus.OK)
    public FacturaVenta getFacturaVentaPorTipoSerieNum(@RequestParam("tipo") char tipo,
                                                       @RequestParam("serie") long serie,
                                                       @RequestParam("numero") long num) {
        return facturaService.getFacturaVentaPorTipoSerieNum(tipo, serie, num);
    }
    
    @GetMapping("/facturas/compra/tipo-serie-numero")
    @ResponseStatus(HttpStatus.OK)
    public FacturaCompra getFacturaCompraPorTipoSerieNum(@RequestParam("tipo") char tipo,
                                                         @RequestParam("serie") long serie,
                                                         @RequestParam("numero") long num) {
        return facturaService.getFacturaCompraPorTipoSerieNum(tipo, serie, num);
    }
    
    @GetMapping("/facturas/{id}/tipo")
    @ResponseStatus(HttpStatus.OK)
    public String getTipoFactura(@PathVariable("id") long id) {
        return facturaService.getTipoFactura(facturaService.getFacturaPorId(id));
    }
    
    @GetMapping("/facturas/{id}/reporte")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getReporteFacturaVenta(@PathVariable("id") long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("factura.pdf", "factura.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        byte[] reportePDF = facturaService.getReporteFacturaVenta(facturaService.getFacturaPorId(id));
        return new ResponseEntity<>(reportePDF, headers, HttpStatus.OK);
    }
    
    @GetMapping("/facturas/pedido/{id}/renglones-pedido-a-renglon-factura")
    @ResponseStatus(HttpStatus.OK)
    public List<RenglonFactura> getRenglonesPedidoParaFacturar(@PathVariable("id") long id,
                                                               @RequestParam("tipoComprobante") String tipoComprobante) {
        return facturaService.getRenglonesPedidoParaFacturar(pedidoService.getPedidoPorId(id), tipoComprobante);
    }    
     
    @GetMapping("/facturas/validaciones-pago-multiple")
    @ResponseStatus(HttpStatus.OK)
    public boolean validarFacturasParaPagoMultiple(@RequestParam("id") long[] ids,
                                                   @RequestParam("movimiento") Movimiento movimiento) {
        List<Factura> facturas = new ArrayList<>();
        for (long id : ids) {
            facturas.add(facturaService.getFacturaPorId(id));
        }

        if (facturaService.validarFacturasParaPagoMultiple(facturas, movimiento)) {
            return true;
        } else if (!facturaService.validarClienteProveedorParaPagosMultiples(facturas, movimiento)) {              
            if (movimiento == Movimiento.COMPRA) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes").getString("mensaje_facturas_distintos_proveedores"));
            } else if (movimiento == Movimiento.VENTA) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes").getString("mensaje_facturas_distintos_clientes"));
            }

        } else if (!facturaService.validarFacturasImpagasParaPagoMultiple(facturas)) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes").getString("mensaje_facturas_seEncuentran_pagadas"));
        }
        return false;
    }
    
    @GetMapping("/facturas/empresa/{id}/validacion-cantidad-renglones/{cantidad}")
    @ResponseStatus(HttpStatus.OK)
    public boolean validarCantidadMaximaDeRenglones(@PathVariable("id") long idEmpresa,
                                                    @PathVariable("cantidad") int cantidad) {
        return facturaService.validarCantidadMaximaDeRenglones(cantidad, empresaService.getEmpresaPorId(idEmpresa));
    }
    
    @GetMapping("/facturas/producto/{id}/renglon")
    @ResponseStatus(HttpStatus.OK)
    public RenglonFactura calcularRenglon(@PathVariable("id") long id,
                                          @RequestParam("tipoDeFactura") String tipoDeFactura,
                                          @RequestParam("movimiento") Movimiento movimiento,
                                          @RequestParam("cantidad") double cantidad, 
                                          @RequestParam("descuentoPorcentaje") double descuento_porcentaje) {
        return facturaService.calcularRenglon(tipoDeFactura, movimiento, cantidad, productoService.getProductoPorId(id), descuento_porcentaje);
    }
    
    @GetMapping("/facturas/subtotal")
    @ResponseStatus(HttpStatus.OK)
    public double calcularSubTotal(@RequestParam("importe") double[] importes) {
        return facturaService.calcularSubTotal(importes);
    }
    
    @GetMapping("/facturas/descuento-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularDescuento_neto(@RequestParam("subTotal") double subTotal,
                                         @RequestParam("porcentaje") double porcentaje) {
        return facturaService.calcularDescuento_neto(subTotal, porcentaje);
    }
    
    @GetMapping("/facturas/recargo-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularRecargo_neto(@RequestParam("subTotal") double subTotal,
                                       @RequestParam("porcentaje") double porcentaje) {
        return facturaService.calcularRecargo_neto(subTotal, porcentaje);
    }
    
    @GetMapping("/facturas/subtotal-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularSubTotal_neto(@RequestParam("subTotal") double subTotal,
                                        @RequestParam("recargo") double recargo,
                                        @RequestParam("descuento") double descuento) {
        return facturaService.calcularSubTotal_neto(subTotal, recargo, descuento);
    }
    
    @GetMapping("/facturas/imp-interno-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularImpInterno_neto(@RequestParam("tipoFactura") String tipoDeFactura,
                                          @RequestParam("descuentoPorcentaje") double descuento_porcentaje,
                                          @RequestParam("recargoPorcentaje") double recargo_porcentaje,
                                          @RequestParam("importe") double[] importes,
                                          @RequestParam("impuestoPorcentaje") double[] impuesto_porcentajes) {
        return facturaService.calcularImpInterno_neto(tipoDeFactura, descuento_porcentaje, recargo_porcentaje, importes, impuesto_porcentajes);

    }
    
    @GetMapping("/facturas/total")
    @ResponseStatus(HttpStatus.OK)
    public double calcularTotal(@RequestParam("subTotal") double subTotal,
                                @RequestParam("descuentoNeto") double descuentoNeto,
                                @RequestParam("recargoNeto") double recargoNeto,
                                @RequestParam("iva105neto") double iva105Neto,
                                @RequestParam("iva21neto") double iva21Neto,
                                @RequestParam("impInternoneto") double impInternoNeto) {
        return facturaService.calcularTotal(subTotal, descuentoNeto, recargoNeto, iva105Neto, iva21Neto, impInternoNeto);
    }
    
    @GetMapping("/facturas/total-facturado-venta")
    @ResponseStatus(HttpStatus.OK)
    public double calcularTotalFacturadoVenta(@RequestParam("id") long[] ids) {
        List<FacturaVenta> facturas = new ArrayList<>();
        for(long id : ids) {
            Factura factura = facturaService.getFacturaPorId(id);
            if(factura instanceof FacturaVenta) {
                facturas.add((FacturaVenta) factura);
            }
        }
        return facturaService.calcularTotalFacturadoVenta(facturas);
    }
    
    @GetMapping("/facturas/total-facturado-compra")
    @ResponseStatus(HttpStatus.OK)
    public double calcularTotalFacturadoCompra(@RequestParam("id") long[] ids) {
        List<FacturaCompra> facturas = new ArrayList<>();
        Factura factura;
        for (long id : ids) {
            factura = facturaService.getFacturaPorId(id);
            if (factura instanceof FacturaCompra) {
                facturas.add((FacturaCompra) factura);
            }
        }
        return facturaService.calcularTotalFacturadoCompra(facturas);
    }
    
    @GetMapping("/facturas/total-iva-venta")
    @ResponseStatus(HttpStatus.OK)
    public double calcularIvaVenta(@RequestParam("id") long[] ids) {
        List<FacturaVenta> facturas = new ArrayList<>();
        for(long id : ids) {
            Factura factura = facturaService.getFacturaPorId(id);
            if(factura instanceof FacturaVenta) {
                facturas.add((FacturaVenta) factura);
            }
        }
        return facturaService.calcularIVA_Venta(facturas);
    }
    
    @GetMapping("/facturas/total-iva-compra")
    @ResponseStatus(HttpStatus.OK)
    public double calcularTotalIvaCompra(@RequestParam("id") long[] ids) {
        List<FacturaCompra> facturas = new ArrayList<>();
        Factura factura;
        for (long id : ids) {
            factura = facturaService.getFacturaPorId(id);
            if (factura instanceof FacturaCompra) {
                facturas.add((FacturaCompra) factura);
            }
        }
        return facturaService.calcularIVA_Compra(facturas);
    }
    
    @GetMapping("/facturas/ganancia-total")
    @ResponseStatus(HttpStatus.OK)
    public double calcularGananciaTotal(@RequestParam("id") long[] ids) {
        List<FacturaVenta> facturas = new ArrayList<>();
        for(long id : ids) {
            Factura factura = facturaService.getFacturaPorId(id);
            if(factura instanceof FacturaVenta) {
                facturas.add((FacturaVenta) factura);
            }
        }
        return facturaService.calcularGananciaTotal(facturas);
    }
    
    @GetMapping("/facturas/producto/{id}/iva-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularIVA_neto(@RequestParam("tipoFactura") String tipoDeFactura,
                                   @RequestParam("descuentoPorcentaje") double descuento_porcentaje,
                                   @RequestParam("recargoPorcentaje") double recargo_porcentaje,
                                   @RequestParam("ivaPorcentaje") double iva_porcentaje,
                                   @RequestBody List<RenglonFactura> renglones) {       
        return facturaService.calcularIva_neto(tipoDeFactura, descuento_porcentaje, recargo_porcentaje, renglones, iva_porcentaje);

    }
        
}
