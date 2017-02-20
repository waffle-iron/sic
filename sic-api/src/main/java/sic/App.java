package sic;

import afip.wsfe.wsdl.FEAuthRequest;
import afip.wsfe.wsdl.FECAERequest;
import afip.wsfe.wsdl.FECAESolicitar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sic.modelo.AfipWSAACredencial;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;
import sic.service.impl.AfipServiceImpl;
import sic.service.impl.AfipWebServiceSOAPClient;

@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {
    
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
    
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml        
        marshaller.setContextPaths("afip.wsaa.wsdl", "afip.wsfe.wsdl");
        return marshaller;
    }
    
    @Bean
    public AfipWebServiceSOAPClient afipWebServiceSOAPClient(Jaxb2Marshaller marshaller) {
        AfipWebServiceSOAPClient afipClient = new AfipWebServiceSOAPClient();
        afipClient.setMarshaller(marshaller);
        afipClient.setUnmarshaller(marshaller);
        return afipClient;
    }   
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/*/login");
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    
    // Mover a AfipService.java
    @Bean
    public CommandLineRunner lookup(AfipServiceImpl afipServiceImpl) {
        return args -> {
            AfipWSAACredencial afipCredencial = afipServiceImpl.getAfipWSAACredencial("wsfe");      
            //auth
            FEAuthRequest feAuthRequest = new FEAuthRequest();            
            feAuthRequest.setCuit(this.construirFacturaDePrueba().getEmpresa().getCuip());
            feAuthRequest.setSign(afipCredencial.getSign());
            feAuthRequest.setToken(afipCredencial.getToken());
            //FECAE req
            FECAERequest fecaeRequest = new FECAERequest();
            
            //--------
            FECAESolicitar fecaeSolicitud = new FECAESolicitar();
            fecaeSolicitud.setAuth(feAuthRequest);
            fecaeSolicitud.setFeCAEReq(fecaeRequest);            
            afipServiceImpl.autorizarFacturaVenta(afipCredencial, this.construirFacturaDePrueba());
        };
    }
    
    private FacturaVenta construirFacturaDePrueba() {
        FacturaVenta factura = new FacturaVenta();
        Empresa empresa = new Empresa();
        empresa.setCuip(30712391215L);
        factura.setEmpresa(empresa);
        return factura;
    }
}