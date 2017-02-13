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
import sic.builder.ProductoBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Credencial;
import sic.modelo.Producto;
import sic.modelo.Usuario;
import sic.repository.EmpresaRepository;
import sic.repository.ProductoRepository;
import sic.service.IEmpresaService;
import sic.service.IProductoService;
import sic.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DataJpaTest
@Transactional
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
    
    @TestConfiguration
    static class Config {

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .requestFactory(new HttpComponentsClientHttpRequestFactory());
        }
    }
    
    @Before
    public void Setup() {
        Usuario user = new UsuarioBuilder().build();
        usuarioService.guardar(user);
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
        Producto guardar = (new ProductoBuilder()).withIva_porcentaje(21.0).build();
        Credencial cred = new Credencial("jose", "jose");
        String token = this.restTemplate.postForObject("/api/v1/login", cred, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Producto> entity = new HttpEntity<>(guardar, headers);
////        restTemplate.postForObject("api/v1/productos", guardar, Producto.class);
////        System.out.println(restTemplate.exchange("api/v1/productos", HttpMethod.POST, entity, Producto.class));
//        service.guardar(guardar);

//        Producto test = this.restTemplate.postForObject("/productos", 
//                         guardar, Producto.class);
//        System.out.print(test.toString());
    }
    
}
