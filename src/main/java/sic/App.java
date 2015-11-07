package sic;

import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sic.vista.swing.GUI_LogIn;

/**
 * Esta es la clase principal que arranca la aplicación.
 */
@Configuration
@ComponentScan
public class App {

    public static void main(String[] args) {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));                   
        GUI_LogIn gui_LogIn = new GUI_LogIn();
        gui_LogIn.setVisible(true);
    }   
}