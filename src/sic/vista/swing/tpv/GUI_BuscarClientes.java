package sic.vista.swing.tpv;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.Cliente;
import sic.service.ClienteService;
import sic.service.EmpresaService;
import sic.util.Utilidades;
import sic.vista.swing.ModeloTabla;
import sic.vista.swing.administracion.GUI_DetalleCliente;

public class GUI_BuscarClientes extends JDialog {

    private final GUI_PrincipalTPV gui_PrincipalTPV;
    private ModeloTabla modeloTablaResultados = new ModeloTabla();
    private List<Cliente> clientes;
    private Cliente cliSeleccionado;
    private final ClienteService clienteService = new ClienteService();
    private final EmpresaService empresaService = new EmpresaService();
    private final HotKeysHandler keyHandler = new HotKeysHandler();
    private static final Logger log = Logger.getLogger(GUI_BuscarClientes.class.getPackage().getName());

    public GUI_BuscarClientes(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIcon();
        gui_PrincipalTPV = (GUI_PrincipalTPV) parent;
        this.setSize(gui_PrincipalTPV.getWidth() - 100, gui_PrincipalTPV.getHeight() - 200);
        this.setLocationRelativeTo(parent);
        this.setColumnas();

        //listeners
        cmb_CriterioBusqueda.addKeyListener(keyHandler);
        txt_TextoBusqueda.addKeyListener(keyHandler);
        tbl_Resultado.addKeyListener(keyHandler);
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Client_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    public Cliente getCliSeleccionado() {
        return cliSeleccionado;
    }

    public void setCliSeleccionado(Cliente cliSeleccionado) {
        this.cliSeleccionado = cliSeleccionado;
    }

    private void Buscar() {
        if (cmb_CriterioBusqueda.getModel().getSelectedItem().equals("NOMBRE")) {
            clientes = clienteService.getClientesPorNombreQueContenga(txt_TextoBusqueda.getText().trim(), empresaService.getEmpresaActiva().getEmpresa());
        }

        if (cmb_CriterioBusqueda.getModel().getSelectedItem().equals("ID FISCAL")) {
            if (txt_TextoBusqueda.getText().trim().length() == 0) {
                clientes = clienteService.getClientes(empresaService.getEmpresaActiva().getEmpresa());
            } else {
                Cliente cli = clienteService.getClientePorIdFiscal(txt_TextoBusqueda.getText().trim(), empresaService.getEmpresaActiva().getEmpresa());
                clientes = new ArrayList<>();
                if (cli != null) {
                    clientes.add(cli);
                }
            }
        }
        this.cargarResultadosAlTable();
    }

    private void setColumnas() {
        //nombres de columnas
        String[] encabezados = new String[12];
        encabezados[0] = "ID Fiscal";
        encabezados[1] = "Nombre";
        encabezados[2] = "Direccion";
        encabezados[3] = "Condicion IVA";
        encabezados[4] = "Tel. Primario";
        encabezados[5] = "Tel. Secundario";
        encabezados[6] = "Contacto";
        encabezados[7] = "Email";
        encabezados[8] = "Fecha Alta";
        encabezados[9] = "Localidad";
        encabezados[10] = "Provincia";
        encabezados[11] = "Pais";
        modeloTablaResultados.setColumnIdentifiers(encabezados);
        tbl_Resultado.setModel(modeloTablaResultados);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaResultados.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = String.class;
        tipos[3] = String.class;
        tipos[4] = String.class;
        tipos[5] = String.class;
        tipos[6] = String.class;
        tipos[7] = String.class;
        tipos[8] = Date.class;
        tipos[9] = String.class;
        tipos[10] = String.class;
        tipos[11] = String.class;
        modeloTablaResultados.setClaseColumnas(tipos);
        tbl_Resultado.getTableHeader().setReorderingAllowed(false);
        tbl_Resultado.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Resultado.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Resultado.getColumnModel().getColumn(1).setPreferredWidth(350);
        tbl_Resultado.getColumnModel().getColumn(2).setPreferredWidth(350);
        tbl_Resultado.getColumnModel().getColumn(3).setPreferredWidth(250);
        tbl_Resultado.getColumnModel().getColumn(4).setPreferredWidth(180);
        tbl_Resultado.getColumnModel().getColumn(5).setPreferredWidth(180);
        tbl_Resultado.getColumnModel().getColumn(6).setPreferredWidth(200);
        tbl_Resultado.getColumnModel().getColumn(7).setPreferredWidth(250);
        tbl_Resultado.getColumnModel().getColumn(8).setPreferredWidth(200);
        tbl_Resultado.getColumnModel().getColumn(9).setPreferredWidth(200);
        tbl_Resultado.getColumnModel().getColumn(10).setPreferredWidth(200);
        tbl_Resultado.getColumnModel().getColumn(11).setPreferredWidth(200);
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTable();
        for (Cliente cliente : clientes) {
            Object[] fila = new Object[12];
            fila[0] = cliente.getId_Fiscal();
            fila[1] = cliente.getNombre();
            fila[2] = cliente.getDireccion();
            fila[3] = cliente.getCondicionIVA();
            fila[4] = cliente.getTelPrimario();
            fila[5] = cliente.getTelSecundario();
            fila[6] = cliente.getContacto();
            fila[7] = cliente.getEmail();
            fila[8] = cliente.getFechaAlta();
            fila[9] = cliente.getLocalidad();
            fila[10] = cliente.getLocalidad().getProvincia();
            fila[11] = cliente.getLocalidad().getProvincia().getPais();
            modeloTablaResultados.addRow(fila);
        }
        tbl_Resultado.setModel(modeloTablaResultados);
    }

    private void limpiarJTable() {
        modeloTablaResultados = new ModeloTabla();
        tbl_Resultado.setModel(modeloTablaResultados);
        this.setColumnas();
    }

    private void seleccionarCliente() {
        if (tbl_Resultado.getSelectedRow() != -1) {
            int filaSeleccionada = tbl_Resultado.getSelectedRow();
            cliSeleccionado = clientes.get(filaSeleccionada);
        } else {
            cliSeleccionado = null;
        }
        this.dispose();
    }

    /**
     * Clase interna para manejar las hotkeys
     */
    class HotKeysHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dispose();
            }
        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        txt_TextoBusqueda = new javax.swing.JTextField();
        btn_Buscar = new javax.swing.JButton();
        btn_Aceptar = new javax.swing.JButton();
        cmb_CriterioBusqueda = new javax.swing.JComboBox();
        sp_Resultado = new javax.swing.JScrollPane();
        tbl_Resultado = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txt_TextoBusqueda.setFont(new java.awt.Font("DejaVu Sans", 0, 17)); // NOI18N
        txt_TextoBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TextoBusquedaActionPerformed(evt);
            }
        });
        txt_TextoBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_TextoBusquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_TextoBusquedaKeyTyped(evt);
            }
        });

        btn_Buscar.setForeground(java.awt.Color.blue);
        btn_Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/22x22_LupaBuscar.png"))); // NOI18N
        btn_Buscar.setFocusable(false);
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });

        btn_Aceptar.setForeground(java.awt.Color.blue);
        btn_Aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/22x22_FlechaGO.png"))); // NOI18N
        btn_Aceptar.setFocusable(false);
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        cmb_CriterioBusqueda.setFont(new java.awt.Font("DejaVu Sans", 0, 17)); // NOI18N
        cmb_CriterioBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NOMBRE", "ID FISCAL" }));
        cmb_CriterioBusqueda.setFocusable(false);
        cmb_CriterioBusqueda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_CriterioBusquedaItemStateChanged(evt);
            }
        });

        tbl_Resultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_Resultado.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_Resultado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_Resultado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbl_ResultadoFocusGained(evt);
            }
        });
        tbl_Resultado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_ResultadoKeyPressed(evt);
            }
        });
        sp_Resultado.setViewportView(tbl_Resultado);

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_Resultado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addComponent(cmb_CriterioBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_TextoBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(btn_Buscar))
                    .addComponent(btn_Aceptar))
                .addContainerGap())
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TextoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Buscar)
                    .addComponent(cmb_CriterioBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Resultado, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Aceptar)
                .addContainerGap())
        );

        panelFondoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_Buscar, cmb_CriterioBusqueda, txt_TextoBusqueda});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        this.seleccionarCliente();
}//GEN-LAST:event_btn_AceptarActionPerformed

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
        try {
            this.Buscar();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_BuscarActionPerformed

    private void txt_TextoBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TextoBusquedaActionPerformed
        try {
            this.Buscar();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_TextoBusquedaActionPerformed

    private void tbl_ResultadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_ResultadoKeyPressed
        if (evt.getKeyCode() == 10) {
            this.seleccionarCliente();
        }
        if (evt.getKeyCode() == 9) {
            txt_TextoBusqueda.requestFocus();
        }
    }//GEN-LAST:event_tbl_ResultadoKeyPressed

    private void txt_TextoBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TextoBusquedaKeyTyped
        evt.setKeyChar(Utilidades.convertirAMayusculas(evt.getKeyChar()));
    }//GEN-LAST:event_txt_TextoBusquedaKeyTyped

    private void txt_TextoBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TextoBusquedaKeyReleased
        try {
            this.Buscar();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_TextoBusquedaKeyReleased

    private void cmb_CriterioBusquedaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_CriterioBusquedaItemStateChanged
        try {
            this.Buscar();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_CriterioBusquedaItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.Buscar();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void tbl_ResultadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_ResultadoFocusGained
        //Si no hay nada seleccionado y NO esta vacio el table, selecciona la primer fila
        if ((tbl_Resultado.getSelectedRow() == -1) && (tbl_Resultado.getRowCount() != 0)) {
            tbl_Resultado.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_tbl_ResultadoFocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JComboBox cmb_CriterioBusqueda;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JScrollPane sp_Resultado;
    private javax.swing.JTable tbl_Resultado;
    private javax.swing.JTextField txt_TextoBusqueda;
    // End of variables declaration//GEN-END:variables
}
