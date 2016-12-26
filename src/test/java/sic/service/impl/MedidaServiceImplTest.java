package sic.service.impl;

import java.util.ResourceBundle;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import sic.builder.MedidaBuilder;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.repository.IMedidaRepository;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;

@RunWith(MockitoJUnitRunner.class)
public class MedidaServiceImplTest {

    @InjectMocks
    private MedidaServiceImpl medidaService;
    private Empresa empresa;
        
    @Mock
    private IMedidaRepository medidaRepository;
    private Medida medida = new MedidaBuilder().build();
    private Medida medidaParaTest = new MedidaBuilder().build();
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldValidarOperacionWhenNombreVacio() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_medida_vacio_nombre"));
        when(medidaRepository.getMedidaPorNombre("", empresa)).thenReturn(medidaParaTest);
        medida.setNombre("");
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test
    public void shouldValidarOperacionWhenNombreDuplicadoAlta() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_medida_duplicada_nombre"));
        when(medidaRepository.getMedidaPorNombre("unidad", empresa)).thenReturn(medidaParaTest);
        medida.setNombre("unidad");
        medida.setEmpresa(empresa);
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test
    public void shouldValidarOperacionWhenNombreDuplicadoActualizacion() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_medida_duplicada_nombre"));
        when(medidaRepository.getMedidaPorNombre("metro", empresa)).thenReturn(medidaParaTest);
        medida.setNombre("metro");
        medida.setEmpresa(empresa);
        medida.setId_Medida((long) 1);
        when(medidaService.getMedidaPorNombre("metro", empresa)).thenReturn(medida);
        Medida medidaDuplicada = new Medida();
        medidaDuplicada.setNombre("metro");
        medidaDuplicada.setEmpresa(empresa);
        medidaDuplicada.setId_Medida((long) 2);
        medidaService.validarOperacion(TipoDeOperacion.ACTUALIZACION, medidaDuplicada);
    }

}
