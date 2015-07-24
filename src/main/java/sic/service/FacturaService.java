package sic.service;

import sic.modelo.BusquedaFacturaCompraCriteria;
import sic.modelo.BusquedaFacturaVentaCriteria;
import java.io.InputStream;
import java.util.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sic.repository.FacturaRepository;
import sic.modelo.Cliente;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.util.Utilidades;
import sic.util.Validator;

public class FacturaService {

    private final FacturaRepository facturaRepository = new FacturaRepository();
    private final ProductoService productoService = new ProductoService();
    private final ConfiguracionDelSistemaService configuracionDelSistemaService = new ConfiguracionDelSistemaService();
    private final EmpresaService EmpresaService = new EmpresaService();

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
        } else {
            //cuando la Empresa NO discrimina IVA
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

    public char[] getTipoFacturaVenta(Empresa empresa, Cliente cliente) {
        //cuando la Empresa discrimina IVA
        if (empresa.getCondicionIVA().isDiscriminaIVA()) {
            if (cliente.getCondicionIVA().isDiscriminaIVA()) {
                //cuando la Empresa discrimina IVA y el Cliente tambien
                char[] tiposPermitidos = new char[4];
                tiposPermitidos[0] = 'A';
                tiposPermitidos[1] = 'B';
                tiposPermitidos[2] = 'X';
                tiposPermitidos[3] = 'Y';
                return tiposPermitidos;
            } else {
                //cuando la Empresa discrminina IVA y el Cliente NO
                char[] tiposPermitidos = new char[3];
                tiposPermitidos[0] = 'B';
                tiposPermitidos[1] = 'X';
                tiposPermitidos[2] = 'Y';
                return tiposPermitidos;
            }
        } else {
            //cuando la Empresa NO discrimina IVA
            if (cliente.getCondicionIVA().isDiscriminaIVA()) {
                //cuando Empresa NO discrimina IVA y el Cliente SI
                char[] tiposPermitidos = new char[3];
                tiposPermitidos[0] = 'C';
                tiposPermitidos[1] = 'X';
                tiposPermitidos[2] = 'Y';
                return tiposPermitidos;
            } else {
                //cuando la Empresa NO discrminina IVA y el Cliente tampoco
                char[] tiposPermitidos = new char[3];
                tiposPermitidos[0] = 'C';
                tiposPermitidos[1] = 'X';
                tiposPermitidos[2] = 'Y';
                return tiposPermitidos;
            }
        }
    }

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

    public List<RenglonFactura> getRenglonesDeLaFactura(Factura factura) {
        return facturaRepository.getRenglonesDeLaFactura(factura);
    }

    public FacturaVenta getFacturaVentaPorTipoSerieNum(char tipo, long serie, long num) {
        return facturaRepository.getFacturaVentaPorTipoSerieNum(tipo, serie, num);
    }

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

    public void guardar(Factura factura) {
        this.validarFactura(factura);
        facturaRepository.guardar(factura);
        productoService.actualizarStock(factura, TipoDeOperacion.ALTA);
    }

    public void eliminar(Factura factura) {
        factura.setEliminada(true);
        facturaRepository.actualizar(factura);
        productoService.actualizarStock(factura, TipoDeOperacion.ELIMINACION);
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

    public long calcularNumeroFactura(char tipoDeFactura, long serie) {
        if (tipoDeFactura == 'Y') {
            return facturaRepository.getMayorNumFacturaSegunTipo('X', serie);
        } else {
            return facturaRepository.getMayorNumFacturaSegunTipo(tipoDeFactura, serie);
        }
    }

    public double calcularVuelto(double importeAPagar, double importeAbonado) {
        if (importeAbonado <= importeAPagar) {
            return 0;
        } else {
            return importeAbonado - importeAPagar;
        }
    }

    public boolean validarCantidadMaximaDeRenglones(int cantidad) {
        ConfiguracionDelSistema cds = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(
                EmpresaService.getEmpresaActiva().getEmpresa());
        int max = cds.getCantidadMaximaDeRenglonesEnFactura();
        return cantidad < max;
    }

    //**************************************************************************
    //Calculos
    public double calcularSubTotal(List<RenglonFactura> renglones) {
        double resultado = 0;
        for (RenglonFactura renglon : renglones) {
            resultado += renglon.getImporte();
        }
        return resultado;
    }

    public double calcularDescuento_neto(double subtotal, double descuento_porcentaje) {
        double resultado = 0;
        if (descuento_porcentaje != 0) {
            resultado = (subtotal * descuento_porcentaje) / 100;
        }
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public double calcularRecargo_neto(double subtotal, double recargo_porcentaje) {
        double resultado = 0;
        if (recargo_porcentaje != 0) {
            resultado = (subtotal * recargo_porcentaje) / 100;
        }
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public double calcularSubTotal_neto(double subtotal, double recargo_neto, double descuento_neto) {
        return subtotal + recargo_neto - descuento_neto;
    }

    public double calcularIva_neto(char tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones, double iva_porcentaje) {
        double resultado = 0;
        if (tipoDeFactura == 'A') {
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
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public double calcularImpInterno_neto(char tipoDeFactura, double descuento_porcentaje, double recargo_porcentaje, List<RenglonFactura> renglones) {
        double resultado = 0;
        if (tipoDeFactura == 'A') {
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
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public double calcularTotal(double subTotal, double descuento_neto, double recargo_neto, double iva105_neto, double iva21_neto, double impInterno_neto) {
        double resultado;
        resultado = (subTotal + recargo_neto - descuento_neto) + iva105_neto + iva21_neto + impInterno_neto;
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public double calcularTotalFacturado(List<FacturaVenta> facturas) {
        double resultado = 0;
        for (FacturaVenta facturaVenta : facturas) {
            resultado += facturaVenta.getTotal();
        }
        return resultado;
    }

    public double calcularIVA_Venta(List<FacturaVenta> facturas) {
        double resultado = 0;
        for (FacturaVenta facturaVenta : facturas) {
            resultado += (facturaVenta.getIva_105_neto() + facturaVenta.getIva_21_neto());
        }
        return resultado;
    }

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

    //**************************************************************************
    //Estadisticas
    public List<Object[]> listarProductosMasVendidosPorAnio(int anio) {
        return facturaRepository.listarProductosMasVendidosPorAnio(anio);
    }

    //**************************************************************************
    //Reportes
    public JasperPrint getReporteFacturaVenta(Factura factura) throws JRException {
        ClassLoader classLoader = FacturaService.class.getClassLoader();
        InputStream isFileReport = classLoader.getResourceAsStream("sic/vista/reportes/FacturaVenta.jasper");
        Map params = new HashMap();
        ConfiguracionDelSistema cds = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(
                EmpresaService.getEmpresaActiva().getEmpresa());
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
    public List<FacturaVenta> dividirFactura(FacturaVenta factura, int[] indices) {
        double mitad1;
        double mitad2 = 0;
        RenglonDeFacturaService renglonService = new RenglonDeFacturaService();
        List<RenglonFactura> renglones = new ArrayList<>(factura.getRenglones());
        List<RenglonFactura> renglonesConIVA = new ArrayList<>();
        List<RenglonFactura> renglonesSinIVA = new ArrayList<>();
        List<RenglonFactura> renglonesADividir = new ArrayList<>();
        List<RenglonFactura> renglonesANoDividir = new ArrayList();
        for (int i = 0; i >= renglones.size(); i++) {
            int j = 0;
            if (indices[j] == i) {
                renglonesADividir.add(renglones.get(j));
                j++;
            }
            else{
                renglonesANoDividir.add(renglones.get(i));
            }

        }

        FacturaVenta facturaSinIVA = new FacturaVenta();
        facturaSinIVA.setCliente(factura.getCliente());
        facturaSinIVA.setUsuario(factura.getUsuario());
        FacturaVenta facturaConIVA = new FacturaVenta();
        facturaConIVA.setCliente(factura.getCliente());
        facturaConIVA.setUsuario(factura.getUsuario());

        for (RenglonFactura renglon : renglonesANoDividir) {
            RenglonFactura nuevoRenglonConIVA = renglonService.calcularRenglon(factura.getTipoFactura(), Movimiento.VENTA, renglon.getCantidad(), productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
            nuevoRenglonConIVA.setFactura(facturaConIVA);
            renglonesConIVA.add(nuevoRenglonConIVA);
        }

        for (RenglonFactura renglon : renglonesADividir) {
            if (renglon.getCantidad() < 1) {
                mitad1 = renglon.getCantidad();
            } else {
                if (renglon.getCantidad() / 2 < 1) {
                    mitad1 = 1;
                    mitad2 = renglon.getCantidad() - 1;
                } else {// si es entero
                    if (renglon.getCantidad() % 1 == 0) {
                        if (renglon.getCantidad() / 2 % 1 == 0) {
                            mitad1 = renglon.getCantidad() / 2;
                            mitad2 = renglon.getCantidad() / 2;
                        } else {
                            mitad1 = Math.ceil(renglon.getCantidad() / 2);
                            mitad2 = Math.floor(renglon.getCantidad() / 2);
                        }
                    } else {
                        mitad1 = Math.ceil(renglon.getCantidad() / 2);
                        mitad2 = Math.rint(renglon.getCantidad() - mitad1);
                    }
                }
            }
            RenglonFactura nuevoRenglonConIVA = renglonService.calcularRenglon(factura.getTipoFactura(), Movimiento.VENTA, mitad1, productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
            nuevoRenglonConIVA.setFactura(facturaConIVA);
            renglonesConIVA.add(nuevoRenglonConIVA);
            RenglonFactura nuevoRenglonSinIVA = renglonService.calcularRenglon('X', Movimiento.VENTA, mitad2, productoService.getProductoPorId(renglon.getId_ProductoItem()), renglon.getDescuento_porcentaje());
            nuevoRenglonSinIVA.setFactura(facturaSinIVA);
            if (nuevoRenglonSinIVA.getCantidad() != 0) {
                renglonesSinIVA.add(nuevoRenglonSinIVA);
            }
        }
        List<RenglonFactura> listRenglonesSinIVA = new ArrayList<>(renglonesSinIVA);
        facturaSinIVA.setFecha(factura.getFecha());
        facturaSinIVA.setTipoFactura('X');
        facturaSinIVA.setNumSerie(factura.getNumSerie());
        facturaSinIVA.setNumFactura(this.calcularNumeroFactura(facturaSinIVA.getTipoFactura(), facturaSinIVA.getNumSerie()));
        facturaSinIVA.setFormaPago(factura.getFormaPago());
        facturaSinIVA.setFechaVencimiento(factura.getFechaVencimiento());
        facturaSinIVA.setTransportista(factura.getTransportista());
        facturaSinIVA.setRenglones(listRenglonesSinIVA);
        facturaSinIVA.setSubTotal(this.calcularSubTotal(renglonesSinIVA));
        facturaSinIVA.setDescuento_neto(this.calcularDescuento_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getDescuento_porcentaje()));
        facturaSinIVA.setRecargo_neto(this.calcularRecargo_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getRecargo_porcentaje()));
        facturaSinIVA.setSubTotal_neto(this.calcularSubTotal_neto(facturaSinIVA.getSubTotal(), facturaSinIVA.getRecargo_neto(), facturaSinIVA.getDescuento_neto()));
        facturaSinIVA.setIva_105_neto(this.calcularIva_neto(facturaSinIVA.getTipoFactura(), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesConIVA, 10.5));
        facturaSinIVA.setIva_21_neto(this.calcularIva_neto(facturaSinIVA.getTipoFactura(), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesConIVA, 21));
        facturaSinIVA.setImpuestoInterno_neto(this.calcularImpInterno_neto(facturaSinIVA.getTipoFactura(), facturaSinIVA.getDescuento_porcentaje(), facturaSinIVA.getRecargo_porcentaje(), renglonesSinIVA));
        facturaSinIVA.setTotal(this.calcularTotal(facturaSinIVA.getSubTotal(), facturaSinIVA.getDescuento_neto(), facturaSinIVA.getRecargo_neto(), facturaSinIVA.getIva_105_neto(), facturaSinIVA.getIva_21_neto(), facturaSinIVA.getImpuestoInterno_neto()));
        facturaSinIVA.setObservaciones(factura.getObservaciones());
        facturaSinIVA.setPagada(factura.isPagada());
        facturaSinIVA.setEmpresa(factura.getEmpresa());
        facturaSinIVA.setEliminada(factura.isEliminada());

        List<RenglonFactura> listRenglonesConIVA = new ArrayList<>(renglonesConIVA);
        facturaConIVA.setFecha(factura.getFecha());
        facturaConIVA.setTipoFactura(factura.getTipoFactura());
        facturaConIVA.setNumSerie(factura.getNumSerie());
        facturaConIVA.setNumFactura(this.calcularNumeroFactura(factura.getTipoFactura(), facturaConIVA.getNumSerie()));
        facturaConIVA.setFormaPago(factura.getFormaPago());
        facturaConIVA.setFechaVencimiento(factura.getFechaVencimiento());
        facturaConIVA.setTransportista(factura.getTransportista());
        facturaConIVA.setRenglones(listRenglonesConIVA);
        facturaConIVA.setSubTotal(this.calcularSubTotal(renglonesConIVA));
        facturaConIVA.setDescuento_neto(this.calcularDescuento_neto(facturaConIVA.getSubTotal(), facturaConIVA.getDescuento_porcentaje()));
        facturaConIVA.setRecargo_neto(this.calcularRecargo_neto(facturaConIVA.getSubTotal(), facturaConIVA.getRecargo_porcentaje()));
        facturaConIVA.setSubTotal_neto(this.calcularSubTotal_neto(facturaConIVA.getSubTotal(), facturaConIVA.getRecargo_neto(), facturaConIVA.getDescuento_neto()));
        facturaConIVA.setIva_105_neto(this.calcularIva_neto(facturaConIVA.getTipoFactura(), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA, 10.5));
        facturaConIVA.setIva_21_neto(this.calcularIva_neto(facturaConIVA.getTipoFactura(), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA, 21));
        facturaConIVA.setImpuestoInterno_neto(this.calcularImpInterno_neto(facturaConIVA.getTipoFactura(), facturaConIVA.getDescuento_porcentaje(), facturaConIVA.getRecargo_porcentaje(), renglonesConIVA));
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

    public double calcularTotalFacturas(List<FacturaVenta> facturas) {
        double total = 0;
        for (Factura factura : facturas) {
            total += factura.getTotal();
        }
        return total;
    }
}
