package sic.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sic.builder.ConfiguracionDelSistemaBuilder;
import sic.builder.FacturaVentaBuilder;
import sic.modelo.FacturaVenta;
import sic.service.impl.AfipServiceImpl;
import sic.service.impl.AfipWebServiceSOAPClient;
import sic.service.impl.ConfiguracionDelSistemaServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AfipIntegrationTest {
    
    @Autowired
    private AfipWebServiceSOAPClient afipWebServiceSOAPClient;
    
    @Mock
    private ConfiguracionDelSistemaServiceImpl configuracionDelSistemaService;
    
    //@InjectMocks
    @Autowired
    private AfipServiceImpl afipService;
    
    @Test    
    public void testAutorizarFacturaVenta() {
        
        // Save Empresa con Localidad, Provincia y Pais
        // Save Configuracion del Sistema
        // Save FacturaVenta con Transportista, Renglones, Cliente
        
        
        FacturaVenta fv = new FacturaVentaBuilder().build();        
        when(configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(fv.getEmpresa()))
                .thenReturn(new ConfiguracionDelSistemaBuilder().build());                                
        afipService.autorizarFacturaVenta(fv);
    }

}
