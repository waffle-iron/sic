package sic.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import  org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.FacturaVentaBuilder;
import sic.builder.FormaDePagoBuilder;
import sic.builder.LocalidadBuilder;
import sic.builder.MedidaBuilder;
import sic.builder.ProductoBuilder;
import sic.builder.ProveedorBuilder;
import sic.builder.RubroBuilder;
import sic.builder.TransportistaBuilder;
import sic.builder.UsuarioBuilder;
import sic.controller.FacturaController;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Credencial;
import sic.modelo.Empresa;
import sic.modelo.Factura;
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
import sic.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@JsonTest
//@WebMvcTest(FacturaController.class)
public class FacturaBIntegrationTest {
    
    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
//    @Autowired
//    private MockMvc mvc;
//    
////    @Autowired
//    private JacksonTester<Factura> json;
    
    private HttpHeaders headers = new HttpHeaders();
    
    @Before
    public void setup() {
        Usuario user = new UsuarioBuilder().build();
        usuarioService.guardar(user);

        Credencial cred = new Credencial("Daenerys Targaryen", "LaQueNoArde");
        String token = this.restTemplate.postForObject("/api/v1/login", cred, String.class);
        headers.set("Authorization", "Bearer " + token);
        
    }
    
    @Test
    public void test() {
        //Ubicacion
        Localidad localidad = new LocalidadBuilder().build();
        HttpEntity<Pais> payLoadPais = new HttpEntity<>(localidad.getProvincia().getPais(), headers);
        //Pais
        localidad.getProvincia().setPais(restTemplate.exchange("/api/v1/paises", HttpMethod.POST, payLoadPais, Pais.class).getBody());
        //Provincia
        HttpEntity<Provincia> payLoadProvincia = new HttpEntity<>(localidad.getProvincia(), headers);
        localidad.setProvincia(restTemplate.exchange("/api/v1/provincias", HttpMethod.POST, payLoadProvincia, Provincia.class).getBody());
        //Localidad
        HttpEntity<Localidad> payLoadLocalidad = new HttpEntity<>(localidad, headers);
        //condicion-iva
        CondicionIVA condicionIVA = new CondicionIVABuilder().build();
        HttpEntity<CondicionIVA> payLoadCondicionIVA = new HttpEntity<>(condicionIVA, headers);
        //Empresa           
        Empresa empresa = new EmpresaBuilder()
                        .withLocalidad(restTemplate.exchange("/api/v1/localidades", HttpMethod.POST, payLoadLocalidad, Localidad.class).getBody())
                        .withCondicionIVA(restTemplate.exchange("/api/v1/condiciones-iva", HttpMethod.POST, payLoadCondicionIVA, CondicionIVA.class).getBody())
                        .build();
        HttpEntity<Empresa> payLoadEmpresa = new HttpEntity<>(empresa, headers);
        empresa = restTemplate.exchange("/api/v1/empresas", HttpMethod.POST, payLoadEmpresa, Empresa.class).getBody();
        //FormaDePago
        FormaDePago formaDePago = new FormaDePagoBuilder()
                                .withAfectaCaja(false)
                                .withEmpresa(empresa)
                                .withPredeterminado(true)
                                .withNombre("Efectivo")
                                .build();
        HttpEntity<FormaDePago> payLoadFormaDePago = new HttpEntity<>(formaDePago, headers);
        restTemplate.exchange("/api/v1/formas-de-pago", HttpMethod.POST, payLoadFormaDePago, FormaDePago.class);
        //Cliente
        Cliente cliente = new ClienteBuilder()
                          .withEmpresa(empresa)
                          .withCondicionIVA(empresa.getCondicionIVA())
                          .withLocalidad(empresa.getLocalidad())
                          .withPredeterminado(true)
                          .build();
        HttpEntity<Cliente> payLoadCliente = new HttpEntity<>(cliente, headers);
        cliente = restTemplate.exchange("/api/v1/clientes", HttpMethod.POST, payLoadCliente, Cliente.class).getBody();
        //Transportista
        Transportista transportista = new TransportistaBuilder()
                                    .withEmpresa(empresa)
                                    .withLocalidad(empresa.getLocalidad())
                                    .build();
        HttpEntity<Transportista> payLoadTransportista = new HttpEntity<>(transportista, headers);
        transportista = restTemplate.exchange("/api/v1/transportistas", HttpMethod.POST, payLoadTransportista, Transportista.class).getBody();
        //Medida
        Medida medida = new MedidaBuilder().withEmpresa(empresa).build();
        HttpEntity<Medida> payLoadMedida = new HttpEntity<>(medida, headers);
        medida = restTemplate.exchange("/api/v1/medidas", HttpMethod.POST, payLoadMedida, Medida.class).getBody();
        //Proveedor
        Proveedor proveedor = new ProveedorBuilder().withEmpresa(empresa)
                            .withLocalidad(empresa.getLocalidad())
                            .withCondicionIVA(empresa.getCondicionIVA())
                            .build();
        HttpEntity<Proveedor> payLoadProveedor = new HttpEntity<>(proveedor, headers);
        proveedor = restTemplate.exchange("/api/v1/proveedores", HttpMethod.POST, payLoadProveedor, Proveedor.class).getBody();
        //Rubro
        Rubro rubro = new RubroBuilder().withEmpresa(empresa).build();
        HttpEntity<Rubro> payLoadRubro = new HttpEntity<>(rubro, headers);
        rubro = restTemplate.exchange("/api/v1/rubros", HttpMethod.POST, payLoadRubro, Rubro.class).getBody();
        
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
        
        HttpEntity<Producto> payLoadProducto1 = new HttpEntity<>(productoUno, headers);
        productoUno = restTemplate.exchange("/api/v1/productos", HttpMethod.POST, payLoadProducto1, Producto.class).getBody();
        HttpEntity<Producto> payLoadProducto2 = new HttpEntity<>(productoDos, headers);
        productoDos = restTemplate.exchange("/api/v1/productos", HttpMethod.POST, payLoadProducto2, Producto.class).getBody();
        
        assertEquals(10, productoUno.getCantidad(), 0);
        assertEquals(6, productoDos.getCantidad(), 0);
        
        HttpEntity<Void> token = new HttpEntity<>(headers);
                
        RenglonFactura renglonUno = restTemplate.exchange("/api/v1/facturas/renglon?"
                            + "idProducto=" + productoUno.getId_Producto()
                            + "&tipoComprobante=" + 'B'
                            + "&movimiento=" + Movimiento.VENTA
                            + "&cantidad=" + 5
                            + "&descuentoPorcentaje=" + 0, HttpMethod.GET, token,
                            RenglonFactura.class).getBody();
        

        RenglonFactura renglonDos = restTemplate.exchange("/api/v1/facturas/renglon?"
                            + "idProducto=" + productoDos.getId_Producto()
                            + "&tipoComprobante=" + 'B'
                            + "&movimiento=" + Movimiento.VENTA
                            + "&cantidad=" + 2
                            + "&descuentoPorcentaje=" + 0, HttpMethod.GET, token,
                            RenglonFactura.class).getBody();
        
        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglonUno);
        renglones.add(renglonDos);
        
        Factura facturaB = new FacturaVentaBuilder()
                                .withTipoFactura('B')
                                .withCliente(cliente)
                                .withEmpresa(empresa)
                                .withTransportista(transportista)
                                .withUsuario(restTemplate.exchange("/api/v1/usuarios/1", HttpMethod.GET, token, Usuario.class).getBody())
                               // .withRenglones(renglones)
                                .build();
        facturaB.setRenglones(renglones);
        
        HttpEntity<Factura> payLoadFacturaVenta = new HttpEntity<>(facturaB, headers);
        
//        try {
//            mvc.perform(MockMvcRequestBuilders.get("/validation-success")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .header("Authorization", headers.containsValue("Authorization"))
//                    .content(this.json.write(facturaB).getJson().getBytes()));
//        } catch (IOException ex) {
//            Logger.getLogger(FacturaBIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(FacturaBIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        Factura[] factura = restTemplate.exchange("/api/v1/facturas", HttpMethod.POST, payLoadFacturaVenta, Factura[].class).getBody();
        factura = restTemplate.exchange("/api/v1/facturas/1", HttpMethod.GET, token, Factura[].class).getBody();

    }
    
}
