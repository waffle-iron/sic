package sic.integration;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import sic.builder.ClienteBuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.RenglonPedidoBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Credencial;
import sic.modelo.RenglonPedido;
import sic.modelo.Usuario;
import sic.modelo.Pedido;
import sic.modelo.dto.PedidoDTO;
import sic.modelo.dto.UsuarioDTO;
import sic.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
            
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private String token;
    
    @Before
    public void setup() {
        String md5Test = "098f6bcd4621d373cade4e832627b4f6";
        usuarioRepository.save(new UsuarioBuilder().withNombre("test").withPassword(md5Test).build());
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((ClientHttpRequestInterceptor) (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        });
        restTemplate.getRestTemplate().setInterceptors(interceptors);
    }

    @Test    
    public void testUsuario() {
        this.token = restTemplate.postForEntity("/api/v1/login", new Credencial("test", "test"), String.class).getBody();
        UsuarioDTO usuarioDTO = this.construirUsuarioDTO();
        restTemplate.postForEntity("/api/v1/usuarios", usuarioDTO, Usuario.class);
        Usuario usuarioGuardado = restTemplate.getForEntity("/api/v1/usuarios/busqueda?nombre="+usuarioDTO.getNombre(), Usuario.class).getBody();
        Assert.assertEquals(usuarioDTO.getNombre(), usuarioGuardado.getNombre());
    }
    
    // metodo auxiliar para construir el DTO (Data Transfer Object)
    private UsuarioDTO construirUsuarioDTO() {
        // usa Usuario del paquete modelo.dto, en vez de Usuario de builder para evitar el @JsonProperty(access = Access.WRITE_ONLY)
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Jhon");
        usuarioDTO.setPassword("test");
        usuarioDTO.setPermisosAdministrador(true);
        return usuarioDTO;
    }
    
    
    // *************** FALLA EL TEST POR LAS RELACIONES QUE NO ESTAN PERSISTIDAS, EJ: EMPRESA **************
    @Test
    public void testPedido() {
        restTemplate.postForEntity("/api/v1/pedidos", this.construirPedidoDTO(), Pedido.class);
    }
    
    // metodo auxiliar para construir el DTO (Data Transfer Object)
    private PedidoDTO construirPedidoDTO() {
        // usa Pedido del paquete modelo.dto, en vez de Pedido de builder para evitar el @JsonProperty(access = Access.WRITE_ONLY)
        // se puede usar los builders para sus relaciones
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(new ClienteBuilder().build());
        pedidoDTO.setEmpresa(new EmpresaBuilder().build());
        List<RenglonPedido> renglonesPedido = new ArrayList<>();
        renglonesPedido.add(new RenglonPedidoBuilder().build());
        pedidoDTO.setRenglones(renglonesPedido);
        // and so on...        
        return pedidoDTO;
    }

}
