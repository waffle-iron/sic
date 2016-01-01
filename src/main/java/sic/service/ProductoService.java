package sic.service;

import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.PreciosProducto;
import java.io.InputStream;
import java.util.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sic.repository.ProductoRepository;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Medida;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.modelo.Rubro;
import sic.util.Utilidades;
import sic.util.Validator;

public class ProductoService {

    private final ProductoRepository modeloProducto = new ProductoRepository();
    private final EmpresaService empresaService = new EmpresaService();

    private void validarOperacion(TipoDeOperacion operacion, Producto producto) {
        //Entrada de Datos
        if (producto.getCantidad() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_cantidad_negativa"));
        }
        if (producto.getCantMinima() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_cantidadMinima_negativa"));
        }
        if (producto.getPrecioCosto() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_precioCosto_negativo"));
        }
        if (producto.getPrecioVentaPublico() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_precioVentaPublico_negativo"));
        }
        if (producto.getIva_porcentaje() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_IVAPorcentaje_negativo"));
        }
        if (producto.getImpuestoInterno_porcentaje() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_ImpInternoPorcentaje_negativo"));
        }
        if (producto.getGanancia_porcentaje() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_gananciaPorcentaje_negativo"));
        }
        if (producto.getPrecioLista() < 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_precioLista_negativo"));
        }
        //Requeridos
        if (Validator.esVacio(producto.getDescripcion())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_descripcion"));
        }
        if (producto.getMedida() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_medida"));
        }
        if (producto.getRubro() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_rubro"));
        }
        if (producto.getProveedor() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_proveedor"));
        }
        if (producto.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_empresa"));
        }
        //Duplicados
        //Codigo
        if (!producto.getCodigo().equals("")) {
            Producto productoDuplicado = this.getProductoPorCodigo(producto.getCodigo(), producto.getEmpresa());

            if (operacion.equals(TipoDeOperacion.ACTUALIZACION)
                    && productoDuplicado != null
                    && productoDuplicado.getId_Producto() != producto.getId_Producto()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_producto_duplicado_codigo"));
            }
            if (operacion.equals(TipoDeOperacion.ALTA)
                    && productoDuplicado != null
                    && !producto.getCodigo().equals("")) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_producto_duplicado_codigo"));
            }
        }
        //Descripcion
        Producto productoDuplicado = this.getProductoPorDescripcion(producto.getDescripcion(), producto.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && productoDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_duplicado_descripcion"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (productoDuplicado != null && productoDuplicado.getId_Producto() != producto.getId_Producto()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_producto_duplicado_descripcion"));
            }
        }
    }

    public List<Producto> buscarProductos(BusquedaProductoCriteria criteria) {
        //Rubro
        if (criteria.isBuscarPorRubro() == true && criteria.getRubro() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_rubro"));
        }
        //Proveedor
        if (criteria.isBuscarPorProveedor() == true && criteria.getProveedor() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_proveedor"));
        }
        return modeloProducto.BuscarProductos(criteria);
    }

    public void guardar(Producto producto) {
        this.validarOperacion(TipoDeOperacion.ALTA, producto);
        modeloProducto.guardar(producto);
    }

    public void actualizar(Producto producto) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, producto);
        modeloProducto.actualizar(producto);
    }

    public void actualizarStock(Factura factura, TipoDeOperacion operacion) {
        for (RenglonFactura renglon : factura.getRenglones()) {
            Producto producto = modeloProducto.getProductoPorId(renglon.getId_ProductoItem());
            if (producto.isIlimitado() == false) {

                if (renglon.getFactura() instanceof FacturaVenta) {
                    if (operacion == TipoDeOperacion.ALTA) {
                        producto.setCantidad(producto.getCantidad() - renglon.getCantidad());
                    }

                    if (operacion == TipoDeOperacion.ELIMINACION) {
                        producto.setCantidad(producto.getCantidad() + renglon.getCantidad());
                    }
                } else if (renglon.getFactura() instanceof FacturaCompra) {
                    if (operacion == TipoDeOperacion.ALTA) {
                        producto.setCantidad(producto.getCantidad() + renglon.getCantidad());
                    }

                    if (operacion == TipoDeOperacion.ELIMINACION) {
                        double result = producto.getCantidad() - renglon.getCantidad();
                        if (result < 0) {
                            result = 0;
                        }
                        producto.setCantidad(result);

                    }
                }

                modeloProducto.actualizar(producto);
            }
        }
    }

    public void eliminarMultiplesProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            producto.setEliminado(true);
        }
        modeloProducto.actualizarMultiplesProductos(productos);
    }

    public void modificarMultiplesProductos(List<Producto> productos,
            boolean checkPrecios, PreciosProducto preciosProducto,
            boolean checkMedida, Medida medida,
            boolean checkRubro, Rubro rubro,
            boolean checkProveedor, Proveedor proveedor) {

        //Requeridos
        if (checkMedida == true && medida == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_medida"));
        }
        if (checkRubro == true && rubro == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_rubro"));
        }
        if (checkProveedor == true && proveedor == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_producto_vacio_proveedor"));
        }
        if (checkPrecios == true) {
            for (Producto producto : productos) {
                producto.setPrecioCosto(preciosProducto.getPrecioCosto());
                producto.setGanancia_porcentaje(preciosProducto.getGanancia_porcentaje());
                producto.setGanancia_neto(preciosProducto.getGanancia_neto());
                producto.setPrecioVentaPublico(preciosProducto.getPrecioVentaPublico());
                producto.setIva_porcentaje(preciosProducto.getIva_porcentaje());
                producto.setIva_neto(preciosProducto.getIva_neto());
                producto.setImpuestoInterno_porcentaje(preciosProducto.getImpuestoInterno_porcentaje());
                producto.setImpuestoInterno_neto(preciosProducto.getImpuestoInterno_neto());
                producto.setPrecioLista(preciosProducto.getPrecioLista());
            }
        }
        if (checkMedida == true) {
            for (Producto producto : productos) {
                producto.setMedida(medida);
            }
        }
        if (checkRubro == true) {
            for (Producto producto : productos) {
                producto.setRubro(rubro);
            }
        }
        if (checkProveedor == true) {
            for (Producto producto : productos) {
                producto.setProveedor(proveedor);
            }
        }
        //modifica el campo fecha ultima modificacion
        if (checkPrecios == true || checkMedida == true || checkRubro == true || checkProveedor == true) {
            Calendar fechaHora = new GregorianCalendar();
            Date fechaHoraActual = fechaHora.getTime();
            for (Producto producto : productos) {
                producto.setFechaUltimaModificacion(fechaHoraActual);
            }
        }
        modeloProducto.actualizarMultiplesProductos(productos);
    }

    public Producto getProductoPorId(long id_Producto) {
        return modeloProducto.getProductoPorId(id_Producto);
    }

    public Producto getProductoPorCodigo(String codigo, Empresa empresa) {
        if (codigo.isEmpty() == true || empresa == null) {
            return null;
        } else {
            return modeloProducto.getProductoPorCodigo(codigo, empresa);
        }
    }

    public Producto getProductoPorDescripcion(String descripcion, Empresa empresa) {
        return modeloProducto.getProductoPorDescripcion(descripcion, empresa);
    }

    public List<Producto> getProductosPorDescripcionQueContenga(String criteria, int cantRegistros, Empresa empresa) {
        return modeloProducto.getProductosQueContengaCodigoDescripcion(criteria, cantRegistros, empresa);
    }

    public boolean existeStockDisponible(long idProducto, double cantidad) {
        return (this.getProductoPorId(idProducto).getCantidad() >= cantidad) || this.getProductoPorId(idProducto).isIlimitado();
    }

    //**************************************************************************
    //Calculos

    public double calcularGanancia_Porcentaje(double precioCosto, double PVP) {
        //evita la division por cero
        if (precioCosto == 0) {
            return 0;
        }
        double resultado = ((PVP - precioCosto) / precioCosto) * 100;
        return Math.round(resultado * 100.0) / 100.0;
    }

    public double calcularGanancia_Neto(double precioCosto, double ganancia_porcentaje) {
        double resultado = (precioCosto * ganancia_porcentaje) / 100;
        return Math.round(resultado * 100.0) / 100.0;
    }

    public double calcularPVP(double precioCosto, double ganancia_porcentaje) {
        double resultado = (precioCosto * (ganancia_porcentaje / 100)) + precioCosto;
        return Math.round(resultado * 100.0) / 100.0;
    }

    public double calcularIVA_Neto(double precioCosto, double iva_porcentaje) {
        double resultado = (precioCosto * iva_porcentaje) / 100;
        return Math.round(resultado * 100.0) / 100.0;
    }

    public double calcularImpInterno_Neto(double precioCosto, double impInterno_porcentaje) {
        double resultado = (precioCosto * impInterno_porcentaje) / 100;
        return Math.round(resultado * 100.0) / 100.0;
    }

    public double calcularPrecioLista(double PVP, double iva_porcentaje, double impInterno_porcentaje) {
        double resulIVA = PVP * (iva_porcentaje / 100);
        double resultImpInterno = PVP * (impInterno_porcentaje / 100);
        double PVPConImpuestos = PVP + resulIVA + resultImpInterno;
        return Math.round(PVPConImpuestos * 100.0) / 100.0;
    }

    //**************************************************************************
    //Reportes
    public JasperPrint getReporteListaDePrecios(List<Producto> productos) throws JRException {
        ClassLoader classLoader = FacturaService.class.getClassLoader();
        InputStream isFileReport = classLoader.getResourceAsStream("sic/vista/reportes/ListaPreciosProductos.jasper");
        Map params = new HashMap();
        params.put("empresa", empresaService.getEmpresaActiva().getEmpresa());
        params.put("logo", Utilidades.convertirByteArrayIntoImage(empresaService.getEmpresaActiva().getEmpresa().getLogo()));
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(productos);
        return JasperFillManager.fillReport(isFileReport, params, ds);
    }
}
