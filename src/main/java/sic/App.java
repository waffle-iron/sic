package sic;

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
        //ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        GUI_LogIn gui_LogIn = new GUI_LogIn();
        gui_LogIn.setVisible(true);
    }   
}