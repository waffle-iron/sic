package sic.integration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.embedded.LocalServerPort;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.FormaDePagoBuilder;
import sic.builder.LocalidadBuilder;
import sic.builder.MedidaBuilder;
import sic.builder.ProductoBuilder;
import sic.builder.ProveedorBuilder;
import sic.builder.RubroBuilder;
import sic.builder.TransportistaBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Credencial;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.modelo.Localidad;
import sic.modelo.Medida;
import sic.modelo.Movimiento;
import sic.modelo.Pais;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Provincia;
import sic.modelo.RenglonFactura;
import sic.modelo.Rubro;
import sic.modelo.Transportista;
import sic.modelo.Usuario;
import sic.modelo.dto.FacturaVentaDTO;
import sic.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FacturaBIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;
    
    @LocalServerPort
    private int port;

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
    public void test() {
        //Token
        this.token = restTemplate.postForEntity("http://localhost:"+port+"/api/v1/login", new Credencial("test", "test"), String.class).getBody();
        //Ubicacion
        Localidad localidad = new LocalidadBuilder().build();
        //Pais
        localidad.getProvincia().setPais(restTemplate.postForObject("/api/v1/paises", localidad.getProvincia().getPais(), Pais.class));
        //Provincia
        localidad.setProvincia(restTemplate.postForObject("/api/v1/provincias", localidad.getProvincia(), Provincia.class));
        //condicion-iva
        CondicionIVA condicionIVA = new CondicionIVABuilder().build();
        //Empresa           
        Empresa empresa = new EmpresaBuilder()
                .withLocalidad(restTemplate.postForObject("/api/v1/localidades", localidad, Localidad.class))
                .withCondicionIVA(restTemplate.postForObject("/api/v1/condiciones-iva", condicionIVA, CondicionIVA.class))
                .build();
        empresa = restTemplate.postForObject("/api/v1/empresas", empresa, Empresa.class);
        //FormaDePago
        FormaDePago formaDePago = new FormaDePagoBuilder()
                .withAfectaCaja(false)
                .withEmpresa(empresa)
                .withPredeterminado(true)
                .withNombre("Efectivo")
                .build();
        restTemplate.postForObject("/api/v1/formas-de-pago", formaDePago, FormaDePago.class);
        //Cliente
        Cliente cliente = new ClienteBuilder()
                .withEmpresa(empresa)
                .withCondicionIVA(empresa.getCondicionIVA())
                .withLocalidad(empresa.getLocalidad())
                .withPredeterminado(true)
                .build();
        cliente = restTemplate.postForObject("/api/v1/clientes", cliente, Cliente.class);
        //Transportista
        Transportista transportista = new TransportistaBuilder()
                .withEmpresa(empresa)
                .withLocalidad(empresa.getLocalidad())
                .build();
        transportista = restTemplate.postForObject("/api/v1/transportistas", transportista, Transportista.class);
        //Medida
        Medida medida = new MedidaBuilder().withEmpresa(empresa).build();
        medida = restTemplate.postForObject("/api/v1/medidas", medida, Medida.class);
        //Proveedor
        Proveedor proveedor = new ProveedorBuilder().withEmpresa(empresa)
                .withLocalidad(empresa.getLocalidad())
                .withCondicionIVA(empresa.getCondicionIVA())
                .build();
        proveedor = restTemplate.postForObject("/api/v1/proveedores", proveedor, Proveedor.class);
        //Rubro
        Rubro rubro = new RubroBuilder().withEmpresa(empresa).build();
        rubro = restTemplate.postForObject("/api/v1/rubros", rubro, Rubro.class);
        //Productos
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

        productoUno = restTemplate.postForObject("/api/v1/productos", productoUno, Producto.class);
        productoDos = restTemplate.postForObject("/api/v1/productos", productoDos, Producto.class);

        assertEquals(10, productoUno.getCantidad(), 0);
        assertEquals(6, productoDos.getCantidad(), 0);

        RenglonFactura renglonUno = restTemplate.getForObject("/api/v1/facturas/renglon?"
                + "idProducto=" + productoUno.getId_Producto()
                + "&tipoComprobante=" + 'B'
                + "&movimiento=" + Movimiento.VENTA
                + "&cantidad=" + 5
                + "&descuentoPorcentaje=" + 0,
                RenglonFactura.class);

        RenglonFactura renglonDos = restTemplate.getForObject("/api/v1/facturas/renglon?"
                + "idProducto=" + productoDos.getId_Producto()
                + "&tipoComprobante=" + 'B'
                + "&movimiento=" + Movimiento.VENTA
                + "&cantidad=" + 2
                + "&descuentoPorcentaje=" + 0,
                RenglonFactura.class);

        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglonUno);
        renglones.add(renglonDos);

        FacturaVentaDTO facturaVentaB = new FacturaVentaDTO();
        facturaVentaB.setTipoFactura('B');
        facturaVentaB.setCliente(cliente);
        facturaVentaB.setEmpresa(empresa);
        facturaVentaB.setTransportista(transportista);
        facturaVentaB.setUsuario(restTemplate.getForObject("/api/v1/usuarios/1", Usuario.class));
        facturaVentaB.setRenglones(renglones);
        facturaVentaB.setFecha(new Date());

        restTemplate.postForObject("/api/v1/facturas", facturaVentaB, FacturaVentaDTO[].class);
        restTemplate.getForObject("/api/v1/facturas/1", FacturaVentaDTO.class);

        assertEquals(5, restTemplate.getForObject("/api/v1/productos/1", Producto.class).getCantidad(), 0);
        assertEquals(4, restTemplate.getForObject("/api/v1/productos/2", Producto.class).getCantidad(), 0);
    }

}
