package sic.service.impl;

import java.util.ResourceBundle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import sic.builder.CondicionIVABuilder;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;

@RunWith(MockitoJUnitRunner.class)
public class CondicionIVAServiceImplTest {

    @Mock
    private ICondicionIVARepository condicionIVARepository;
    
    @InjectMocks
    private CondicionDeIVAServiceImpl condicionDeIVAServiceImpl;
    private CondicionIVA condicionIVA = new CondicionIVABuilder().build();
    private CondicionIVA condicionIVADuplicada = new CondicionIVABuilder().build();
   
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldValidarOperacionWhenDuplicadoAlta() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_condicionIVA_nombre_duplicado"));
        when(condicionDeIVAServiceImpl.getCondicionIVAPorNombre(condicionIVA.getNombre())).thenReturn(condicionIVA);
        condicionDeIVAServiceImpl.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
    }

    @Test
    public void shouldValidarOperacionWhenDuplicadoActualizacion() {
        thrown.expect(BusinessServiceException.class);
        thrown.expectMessage(ResourceBundle.getBundle("Mensajes").getString("mensaje_condicionIVA_nombre_duplicado"));
        condicionIVA.setNombre("discrimina IVA");
        condicionIVA.setId_CondicionIVA(Long.MIN_VALUE);
        condicionIVADuplicada.setNombre("discimina IVA");
        condicionIVADuplicada.setId_CondicionIVA(Long.MAX_VALUE);
        when(condicionDeIVAServiceImpl.getCondicionIVAPorNombre(condicionIVADuplicada.getNombre())).thenReturn(condicionIVA);
        condicionDeIVAServiceImpl.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVADuplicada);
    }

}
