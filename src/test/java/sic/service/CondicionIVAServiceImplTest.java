package sic.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.impl.CondicionDeIVAServiceImpl;

public class CondicionIVAServiceImplTest {

    private final CondicionIVA condicionIVA = new CondicionIVA();
    private final CondicionIVA condicionIVADuplicada = new CondicionIVA();
    private ICondicionIVAService condicionIVAService;

    @Mock
    private ICondicionIVARepository condicionIVARepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoAlta() {
        condicionIVA.setNombre("discrimina IVA");
        when(condicionIVARepository.getCondicionIVAPorNombre(condicionIVA.getNombre())).thenReturn(condicionIVA);
        condicionIVAService = new CondicionDeIVAServiceImpl(condicionIVARepository);
        condicionIVAService.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
    }

    @Test(expected = ServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoActualizacion() {
        condicionIVA.setNombre("discrimina IVA");
        condicionIVA.setId_CondicionIVA(Long.MIN_VALUE);
        condicionIVADuplicada.setNombre("discimina IVA");
        condicionIVADuplicada.setId_CondicionIVA(Long.MAX_VALUE);
        when(condicionIVARepository.getCondicionIVAPorNombre(condicionIVADuplicada.getNombre())).thenReturn(condicionIVA);
        condicionIVAService = new CondicionDeIVAServiceImpl(condicionIVARepository);
        condicionIVAService.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVADuplicada);
    }

}
