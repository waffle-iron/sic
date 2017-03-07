package sic.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit4.SpringRunner;
import sic.builder.ConfiguracionDelSistemaBuilder;
import sic.builder.FacturaVentaBuilder;
import sic.repository.ConfiguracionDelSistemaRepository;

@RunWith(SpringRunner.class)
public class AfipServiceImplTest {
    
    @Mock
    private ConfiguracionDelSistemaRepository configuracionDelSistemaRepository;
    
    @InjectMocks
    private ConfiguracionDelSistemaServiceImpl configuracionDelSistemaService;
         
    @Mock
    private AfipWebServiceSOAPClient afipWebServiceSOAPClient;
    
    @InjectMocks
    private AfipServiceImpl afipService;
    
    @Test    
    public void testAutorizarFacturaVenta() {
        when(configuracionDelSistemaRepository.findOne(1L)).thenReturn(new ConfiguracionDelSistemaBuilder().build());
        when(configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getPathCertificadoAfip())
                .thenReturn("certs/GloboDistribucionesTesting.p12");
        when(configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getFirmanteCertificadoAfip())
                .thenReturn("globo");
        when(configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getPasswordCertificadoAfip())
                .thenReturn("globo123");
        
        afipService.autorizarFacturaVenta(new FacturaVentaBuilder().build());
    }

}
