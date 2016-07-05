package sic.service;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.Localidad;
import sic.repository.IClienteRepository;
import sic.service.impl.ClienteServiceImpl;

@Service
public class ClienteServiceImplTest {

    private IClienteService clienteService;
    private final Empresa empresa = new Empresa();
    private final Cliente cliente = new Cliente();
    private final Cliente clienteDuplicado = new Cliente();

    @Mock
    private IClienteRepository clienteRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSetClientePredeterminado() {
        when(clienteRepository.getClientePredeterminado(empresa)).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        Cliente resultadoEsperado = cliente;
        Cliente resultadoObtenido = clienteService.getClientePredeterminado(empresa);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenEmailValido() {
        cliente.setEmail("");
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenCondicionIVANoNull() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(null);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenLocalidadNoNull() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(null);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenEmpresaNoNull() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(new Localidad());
        cliente.setEmpresa(null);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ELIMINACION, cliente);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoAltaIdFiscal() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(new Localidad());
        cliente.setEmpresa(new Empresa());
        cliente.setId_Fiscal("id Fiscal Cliente");
        clienteDuplicado.setEmail("emailValido@email.com");
        clienteDuplicado.setRazonSocial("razon Social");
        clienteDuplicado.setCondicionIVA(new CondicionIVA());
        clienteDuplicado.setLocalidad(new Localidad());
        clienteDuplicado.setEmpresa(new Empresa());
        clienteDuplicado.setId_Fiscal("id Fiscal Cliente");
        when(clienteRepository.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoActualizacionIdFiscal() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(new Localidad());
        cliente.setEmpresa(new Empresa());
        cliente.setId_Fiscal("id Fiscal Cliente");
        cliente.setId_Cliente(Long.MIN_VALUE);
        clienteDuplicado.setEmail("emailValido@email.com");
        clienteDuplicado.setRazonSocial("razon Social");
        clienteDuplicado.setCondicionIVA(new CondicionIVA());
        clienteDuplicado.setLocalidad(new Localidad());
        clienteDuplicado.setEmpresa(new Empresa());
        clienteDuplicado.setId_Fiscal("id Fiscal Cliente");
        clienteDuplicado.setId_Cliente(Long.MAX_VALUE);
        when(clienteRepository.getClientePorId_Fiscal(cliente.getId_Fiscal(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoAltaRazonSocial() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(new Localidad());
        cliente.setEmpresa(new Empresa());
        cliente.setId_Fiscal("id Fiscal Cliente");
        cliente.setId_Cliente(Long.MIN_VALUE);
        clienteDuplicado.setEmail("emailValido@email.com");
        clienteDuplicado.setRazonSocial("razon Social");
        clienteDuplicado.setCondicionIVA(new CondicionIVA());
        clienteDuplicado.setLocalidad(new Localidad());
        clienteDuplicado.setEmpresa(new Empresa());
        clienteDuplicado.setId_Fiscal("id Fiscal Cliente");
        clienteDuplicado.setId_Cliente(Long.MAX_VALUE);
        when(clienteRepository.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ALTA, clienteDuplicado);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoActualizacionRazonSocial() {
        cliente.setEmail("emailValido@email.com");
        cliente.setRazonSocial("razon Social");
        cliente.setCondicionIVA(new CondicionIVA());
        cliente.setLocalidad(new Localidad());
        cliente.setEmpresa(new Empresa());
        cliente.setId_Fiscal("id Fiscal Cliente");
        cliente.setId_Cliente(Long.MIN_VALUE);
        clienteDuplicado.setEmail("emailValido@email.com");
        clienteDuplicado.setRazonSocial("razon Social");
        clienteDuplicado.setCondicionIVA(new CondicionIVA());
        clienteDuplicado.setLocalidad(new Localidad());
        clienteDuplicado.setEmpresa(new Empresa());
        clienteDuplicado.setId_Fiscal("id Fiscal Cliente");
        clienteDuplicado.setId_Cliente(Long.MAX_VALUE);
        when(clienteRepository.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa())).thenReturn(cliente);
        clienteService = new ClienteServiceImpl(clienteRepository);
        clienteService.validarOperacion(TipoDeOperacion.ACTUALIZACION, clienteDuplicado);
    }
}
