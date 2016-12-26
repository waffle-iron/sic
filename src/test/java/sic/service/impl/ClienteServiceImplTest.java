package sic.service.impl;

import java.util.ResourceBundle;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.LocalidadBuilder;
import sic.modelo.Cliente;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;
import sic.repository.jpa.ClienteRepositoryJPAImpl;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepositoryJPAImpl clienteRepositoryJPAImpl;
    
    @InjectMocks
    private ClienteServiceImpl clienteServiceImpl;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldSetClientePredeterminado() {
        Cliente resultadoEsperado = new ClienteBuilder().build();
        clienteServiceImpl.setClientePredeterminado(resultadoEsperado);
        when(clienteRepositoryJPAImpl.getClientePredeterminado((new EmpresaBuilder()).build()))
                                     .thenReturn((new ClienteBuilder()).build());
        Cliente resultadoObtenido = clienteServiceImpl.getClientePredeterminado((new EmpresaBuilder()).build());
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void shouldValidarOperacionWhenEmailInvalido() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_cliente_email_invalido"));
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ELIMINACION, new ClienteBuilder().withEmail("@@.com").build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenCondicionIVAesNull() {
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ELIMINACION, new ClienteBuilder()
                .withEmail("soporte@gmail.com")
                .withRazonSocial("Ferreteria Julian")
                .withCondicionIVA(null)
                .build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenLocalidadEsNull() {        
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ELIMINACION, new ClienteBuilder()
                .withEmail("soporte@gmail.com")
                .withRazonSocial("Ferreteria Julian")
                .withLocalidad(null)
                .withCondicionIVA(new CondicionIVABuilder().build())
                .build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenEmpresaEsNull() {
        Cliente cliente = new ClienteBuilder()
                .withEmail("soporte@gmail.com")
                .withRazonSocial("Ferreteria Julian")
                .withCondicionIVA(new CondicionIVABuilder().build())
                .withLocalidad(new LocalidadBuilder().build())
                .withEmpresa(null)
                .build();       
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenIdFiscalDuplicadoEnAlta() {
        Cliente cliente = new ClienteBuilder().build();
        Cliente clienteDuplicado = new ClienteBuilder().build();
        when(clienteRepositoryJPAImpl.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa()))
                .thenReturn(cliente);        
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenIdFiscalDuplicadoEnActualizacion() {
        Cliente cliente = new ClienteBuilder()
                .withId_Cliente(7L)
                .withRazonSocial("Merceria los dos botones")
                .withId_Fiscal("23111111119")
                .build();
        Cliente clienteDuplicado = new ClienteBuilder()
                .withId_Cliente(2L)
                .withRazonSocial("Merceria los dos botones")
                .withId_Fiscal("23111111119")
                .build();
        when(clienteRepositoryJPAImpl.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa()))
                .thenReturn(cliente);        
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenRazonSocialDuplicadaEnAlta() {
        Cliente cliente = new ClienteBuilder()
                .withEmail("soporte@gmail.com")
                .withRazonSocial("Ferreteria Julian")
                .withCondicionIVA(new CondicionIVABuilder().build())
                .withLocalidad(new LocalidadBuilder().build())
                .withEmpresa(new EmpresaBuilder().build())
                .withId_Fiscal("23111111119")
                .withId_Cliente(Long.MIN_VALUE)
                .build();
        Cliente clienteDuplicado = new ClienteBuilder()
                .withEmail("soporte@gmail.com")
                .withRazonSocial("Ferreteria Julian")
                .withCondicionIVA(new CondicionIVABuilder().build())
                .withLocalidad(new LocalidadBuilder().build())
                .withEmpresa(new EmpresaBuilder().build())
                .withId_Fiscal("23111111119")
                .withId_Cliente(Long.MIN_VALUE)
                .build();
        when(clienteRepositoryJPAImpl.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa()))
                .thenReturn(cliente);        
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenRazonSocialDuplicadaEnActualizacion() {
        Cliente cliente = new ClienteBuilder()
                .withId_Cliente(2L)
                .withRazonSocial("Ferreteria Julian")
                .build();
        Cliente clienteDuplicado = new ClienteBuilder()
                .withId_Cliente(4L)
                .withRazonSocial("Ferreteria Julian")
                .build();
        when(clienteRepositoryJPAImpl.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa()))
                .thenReturn(cliente);        
        clienteServiceImpl.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }
}
