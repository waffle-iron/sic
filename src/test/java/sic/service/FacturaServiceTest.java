package sic.service;

import org.junit.Test;
import static org.junit.Assert.*;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.Proveedor;

public class FacturaServiceTest {

    
//    @Test
//    public void testGetTipoFacturaCompraCuandoEmpresaYProveedorDiscriminan() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAqueDiscrimina = new CondicionIVA();
//        condicionIVAqueDiscrimina.setDiscriminaIVA(true);
//        empresa.setCondicionIVA(condicionIVAqueDiscrimina);
//        Proveedor proveedor = new Proveedor();
//        proveedor.setCondicionIVA(condicionIVAqueDiscrimina);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'A', 'B', 'X'};
//        char[] result = instance.getTipoFacturaCompra(empresa, proveedor);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaCompraCuandoEmpresaDiscriminaYProveedorNO() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAqueDiscrimina = new CondicionIVA();
//        condicionIVAqueDiscrimina.setDiscriminaIVA(true);
//        empresa.setCondicionIVA(condicionIVAqueDiscrimina);
//        Proveedor proveedor = new Proveedor();
//        CondicionIVA condicionIVAqueNoDiscrimina = new CondicionIVA();
//        condicionIVAqueNoDiscrimina.setDiscriminaIVA(false);
//        proveedor.setCondicionIVA(condicionIVAqueNoDiscrimina);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'C', 'X'};
//        empresa.getCondicionIVA().isDiscriminaIVA();
//        char[] result = instance.getTipoFacturaCompra(empresa, proveedor);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaCompraCuandoEmpresaNoDiscriminaYProveedorSI() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAqueNoDiscrimina = new CondicionIVA();
//        condicionIVAqueNoDiscrimina.setDiscriminaIVA(false);
//        empresa.setCondicionIVA(condicionIVAqueNoDiscrimina);
//        Proveedor proveedor = new Proveedor();
//        CondicionIVA condicionIVAqueDiscrimina = new CondicionIVA();
//        condicionIVAqueDiscrimina.setDiscriminaIVA(true);
//        proveedor.setCondicionIVA(condicionIVAqueDiscrimina);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'B', 'X'};
//        empresa.getCondicionIVA().isDiscriminaIVA();
//        char[] result = instance.getTipoFacturaCompra(empresa, proveedor);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaCompraCuandoEmpresaNoDiscriminaYProveedorTampoco() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAqueNoDiscrimina = new CondicionIVA();
//        condicionIVAqueNoDiscrimina.setDiscriminaIVA(false);
//        empresa.setCondicionIVA(condicionIVAqueNoDiscrimina);
//        Proveedor proveedor = new Proveedor();
//        proveedor.setCondicionIVA(condicionIVAqueNoDiscrimina);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'C', 'X'};
//        empresa.getCondicionIVA().isDiscriminaIVA();
//        char[] result = instance.getTipoFacturaCompra(empresa, proveedor);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaVenta() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVA = new CondicionIVA();
//        condicionIVA.setDiscriminaIVA(true);
//        empresa.setCondicionIVA(condicionIVA);
//        Cliente cliente = new Cliente();
//        cliente.setCondicionIVA(condicionIVA);
//        FacturaService instance = new FacturaService();
//        String[] expResult = {"Factura A", "Factura B", "Factura X", "Factura Y", "Pedido"};
//        String[] result = instance.getTipoFacturaVenta(empresa, cliente);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaVentaEmpresaDiscriminaYClienteNo() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVA = new CondicionIVA();
//        condicionIVA.setDiscriminaIVA(true);
//        empresa.setCondicionIVA(condicionIVA);
//        Cliente cliente = new Cliente();
//        CondicionIVA condicionIVAnoDiscrimina = new CondicionIVA();
//        condicionIVAnoDiscrimina.setDiscriminaIVA(false);
//        cliente.setCondicionIVA(condicionIVAnoDiscrimina);
//        FacturaService instance = new FacturaService();
//        String[] expResult = {"Factura B", "Factura X", "Factura Y", "Pedido"};
//        String[] result = instance.getTipoFacturaVenta(empresa, cliente);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaVentaEmpresaNoDiscriminaYClienteSi() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAnoDiscrimina = new CondicionIVA();
//        condicionIVAnoDiscrimina.setDiscriminaIVA(false);
//        empresa.setCondicionIVA(condicionIVAnoDiscrimina);
//        Cliente cliente = new Cliente();
//        CondicionIVA condicionIVA = new CondicionIVA();
//        condicionIVA.setDiscriminaIVA(true);
//        cliente.setCondicionIVA(condicionIVA);
//        FacturaService instance = new FacturaService();
//        String[] expResult = {"Factura C", "Factura X", "Factura Y", "Pedido"};
//        String[] result = instance.getTipoFacturaVenta(empresa, cliente);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTipoFacturaVentaEmpresaNoDiscriminaYClienteNO() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVAnoDiscrimina = new CondicionIVA();
//        condicionIVAnoDiscrimina.setDiscriminaIVA(false);
//        empresa.setCondicionIVA(condicionIVAnoDiscrimina);
//        Cliente cliente = new Cliente();
//        cliente.setCondicionIVA(condicionIVAnoDiscrimina);
//        FacturaService instance = new FacturaService();
//        String[] expResult = {"Factura C", "Factura X", "Factura Y", "Pedido"};
//        String[] result = instance.getTipoFacturaVenta(empresa, cliente);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTiposFacturaSegunEmpresaSiDiscriminaIVA() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVA = new CondicionIVA();
//        condicionIVA.setDiscriminaIVA(true);
//        empresa.setCondicionIVA(condicionIVA);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'A', 'B', 'X', 'Y'};
//        char[] result = instance.getTiposFacturaSegunEmpresa(empresa);
//        assertArrayEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetTiposFacturaSegunEmpresaSiNoDiscriminaIVA() {
//        Empresa empresa = new Empresa();
//        CondicionIVA condicionIVA = new CondicionIVA();
//        condicionIVA.setDiscriminaIVA(false);
//        empresa.setCondicionIVA(condicionIVA);
//        FacturaService instance = new FacturaService();
//        char[] expResult = {'C', 'X', 'Y'};
//        char[] result = instance.getTiposFacturaSegunEmpresa(empresa);
//        assertArrayEquals(expResult, result);
//    }
}