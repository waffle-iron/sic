package sic.vista.swing;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Cliente;
import sic.modelo.Pedido;
import sic.modelo.Usuario;
import sic.service.ClienteService;
import sic.service.EmpresaService;
import sic.service.PedidoService;
import sic.service.UsuarioService;
import sic.util.RenderTabla;

public class GUI_Pedidos extends javax.swing.JDialog {

    private final EmpresaService empresaService = new EmpresaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final ClienteService clienteService = new ClienteService();
    private final PedidoService pedidoService = new PedidoService();
    private List<Pedido> pedidos;
    private ModeloTabla modeloTablaPedidos;

    public GUI_Pedidos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chk_Fecha = new javax.swing.JCheckBox();
        chk_Cliente = new javax.swing.JCheckBox();
        chk_Vendedor = new javax.swing.JCheckBox();
        dc_Desde = new com.toedter.calendar.JDateChooser();
        dc_Hasta = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pedidos = new javax.swing.JTable();
        btn_Buscar = new javax.swing.JButton();
        chk_Numero = new javax.swing.JCheckBox();
        btn_NuevoPedido = new javax.swing.JButton();
        btn_VerFacturas = new javax.swing.JButton();
        btn_Facturar = new javax.swing.JButton();
        cmb_Cliente = new javax.swing.JComboBox();
        cmb_Vendedor = new javax.swing.JComboBox();
        tf_Numero = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        chk_Fecha.setText("Fecha");

        chk_Cliente.setText("Cliente");
        chk_Cliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_ClienteItemStateChanged(evt);
            }
        });

        chk_Vendedor.setText("Vendedor");
        chk_Vendedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_VendedorItemStateChanged(evt);
            }
        });

        tbl_Pedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_Pedidos);

        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });

        chk_Numero.setText("Numero");

        btn_NuevoPedido.setText("Nuevo");

        btn_VerFacturas.setText("VerFacturas");

        btn_Facturar.setText("Facturar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chk_Vendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Buscar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chk_Fecha)
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dc_Desde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_Vendedor, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_Cliente, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chk_Cliente)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chk_Numero)
                                .addGap(76, 76, 76)
                                .addComponent(tf_Numero)
                                .addGap(9, 9, 9))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_NuevoPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_VerFacturas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_Facturar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chk_Numero)
                    .addComponent(tf_Numero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chk_Fecha)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dc_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chk_Cliente)
                    .addComponent(cmb_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_Vendedor)
                    .addComponent(btn_Buscar)
                    .addComponent(cmb_Vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_NuevoPedido)
                    .addComponent(btn_VerFacturas)
                    .addComponent(btn_Facturar))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chk_ClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_ClienteItemStateChanged
        this.cargarComboBoxClientes();
    }//GEN-LAST:event_chk_ClienteItemStateChanged

    private void chk_VendedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_VendedorItemStateChanged
        this.cargarComboBoxUsuarios();
    }//GEN-LAST:event_chk_VendedorItemStateChanged

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btn_BuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI_Pedidos dialog = new GUI_Pedidos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JButton btn_Facturar;
    private javax.swing.JButton btn_NuevoPedido;
    private javax.swing.JButton btn_VerFacturas;
    private javax.swing.JCheckBox chk_Cliente;
    private javax.swing.JCheckBox chk_Fecha;
    private javax.swing.JCheckBox chk_Numero;
    private javax.swing.JCheckBox chk_Vendedor;
    private javax.swing.JComboBox cmb_Cliente;
    private javax.swing.JComboBox cmb_Vendedor;
    private com.toedter.calendar.JDateChooser dc_Desde;
    private com.toedter.calendar.JDateChooser dc_Hasta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_Pedidos;
    private javax.swing.JTextField tf_Numero;
    // End of variables declaration//GEN-END:variables
    public void buscar() {
        try {
            BusquedaPedidoCriteria criteria = new BusquedaPedidoCriteria();
            criteria.setCliente((Cliente) cmb_Cliente.getSelectedItem());
            criteria.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
            criteria.setFechaDesde(dc_Desde.getDate());
            criteria.setFechaHasta(dc_Hasta.getDate());
            if (tf_Numero.getText().isEmpty()) {
                criteria.setNumPedido(0);
            } else {
                criteria.setNumPedido(Long.valueOf(tf_Numero.getText()));
            }
            criteria.setUsuario((Usuario) cmb_Vendedor.getSelectedItem());
            criteria.setBuscaCliente(chk_Cliente.isSelected());
            criteria.setBuscaPorFecha(chk_Fecha.isSelected());
            criteria.setBuscaPorNumeroPedido(chk_Numero.isSelected());
            criteria.setBuscaUsuario(chk_Vendedor.isSelected());
            criteria.setCantRegistros(0);
            pedidos = pedidoService.buscarPedidos(criteria);
            this.cargarResultadosAlTable();
            if (pedidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle(
                        "Mensajes").getString("mensaje_busqueda_sin_resultados"),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cargarComboBoxClientes() {
        cmb_Cliente.removeAllItems();
        List<Cliente> clientes;
        clientes = clienteService.getClientes(empresaService.getEmpresaActiva().getEmpresa());
        for (Cliente cliente : clientes) {
            cmb_Cliente.addItem(cliente);
        }
    }

    private void cargarComboBoxUsuarios() {
        cmb_Vendedor.removeAllItems();
        List<Usuario> usuarios;
        usuarios = usuarioService.getUsuarios();
        for (Usuario usuario : usuarios) {
            cmb_Vendedor.addItem(usuario);
        }
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTable();
        for (Pedido pedido : pedidos) {
            Object[] fila = new Object[17];
            fila[0] = pedido.getFecha();
            fila[1] = pedido.getNroPedido();
            fila[2] = pedido.getCliente().getRazonSocial();
            fila[3] = pedido.getUsuario().getNombre();
            modeloTablaPedidos.addRow(fila);
        }
        tbl_Pedidos.setModel(modeloTablaPedidos);
        String mensaje = pedidos.size() + " facturas encontradas.";
    }

    private void limpiarJTable() {
        modeloTablaPedidos = new ModeloTabla();
        tbl_Pedidos.setModel(modeloTablaPedidos);
        this.setColumnas();
    }

    private void setColumnas() {
        //sorting
        tbl_Pedidos.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[4];
        encabezados[0] = "Fecha Pedido";
        encabezados[1] = "Nº Pedido";
        encabezados[2] = "Cliente";
        encabezados[3] = "Usuario (Vendedor)";
        modeloTablaPedidos.setColumnIdentifiers(encabezados);
        tbl_Pedidos.setModel(modeloTablaPedidos);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaPedidos.getColumnCount()];
        tipos[0] = Date.class;
        tipos[1] = Long.class;
        tipos[2] = String.class;
        tipos[3] = String.class;
        modeloTablaPedidos.setClaseColumnas(tipos);
        tbl_Pedidos.getTableHeader().setReorderingAllowed(false);
        tbl_Pedidos.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Pedidos.setDefaultRenderer(Double.class, new RenderTabla());

        //Tamanios de columnas
        tbl_Pedidos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Pedidos.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbl_Pedidos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_Pedidos.getColumnModel().getColumn(3).setPreferredWidth(130);
    }

}
