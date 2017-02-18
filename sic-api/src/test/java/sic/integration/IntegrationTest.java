package sic.integration;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import sic.builder.ClienteBuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.RenglonPedidoBuilder;
import sic.modelo.RenglonPedido;
import sic.modelo.Usuario;
import sic.modelo.Pedido;
import sic.modelo.dto.PedidoDTO;
import sic.modelo.dto.UsuarioDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test    
    public void testUsuario() {        
        UsuarioDTO usuarioDTO = this.construirUsuarioDTO();
        restTemplate.postForEntity("/api/v1/usuarios", usuarioDTO, Usuario.class);
        Usuario usuarioGuardado = restTemplate.getForEntity("/api/v1/usuarios/1", Usuario.class).getBody();
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
