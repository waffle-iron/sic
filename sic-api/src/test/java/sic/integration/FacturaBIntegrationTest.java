package sic.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.FormaDePagoBuilder;
import sic.builder.LocalidadBuilder;
import sic.builder.ProductoBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Credencial;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.modelo.Localidad;
import sic.modelo.Pais;
import sic.modelo.Producto;
import sic.modelo.Provincia;
import sic.modelo.Usuario;
import sic.repository.EmpresaRepository;
import sic.repository.ProductoRepository;
import sic.service.IEmpresaService;
import sic.service.IProductoService;
import sic.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DataJpaTest
////@Transactional
public class FacturaBIntegrationTest {
    
    @Autowired
    private IProductoService service;
    
    @Autowired 
    private IEmpresaService empresaService;
    
    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private ProductoRepository repository;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private HttpHeaders headers = new HttpHeaders();
//    
//    @TestConfiguration
//    static class Config {
//
//        @Bean
//        public RestTemplateBuilder restTemplateBuilder() {
//            return new RestTemplateBuilder()
//                    .requestFactory(new HttpComponentsClientHttpRequestFactory());
//        }
//    }
    
    @Before
    public void setup() {
        Usuario user = new UsuarioBuilder().build();
        usuarioService.guardar(user);
        
        Credencial cred = new Credencial("Daenerys Targaryen", "LaQueNoArde");
        String token = this.restTemplate.postForObject("/api/v1/login", cred, String.class);
        headers.set("Authorization", "Bearer " + token);
        

//        Credencial cred = new Credencial("jose", "jose");
//        this.restTemplate.postForEntity("/api/v1/login", cred, String.class);
//        Empresa empresa = new EmpresaBuilder().build();
////        empresaService.guardar(empresa);
////        ResponseEntity<Empresa> asd = this.restTemplate.getForEntity("/api/v1/empresas/idEmpresa=1", Empresa.class);
//        Empresa testEmpresa = this.restTemplate.postForObject("/api/v1/empresas", empresa, Empresa.class);
//        System.err.println(testEmpresa.toString());
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
////        restTemplate.exchange("/api/v1/localidad", HttpMethod.POST, payLoadLocalidad, Localidad.class);
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
        //
        Cliente cliente = new ClienteBuilder()
                          .withEmpresa(empresa)
                          .withCondicionIVA(empresa.getCondicionIVA())
                          .withLocalidad(empresa.getLocalidad())
                          .withPredeterminado(true)
                          .build();
        HttpEntity<Cliente> payLoadCliente = new HttpEntity<>(cliente, headers);
        cliente = restTemplate.exchange("/api/v1/clientes", HttpMethod.POST, payLoadCliente, Cliente.class).getBody();
        
        Producto producto1 = (new ProductoBuilder()).withIva_porcentaje(21.0).build();
        Producto producto2 = (new ProductoBuilder()).withIva_porcentaje(10.5).build();
//        service.guardar(guardar);

//        Producto test = this.restTemplate.postForObject("/productos", 
//                         guardar, Producto.class);
//        System.out.print(test.toString());
    }
    
}
