package sic.service.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;
import sic.builder.ClienteBuilder;
import sic.builder.CondicionIVABuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.LocalidadBuilder;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.Localidad;
import sic.repository.IClienteRepository;
import sic.service.IClienteService;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;

@Service
public class ClienteServiceImplTest {

    private IClienteService clienteService;
//    private Empresa empresa;
//    private Cliente cliente;
//    private Cliente clienteDuplicado;

    @Mock
    private IClienteRepository clienteRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        empresa = EmpresaBuilder.build();
//        cliente = Cliente.builder().build();
//        clienteDuplicado = Cliente.builder().build();
    }

    @Test
    public void shouldSetClientePredeterminado() {
        when(clienteRepository.getClientePredeterminado((new EmpresaBuilder()).build())).thenReturn((new ClienteBuilder()).build());
        clienteService = new ClienteServiceImpl(clienteRepository);
        Cliente resultadoEsperado = (new ClienteBuilder()).build();
        Cliente resultadoObtenido = clienteService.getClientePredeterminado((new EmpresaBuilder()).build());
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenEmailInvalido() {
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, new ClienteBuilder().withEmail("@@.com").build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenCondicionIVAesNull() {
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, new ClienteBuilder()
                                        .withEmail("emailValido@email.com")
                                        .withRazonSocial("razon Social")
                                        .withCondicionIVA(null)
                                        .build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenLocalidadEsNull() {
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, (new ClienteBuilder())
                                        .withEmail("emailValido@email.com")
                                        .withRazonSocial("razon Social")
                                        .withLocalidad(null)
                                        .withCondicionIVA((new CondicionIVABuilder()).build())
                                        .build());
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenEmpresaEsNull() {
        Cliente cliente = (new ClienteBuilder()).withEmail("emailValido@email.com").withRazonSocial("razon Social")
                          .withCondicionIVA(new CondicionIVABuilder().build())
                          .withLocalidad(new LocalidadBuilder().build())
                          .withEmpresa(null)
                          .build();
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenIdFiscalDuplicadoEnAlta() {
        Cliente cliente = (new ClienteBuilder()).build();
        Cliente clienteDuplicado = (new ClienteBuilder()).build();
        when(clienteRepository.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
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
        when(clienteRepository.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenRazonSocialDuplicadaEnAlta() {
        Cliente cliente = new ClienteBuilder()
                .withEmail("emailValido@email.com")
                .withRazonSocial("razon social")
                .withCondicionIVA(new CondicionIVABuilder().build())
                .withLocalidad(new LocalidadBuilder().build())
                .withEmpresa(new EmpresaBuilder().build())
                .withId_Fiscal("23111111119")
                .withId_Cliente(Long.MIN_VALUE)
                .build();
        Cliente clienteDuplicado = new ClienteBuilder()
                .withEmail("emailValido@email.com")
                .withRazonSocial("razon social")
                .withCondicionIVA(new CondicionIVABuilder().build())
                .withLocalidad(new LocalidadBuilder().build())
                .withEmpresa(new EmpresaBuilder().build())
                .withId_Fiscal("23111111119")
                .withId_Cliente(Long.MIN_VALUE)
                .build();
        when(clienteRepository.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenRazonSocialDuplicadaEnActualizacion() {
        Cliente cliente = new ClienteBuilder()
                .withId_Cliente(2L)
                .withRazonSocial("razon social")
                .build();
        Cliente clienteDuplicado = new ClienteBuilder()
                .withId_Cliente(4L)
                .withRazonSocial("razon social")
                .build();
        when(clienteRepository.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }
}
