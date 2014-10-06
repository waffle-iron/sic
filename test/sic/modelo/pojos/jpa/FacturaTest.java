package sic.modelo.pojos.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Localidad;
import sic.modelo.Medida;
import sic.modelo.PagoFacturaCompra;
import sic.modelo.Pais;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Provincia;
import sic.modelo.RenglonFactura;
import sic.modelo.Rubro;
import sic.modelo.Transportista;
import sic.modelo.Usuario;
import sic.modelo.XMLFileConfig;
import sic.service.EmpresaService;
import sic.service.FacturaService;
import sic.service.Movimiento;
import sic.service.ProductoService;
import sic.service.RenglonDeFacturaService;

public class FacturaTest {

    private final FacturaService facturaService = new FacturaService();
    private final ProductoService productoService = new ProductoService();
    private final EmpresaService empresaService = new EmpresaService();
    private final RenglonDeFacturaService renglonFacturaService = new RenglonDeFacturaService();

    @Before
    public void setDatosConexion() {
        XMLFileConfig.setBdConexion("sic-test");
        XMLFileConfig.setHostConexion("localhost");
        XMLFileConfig.setPuertoConexion(3306);
    }

    @Test
    public void guardarUnaFacturaCompra() {
        FacturaCompra factura = new FacturaCompra();
        factura.setFecha(new Date());
        factura.setTipoFactura('A');

        //Forma de Pago
        FormaDePago formaDePago = new FormaDePago();
        formaDePago.setNombre("Contado");

        factura.setFormaPago(formaDePago);

        //Transportista
        Transportista transportista = new Transportista();
        transportista.setNombre("Demonte");
        transportista.setDireccion("Calle 123");
        transportista.setTelefono("");
        transportista.setWeb("");

        factura.setTransportista(transportista);

        //Renglones
        RenglonFactura renglon1 = new RenglonFactura();
        renglon1.setCodigoItem("123");
        renglon1.setDescripcionItem("Producto de pruebas");
        renglon1.setMedidaItem("Unidad");
        //factura.addRenglonFactura(renglon1);

        RenglonFactura renglon2 = new RenglonFactura();
        renglon2.setCodigoItem("321");
        renglon2.setDescripcionItem("Descripcion de prueba");
        renglon2.setMedidaItem("Docena");
        factura.setObservaciones("");
        //factura.addRenglonFactura(renglon2);

        PagoFacturaCompra pago1 = new PagoFacturaCompra();
        pago1.setFecha(new Date());
        pago1.setNota("");
        pago1.setMonto(122.15);
        //factura.addPagoFacturaCompra(pago1);

        PagoFacturaCompra pago2 = new PagoFacturaCompra();
        pago2.setFecha(new Date());
        pago2.setNota("");
        pago2.setMonto(122.15);
        //factura.addPagoFacturaCompra(pago2);

    }

    @Test
    public void guardarUnaFacturaVentaTipoAconDivision() {
        FacturaVenta factura = new FacturaVenta();
        factura.setFecha(new Date());
        factura.setTipoFactura('A');
        factura.setNumSerie(1L);
        factura.setNumFactura(1L);

        //Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("miUsuario");
        usuario.setPassword("miPassword");
        usuario.setPermisosAdministrador(true);

        //Forma de Pago
        FormaDePago formaDePago = new FormaDePago();
        formaDePago.setNombre("Contado");
        formaDePago.setAfectaCaja(true);
        formaDePago.setPredeterminado(true);

        //Transportista
        Transportista transportista = new Transportista();
        transportista.setNombre("Transportista de ejemplo");
        transportista.setDireccion("Calle 123");
        transportista.setTelefono("");
        transportista.setWeb("");

        //Medida
        Medida medida = new Medida();
        medida.setNombre("Unidad");

        //Condicion IVA
        CondicionIVA condicionIVARespInscp = new CondicionIVA();
        condicionIVARespInscp.setNombre("Responsable Inscripto");
        condicionIVARespInscp.setDiscriminaIVA(true);

        //Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Demonte");
        cliente.setDireccion("Calle 123");
        cliente.setCondicionIVA(condicionIVARespInscp);
        cliente.setTelPrimario("");
        cliente.setTelSecundario("");
        cliente.setContacto("");
        cliente.setEmail("");
        cliente.setId_Fiscal("");
        cliente.setFechaAlta(new java.util.Date());

        //Pais
        Pais pais = new Pais();
        pais.setNombre("USA");

        //Provincia
        Provincia provincia = new Provincia();
        provincia.setNombre("Texas");
        provincia.setPais(pais);

        //Localidad
        Localidad localidad = new Localidad();
        localidad.setNombre("Dallas");
        localidad.setCodigoPostal("");
        localidad.setProvincia(provincia);

        //Rubro
        Rubro rubro = new Rubro();
        rubro.setNombre("VARIOS");

        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa Prueba");
        empresa.setDireccion("Calle Prueba 456");
        empresa.setCondicionIVA(condicionIVARespInscp);
        empresa.setEmail("");
        empresa.setLocalidad(localidad);
        empresa.setLema("");
        empresa.setTelefono("");

        empresaService.guardar(empresa);
        empresa = empresaService.getEmpresaPorNombre(empresa.getNombre());

        //Proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("");
        proveedor.setRazonSocial("Proveedor de ejemplo");
        proveedor.setDireccion("Calle 123");
        proveedor.setId_Fiscal("");
        proveedor.setTelPrimario("");
        proveedor.setTelSecundario("");
        proveedor.setContacto("");
        proveedor.setEmail("");
        proveedor.setWeb("");

        //Producto
        Producto producto = new Producto();
        producto.setCodigo("12345");
        producto.setDescripcion("Producto de pruebas ABC");
        producto.setCantidad(100);
        producto.setPrecioCosto(100);
        producto.setGanancia_porcentaje(20);
        producto.setPrecioVentaPublico(120);
        producto.setIva_porcentaje(21);
        producto.setGanancia_neto(20);
        producto.setIva_neto(25.2);
        producto.setPrecioLista(145.2);
        producto.setMedida(medida);
        producto.setRubro(rubro);
        producto.setProveedor(proveedor);
        producto.setEmpresa(empresa);
        producto.setFechaUltimaModificacion(new java.util.Date());
        producto.setEstanteria("");
        producto.setEstante("");
        producto.setFechaAlta(new java.util.Date());
        producto.setNota("");

        productoService.guardar(producto);
        producto = productoService.getProductoPorCodigo(producto.getCodigo(), empresa);

        Producto producto2 = new Producto();
        producto2.setCodigo("45678");
        producto2.setDescripcion("Producto de pruebas DEF");
        producto2.setCantidad(100);
        producto2.setPrecioCosto(200);
        producto2.setGanancia_porcentaje(30);
        producto2.setPrecioVentaPublico(260);
        producto2.setIva_porcentaje(10.5);
        producto2.setGanancia_neto(60);
        producto2.setIva_neto(27.3);
        producto2.setPrecioLista(287.3);
        producto2.setMedida(medida);
        producto2.setRubro(rubro);
        producto2.setProveedor(proveedor);
        producto2.setEmpresa(empresa);
        producto2.setFechaUltimaModificacion(new java.util.Date());
        producto2.setEstanteria("");
        producto2.setEstante("");
        producto2.setFechaAlta(new java.util.Date());
        producto2.setNota("");

        productoService.guardar(producto2);
        producto2 = productoService.getProductoPorCodigo(producto2.getCodigo(), empresa);

        //Renglones
        Set<RenglonFactura> renglones = new HashSet<>();
        RenglonFactura renglon1 = renglonFacturaService.calcularRenglon(factura.getTipoFactura(), Movimiento.VENTA, 8, producto, 0);
        renglon1.setFactura(factura);
        renglones.add(renglon1);
        RenglonFactura renglon2 = renglonFacturaService.calcularRenglon(factura.getTipoFactura(), Movimiento.VENTA, 8, producto2, 0);
        renglon2.setFactura(factura);
        renglones.add(renglon2);
        List<RenglonFactura> renglonesList = new ArrayList<>(renglones);

        factura.setRenglones(renglones);

        factura.setSubTotal(facturaService.calcularSubTotal(renglonesList));
        factura.setDescuento_neto(facturaService.calcularDescuento_neto(factura.getSubTotal(), factura.getDescuento_porcentaje()));
        factura.setRecargo_neto(facturaService.calcularRecargo_neto(factura.getSubTotal(), factura.getRecargo_porcentaje()));
        factura.setSubTotal_neto(facturaService.calcularSubTotal_neto(factura.getSubTotal(), factura.getRecargo_neto(), factura.getDescuento_neto()));
        factura.setIva_105_neto(facturaService.calcularIva_neto(factura.getTipoFactura(), factura.getDescuento_porcentaje(), factura.getRecargo_porcentaje(), renglonesList, 10.5));
        factura.setIva_21_neto(facturaService.calcularIva_neto(factura.getTipoFactura(), factura.getDescuento_porcentaje(), factura.getRecargo_porcentaje(), renglonesList, 21));
        factura.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(factura.getTipoFactura(), factura.getDescuento_porcentaje(), factura.getRecargo_porcentaje(), renglonesList));
        factura.setTotal(facturaService.calcularTotal(factura.getSubTotal(), factura.getDescuento_neto(), factura.getRecargo_neto(), factura.getIva_105_neto(), factura.getIva_21_neto(), factura.getImpuestoInterno_neto()));
        factura.setObservaciones("");
        factura.setPagada(true);
        factura.setEliminada(true);
        factura.setEmpresa(empresa);
        factura.setCliente(cliente);
        factura.setTransportista(transportista);
        factura.setFormaPago(formaDePago);
        factura.setUsuario(usuario);

        //facturaService.guardar(factura);

        // Factura A
        FacturaVenta facturaA = new FacturaVenta();
        facturaA.setFecha(factura.getFecha());
        facturaA.setTipoFactura(factura.getTipoFactura());
        facturaA.setNumSerie(1L);
        facturaA.setNumFactura(1L);

        Set<RenglonFactura> renglonesA = new HashSet<>();
        renglon1 = renglonFacturaService.calcularRenglon(facturaA.getTipoFactura(), Movimiento.VENTA, 4, producto, 0);
        renglon1.setFactura(facturaA);
        renglon2 = renglonFacturaService.calcularRenglon(facturaA.getTipoFactura(), Movimiento.VENTA, 4, producto2, 0);
        renglon2.setFactura(facturaA);
        renglonesA.add(renglon2);
        renglonesA.add(renglon1);

        List<RenglonFactura> renglonesAList = new ArrayList<>(renglonesA);

        facturaA.setRenglones(renglonesA);

        facturaA.setSubTotal(facturaService.calcularSubTotal(renglonesAList));
        facturaA.setDescuento_neto(facturaService.calcularDescuento_neto(facturaA.getSubTotal(), facturaA.getDescuento_porcentaje()));
        facturaA.setRecargo_neto(facturaService.calcularRecargo_neto(facturaA.getSubTotal(), facturaA.getRecargo_porcentaje()));
        facturaA.setSubTotal_neto(facturaService.calcularSubTotal_neto(facturaA.getSubTotal(), facturaA.getRecargo_neto(), facturaA.getDescuento_neto()));
        facturaA.setIva_105_neto(facturaService.calcularIva_neto(facturaA.getTipoFactura(), facturaA.getDescuento_porcentaje(), facturaA.getRecargo_porcentaje(), renglonesAList, 10.5));
        facturaA.setIva_21_neto(facturaService.calcularIva_neto(facturaA.getTipoFactura(), facturaA.getDescuento_porcentaje(), facturaA.getRecargo_porcentaje(), renglonesAList, 21));
        facturaA.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(facturaA.getTipoFactura(), facturaA.getDescuento_porcentaje(), facturaA.getRecargo_porcentaje(), renglonesAList));
        facturaA.setTotal(facturaService.calcularTotal(facturaA.getSubTotal(), facturaA.getDescuento_neto(), facturaA.getRecargo_neto(), facturaA.getIva_105_neto(), facturaA.getIva_21_neto(), facturaA.getImpuestoInterno_neto()));
        facturaA.setObservaciones("");
        facturaA.setPagada(true);
        facturaA.setEliminada(true);
        facturaA.setEmpresa(empresa);
        facturaA.setCliente(cliente);
        facturaA.setTransportista(transportista);
        facturaA.setFormaPago(formaDePago);
        facturaA.setUsuario(usuario);

        //facturaService.guardar(facturaA);

        //FacturaX
        FacturaVenta facturaX = new FacturaVenta();
        facturaX.setFecha(factura.getFecha());
        facturaX.setTipoFactura('X');
        facturaX.setNumSerie(1L);
        facturaX.setNumFactura(1L);

        Set<RenglonFactura> renglonesX = new HashSet<>();
        renglon1 = renglonFacturaService.calcularRenglon(facturaX.getTipoFactura(), Movimiento.VENTA, 4, producto, 0);
        renglon1.setFactura(facturaX);
        renglon2 = renglonFacturaService.calcularRenglon(facturaX.getTipoFactura(), Movimiento.VENTA, 4, producto2, 0);
        renglon2.setFactura(facturaX);
        renglonesX.add(renglon2);
        renglonesX.add(renglon1);

        List<RenglonFactura> renglonesXList = new ArrayList<>(renglonesX);

        facturaX.setRenglones(renglonesX);

        facturaX.setSubTotal(facturaService.calcularSubTotal(renglonesXList));
        facturaX.setDescuento_neto(facturaService.calcularDescuento_neto(facturaX.getSubTotal(), facturaX.getDescuento_porcentaje()));
        facturaX.setRecargo_neto(facturaService.calcularRecargo_neto(facturaX.getSubTotal(), facturaX.getRecargo_porcentaje()));
        facturaX.setSubTotal_neto(facturaService.calcularSubTotal_neto(facturaX.getSubTotal(), facturaX.getRecargo_neto(), facturaX.getDescuento_neto()));
        facturaX.setIva_105_neto(facturaService.calcularIva_neto(facturaX.getTipoFactura(), facturaX.getDescuento_porcentaje(), facturaX.getRecargo_porcentaje(), renglonesXList, 10.5));
        facturaX.setIva_21_neto(facturaService.calcularIva_neto(facturaX.getTipoFactura(), facturaX.getDescuento_porcentaje(), facturaX.getRecargo_porcentaje(), renglonesXList, 21));
        facturaX.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(facturaX.getTipoFactura(), facturaX.getDescuento_porcentaje(), facturaX.getRecargo_porcentaje(), renglonesXList));
        facturaX.setTotal(facturaService.calcularTotal(facturaX.getSubTotal(), facturaX.getDescuento_neto(), facturaX.getRecargo_neto(), facturaX.getIva_105_neto(), facturaX.getIva_21_neto(), facturaX.getImpuestoInterno_neto()));
        facturaX.setObservaciones("");
        facturaX.setPagada(true);
        facturaX.setEliminada(true);
        facturaX.setEmpresa(empresa);
        facturaX.setCliente(cliente);
        facturaX.setTransportista(transportista);
        facturaX.setFormaPago(formaDePago);
        facturaX.setUsuario(usuario);

       // facturaService.guardar(facturaX);
        
        FacturaVenta facturaXObtenida = facturaService.dividirFactura(factura).get(1);
        facturaXObtenida.setFecha(factura.getFecha());
        
        FacturaVenta facturaAObtenida = facturaService.dividirFactura(factura).get(0);
        facturaAObtenida.setFecha(factura.getFecha());

        assertEquals("La factura X no es la esperada", facturaX, facturaService.dividirFactura(factura).get(0));
        assertEquals("La factura A no es la esperada", facturaA, facturaService.dividirFactura(factura).get(1));

    }
}
