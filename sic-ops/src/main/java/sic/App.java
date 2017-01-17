package sic;

import org.springframework.boot.SpringApplication;
import sic.vista.swing.GUI_LogIn;

public class App {

    public static void main(String[] args) {                              
        SpringApplication app = new SpringApplication(App.class);
        app.setHeadless(false);
        app.run(args);
        GUI_LogIn gui_LogIn = new GUI_LogIn();
        gui_LogIn.setVisible(true);
    }
}
