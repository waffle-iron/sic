package sic.vista.swing;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import sic.AppContextProvider;
import sic.modelo.UsuarioActivo;
import sic.modelo.Usuario;
import sic.service.IUsuarioService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;

public class GUI_Usuarios extends JDialog {

    private final DefaultListModel modeloList = new DefaultListModel();
    private Usuario usuarioSeleccionado;
    private TipoDeOperacion operacion;
    private boolean mismoUsuarioActivo = false;
    private final ApplicationContext appContext = AppContextProvider.getApplicationContext();
    private final IUsuarioService usuarioService = appContext.getBean(IUsuarioService.class);
    private static final Logger log = Logger.getLogger(GUI_Usuarios.class.getPackage().getName());

    public GUI_Usuarios() {
        this.initComponents();
        this.setIcon();
        panel2.setVisible(false);
        btn_Aceptar.setVisible(false);
        btn_Cancelar.setVisible(false);
        this.setSize(430, 188);
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Group_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void comprobarPrivilegiosUsuarioActivo() {
        //Comprobamos si el usuario es Administrador
        if (UsuarioActivo.getInstance().getUsuario().getPermisosAdministrador() == true) {
            this.cargarListUsuarios();
            this.desactivarComponentesInferiores();
        } else {
            JOptionPane.showMessageDialog(this, "No tiene privilegios de Administrador para poder ver esta ventana.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void cargarListUsuarios() {
        modeloList.clear();
        List<Usuario> usuarios = usuarioService.getUsuarios();
        for (Usuario u : usuarios) {
            modeloList.addElement(u);
        }
        lst_Usuarios.setModel(modeloList);
    }

    private void activarComponentesInferiores() {
        txt_Usuario.setEnabled(true);
        txt_Contrasenia.setEnabled(true);
        txt_RepetirContrasenia.setEnabled(true);
        chk_Administrador.setEnabled(true);
        btn_Aceptar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
    }

    private void desactivarComponentesInferiores() {
        txt_Usuario.setEnabled(false);
        txt_Contrasenia.setEnabled(false);
        txt_RepetirContrasenia.setEnabled(false);
        chk_Administrador.setEnabled(false);
        btn_Aceptar.setEnabled(false);
        btn_Cancelar.setEnabled(false);
    }

    private void activarComponentesSuperiores() {
        btn_Agregar.setEnabled(true);
        btn_Actualizar.setEnabled(true);
        btn_Eliminar.setEnabled(true);
        lst_Usuarios.setEnabled(true);
    }

    private void desactivarComponentesSuperiores() {
        btn_Agregar.setEnabled(false);
        btn_Actualizar.setEnabled(false);
        btn_Eliminar.setEnabled(false);
        lst_Usuarios.setEnabled(false);
    }

    private void limpiarDatos() {
        txt_Usuario.setText("");
        txt_Contrasenia.setText("");
        txt_RepetirContrasenia.setText("");
        chk_Administrador.setSelected(false);
    }

    private void desplegarDetalle() {
        panel2.setVisible(true);
        btn_Aceptar.setVisible(true);
        btn_Cancelar.setVisible(true);
        this.setSize(430, 320);
        txt_Usuario.requestFocus();
        this.centrarInternalFrame();
    }

    private void plegarDetalle() {
        panel2.setVisible(false);
        btn_Aceptar.setVisible(false);
        btn_Cancelar.setVisible(false);
        this.setSize(430, 188);
        this.centrarInternalFrame();
    }

    private void centrarInternalFrame() {
        this.setLocationRelativeTo(this.getParent());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        sp_ListaMedidas = new javax.swing.JScrollPane();
        lst_Usuarios = new javax.swing.JList();
        btn_Actualizar = new javax.swing.JButton();
        btn_Agregar = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        lbl_Usuarios = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administración de Usuarios");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lst_Usuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Usuarios.setVisibleRowCount(9);
        lst_Usuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lst_UsuariosValueChanged(evt);
            }
        });
        sp_ListaMedidas.setViewportView(lst_Usuarios);

        btn_Actualizar.setForeground(java.awt.Color.blue);
        btn_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/EditGroup_16x16.png"))); // NOI18N
        btn_Actualizar.setText("Actualizar");
        btn_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualizarActionPerformed(evt);
            }
        });

        btn_Agregar.setForeground(java.awt.Color.blue);
        btn_Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddGroup_16x16.png"))); // NOI18N
        btn_Agregar.setText("Agregar");
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });

        btn_Eliminar.setForeground(java.awt.Color.blue);
        btn_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/DeleteGroup_16x16.png"))); // NOI18N
        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btn_Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btn_Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btn_Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(sp_ListaMedidas))
                .addContainerGap())
        );

        panelPrincipalLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_Actualizar, btn_Agregar, btn_Eliminar});

        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(sp_ListaMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Agregar)
                    .addComponent(btn_Actualizar)
                    .addComponent(btn_Eliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPrincipalLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_Actualizar, btn_Agregar, btn_Eliminar});

        lbl_Usuarios.setText("Usuarios:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Usuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Usuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        try {
            if (usuarioSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista para poder continuar.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //Si el usuario activo corresponde con el usuario seleccionado para modificar
                int respuesta;
                if (UsuarioActivo.getInstance().getUsuario().getNombre().equals(usuarioSeleccionado.getNombre())) {
                    respuesta = JOptionPane.showConfirmDialog(this,
                            "¡Atención! ¿Esta seguro de que desea eliminar su propio\n"
                            + "usuario? No podrá iniciar de nuevo con este usuario.",
                            "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    mismoUsuarioActivo = true;
                } else {
                    respuesta = JOptionPane.showConfirmDialog(this,
                            "¿Esta seguro que desea eliminar el usuario " + usuarioSeleccionado.getNombre() + "?",
                            "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    mismoUsuarioActivo = false;
                }

                if (respuesta == JOptionPane.YES_OPTION) {
                    usuarioService.eliminar(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    this.cargarListUsuarios();
                    this.limpiarDatos();
                }
            }

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        this.desplegarDetalle();
        this.activarComponentesInferiores();
        this.desactivarComponentesSuperiores();
        this.limpiarDatos();
        operacion = TipoDeOperacion.ALTA;
    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualizarActionPerformed
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista para poder continuar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //Si el usuario activo corresponde con el usuario seleccionado para modificar
            int respuesta = -1;
            if (UsuarioActivo.getInstance().getUsuario().getNombre().equals(usuarioSeleccionado.getNombre())) {
                respuesta = JOptionPane.showConfirmDialog(this,
                        "¡Atención! Va a modificar su propio usuario, para que los cambios tengan efecto\n"
                        + "tendrá que volver a iniciar sesion. ¿Desea continuar?",
                        "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                mismoUsuarioActivo = true;
            } else {
                mismoUsuarioActivo = false;
            }

            //Si la respuesta SI o -1 (por defecto) que entre aqui
            if (respuesta == JOptionPane.YES_OPTION || respuesta == -1) {
                this.activarComponentesInferiores();
                this.desactivarComponentesSuperiores();
                txt_Contrasenia.setText("");
                txt_RepetirContrasenia.setText("");
                operacion = TipoDeOperacion.ACTUALIZACION;
                this.desplegarDetalle();
                this.centrarInternalFrame();
            }
        }
    }//GEN-LAST:event_btn_ActualizarActionPerformed

    private void lst_UsuariosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lst_UsuariosValueChanged
        if (lst_Usuarios.getModel().getSize() != 0) {
            if (lst_Usuarios.getSelectedValue() != null) {
                usuarioSeleccionado = (Usuario) lst_Usuarios.getSelectedValue();
                txt_Usuario.setText(lst_Usuarios.getSelectedValue().toString());
                txt_Contrasenia.setText("********");
                txt_RepetirContrasenia.setText("********");
                chk_Administrador.setSelected(usuarioSeleccionado.getPermisosAdministrador());
            }
        }
    }//GEN-LAST:event_lst_UsuariosValueChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.comprobarPrivilegiosUsuarioActivo();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Actualizar;
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JButton btn_Eliminar;
    private javax.swing.JLabel lbl_Usuarios;
    private javax.swing.JList lst_Usuarios;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JScrollPane sp_ListaMedidas;
    // End of variables declaration//GEN-END:variables
}
