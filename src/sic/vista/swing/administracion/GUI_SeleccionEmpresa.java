package sic.vista.swing.administracion;

import java.awt.Frame;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.Empresa;
import sic.service.EmpresaService;

public class GUI_SeleccionEmpresa extends JDialog {

    private Empresa empresaSeleccionada;
    private EmpresaService empresaService = new EmpresaService();
    private static final Logger log = Logger.getLogger(GUI_SeleccionEmpresa.class.getPackage().getName());

    public GUI_SeleccionEmpresa(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIcon();
        this.setLocationRelativeTo(null);
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Empresa_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Leyenda = new javax.swing.JLabel();
        cmb_Empresas = new javax.swing.JComboBox();
        btn_Aceptar = new javax.swing.JButton();
        lbl_Icon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empresas");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbl_Leyenda.setFont(new java.awt.Font("DejaVu Sans", 0, 15)); // NOI18N
        lbl_Leyenda.setText("Seleccione la Empresa con la que desea trabajar:");

        cmb_Empresas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmb_EmpresasKeyPressed(evt);
            }
        });

        btn_Aceptar.setForeground(java.awt.Color.blue);
        btn_Aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/ArrowRight_16x16.png"))); // NOI18N
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        lbl_Icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Empresa_32x32.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_Icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_Empresas, 0, 348, Short.MAX_VALUE))
                    .addComponent(lbl_Leyenda)
                    .addComponent(btn_Aceptar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Leyenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_Empresas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Icon, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Aceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarComboBoxEmpresas() {
        cmb_Empresas.removeAllItems();
        List<Empresa> empresas = empresaService.getEmpresas();
        for (Empresa e : empresas) {
            cmb_Empresas.addItem(e);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        empresaSeleccionada = (Empresa) cmb_Empresas.getSelectedItem();
        if (empresaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una Empresa para poder continuar!\nEn "
                    + "caso de que no encuentre ninguna, comuniquese con un "
                    + "Administrador del sistema",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            empresaService.setEmpresaActiva(empresaSeleccionada);
            this.dispose();
        }
    }//GEN-LAST:event_btn_AceptarActionPerformed

    private void cmb_EmpresasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_EmpresasKeyPressed
        //tecla ENTER
        if (evt.getKeyCode() == 10) {
            btn_AceptarActionPerformed(null);
        }
    }//GEN-LAST:event_cmb_EmpresasKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.cargarComboBoxEmpresas();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowOpened
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JComboBox cmb_Empresas;
    private javax.swing.JLabel lbl_Icon;
    private javax.swing.JLabel lbl_Leyenda;
    // End of variables declaration//GEN-END:variables
}
