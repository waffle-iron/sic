package sic.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FacturacionIntegrationTest {
    
    @Before
    public void setup() {
        
    }
    
    @Test
    public void testFacturarConComprobanteB() {
        
    }
    
    @Test
    public void testFacturarPedido() {
        
    }
    
}
