package sic.integration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.FormaDePagoBuilder;
import sic.builder.LocalidadBuilder;
import sic.builder.MedidaBuilder;
import sic.builder.PedidoBuilder;
import sic.builder.ProductoBuilder;
import sic.builder.ProveedorBuilder;
import sic.builder.RenglonPedidoBuilder;
import sic.builder.RubroBuilder;
import sic.builder.TransportistaBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Credencial;
import sic.modelo.Empresa;
import sic.modelo.EstadoPedido;
import sic.modelo.Factura;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Localidad;
import sic.modelo.Medida;
import sic.modelo.Movimiento;
import sic.modelo.Pais;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Provincia;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.modelo.Rol;
import sic.modelo.Rubro;
import sic.modelo.TipoDeComprobante;
import sic.modelo.Transportista;
import sic.modelo.Usuario;
import sic.modelo.dto.FacturaVentaDTO;
import sic.modelo.dto.PedidoDTO;
import sic.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FacturacionDePedidoIntegrationTest {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;
    
    private final String apiPrefix = "/api/v1";
   
    @Before
    public void setup() {
        String md5Test = "098f6bcd4621d373cade4e832627b4f6";
        usuarioRepository.save(new UsuarioBuilder().withNombre("test").withPassword(md5Test).build());
        // Interceptor de RestTemplate para JWT
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((ClientHttpRequestInterceptor) (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        });        
        restTemplate.getRestTemplate().setInterceptors(interceptors);
        // ErrorHandler para RestTemplate        
        restTemplate.getRestTemplate().setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus.Series series = response.getStatusCode().series();
                return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                String mensaje = IOUtils.toString(response.getBody());                
                throw new RestClientResponseException(mensaje, response.getRawStatusCode(),
                        response.getStatusText(), response.getHeaders(),
                        null, Charset.defaultCharset());
            }
        });
    }
    
    @Test
    public void Test() {
        this.token = restTemplate.postForEntity(apiPrefix + "/login", new Credencial("test", "test"), String.class).getBody();
        Localidad localidad = new LocalidadBuilder().build();
        localidad.getProvincia().setPais(restTemplate.postForObject(apiPrefix + "/paises", localidad.getProvincia().getPais(), Pais.class));
        localidad.setProvincia(restTemplate.postForObject(apiPrefix + "/provincias", localidad.getProvincia(), Provincia.class));
        CondicionIVA condicionIVA = new CondicionIVABuilder().build();          
        Empresa empresa = new EmpresaBuilder()
                .withLocalidad(restTemplate.postForObject(apiPrefix + "/localidades", localidad, Localidad.class))
                .withCondicionIVA(restTemplate.postForObject(apiPrefix + "/condiciones-iva", condicionIVA, CondicionIVA.class))
                .build();
        empresa = restTemplate.postForObject(apiPrefix + "/empresas", empresa, Empresa.class);
        FormaDePago formaDePago = new FormaDePagoBuilder()
                .withAfectaCaja(false)
                .withEmpresa(empresa)
                .withPredeterminado(true)
                .withNombre("Efectivo")
                .build();
        restTemplate.postForObject(apiPrefix + "/formas-de-pago", formaDePago, FormaDePago.class);
        Usuario credencial = new UsuarioBuilder()
                .withId_Usuario(1)
                .withEliminado(false)
                .withNombre("Marcelo Cruz")
                .withPassword("marce")
                .withToken("yJhbGci1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJjZWNpbGlvIn0.MCfaorSC7Wdc8rSW7BJizasfzsa")
                .withRol(new ArrayList<>())
                .build();
        Usuario viajante = new UsuarioBuilder()
                .withId_Usuario(1)
                .withEliminado(false)
                .withNombre("Fernando Aguirre")
                .withPassword("fernando")
                .withToken("yJhbGci1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJjZWNpbGlvIn0.MCfaorSC7Wdc8rSW7BJizasfzsb")
                .withRol(new ArrayList<>(Arrays.asList(Rol.VIAJANTE)))
                .build();
        Cliente cliente = new ClienteBuilder()
                .withEmpresa(empresa)
                .withCondicionIVA(empresa.getCondicionIVA())
                .withLocalidad(empresa.getLocalidad())
                .withPredeterminado(true)
                .withCredencial(credencial)
                .withViajante(viajante)
                .build();
        cliente = restTemplate.postForObject(apiPrefix + "/clientes", cliente, Cliente.class);
        Transportista transportista = new TransportistaBuilder()
                .withEmpresa(empresa)
                .withLocalidad(empresa.getLocalidad())
                .build();
        transportista = restTemplate.postForObject(apiPrefix + "/transportistas", transportista, Transportista.class);
        Medida medida = new MedidaBuilder().withEmpresa(empresa).build();
        medida = restTemplate.postForObject(apiPrefix + "/medidas", medida, Medida.class);
        Proveedor proveedor = new ProveedorBuilder().withEmpresa(empresa)
                .withLocalidad(empresa.getLocalidad())
                .withCondicionIVA(empresa.getCondicionIVA())
                .build();
        proveedor = restTemplate.postForObject(apiPrefix + "/proveedores", proveedor, Proveedor.class);
        Rubro rubro = new RubroBuilder().withEmpresa(empresa).build();
        rubro = restTemplate.postForObject(apiPrefix + "/rubros", rubro, Rubro.class);
        Producto productoUno = (new ProductoBuilder())
                .withCodigo("1")
                .withDescripcion("uno")
                .withCantidad(10)
                .withIva_porcentaje(21.0)
                .withEmpresa(empresa)
                .withMedida(medida)
                .withProveedor(proveedor)
                .withRubro(rubro)
                .build();

        Producto productoDos = (new ProductoBuilder())
                .withIva_porcentaje(10.5)
                .withCodigo("2")
                .withDescripcion("dos")
                .withCantidad(6)
                .withEmpresa(empresa)
                .withMedida(medida)
                .withProveedor(proveedor)
                .withRubro(rubro)
                .build();
        productoUno = restTemplate.postForObject(apiPrefix + "/productos", productoUno, Producto.class);
        productoDos = restTemplate.postForObject(apiPrefix + "/productos", productoDos, Producto.class);
        assertEquals(10, productoUno.getCantidad(), 0);
        assertEquals(6, productoDos.getCantidad(), 0);
        
        RenglonFactura renglonUno = restTemplate.getForObject(apiPrefix + "/facturas/renglon?"
                + "idProducto=" + productoUno.getId_Producto()
                + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_B
                + "&movimiento=" + Movimiento.VENTA
                + "&cantidad=" + 5
                + "&descuentoPorcentaje=" + 0,
                RenglonFactura.class);
        
        RenglonPedido renglonPedidoUno = (new RenglonPedidoBuilder())
                .withCantidad(renglonUno.getCantidad())
                .withDescuentoPorcentaje(renglonUno.getDescuento_porcentaje())
                .withDescuentoNeto(renglonUno.getDescuento_neto())
                .withProducto(productoUno)
                .withSubTotal(renglonUno.getImporte())
                .build();
                
        RenglonFactura renglonDos = restTemplate.getForObject(apiPrefix + "/facturas/renglon?"
                + "idProducto=" + productoDos.getId_Producto()
                + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_B 
                + "&movimiento=" + Movimiento.VENTA
                + "&cantidad=" + 2
                + "&descuentoPorcentaje=" + 0,
                RenglonFactura.class);
        
        RenglonPedido renglonPedidoDos = (new RenglonPedidoBuilder())
                .withCantidad(renglonDos.getCantidad())
                .withDescuentoPorcentaje(renglonDos.getDescuento_porcentaje())
                .withDescuentoNeto(renglonDos.getDescuento_neto())
                .withProducto(productoDos)
                .withSubTotal(renglonDos.getImporte())
                .build();
        
        List<RenglonFactura> renglonesFactura = new ArrayList<>();
        renglonesFactura.add(renglonUno);
        renglonesFactura.add(renglonDos);
        
        List<RenglonPedido> renglonesPedido = new ArrayList<>();
        renglonesPedido.add(renglonPedidoUno);
        renglonesPedido.add(renglonPedidoDos);
        
        double[] importes = new double[renglonesPedido.size()];
        int indice = 0;
        for (RenglonFactura renglon : renglonesFactura) {
            importes[indice] = renglon.getImporte();
            indice++;
        }
        
        PedidoDTO pedido = new PedidoDTO();
        pedido.setCliente(cliente);
        pedido.setEmpresa(empresa);
        pedido.setFecha(new Date());
        pedido.setRenglones(renglonesPedido);
        pedido.setUsuario(restTemplate.getForObject(apiPrefix + "/usuarios/busqueda?nombre=test", Usuario.class));
        pedido.setTotalEstimado(restTemplate.getForObject(apiPrefix +"/facturas/subtotal?"
                        + "importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1),
                        double.class));
        pedido.setObservaciones("Pedido Test");
        pedido.setEstado(EstadoPedido.ABIERTO);
        
        Pedido pedidoRecuperado = restTemplate.postForObject(apiPrefix + "/pedidos", pedido, Pedido.class);

        RenglonFactura[] renglonesParaFacturar = restTemplate.getForObject(apiPrefix + "/facturas/renglones/pedidos/" + pedidoRecuperado.getId_Pedido()
                + "?tipoDeComprobante=" + TipoDeComprobante.FACTURA_A.name(), RenglonFactura[].class);
        
        importes = new double[1];
        double[] ivaRenglones = new double[1];
        double[] impuestoPorcentajes = new double[1];

        importes[0] = renglonesParaFacturar[0].getImporte();
        ivaRenglones[0] = renglonesParaFacturar[0].getIva_porcentaje();
        impuestoPorcentajes[0] = renglonesParaFacturar[0].getImpuesto_porcentaje();
        
        double subTotal = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/subtotal?"
                    + "importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1),
                    double.class);
        assertEquals(750.0, subTotal, 0);
        double recargo_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/recargo-neto?"
                    + "subTotal=" + subTotal
                    + "&recargoPorcentaje=" + 10, double.class);
        assertEquals(75, recargo_neto, 0);
        double subTotal_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/subtotal-neto?"
                    + "subTotal=" + subTotal
                    + "&recargoNeto=" + recargo_neto
                    + "&descuentoNeto=0", double.class);
        assertEquals(825.0, subTotal_neto, 0);
        double iva_105_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/iva-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_A 
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&ivaPorcentaje=10.5"
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&ivaRenglones=" + Arrays.toString(ivaRenglones).substring(1, Arrays.toString(ivaRenglones).length() - 1),
                    double.class);
        assertEquals(0, iva_105_neto, 0);
        double iva_21_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/iva-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_A 
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&ivaPorcentaje=21.0"
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&ivaRenglones=" + Arrays.toString(ivaRenglones).substring(1, Arrays.toString(ivaRenglones).length() - 1),
                    double.class);
        assertEquals(173.25, iva_21_neto, 0);
        double impInterno_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/impuesto-interno-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_A  
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&impuestoPorcentaje=" + Arrays.toString(impuestoPorcentajes).substring(1, Arrays.toString(impuestoPorcentajes).length() - 1),
                    double.class);
        assertEquals(0, impInterno_neto, 0);
        double total = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/total?"
                    + "subTotal=" + subTotal
                    + "&descuentoNeto=0"
                    + "&recargoNeto=" + recargo_neto
                    + "&iva105Neto=" + iva_105_neto
                    + "&iva21Neto=" + iva_21_neto
                    + "&impuestoInternoNeto=" + impInterno_neto, double.class);        
        assertEquals(998.25, total, 0);
        FacturaVentaDTO facturaVentaA = new FacturaVentaDTO();
        facturaVentaA.setTipoComprobante(TipoDeComprobante.FACTURA_A);
        facturaVentaA.setCliente(cliente);
        facturaVentaA.setEmpresa(empresa);
        facturaVentaA.setTransportista(transportista);
        facturaVentaA.setUsuario(restTemplate.getForObject(apiPrefix + "/usuarios/busqueda?nombre=test", Usuario.class));
        List<RenglonFactura>  renglon = new ArrayList<>();
        renglon.add(renglonesParaFacturar[0]);
        facturaVentaA.setRenglones(renglon);
        facturaVentaA.setSubTotal(subTotal);
        facturaVentaA.setRecargo_neto(recargo_neto);
        facturaVentaA.setSubTotal_neto(subTotal_neto);
        facturaVentaA.setIva_105_neto(iva_105_neto);
        facturaVentaA.setIva_21_neto(iva_21_neto);
        facturaVentaA.setImpuestoInterno_neto(impInterno_neto);
        facturaVentaA.setTotal(total);
        facturaVentaA.setFecha(new Date());
        facturaVentaA.setPedido(pedidoRecuperado);
        restTemplate.postForObject(apiPrefix + "/facturas?idPedido=" + pedidoRecuperado.getId_Pedido(), facturaVentaA, Factura[].class);
        FacturaVenta[] facturasRecuperadas = restTemplate.getForObject(apiPrefix + "/facturas/venta/busqueda/criteria?idEmpresa=1&nroPedido=1", FacturaVenta[].class);
        
        assertEquals(1, facturasRecuperadas.length, 0);
        
        pedidoRecuperado = restTemplate.getForObject(apiPrefix + "/pedidos/" + pedidoRecuperado.getId_Pedido(), Pedido.class);
        assertEquals(EstadoPedido.ACTIVO, pedidoRecuperado.getEstado());

        renglonesParaFacturar = restTemplate.getForObject(apiPrefix + "/facturas/renglones/pedidos/" + pedidoRecuperado.getId_Pedido()
                + "?tipoDeComprobante=" + TipoDeComprobante.FACTURA_B.name(), RenglonFactura[].class);
        
        importes = new double[1];
        ivaRenglones = new double[1];
        impuestoPorcentajes = new double[1];

        importes[0] = renglonesParaFacturar[0].getImporte();
        ivaRenglones[0] = renglonesParaFacturar[0].getIva_porcentaje();
        impuestoPorcentajes[0] = renglonesParaFacturar[0].getImpuesto_porcentaje();
        
        subTotal = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/subtotal?"
                    + "importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1),
                    double.class);
        assertEquals(363.0, subTotal, 0);
        recargo_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/recargo-neto?"
                    + "subTotal=" + subTotal
                    + "&recargoPorcentaje=" + 10, double.class);
        assertEquals(36.3, recargo_neto, 0);
        subTotal_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/subtotal-neto?"
                    + "subTotal=" + subTotal
                    + "&recargoNeto=" + recargo_neto
                    + "&descuentoNeto=0", double.class);
        assertEquals(399.3, subTotal_neto, 0);
        iva_105_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/iva-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_B 
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&ivaPorcentaje=10.5"
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&ivaRenglones=" + Arrays.toString(ivaRenglones).substring(1, Arrays.toString(ivaRenglones).length() - 1),
                    double.class);
        assertEquals(0, iva_105_neto, 0);
        iva_21_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/iva-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_B 
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&ivaPorcentaje=21.0"
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&ivaRenglones=" + Arrays.toString(ivaRenglones).substring(1, Arrays.toString(ivaRenglones).length() - 1),
                    double.class);
        assertEquals(0, iva_21_neto, 0);
        impInterno_neto = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/impuesto-interno-neto?"
                    + "&tipoDeComprobante=" + TipoDeComprobante.FACTURA_B  
                    + "&descuentoPorcentaje=0"
                    + "&recargoPorcentaje=" + 10
                    + "&importe=" + Arrays.toString(importes).substring(1, Arrays.toString(importes).length() - 1)
                    + "&impuestoPorcentaje=" + Arrays.toString(impuestoPorcentajes).substring(1, Arrays.toString(impuestoPorcentajes).length() - 1),
                    double.class);
        assertEquals(0, impInterno_neto, 0);
        total = restTemplate.getRestTemplate().getForObject(apiPrefix +"/facturas/total?"
                    + "subTotal=" + subTotal
                    + "&descuentoNeto=0"
                    + "&recargoNeto=" + recargo_neto
                    + "&iva105Neto=" + iva_105_neto
                    + "&iva21Neto=" + iva_21_neto
                    + "&impuestoInternoNeto=" + impInterno_neto, double.class);        
        assertEquals(399.3, total, 0);
        FacturaVentaDTO facturaVentaB = new FacturaVentaDTO();
        facturaVentaB.setTipoComprobante(TipoDeComprobante.FACTURA_B);
        facturaVentaB.setCliente(cliente);
        facturaVentaB.setEmpresa(empresa);
        facturaVentaB.setTransportista(transportista);
        facturaVentaB.setUsuario(restTemplate.getForObject(apiPrefix + "/usuarios/busqueda?nombre=test", Usuario.class));
        renglon = new ArrayList<>();
        renglon.add(renglonesParaFacturar[0]);
        facturaVentaB.setRenglones(renglon);
        facturaVentaB.setSubTotal(subTotal);
        facturaVentaB.setRecargo_neto(recargo_neto);
        facturaVentaB.setSubTotal_neto(subTotal_neto);
        facturaVentaB.setIva_105_neto(iva_105_neto);
        facturaVentaB.setIva_21_neto(iva_21_neto);
        facturaVentaB.setImpuestoInterno_neto(impInterno_neto);
        facturaVentaB.setTotal(total);
        facturaVentaB.setFecha(new Date());
        facturaVentaB.setPedido(pedidoRecuperado);
        restTemplate.postForObject(apiPrefix + "/facturas?idPedido=" + pedidoRecuperado.getId_Pedido(), facturaVentaB, Factura[].class);
        facturasRecuperadas = restTemplate.getForObject(apiPrefix + "/facturas/venta/busqueda/criteria?idEmpresa=1&nroPedido=1", FacturaVenta[].class);
        
        assertEquals(2, facturasRecuperadas.length, 0);
        
        pedidoRecuperado = restTemplate.getForObject(apiPrefix + "/pedidos/" + pedidoRecuperado.getId_Pedido(), Pedido.class);
        assertEquals(EstadoPedido.CERRADO, pedidoRecuperado.getEstado());
        
        restTemplate.delete(apiPrefix + "/facturas?idFactura=1,2");
        pedidoRecuperado = restTemplate.getForObject(apiPrefix + "/pedidos/" + pedidoRecuperado.getId_Pedido(), Pedido.class);
        assertEquals(EstadoPedido.ABIERTO, pedidoRecuperado.getEstado());
    }
    
}
