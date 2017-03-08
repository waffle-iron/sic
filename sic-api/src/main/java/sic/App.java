package sic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sic.service.IUsuarioService;

@SpringBootApplication
@EnableScheduling
public class App extends WebMvcConfigurerAdapter {
    
    @Autowired
    IUsuarioService usuarioService;
    
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(usuarioService);
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
}