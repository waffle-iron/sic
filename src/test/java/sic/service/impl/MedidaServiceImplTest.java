package sic.service.impl;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import sic.builder.EmpresaBuilder;
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
    private Medida medida;
    private Medida medidaParaTest;
        
    @Mock
    private IMedidaRepository medidaRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        medida = Mockito.mock(Medida.class);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenNombreVacio() {
        when(medidaRepository.getMedidaPorNombre("", empresa)).thenReturn(medidaParaTest);
        medida.setNombre("");
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenNombreDuplicadoAlta() {
        when(medidaRepository.getMedidaPorNombre("unidad", empresa)).thenReturn(medidaParaTest);
        medida.setNombre("unidad");
        medida.setEmpresa(empresa);
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenNombreDuplicadoActualizacion() {
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
