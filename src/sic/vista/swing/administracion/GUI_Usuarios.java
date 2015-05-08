package sic.vista.swing.administracion;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.UsuarioActivo;
import sic.modelo.Usuario;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.service.UsuarioService;

public class GUI_Usuarios extends JDialog {

    private final DefaultListModel modeloList = new DefaultListModel();
    private Usuario usuarioSeleccionado;
    private TipoDeOperacion operacion;
    private boolean mismoUsuarioActivo = false;
    private final UsuarioService usuarioService = new UsuarioService();
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

        panel1 = new javax.swing.JPanel();
        sp_ListaMedidas = new javax.swing.JScrollPane();
        lst_Usuarios = new javax.swing.JList();
        btn_Actualizar = new javax.swing.JButton();
        btn_Agregar = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        lbl_Usuarios = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        lbl_Usuario = new javax.swing.JLabel();
        lbl_Contrasenia = new javax.swing.JLabel();
        txt_Usuario = new javax.swing.JTextField();
        btn_Cancelar = new javax.swing.JButton();
        btn_Aceptar = new javax.swing.JButton();
        txt_Contrasenia = new javax.swing.JPasswordField();
        txt_RepetirContrasenia = new javax.swing.JPasswordField();
        lbl_RepetirContrasenia = new javax.swing.JLabel();
        chk_Administrador = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administración de Usuarios");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        lbl_Usuarios.setText("Usuarios:");

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panel2.setPreferredSize(new java.awt.Dimension(403, 118));

        lbl_Usuario.setForeground(java.awt.Color.red);
        lbl_Usuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Usuario.setText("* Usuario:");

        lbl_Contrasenia.setForeground(java.awt.Color.red);
        lbl_Contrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Contrasenia.setText("* Contraseña:");

        btn_Cancelar.setForeground(java.awt.Color.blue);
        btn_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Cancel_16x16.png"))); // NOI18N
        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_Aceptar.setForeground(java.awt.Color.blue);
        btn_Aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Aceptar.setText("Aceptar");
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        txt_Contrasenia.setPreferredSize(new java.awt.Dimension(125, 20));

        txt_RepetirContrasenia.setPreferredSize(new java.awt.Dimension(125, 20));

        lbl_RepetirContrasenia.setForeground(java.awt.Color.red);
        lbl_RepetirContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_RepetirContrasenia.setText("* Repetir:");

        chk_Administrador.setText("Administrador: ");
        chk_Administrador.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chk_Administrador.setMargin(new java.awt.Insets(2, -2, 2, 2));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_RepetirContrasenia)
                            .addComponent(lbl_Contrasenia)
                            .addComponent(lbl_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_RepetirContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Contrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chk_Administrador))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_Aceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancelar)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Usuario)
                    .addComponent(txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Contrasenia)
                    .addComponent(txt_Contrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_RepetirContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_RepetirContrasenia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chk_Administrador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Aceptar)
                    .addComponent(btn_Cancelar)))
        );

        panel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_Contrasenia, txt_RepetirContrasenia, txt_Usuario});

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Usuarios)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(sp_ListaMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(btn_Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(lbl_Usuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btn_Agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Eliminar))
                    .addComponent(sp_ListaMedidas, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
        );

        panel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_Actualizar, btn_Agregar, btn_Eliminar});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        this.plegarDetalle();
        this.activarComponentesSuperiores();
        this.desactivarComponentesInferiores();
        this.limpiarDatos();
        usuarioSeleccionado = null;
        lst_Usuarios.clearSelection();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        try {
            if (operacion == TipoDeOperacion.ALTA) {
                if (new String(txt_Contrasenia.getPassword()).equals(new String(txt_RepetirContrasenia.getPassword()))) {
                    Usuario usuario = new Usuario();
                    usuario.setNombre(txt_Usuario.getText().trim());
                    usuario.setPassword(new String(txt_Contrasenia.getPassword()));
                    usuario.setPermisosAdministrador(chk_Administrador.isSelected());
                    usuarioService.guardar(usuario);
                    this.cargarListUsuarios();
                    this.desactivarComponentesInferiores();
                    this.activarComponentesSuperiores();
                    this.limpiarDatos();
                    this.plegarDetalle();
                    this.centrarInternalFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "Las contraseñas introducidas deben ser las mismas.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (operacion == TipoDeOperacion.ACTUALIZACION) {
                if (new String(txt_Contrasenia.getPassword()).equals(new String(txt_RepetirContrasenia.getPassword()))) {
                    this.desactivarComponentesSuperiores();
                    this.activarComponentesInferiores();
                    Usuario usuarioModificado = new Usuario();
                    usuarioModificado.setId_Usuario(usuarioSeleccionado.getId_Usuario());
                    usuarioModificado.setNombre(txt_Usuario.getText().trim());
                    usuarioModificado.setPassword(new String(txt_Contrasenia.getPassword()));
                    usuarioModificado.setPermisosAdministrador(chk_Administrador.isSelected());
                    usuarioService.actualizar(usuarioModificado);
                    if (mismoUsuarioActivo == true) {
                        usuarioService.setUsuarioActivo(usuarioModificado);
                    }
                    this.cargarListUsuarios();
                    this.desactivarComponentesInferiores();
                    this.activarComponentesSuperiores();
                    this.limpiarDatos();
                    usuarioSeleccionado = null;
                    this.plegarDetalle();
                    this.centrarInternalFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "Las contraseñas introducidas deben ser las mismas.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_AceptarActionPerformed

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
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JButton btn_Actualizar;
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Eliminar;
    private javax.swing.JCheckBox chk_Administrador;
    private javax.swing.JLabel lbl_Contrasenia;
    private javax.swing.JLabel lbl_RepetirContrasenia;
    private javax.swing.JLabel lbl_Usuario;
    private javax.swing.JLabel lbl_Usuarios;
    private javax.swing.JList lst_Usuarios;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane sp_ListaMedidas;
    private javax.swing.JPasswordField txt_Contrasenia;
    private javax.swing.JPasswordField txt_RepetirContrasenia;
    private javax.swing.JTextField txt_Usuario;
    // End of variables declaration//GEN-END:variables
}
