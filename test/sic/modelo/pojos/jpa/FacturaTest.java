package sic.modelo.pojos.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Medida;
import sic.modelo.PagoFacturaCompra;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.service.FacturaService;
import sic.service.Movimiento;
import sic.service.RenglonDeFacturaService;

public class FacturaTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;
    private FacturaService facturaService;

    @BeforeClass
    public static void initEntityManager() {
        emf = Persistence.createEntityManagerFactory("SIC-Test-PU");
        em = emf.createEntityManager();

    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
        facturaService = new FacturaService();
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

        tx.begin();
        em.persist(factura);
        tx.commit();
    }

    @Test
    public void guardarUnaFacturaVentaTipoAconDivision() {
        FacturaVenta facturaEsperada = new FacturaVenta();
        facturaEsperada.setFecha(new Date());
        facturaEsperada.setTipoFactura('A');
        facturaEsperada.setNumSerie(1L);
        facturaEsperada.setNumFactura(1L);
        
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
        facturaEsperada.setCliente(cliente);
        
        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa Prueba");
        empresa.setDireccion("Calle Prueba 456");
        empresa.setCondicionIVA(condicionIVARespInscp);

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

        //Renglones
        RenglonDeFacturaService renglonFacturaService = new RenglonDeFacturaService();
        Set<RenglonFactura> renglones = new HashSet<>();
        RenglonFactura renglon1 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 8, producto, 0);
        renglones.add(renglon1);
        RenglonFactura renglon2 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 8, producto2, 0);
        renglones.add(renglon2);

        List<RenglonFactura> renglonesList = new ArrayList<>(renglones);

        facturaEsperada.setRenglones(renglones);

        facturaEsperada.setSubTotal(facturaService.calcularSubTotal(renglonesList));
        facturaEsperada.setDescuento_neto(facturaService.calcularDescuento_neto(facturaEsperada.getSubTotal(), facturaEsperada.getDescuento_porcentaje()));
        facturaEsperada.setRecargo_neto(facturaService.calcularRecargo_neto(facturaEsperada.getSubTotal(), facturaEsperada.getRecargo_porcentaje()));
        facturaEsperada.setSubTotal_neto(facturaService.calcularSubTotal_neto(facturaEsperada.getSubTotal(), facturaEsperada.getRecargo_neto(), facturaEsperada.getDescuento_neto()));
        facturaEsperada.setIva_105_neto(facturaService.calcularIva_neto(facturaEsperada.getTipoFactura(), facturaEsperada.getDescuento_porcentaje(), facturaEsperada.getRecargo_porcentaje(), renglonesList, 10.5));
        facturaEsperada.setIva_21_neto(facturaService.calcularIva_neto(facturaEsperada.getTipoFactura(), facturaEsperada.getDescuento_porcentaje(), facturaEsperada.getRecargo_porcentaje(), renglonesList, 21));
        facturaEsperada.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(facturaEsperada.getTipoFactura(), facturaEsperada.getDescuento_porcentaje(), facturaEsperada.getRecargo_porcentaje(), renglonesList));
        facturaEsperada.setTotal(facturaService.calcularTotal(facturaEsperada.getSubTotal(), facturaEsperada.getDescuento_neto(), facturaEsperada.getRecargo_neto(), facturaEsperada.getIva_105_neto(), facturaEsperada.getIva_21_neto(), facturaEsperada.getImpuestoInterno_neto()));
        facturaEsperada.setObservaciones("");
        facturaEsperada.setPagada(true);
        facturaEsperada.setEliminada(true);
        facturaEsperada.setEmpresa(empresa);
        facturaEsperada.setCliente(cliente);

        List<Factura> facturas = new ArrayList<Factura>(facturaService.dividirFactura(facturaEsperada));

//        for (Factura factura : facturas) {
//            facturaService.guardar(factura);
//        }
//        Factura facturaObtenida = facturaService.getFacturaVentaPorTipoSerieNum(facturaEsperada.getTipoFactura(), facturaEsperada.getNumSerie(), facturaEsperada.getNumFactura());
//        Factura facturaObtenida2 = facturaService.getFacturaVentaPorTipoSerieNum(facturaEsperada.getTipoFactura(), facturaEsperada.getNumSerie(), facturaEsperada.getNumFactura());

        Factura facturaObtenida = facturas.get(0);
        Factura facturaObtenida2 = facturas.get(1);
        
        /************************* Facturas Divididas Esperadas ******************/
        
        // Factura A
        
        FacturaVenta facturaEsperada1 = new FacturaVenta();
        facturaEsperada1.setFecha(new Date());
        facturaEsperada1.setTipoFactura('A');
        facturaEsperada1.setNumSerie(1L);
        facturaEsperada1.setNumFactura(1L);
        
        Set<RenglonFactura> renglonesEsperados1 = new HashSet<>();
        RenglonFactura renglonEsperado1 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 4, producto, 0);
        renglonesEsperados1.add(renglonEsperado1);
        RenglonFactura renglonEsperado2 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 4, producto2, 0);
        renglonesEsperados1.add(renglonEsperado2);
        
        List<RenglonFactura> renglonesListEsperados1 = new ArrayList<>(renglonesEsperados1);

        facturaEsperada1.setRenglones(renglonesEsperados1);
        
        facturaEsperada1.setSubTotal(facturaService.calcularSubTotal(renglonesListEsperados1));
        facturaEsperada1.setDescuento_neto(facturaService.calcularDescuento_neto(facturaEsperada1.getSubTotal(), facturaEsperada1.getDescuento_porcentaje()));
        facturaEsperada1.setRecargo_neto(facturaService.calcularRecargo_neto(facturaEsperada1.getSubTotal(), facturaEsperada1.getRecargo_porcentaje()));
        facturaEsperada1.setSubTotal_neto(facturaService.calcularSubTotal_neto(facturaEsperada1.getSubTotal(), facturaEsperada1.getRecargo_neto(), facturaEsperada1.getDescuento_neto()));
        facturaEsperada1.setIva_105_neto(facturaService.calcularIva_neto(facturaEsperada1.getTipoFactura(), facturaEsperada1.getDescuento_porcentaje(), facturaEsperada1.getRecargo_porcentaje(), renglonesListEsperados1, 10.5));
        facturaEsperada1.setIva_21_neto(facturaService.calcularIva_neto(facturaEsperada1.getTipoFactura(), facturaEsperada1.getDescuento_porcentaje(), facturaEsperada1.getRecargo_porcentaje(), renglonesListEsperados1, 21));
        facturaEsperada1.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(facturaEsperada1.getTipoFactura(), facturaEsperada1.getDescuento_porcentaje(), facturaEsperada1.getRecargo_porcentaje(), renglonesListEsperados1));
        facturaEsperada1.setTotal(facturaService.calcularTotal(facturaEsperada1.getSubTotal(), facturaEsperada1.getDescuento_neto(), facturaEsperada1.getRecargo_neto(), facturaEsperada1.getIva_105_neto(), facturaEsperada1.getIva_21_neto(), facturaEsperada1.getImpuestoInterno_neto()));
        facturaEsperada1.setObservaciones("");
        facturaEsperada1.setPagada(true);
        facturaEsperada1.setEliminada(true);
        facturaEsperada1.setEmpresa(empresa);
        facturaEsperada1.setCliente(cliente);
        
        
        // Factura X
        
        FacturaVenta facturaEsperada2 = new FacturaVenta();
        facturaEsperada2.setFecha(new Date());
        facturaEsperada2.setTipoFactura('A');
        facturaEsperada2.setNumSerie(1L);
        facturaEsperada2.setNumFactura(1L);
        
        Set<RenglonFactura> renglonesEsperados2 = new HashSet<>();
        RenglonFactura renglonEsperado3 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 4, producto, 0);
        renglonesEsperados2.add(renglonEsperado3);
        RenglonFactura renglonEsperado4 = renglonFacturaService.calcularRenglon(facturaEsperada.getTipoFactura(), Movimiento.VENTA, 4, producto2, 0);
        renglonesEsperados2.add(renglonEsperado4);
        
        List<RenglonFactura> renglonesListEsperados2 = new ArrayList<>(renglonesEsperados2);

        facturaEsperada2.setRenglones(renglonesEsperados2);
        
        facturaEsperada2.setSubTotal(facturaService.calcularSubTotal(renglonesListEsperados2));
        facturaEsperada2.setDescuento_neto(facturaService.calcularDescuento_neto(facturaEsperada2.getSubTotal(), facturaEsperada2.getDescuento_porcentaje()));
        facturaEsperada2.setRecargo_neto(facturaService.calcularRecargo_neto(facturaEsperada2.getSubTotal(), facturaEsperada2.getRecargo_porcentaje()));
        facturaEsperada2.setSubTotal_neto(facturaService.calcularSubTotal_neto(facturaEsperada2.getSubTotal(), facturaEsperada2.getRecargo_neto(), facturaEsperada2.getDescuento_neto()));
        facturaEsperada2.setIva_105_neto(facturaService.calcularIva_neto(facturaEsperada2.getTipoFactura(), facturaEsperada2.getDescuento_porcentaje(), facturaEsperada2.getRecargo_porcentaje(), renglonesListEsperados2, 10.5));
        facturaEsperada2.setIva_21_neto(facturaService.calcularIva_neto(facturaEsperada2.getTipoFactura(), facturaEsperada2.getDescuento_porcentaje(), facturaEsperada2.getRecargo_porcentaje(), renglonesListEsperados2, 21));
        facturaEsperada2.setImpuestoInterno_neto(facturaService.calcularImpInterno_neto(facturaEsperada2.getTipoFactura(), facturaEsperada2.getDescuento_porcentaje(), facturaEsperada2.getRecargo_porcentaje(), renglonesListEsperados2));
        facturaEsperada2.setTotal(facturaService.calcularTotal(facturaEsperada2.getSubTotal(), facturaEsperada2.getDescuento_neto(), facturaEsperada2.getRecargo_neto(), facturaEsperada2.getIva_105_neto(), facturaEsperada2.getIva_21_neto(), facturaEsperada2.getImpuestoInterno_neto()));
        facturaEsperada2.setObservaciones("");
        facturaEsperada2.setPagada(true);
        facturaEsperada2.setEliminada(true);
        facturaEsperada2.setEmpresa(empresa);
        facturaEsperada2.setCliente(cliente);
        
        
        
        // Igualo los ID porque son autogenerados
        facturaEsperada1.setId_Factura(facturaObtenida.getId_Factura());
        facturaEsperada2.setId_Factura(facturaObtenida2.getId_Factura());

        assertEquals(facturaEsperada1, facturaObtenida);
        assertEquals(facturaEsperada2, facturaObtenida2);
    }

    @Test
    public void ejecutarConsultaTopMasVendidos() {
        //cargar facturas antes
        TypedQuery<Object[]> query = (TypedQuery<Object[]>) em.createNamedQuery("Factura.buscarTopProductosMasVendidosPorAnio");
        query.setParameter("anio", 2013);
        List<Object[]> resul = query.getResultList();
        assertNotNull(resul);
    }

}
