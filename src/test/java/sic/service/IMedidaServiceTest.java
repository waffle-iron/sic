package sic.service;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.repository.IMedidaRepository;
import sic.repository.jpa.MedidaRepositoryJPAImpl;
import sic.service.impl.MedidaServiceImpl;

public class IMedidaServiceTest {

    private MedidaServiceImpl medidaService;

    @Mock
    private Medida medidaParaTest;
    @Mock
    private IMedidaRepository medidaRepository;

    private final Empresa empresa = new Empresa();

    private final Medida medida = new Medida();

    public IMedidaServiceTest() {

    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        medidaRepository = Mockito.mock(MedidaRepositoryJPAImpl.class);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenNombreVacio() {
        when(medidaRepository.getMedidaPorNombre("", empresa)).thenReturn(medidaParaTest);
        medidaService = new MedidaServiceImpl(medidaRepository);
        medida.setNombre("");
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenNombreDuplicadoAlta() {
        when(medidaRepository.getMedidaPorNombre("unidad", empresa)).thenReturn(medidaParaTest);
        medidaService = new MedidaServiceImpl(medidaRepository);
        medida.setNombre("unidad");
        medida.setEmpresa(empresa);
        medidaService.validarOperacion(TipoDeOperacion.ALTA, medida);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenNombreDuplicadoActualizacion() {
        when(medidaRepository.getMedidaPorNombre("metro", empresa)).thenReturn(medidaParaTest);
        medidaService = new MedidaServiceImpl(medidaRepository);
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
