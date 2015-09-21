package sic.vista.swing;

import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import sic.repository.XMLException;
import sic.modelo.DatosConexion;
import sic.modelo.XMLFileConfig;
import sic.modelo.Usuario;
import sic.service.ConexionService;
import sic.service.ConfiguracionDelSistemaService;
import sic.service.ServiceException;
import sic.service.UsuarioService;
import sic.util.Validator;
import sic.vista.swing.administracion.GUI_Principal;
import sic.vista.swing.tpv.GUI_PrincipalTPV;

public class GUI_LogIn extends JFrame {

    private boolean configDesplegada;
    private Usuario usuario;
    private UsuarioService usuarioService = new UsuarioService();
    private ConfiguracionDelSistemaService configuracionService = new ConfiguracionDelSistemaService();
    private ConexionService conexionService = new ConexionService();
    private static final Logger log = Logger.getLogger(GUI_LogIn.class.getPackage().getName());

    public GUI_LogIn() {
        this.initComponents();
        this.setTitle("S.I.C. " + ResourceBundle.getBundle("Mensajes").getString("version"));
        ImageIcon iconoVentana = new ImageIcon(GUI_LogIn.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
        this.setSize(290, 150);
        this.setLocationRelativeTo(null);
        txt_Host.setEnabled(false);
        txt_Puerto.setEnabled(false);
        txt_BD.setEnabled(false);
        btn_Guardar.setEnabled(false);
        this.cargarDatosConexion();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_LogIn().setVisible(true);
            }
        });
    }

    private void validarUsuario() {
        if (!txt_Usuario.getText().trim().equals("") || txt_Contrasenia.getPassword().length != 0) {
            try {
                usuario = usuarioService.validarUsuario(txt_Usuario.getText().trim(), new String(txt_Contrasenia.getPassword()));
                usuarioService.setUsuarioActivo(usuario);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (PersistenceException ex) {
                log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un usuario y contraseña para poder continuar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ingresar() {
        if (usuario != null) {
            this.abrirGUI_Principal();
        }
    }

    private void desplegarPlegarConfig() {
        if (configDesplegada == true) {
            this.setSize(290, 150);
            txt_Host.setEnabled(false);
            txt_Puerto.setEnabled(false);
            txt_BD.setEnabled(false);
            btn_Guardar.setEnabled(false);
            configDesplegada = false;
            btn_Ingresar.setEnabled(true);
            btn_Salir.setEnabled(true);
            txt_Usuario.setEnabled(true);
            txt_Contrasenia.setEnabled(true);
            txt_Usuario.requestFocus();
        } else {
            this.setSize(290, 270);
            configDesplegada = true;
            txt_Host.setEnabled(true);
            txt_Puerto.setEnabled(true);
            txt_BD.setEnabled(true);
            btn_Guardar.setEnabled(true);
            btn_Ingresar.setEnabled(false);
            btn_Salir.setEnabled(false);
            txt_Usuario.setEnabled(false);
            txt_Contrasenia.setEnabled(false);
            txt_Host.requestFocus();
            this.cargarDatosConexion();
        }
    }

    private void cargarDatosConexion() {
        try {
            configuracionService.leerXML();
            txt_Host.setText(XMLFileConfig.getHostConexion());
            if (XMLFileConfig.getPuertoConexion() == 0) {
                txt_Puerto.setText("");
            } else {
                txt_Puerto.setText(String.valueOf(XMLFileConfig.getPuertoConexion()));
            }
            txt_BD.setText(XMLFileConfig.getBdConexion());

        } catch (XMLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirGUI_Principal() {
        new GUI_Principal().setVisible(true);
        this.dispose();
    }
    /*
     private void abrirGUI_TPV() {
     new GUI_PrincipalTPV().setVisible(true);
     this.dispose();
     }**/

    private void capturaTeclaEnter(KeyEvent evt) {
        //tecla ENTER
        if (evt.getKeyCode() == 10) {
            btn_IngresarActionPerformed(null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsuario = new javax.swing.JLabel();
        lblContrasenia = new javax.swing.JLabel();
        txt_Usuario = new javax.swing.JTextField();
        btn_Salir = new javax.swing.JButton();
        btn_Ingresar = new javax.swing.JButton();
        btn_Configuracion = new javax.swing.JButton();
        lblHost = new javax.swing.JLabel();
        lblPuerto = new javax.swing.JLabel();
        lblBD = new javax.swing.JLabel();
        txt_Host = new javax.swing.JTextField();
        txt_Puerto = new javax.swing.JTextField();
        txt_BD = new javax.swing.JTextField();
        btn_Guardar = new javax.swing.JButton();
        txt_Contrasenia = new javax.swing.JPasswordField();
        pb_Conectando = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("S.I.C.");
        setResizable(false);

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsuario.setText("Usuario:");

        lblContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContrasenia.setText("Contraseña:");

        txt_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_UsuarioKeyPressed(evt);
            }
        });

        btn_Salir.setForeground(java.awt.Color.blue);
        btn_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/LogOut_16x16.png"))); // NOI18N
        btn_Salir.setText("Salir");
        btn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalirActionPerformed(evt);
            }
        });

        btn_Ingresar.setForeground(java.awt.Color.blue);
        btn_Ingresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/LogIn_16x16.png"))); // NOI18N
        btn_Ingresar.setText("Ingresar");
        btn_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IngresarActionPerformed(evt);
            }
        });

        btn_Configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Plug_16x16.png"))); // NOI18N
        btn_Configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ConfiguracionActionPerformed(evt);
            }
        });

        lblHost.setForeground(java.awt.Color.red);
        lblHost.setText("Host ó IP:");

        lblPuerto.setForeground(java.awt.Color.red);
        lblPuerto.setText("Puerto:");

        lblBD.setForeground(java.awt.Color.red);
        lblBD.setText("Base de Datos:");

        btn_Guardar.setForeground(java.awt.Color.blue);
        btn_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Save_16x16.png"))); // NOI18N
        btn_Guardar.setText("Guardar");
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        txt_Contrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ContraseniaKeyPressed(evt);
            }
        });

        pb_Conectando.setString("");
        pb_Conectando.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pb_Conectando, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                            .addComponent(txt_Contrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Configuracion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Ingresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHost, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblPuerto, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblBD, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_BD, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                    .addComponent(txt_Puerto, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                    .addComponent(txt_Host, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
                            .addComponent(btn_Guardar, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(lblUsuario)
                    .addComponent(txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(lblContrasenia)
                    .addComponent(txt_Contrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pb_Conectando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_Configuracion)
                    .addComponent(btn_Ingresar)
                    .addComponent(btn_Salir))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(lblHost)
                    .addComponent(txt_Host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(lblPuerto)
                    .addComponent(txt_Puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(lblBD)
                    .addComponent(txt_BD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Guardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ConfiguracionActionPerformed
        this.desplegarPlegarConfig();
    }//GEN-LAST:event_btn_ConfiguracionActionPerformed

    private void btn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_SalirActionPerformed

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        try {
            //TO DO - Esta validacion debería ser hecha por un componente swing
            String puertoIngresado = txt_Puerto.getText().trim();
            if (puertoIngresado.equals("") || !Validator.esNumericoPositivo(puertoIngresado)) {
                throw new ServiceException("El puerto ingresado es inválido.");
            }

            DatosConexion datosConexion = new DatosConexion();
            datosConexion.setHost(txt_Host.getText().trim());
            datosConexion.setNombreBaseDeDatos(txt_BD.getText().trim());
            datosConexion.setPuerto(Integer.parseInt(txt_Puerto.getText().trim()));

            conexionService.guardar(datosConexion);
            JOptionPane.showMessageDialog(this, "La configuración de conexion se guardo correctamente!",
                    "Informacion", JOptionPane.INFORMATION_MESSAGE);
            this.cargarDatosConexion();
            this.desplegarPlegarConfig();

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (XMLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IngresarActionPerformed
        Thread hiloLogIn = new Thread(new Runnable() {
            @Override
            public void run() {
                pb_Conectando.setIndeterminate(true);
                pb_Conectando.setString("Conectando...");
                btn_Configuracion.setEnabled(false);
                btn_Ingresar.setEnabled(false);
                btn_Salir.setEnabled(false);
                txt_Usuario.setEnabled(false);
                txt_Contrasenia.setEnabled(false);
                pb_Conectando.requestFocus();
                validarUsuario();
                pb_Conectando.setIndeterminate(false);
                pb_Conectando.setString("");
                btn_Configuracion.setEnabled(true);
                btn_Ingresar.setEnabled(true);
                btn_Salir.setEnabled(true);
                txt_Usuario.setEnabled(true);
                txt_Contrasenia.setEnabled(true);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ingresar();
                    }
                });
            }
        });
        hiloLogIn.start();
    }//GEN-LAST:event_btn_IngresarActionPerformed

    private void txt_ContraseniaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ContraseniaKeyPressed
        this.capturaTeclaEnter(evt);
    }//GEN-LAST:event_txt_ContraseniaKeyPressed

    private void txt_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_UsuarioKeyPressed
        this.capturaTeclaEnter(evt);
    }//GEN-LAST:event_txt_UsuarioKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Configuracion;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_Ingresar;
    private javax.swing.JButton btn_Salir;
    private javax.swing.JLabel lblBD;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblHost;
    private javax.swing.JLabel lblPuerto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JProgressBar pb_Conectando;
    private javax.swing.JTextField txt_BD;
    private javax.swing.JPasswordField txt_Contrasenia;
    private javax.swing.JTextField txt_Host;
    private javax.swing.JTextField txt_Puerto;
    private javax.swing.JTextField txt_Usuario;
    // End of variables declaration//GEN-END:variables
}
