package sic.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;

@RunWith(MockitoJUnitRunner.class)
public class CondicionIVAServiceImplTest {

    private CondicionIVA condicionIVA;
    private CondicionIVA condicionIVADuplicada;

    @Mock
    private ICondicionIVARepository condicionIVARepository;
    
    @InjectMocks
    private CondicionDeIVAServiceImpl condicionDeIVAServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        condicionIVA = new CondicionIVA();
        condicionIVADuplicada = new CondicionIVA();
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoAlta() {
        condicionIVA.setNombre("discrimina IVA");
        when(condicionDeIVAServiceImpl.getCondicionIVAPorNombre(condicionIVA.getNombre())).thenReturn(condicionIVA);
        condicionDeIVAServiceImpl.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldValidarOperacionWhenDuplicadoActualizacion() {
        condicionIVA.setNombre("discrimina IVA");
        condicionIVA.setId_CondicionIVA(Long.MIN_VALUE);
        condicionIVADuplicada.setNombre("discimina IVA");
        condicionIVADuplicada.setId_CondicionIVA(Long.MAX_VALUE);
        when(condicionDeIVAServiceImpl.getCondicionIVAPorNombre(condicionIVADuplicada.getNombre())).thenReturn(condicionIVA);
        condicionDeIVAServiceImpl.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVADuplicada);
    }

}
