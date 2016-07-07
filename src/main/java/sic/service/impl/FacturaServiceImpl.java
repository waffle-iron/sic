package sic.service.impl;

import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Cliente;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.repository.IFacturaRepository;
import sic.service.IConfiguracionDelSistemaService;
import sic.service.IEmpresaService;
import sic.service.IFacturaService;
import sic.service.IPedidoService;
import sic.service.IProductoService;
import sic.service.Movimiento;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class FacturaServiceImpl implements IFacturaService {

    private final IFacturaRepository facturaRepository;
    private final IProductoService productoService;
    private final IConfiguracionDelSistemaService configuracionDelSistemaService;
    private final IEmpresaService empresaService;
    private final IPedidoService pedidoService;

    @Autowired
    @Lazy
    public FacturaServiceImpl(IFacturaRepository facturaRepository,
            IProductoService productoService,
            IConfiguracionDelSistemaService configuracionDelSistemaService,
            IEmpresaService empresaService, IPedidoService pedidoService) {

        this.facturaRepository = facturaRepository;
        this.productoService = productoService;
        this.configuracionDelSistemaService = configuracionDelSistemaService;
        this.empresaService = empresaService;
        this.pedidoService = pedidoService;
    }

    @Override
    public char[] getTipoFacturaCompra(Empresa empresa, Proveedor proveedor) {
        //cuando la Empresa discrimina IVA
        if (empresa.getCondicionIVA().isDiscriminaIVA()) {
            if (proveedor.getCondicionIVA().isDiscriminaIVA()) {
                //cuando la Empresa discrimina IVA y el Proveedor tambien
                char[] tiposPermitidos = new char[3];
                tiposPermitidos[0] = 'A';
                tiposPermitidos[1] = 'B';
                tiposPermitidos[2] = 'X';
                return tiposPermitidos;
            } else {
                //cuando la Empresa discrminina IVA y el Proveedor NO
                char[] tiposPermitidos = new char[2];
                tiposPermitidos[0] = 'C';
                tiposPermitidos[1] = 'X';
                return tiposPermitidos;
            }
        } else //cuando la Empresa NO discrimina IVA
        {
            if (proveedor.getCondicionIVA().isDiscriminaIVA()) {
                //cuando Empresa NO discrimina IVA y el Proveedor SI
                char[] tiposPermitidos = new char[2];
                tiposPermitidos[0] = 'B';
                tiposPermitidos[1] = 'X';
                return tiposPermitidos;
            } else {
                //cuando la Empresa NO discrminina IVA y el Proveedor tampoco
                char[] tiposPermitidos = new char[2];
                tiposPermitidos[0] = 'C';
                tiposPermitidos[1] = 'X';
                return tiposPermitidos;
            }
        }
    }

    @Override
    public String[] getTipoFacturaVenta(Empresa empresa, Cliente cliente) {
        //cuando la Empresa discrimina IVA
        if (empresa.getCondicionIVA().isDiscriminaIVA()) {
            if (cliente.getCondicionIVA().isDiscriminaIVA()) {
                //cuando la Empresa discrimina IVA y el Cliente tambien
                String[] tiposPermitidos = new String[5];
                tiposPermitidos[0] = "Factura A";
                tiposPermitidos[1] = "Factura B";
                tiposPermitidos[2] = "Factura X";
                tiposPermitidos[3] = "Factura Y";
                tiposPermitidos[4] = "Pedido";
                return tiposPermitidos;
            } else {
                //cuando la Empresa discrminina IVA y el Cliente NO
                String[] tiposPermitidos = new String[4];
                tiposPermitidos[0] = "Factura B";
                tiposPermitidos[1] = "Factura X";
                tiposPermitidos[2] = "Factura Y";
                tiposPermitidos[3] = "Pedido";
                return tiposPermitidos;
            }
        } else //cuando la Empresa NO discrimina IVA
        {
            if (cliente.getCondicionIVA().isDiscriminaIVA()) {
                //cuando Empresa NO discrimina IVA y el Cliente SI
                String[] tiposPermitidos = new String[4];
                tiposPermitidos[0] = "Factura C";
                tiposPermitidos[1] = "Factura X";
                tiposPermitidos[2] = "Factura Y";
                tiposPermitidos[3] = "Pedido";
                return tiposPermitidos;
            } else {
                //cuando la Empresa NO discrminina IVA y el Cliente tampoco
                String[] tiposPermitidos = new String[4];
                tiposPermitidos[0] = "Factura C";
                tiposPermitidos[1] = "Factura X";
                tiposPermitidos[2] = "Factura Y";
                tiposPermitidos[3] = "Pedido";
                return tiposPermitidos;
            }
        }
    }

    @Override
    public char[] getTiposFacturaSegunEmpresa(Empresa empresa) {
        if (empresa.getCondicionIVA().isDiscriminaIVA()) {
            char[] tiposPermitidos = new char[4];
            tiposPermitidos[0] = 'A';
            tiposPermitidos[1] = 'B';
            tiposPermitidos[2] = 'X';
            tiposPermitidos[3] = 'Y';
            return tiposPermitidos;
        } else {
            char[] tiposPermitidos = new char[3];
            tiposPermitidos[0] = 'C';
            tiposPermitidos[1] = 'X';
            tiposPermitidos[2] = 'Y';
            return tiposPermitidos;
        }
    }

    @Override
    public List<RenglonFactura> getRenglonesDeLaFactura(Factura factura) {
        return facturaRepository.getRenglonesDeLaFactura(factura);
    }

    @Override
    public FacturaVenta getFacturaVentaPorTipoSerieNum(String tipo, long serie, long num) {
        return facturaRepository.getFacturaVentaPorTipoSerieNum(tipo, serie, num);
    }

    @Override
    public String getTipoFactura(Factura factura) {
        String tipoComprobante = new String();
        switch (factura.getTipoFactura()) {
            case 'A':
                tipoComprobante = "Factura A";
                break;
            case 'B':
                tipoComprobante = "Factura B";
                break;
            case 'C':
                tipoComprobante = "Factura C";
                break;
            case 'Y':
                tipoComprobante = "Factura Y";
                break;
            case 'X':
                tipoComprobante = "Factura X";
                break;
            case 'P':
                tipoComprobante = "Pedido";
                break;
        }
        return tipoComprobante;
    }

    @Override
    public Movimiento getTipoMovimiento(Factura factura) {
        if (factura instanceof FacturaVenta) {
            return Movimiento.VENTA;
        } else {
            return Movimiento.COMPRA;
        }
    }

    @Override
    public List<FacturaCompra> buscarFacturaCompra(BusquedaFacturaCompraCriteria criteria) {
        //Fecha de Factura        
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_fechas_busqueda_invalidas"));
        }
        if (criteria.isBuscaPorFecha() == true) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(criteria.getFechaDesde());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            criteria.setFechaDesde(cal.getTime());
            cal.setTime(criteria.getFechaHasta());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            criteria.setFechaHasta(cal.getTime());
        }
        //Proveedor
        if (criteria.isBuscaPorProveedor() == true && criteria.getProveedor() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_proveedor_vacio"));
        }
        return facturaRepository.buscarFacturasCompra(criteria);
    }

    @Override
    public List<FacturaVenta> buscarFacturaVenta(BusquedaFacturaVentaCriteria criteria) {
        //Fecha de Factura        
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_fechas_busqueda_invalidas"));
        }
        if (criteria.isBuscaPorFecha() == true) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(criteria.getFechaDesde());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            criteria.setFechaDesde(cal.getTime());
            cal.setTime(criteria.getFechaHasta());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            criteria.setFechaHasta(cal.getTime());
        }
        //Cliente
        if (criteria.isBuscaCliente() == true && criteria.getCliente() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_cliente_vacio"));
        }
        //Usuario
        if (criteria.isBuscaUsuario() == true && criteria.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_usuario_vacio"));
        }
        return facturaRepository.buscarFacturasVenta(criteria);
    }

    @Override
    public List<Factura> getFacturasPorFechasYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta) {
        return facturaRepository.getFacturasPorFechasYFormaDePago(id_Empresa, id_FormaDePago, desde, hasta);
    }

    @Override
    @Transactional
    public void guardar(Factura factura) {
        this.validarFactura(factura);
        facturaRepository.guardar(factura);
        productoService.actualizarStock(factura, TipoDeOperacion.ALTA);
    }

    @Override
    @Transactional
    public void eliminar(Factura factura) {
        factura.setEliminada(true);
        facturaRepository.actualizar(factura);
        Factura facturaConRenglones = this.getFacturaVentaPorTipoSerieNum(this.getTipoFactura(factura), factura.getNumSerie(), factura.getNumFactura());
        productoService.actualizarStock(facturaConRenglones, TipoDeOperacion.ELIMINACION);
    }

    private void validarFactura(Factura factura) {
        //Entrada de Datos
        if (factura.getFechaVencimiento() != null) {
            //quitamos la parte de hora de la Fecha de Vencimiento
            Calendar calFechaVencimiento = new GregorianCalendar();
            calFechaVencimiento.setTime(factura.getFechaVencimiento());
            calFechaVencimiento.set(Calendar.HOUR, 0);
            calFechaVencimiento.set(Calendar.MINUTE, 0);
            calFechaVencimiento.set(Calendar.SECOND, 0);
            calFechaVencimiento.set(Calendar.MILLISECOND, 0);
            factura.setFechaVencimiento(calFechaVencimiento.getTime());
            //quitamos la parte de hora de la Fecha Actual para poder comparar correctamente
            Calendar calFechaDeFactura = new GregorianCalendar();
            calFechaDeFactura.setTime(factura.getFecha());
            calFechaDeFactura.set(Calendar.HOUR, 0);
            calFechaDeFactura.set(Calendar.MINUTE, 0);
            calFechaDeFactura.set(Calendar.SECOND, 0);
            calFechaDeFactura.set(Calendar.MILLISECOND, 0);
            if (Validator.compararFechas(factura.getFechaVencimiento(), calFechaDeFactura.getTime()) > 0) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_factura_fecha_invalida"));
            }
        }
        //Requeridos
        if (factura.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_fecha_vacia"));
        }
        if (factura.getTipoFactura() == ' ') {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_tipo_factura_vacia"));
        }
        if (factura.getTransportista() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_transportista_vacio"));
        }
        if (factura.getFormaPago() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_FormaDePago_vacia"));
        }
        if (factura.getRenglones().isEmpty() | factura.getRenglones() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_renglones_vacio"));
        }
        if (factura.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_factura_empresa_vacia"));
        }
        if (factura instanceof FacturaCompra) {
            FacturaCompra facturaCompra = (FacturaCompra) factura;
            if (facturaCompra.getProveedor() == null) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_factura_proveedor_vacio"));
            }
        }
        if (factura instanceof FacturaVenta) {
            FacturaVenta facturaVenta = (FacturaVenta) factura;
            if (facturaVenta.getCliente() == null) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_factura_cliente_vacio"));
            }
            if (facturaVenta.getUsuario() == null) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_factura_usuario_vacio"));
            }
        }
    }

    @Override
    public boolean validarCantidadMaximaDeRenglones(int cantidad) {
        ConfiguracionDelSistema cds = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(
                empresaService.getEmpresaActiva().getEmpresa());
        int max = cds.getCantidadMaximaDeRenglonesEnFactura();
        return cantidad < max;
    }

    //**************************************************************************
    //Calculos
    @Override
    public double calcularSubTotal(List<RenglonFactura> renglones) {
        double resultado = 0;
        for (RenglonFactura renglon : renglones) {
            resultado += renglon.getImporte();
        }
        return resultado;
    }

    @Override
    public double calcularDescuento_neto(double subtotal, double descuento_porcentaje) {
        double resultado = 0;
        if (descuento_porcentaje != 0) {
            resultado = (subtotal * descuento_porcentaje) / 100;
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularRecargo_neto(double subtotal, double recargo_porcentaje) {
        double resultado = 0;
        if (recargo_porcentaje != 0) {
            resultado = (subtotal * recargo_porcentaje) / 100;
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularSubTotal_neto(double subtotal, double recargo_neto, double descuento_neto) {
        return subtotal + recargo_neto - descuento_neto;
    }

    @Override
    public double calcularIva_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones, double iva_porcentaje) {
        double resultado = 0;
        if (tipoDeFactura.charAt(tipoDeFactura.length() - 1) == 'A') {
            for (RenglonFactura renglon : renglones) {
                //descuento
                double descuento = 0;
                if (descuento_porcentaje != 0) {
                    descuento = (renglon.getImporte() * descuento_porcentaje) / 100;
                }
                //recargo
                double recargo = 0;
                if (recargo_porcentaje != 0) {
                    recargo = (renglon.getImporte() * recargo_porcentaje) / 100;
                }
                double iva_neto = 0;
                if (renglon.getIva_porcentaje() == iva_porcentaje) {
                    iva_neto = ((renglon.getImporte() + recargo - descuento) * renglon.getIva_porcentaje()) / 100;
                }
                resultado += iva_neto;
            }
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularImpInterno_neto(String tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones) {
        double resultado = 0;
        if (tipoDeFactura.charAt(tipoDeFactura.length() - 1) == 'A') {
            for (RenglonFactura renglon : renglones) {
                //descuento
                double descuento = 0;
                if (descuento_porcentaje != 0) {
                    descuento = (renglon.getImporte() * descuento_porcentaje) / 100;
                }
                //recargo
                double recargo = 0;
                if (recargo_porcentaje != 0) {
                    recargo = (renglon.getImporte() * recargo_porcentaje) / 100;
                }
                double impInterno_neto = ((renglon.getImporte() + recargo - descuento) * renglon.getImpuesto_porcentaje()) / 100;
                resultado += impInterno_neto;
            }
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularTotal(double subTotal, double descuento_neto, double recargo_neto, double iva105_neto, double iva21_neto, double impInterno_neto) {
        double resultado;
        resultado = (subTotal + recargo_neto - descuento_neto) + iva105_neto + iva21_neto + impInterno_neto;
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularTotalFacturado(List<FacturaVenta> facturas) {
        double resultado = 0;
        for (FacturaVenta facturaVenta : facturas) {
            resultado += facturaVenta.getTotal();
        }
        return resultado;
    }

    @Override
    public double calcularIVA_Venta(List<FacturaVenta> facturas) {
        double resultado = 0;
        for (FacturaVenta facturaVenta : facturas) {
            resultado += (facturaVenta.getIva_105_neto() + facturaVenta.getIva_21_neto());
        }
        return resultado;
    }

    @Override
    public double calcularGananciaTotal(List<FacturaVenta> facturas) {
        double resultado = 0;
        for (FacturaVenta facturaVenta : facturas) {
            List<RenglonFactura> renglones = this.getRenglonesDeLaFactura(facturaVenta);
            for (RenglonFactura renglon : renglones) {
                resultado += renglon.getGanancia_neto() * renglon.getCantidad();
            }
        }
        return resultado;
    }

    @Override
    public double calcularIVA_neto(Movimiento movimiento, Producto producto, double descuento_neto) {
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            resultado = ((producto.getPrecioCosto() - descuento_neto) * producto.getIva_porcentaje()) / 100;
        }

        if (movimiento == Movimiento.VENTA) {
            resultado = ((producto.getPrecioVentaPublico() - descuento_neto) * producto.getIva_porcentaje()) / 100;
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularImpInterno_neto(Movimiento movimiento, Producto producto, double descuento_neto) {
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            resultado = ((producto.getPrecioCosto() - descuento_neto) * producto.getImpuestoInterno_porcentaje()) / 100;
        }

        if (movimiento == Movimiento.VENTA) {
            resultado = ((producto.getPrecioVentaPublico() - descuento_neto) * producto.getImpuestoInterno_porcentaje()) / 100;
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public double calcularPrecioUnitario(Movimiento movimiento, String tipoDeFactura, Producto producto) {
        double iva_resultado;
        double impInterno_resultado;
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            if (tipoDeFactura.equals("Factura A") || tipoDeFactura.equals("Factura X")) {
                resultado = producto.getPrecioCosto();
            } else {
                iva_resultado = (producto.getPrecioCosto() * producto.getIva_porcentaje()) / 100;
                impInterno_resultado = (producto.getPrecioCosto() * producto.getImpuestoInterno_porcentaje()) / 100;
                resultado = producto.getPrecioCosto() + iva_resultado + impInterno_resultado;
            }
        }

        if (movimiento == Movimiento.VENTA) {
            if (tipoDeFactura.equals("Factura A") || tipoDeFactura.equals("Factura X")) {
                resultado = producto.getPrecioVentaPublico();
            } else if (tipoDeFactura.equals("Factura Y")) {
                iva_resultado = (producto.getPrecioVentaPublico() * producto.getIva_porcentaje() / 2) / 100;
                impInterno_resultado = (producto.getPrecioVentaPublico() * producto.getImpuestoInterno_porcentaje()) / 100;
                resultado = producto.getPrecioVentaPublico() + iva_resultado + impInterno_resultado;
            } else {
                resultado = producto.getPrecioLista();
            }
        }
        return Utilidades.truncarDecimal(resultado, 2);
    }

    @Override
    public long calcularNumeroFactura(String tipoDeFactura, long serie) {
        return 1 + facturaRepository.getMayorNumFacturaSegunTipo(tipoDeFactura, serie);
    }

    @Override
    public double calcularVuelto(double importeAPagar, double importeAbonado) {
        if (importeAbonado <= importeAPagar) {
            return 0;
        } else {
            return importeAbonado - importeAPagar;
        }
    }

    @Override
    public double calcularImporte(double cantidad, double precioUnitario, double descuento_neto) {
        double resultado = (precioUnitario - descuento_neto) * cantidad;
        return Utilidades.truncarDecimal(resultado, 2);
    }

    //**************************************************************************
    //Estadisticas
    @Override
    public List<Object[]> listarProductosMasVendidosPorAnio(int anio) {
        return facturaRepository.listarProductosMasVendidosPorAnio(anio);
    }

    //**************************************************************************
    //Reportes
    @Override
    public JasperPrint getReporteFacturaVenta(Factura factura) throws JRException {
        ClassLoader classLoader = FacturaServiceImpl.class.getClassLoader();
        InputStream isFileReport = classLoader.getResourceAsStream("sic/vista/reportes/FacturaVenta.jasper");
        Map params = new HashMap();
        ConfiguracionDelSistema cds = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(
                empresaService.getEmpresaActiva().getEmpresa());
        params.put("preImpresa", cds.usarFacturaVentaPreImpresa());
        params.put("facturaVenta", factura);
        params.put("nroComprobante", factura.getNumSerie() + "-" + factura.getNumFactura());
        params.put("logo", Utilidades.convertirByteArrayIntoImage(factura.getEmpresa().getLogo()));
        List<RenglonFactura> renglones = this.getRenglonesDeLaFactura(factura);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(renglones);
        return JasperFillManager.fillReport(isFileReport, params, ds);
    }

    //**************************************************************************    
    //Division de Factura
    @Override
    public List<FacturaVenta> dividirFactura(FacturaVenta factura, int[] indices) {
        double FacturaABC = 0;
        double FacturaX = 0;
        List<RenglonFactura> renglonesConIVA = new ArrayList<>();
        List<RenglonFactura> renglonesSinIVA = new ArrayList<>();
        FacturaVenta facturaSinIVA = new FacturaVenta();
        facturaSinIVA.setCliente(factura.getCliente());
        facturaSinIVA.setUsuario(factura.getUsuario());
        FacturaVenta facturaConIVA = new FacturaVenta();
        facturaConIVA.setCliente(factura.getCliente());
        facturaConIVA.setUsuario(factura.getUsuario());

        int renglonMarcado = 0;
        int numeroDeRenglon = 0;
        for (RenglonFactura renglon : factura.getRenglones()) {
            if (numeroDeRenglon == indices[renglonMarcado]) {
                if (renglon.getCantidad() < 1) {
                    FacturaABC = renglon.getCantidad();
                } else if (renglon.getCantidad() - Math.ceil(renglon.getCantidad()) == 0) {
                    FacturaABC = Math.ceil(renglon.getCantidad() / 2);
                    FacturaX = Math.floor(renglon.getCantidad() / 2);
                } else if ((renglon.getCantidad() % 2) == 0) {
                    FacturaABC = renglon.getCantidad() / 2;
                    FacturaX = renglon.getCantidad() - FacturaABC;
                    FacturaX = (double) Math.round(FacturaX * 100) / 100;
                } else {
                    FacturaABC = Math.ceil(renglon.getCantidad() / 2);
                    FacturaX = renglon.getCantidad() - FacturaABC;
                    FacturaX = (double) Math.round(FacturaX * 100) / 100;
                }
                RenglonFactura nuevoRenglonConIVA = this.calcularRenglon(this.getTipoFactura(factura), Movimiento.VENTA, FacturaABC, productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
                nuevoRenglonConIVA.setFactura(facturaConIVA);
                renglonesConIVA.add(nuevoRenglonConIVA);
                RenglonFactura nuevoRenglonSinIVA = this.calcularRenglon("Factura X", Movimiento.VENTA, FacturaX, productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
                nuevoRenglonSinIVA.setFactura(facturaSinIVA);
                if (nuevoRenglonSinIVA.getCantidad() != 0) {
                    renglonesSinIVA.add(nuevoRenglonSinIVA);
                }
                numeroDeRenglon++;
                renglonMarcado++;
            } else {
                numeroDeRenglon++;
                RenglonFactura nuevoRenglonConIVA = this.calcularRenglon(this.getTipoFactura(factura), Movimiento.VENTA, renglon.getCantidad(), productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
                nuevoRenglonConIVA.setFactura(facturaConIVA);
                renglonesConIVA.add(nuevoRenglonConIVA);
            }
        }
        List<RenglonFactura> listRenglonesSinIVA = new ArrayList<>(renglonesSinIVA);
        facturaSinIVA.setFecha(factura.getFecha());
        facturaSinIVA.setTipoFactura('X');
        facturaSinIVA.setNumSerie(factura.getNumSerie());
        facturaSinIVA.setNumFactura(this.calcularNumeroFactura(this.getTipoFactura(facturaSinIVA), facturaSinIVA.getNumSerie()));
        facturaSinIVA.setFormaPago(factura.getFormaPago());
        facturaSinIVA.setFechaVencimiento(factura.getFechaVencimiento());
        facturaSinIVA.setTransportista(factura.getTransportista());
        facturaSinIVA.setRenglones(listRenglonesSinIVA);
        facturaSinIVA.setSubTotal(this.calcularSubTotal(renglonesSinIVA));
        facturaSinIVA.setDescuento_neto(this.calcularDescuento_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getDescuento_porcentaje()));
        facturaSinIVA.setRecargo_neto(this.calcularRecargo_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getRecargo_porcentaje()));
        facturaSinIVA.setSubTotal_neto(this.calcularSubTotal_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getRecargo_neto(), facturaSinIVA.getDescuento_neto()));
        facturaSinIVA.setIva_105_neto(this.calcularIva_neto(this.getTipoFactura(facturaSinIVA), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesConIVA, 10.5));
        facturaSinIVA.setIva_21_neto(this.calcularIva_neto(this.getTipoFactura(facturaSinIVA), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesConIVA, 21));
        facturaSinIVA.setImpuestoInterno_neto(this.calcularImpInterno_neto(this.getTipoFactura(facturaSinIVA), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesSinIVA));
        facturaSinIVA.setTotal(this.calcularTotal(facturaSinIVA.getSubTotal(), facturaSinIVA.getDescuento_neto(), facturaSinIVA.getRecargo_neto(), facturaSinIVA.getIva_105_neto(), facturaSinIVA.getIva_21_neto(), facturaSinIVA.getImpuestoInterno_neto()));
        facturaSinIVA.setObservaciones(factura.getObservaciones());
        facturaSinIVA.setPagada(factura.isPagada());
        facturaSinIVA.setEmpresa(factura.getEmpresa());
        facturaSinIVA.setEliminada(factura.isEliminada());

        List<RenglonFactura> listRenglonesConIVA = new ArrayList<>(renglonesConIVA);
        facturaConIVA.setFecha(factura.getFecha());
        facturaConIVA.setTipoFactura(factura.getTipoFactura());
        facturaConIVA.setNumSerie(factura.getNumSerie());
        facturaConIVA.setNumFactura(this.calcularNumeroFactura(this.getTipoFactura(facturaConIVA), facturaConIVA.getNumSerie()));
        facturaConIVA.setFormaPago(factura.getFormaPago());
        facturaConIVA.setFechaVencimiento(factura.getFechaVencimiento());
        facturaConIVA.setTransportista(factura.getTransportista());
        facturaConIVA.setRenglones(listRenglonesConIVA);
        facturaConIVA.setSubTotal(this.calcularSubTotal(renglonesConIVA));
        facturaConIVA.setDescuento_neto(this.calcularDescuento_neto(facturaConIVA.getSubTotal(), facturaConIVA.getDescuento_porcentaje()));
        facturaConIVA.setRecargo_neto(this.calcularRecargo_neto(facturaConIVA.getSubTotal(), facturaConIVA.getRecargo_porcentaje()));
        facturaConIVA.setSubTotal_neto(this.calcularSubTotal_neto(facturaConIVA.getSubTotal(), facturaConIVA.getRecargo_neto(), facturaConIVA.getDescuento_neto()));
        facturaConIVA.setIva_105_neto(this.calcularIva_neto(this.getTipoFactura(facturaConIVA), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA, 10.5));
        facturaConIVA.setIva_21_neto(this.calcularIva_neto(this.getTipoFactura(facturaConIVA), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA, 21));
        facturaConIVA.setImpuestoInterno_neto(this.calcularImpInterno_neto(this.getTipoFactura(facturaConIVA), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA));
        facturaConIVA.setTotal(this.calcularTotal(facturaConIVA.getSubTotal(), facturaConIVA.getDescuento_neto(), facturaConIVA.getRecargo_neto(), facturaConIVA.getIva_105_neto(), facturaConIVA.getIva_21_neto(), facturaConIVA.getImpuestoInterno_neto()));
        facturaConIVA.setObservaciones(factura.getObservaciones());
        facturaConIVA.setPagada(factura.isPagada());
        facturaConIVA.setEmpresa(factura.getEmpresa());
        facturaConIVA.setEliminada(factura.isEliminada());

        List<FacturaVenta> facturas = new ArrayList<>();
        facturas.add(facturaConIVA);
        facturas.add(facturaSinIVA);
        return facturas;
    }

    @Override
    public RenglonFactura getRenglonFacturaPorRenglonPedido(RenglonPedido renglon, String tipoComprobante) {
        return this.calcularRenglon(tipoComprobante, Movimiento.VENTA, renglon.getCantidad(), renglon.getProducto(), renglon.getDescuento_porcentaje());
    }

    @Override
    public List<RenglonFactura> convertirRenglonesPedidoARenglonesFactura(Pedido pedido, String tipoComprobante) {
        List<RenglonFactura> renglonesRestantes = new ArrayList<>();
        HashMap<Long, RenglonFactura> renglonesDeFacturas = pedidoService.getRenglonesDeFacturasUnificadosPorNroPedido(pedido.getNroPedido());
        List<RenglonPedido> renglonesDelPedido = pedidoService.getRenglonesDelPedido(pedido.getNroPedido());
        for (RenglonPedido renglon : renglonesDelPedido) {
            if (renglonesDeFacturas.containsKey(renglon.getProducto().getId_Producto())) {
                if (renglon.getCantidad() > renglonesDeFacturas.get(renglon.getProducto().getId_Producto()).getCantidad()) {
                    renglon.setCantidad(renglon.getCantidad() - renglonesDeFacturas.get(renglon.getProducto().getId_Producto()).getCantidad());
                    renglonesRestantes.add(this.getRenglonFacturaPorRenglonPedido(renglon, tipoComprobante));
                }
            } else {
                renglonesRestantes.add(this.getRenglonFacturaPorRenglonPedido(renglon, tipoComprobante));
            }
        }
        return renglonesRestantes;
    }

    //**************************************************************************
    @Override
    public RenglonFactura calcularRenglon(String tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje) {
        RenglonFactura nuevoRenglon = new RenglonFactura();
        nuevoRenglon.setId_ProductoItem(producto.getId_Producto());
        nuevoRenglon.setCodigoItem(producto.getCodigo());
        nuevoRenglon.setDescripcionItem(producto.getDescripcion());
        nuevoRenglon.setMedidaItem(producto.getMedida().getNombre());
        nuevoRenglon.setCantidad(cantidad);
        nuevoRenglon.setPrecioUnitario(this.calcularPrecioUnitario(movimiento, tipoDeFactura, producto));
        nuevoRenglon.setDescuento_porcentaje(descuento_porcentaje);
        nuevoRenglon.setDescuento_neto(this.calcularDescuento_neto(nuevoRenglon.getPrecioUnitario(), descuento_porcentaje));
        nuevoRenglon.setIva_porcentaje(producto.getIva_porcentaje());
        if (tipoDeFactura.equals("Factura Y")) {
            nuevoRenglon.setIva_porcentaje(producto.getIva_porcentaje() / 2);
        }
        nuevoRenglon.setIva_neto(this.calcularIVA_neto(movimiento, producto, nuevoRenglon.getDescuento_neto()));
        nuevoRenglon.setImpuesto_porcentaje(producto.getImpuestoInterno_porcentaje());
        nuevoRenglon.setImpuesto_neto(this.calcularImpInterno_neto(movimiento, producto, nuevoRenglon.getDescuento_neto()));
        nuevoRenglon.setGanancia_porcentaje(producto.getGanancia_porcentaje());
        nuevoRenglon.setGanancia_neto(producto.getGanancia_neto());
        nuevoRenglon.setImporte(this.calcularImporte(cantidad, nuevoRenglon.getPrecioUnitario(), nuevoRenglon.getDescuento_neto()));
        return nuevoRenglon;
    }

}
