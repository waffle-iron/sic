package sic.vista.swing;

import java.awt.HeadlessException;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Cliente;
import sic.modelo.Pedido;
import sic.modelo.RenglonPedido;
import sic.modelo.Usuario;
import sic.service.ClienteService;
import sic.service.EmpresaService;
import sic.service.PedidoService;
import sic.service.UsuarioService;
import sic.util.RenderTabla;
import sic.util.Utilidades;

public class GUI_Pedidos extends JInternalFrame {

    private final EmpresaService empresaService = new EmpresaService();
    private final PedidoService pedidoService = new PedidoService();
    private final ClienteService clienteService = new ClienteService();
    private final UsuarioService usuarioSercice = new UsuarioService();
    private List<Pedido> pedidos;
    private ModeloTabla modeloTablaPedidos;
    private ModeloTabla modeloTablaRenglones;
    private static final Logger log = Logger.getLogger(GUI_Pedidos.class.getPackage().getName());

    public GUI_Pedidos() {
        initComponents();
        this.setSize(750, 450);
        this.limpiarJTables();
        tf_Numero.setEnabled(false);
        dc_Desde.setEnabled(false);
        dc_Hasta.setEnabled(false);
        cmb_Cliente.setEnabled(false);
        cmb_Vendedor.setEnabled(false);
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
        lb_Desde = new javax.swing.JLabel();
        lb_Hasta = new javax.swing.JLabel();
        pb_Filtro = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_RenglonesPedido = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ventana para administrar pedidos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/PedidoIco_16x16.png"))); // NOI18N

        chk_Fecha.setText("Fecha");
        chk_Fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FechaItemStateChanged(evt);
            }
        });

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
        tbl_Pedidos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_Pedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Pedidos);

        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });

        chk_Numero.setText("Numero");
        chk_Numero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_NumeroItemStateChanged(evt);
            }
        });

        btn_NuevoPedido.setText("Nuevo");
        btn_NuevoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoPedidoActionPerformed(evt);
            }
        });

        btn_VerFacturas.setText("VerFacturas");
        btn_VerFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VerFacturasActionPerformed(evt);
            }
        });

        btn_Facturar.setText("Facturar");
        btn_Facturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FacturarActionPerformed(evt);
            }
        });

        lb_Desde.setText("Desde:");

        lb_Hasta.setText("Hasta:");

        tbl_RenglonesPedido.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_RenglonesPedido);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(chk_Vendedor, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chk_Cliente, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_Buscar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_NuevoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_VerFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Facturar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                                .addComponent(pb_Filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_Numero)
                            .addComponent(chk_Fecha))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_Desde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dc_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(lb_Hasta)
                                .addGap(18, 18, 18)
                                .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tf_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_Vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_Numero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dc_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_Hasta, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(chk_Fecha)
                    .addComponent(lb_Desde))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chk_Cliente)
                            .addComponent(cmb_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chk_Vendedor)
                            .addComponent(cmb_Vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btn_Buscar)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Facturar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_VerFacturas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_NuevoPedido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pb_Filtro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btn_BuscarActionPerformed

    private void chk_ClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_ClienteItemStateChanged
        if (chk_Cliente.isSelected() == true) {
            cmb_Cliente.setEnabled(true);
            this.cargarComboBoxClientes();
            cmb_Cliente.requestFocus();
        } else {
            cmb_Cliente.removeAllItems();
            cmb_Cliente.setEnabled(false);
        }
    }//GEN-LAST:event_chk_ClienteItemStateChanged

    private void chk_VendedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_VendedorItemStateChanged
        if (chk_Vendedor.isSelected() == true) {
            cmb_Vendedor.setEnabled(true);
            this.cargarComboBoxUsuario();
            cmb_Vendedor.requestFocus();
        } else {
            cmb_Vendedor.removeAllItems();
            cmb_Vendedor.setEnabled(false);
        }
    }//GEN-LAST:event_chk_VendedorItemStateChanged

    private void chk_NumeroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_NumeroItemStateChanged
        if (chk_Numero.isSelected() == true) {
            tf_Numero.setEnabled(true);
            tf_Numero.requestFocus();
        } else {
            tf_Numero.removeAll();
            tf_Numero.setEnabled(false);
        }
    }//GEN-LAST:event_chk_NumeroItemStateChanged

    private void chk_FechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FechaItemStateChanged
        if (chk_Fecha.isSelected() == true) {
            dc_Desde.setEnabled(true);
            dc_Hasta.setEnabled(true);
            dc_Desde.requestFocus();
        } else {
            dc_Desde.setEnabled(false);
            dc_Hasta.setEnabled(false);
        }
    }//GEN-LAST:event_chk_FechaItemStateChanged

    private void btn_NuevoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoPedidoActionPerformed
        try {
            if (this.existeClienteDisponible()) {
                GUI_PuntoDeVenta gui_puntoDeVenta = new GUI_PuntoDeVenta();
                gui_puntoDeVenta.setModal(true);
                gui_puntoDeVenta.setLocationRelativeTo(this);
                gui_puntoDeVenta.setVisible(true);
            } else {
                String mensaje = ResourceBundle.getBundle("Mensajes").getString("mensaje_sin_cliente");
                JOptionPane.showInternalMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showInternalMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevoPedidoActionPerformed

    private void btn_FacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FacturarActionPerformed
        try {
            if (this.existeClienteDisponible()) { //setPedido
                GUI_PuntoDeVenta gui_puntoDeVenta = new GUI_PuntoDeVenta();
                gui_puntoDeVenta.setPedido(pedidoService.getPedidoPorNumero((long) tbl_Pedidos.getValueAt(Utilidades.getSelectedRowModelIndice(tbl_Pedidos), 1)));
                gui_puntoDeVenta.setModal(true);
                gui_puntoDeVenta.setLocationRelativeTo(this);
                gui_puntoDeVenta.setVisible(true);
            } else {
                String mensaje = ResourceBundle.getBundle("Mensajes").getString("mensaje_sin_cliente");
                JOptionPane.showInternalMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showInternalMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_FacturarActionPerformed

    private void btn_VerFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VerFacturasActionPerformed
        GUI_FacturasVenta gui = new GUI_FacturasVenta();
        gui.setLocation(getDesktopPane().getWidth() / 2 - gui.getWidth() / 2,
                getDesktopPane().getHeight() / 2 - gui.getHeight() / 2);
        getDesktopPane().add(gui);
        long numeroDePedido = (long) tbl_Pedidos.getValueAt(Utilidades.getSelectedRowModelIndice(tbl_Pedidos), 1);
        gui.setNumeroDePedido(numeroDePedido);
        gui.setVisible(true);
        gui.buscarPedido();

        try {
            gui.setSelected(true);
        } catch (PropertyVetoException ex) {
            String msjError = "No se pudo seleccionar la ventana requerida.";
            log.error(msjError + " - " + ex.getMessage());
            JOptionPane.showInternalMessageDialog(this.getDesktopPane(), msjError, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_VerFacturasActionPerformed

    private void tbl_PedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PedidosMouseClicked
        int row = tbl_Pedidos.getSelectedRow();
        // int column = tbl_Pedidos.getSelectedColumn();
        this.limpiarTablaRenglones();
        this.setColumnasRenglones();
        long nroPedido = (long) tbl_Pedidos.getValueAt(row, 1);
        Pedido paraListarRenglones = pedidoService.getPedidoPorNumeroConRenglones(nroPedido);
        for (RenglonPedido renglon : paraListarRenglones.getRenglones()) {
            Object[] fila = new Object[4];
            fila[0] = renglon.getProducto().getId_Producto();
            fila[1] = renglon.getProducto().getDescripcion();
            fila[2] = renglon.getCantidad();
            fila[3] = renglon.getSubTotal();
            modeloTablaRenglones.addRow(fila);
        }
        tbl_RenglonesPedido.setModel(modeloTablaRenglones);
        tbl_Pedidos.setRowSelectionInterval(row, row);
    }//GEN-LAST:event_tbl_PedidosMouseClicked


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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_Desde;
    private javax.swing.JLabel lb_Hasta;
    private javax.swing.JProgressBar pb_Filtro;
    private javax.swing.JTable tbl_Pedidos;
    private javax.swing.JTable tbl_RenglonesPedido;
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
            pedidos = pedidoService.buscarConCriteria(criteria);
            this.cargarResultadosAlTable();
            if (pedidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle(
                        "Mensajes").getString("mensaje_busqueda_sin_resultados"),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_pedido_error_al_buscar"),
                    e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTables();
        for (Pedido pedido : pedidos) {
            Object[] fila = new Object[4];
            fila[0] = pedido.getFecha();
            fila[1] = pedido.getNroPedido();
            fila[2] = pedido.getCliente().getRazonSocial();
            fila[3] = pedido.getUsuario().getNombre();
            modeloTablaPedidos.addRow(fila);
        }
        tbl_Pedidos.setModel(modeloTablaPedidos);
    }

    private void limpiarJTables() {
        modeloTablaPedidos = new ModeloTabla();
        tbl_Pedidos.setModel(modeloTablaPedidos);
        modeloTablaRenglones = new ModeloTabla();
        tbl_RenglonesPedido.setModel(modeloTablaRenglones);
        this.setColumnasPedido();
        this.setColumnasRenglones();
    }

    private void limpiarTablaRenglones() {
        modeloTablaRenglones = new ModeloTabla();
        tbl_RenglonesPedido.setModel(modeloTablaRenglones);
        this.setColumnasPedido();
    }

    private void setColumnasRenglones() {
        /// Renglones Pedido
        //sorting
        tbl_RenglonesPedido.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezadoRenglones = new String[4];
        encabezadoRenglones[0] = "id Producto";
        encabezadoRenglones[1] = "Descripcion";
        encabezadoRenglones[2] = "Cantidad";
        encabezadoRenglones[3] = "Subtotal";
        modeloTablaRenglones.setColumnIdentifiers(encabezadoRenglones);
        tbl_RenglonesPedido.setModel(modeloTablaRenglones);

        //tipo de dato columnas
        Class[] tiposRenglones = new Class[modeloTablaRenglones.getColumnCount()];
        tiposRenglones[0] = Long.class;
        tiposRenglones[1] = String.class;
        tiposRenglones[2] = String.class;
        tiposRenglones[3] = String.class;
        modeloTablaRenglones.setClaseColumnas(tiposRenglones);
        tbl_RenglonesPedido.getTableHeader().setReorderingAllowed(false);
        tbl_RenglonesPedido.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_RenglonesPedido.setDefaultRenderer(Double.class, new RenderTabla());

        //Tamanios de columnas
        tbl_RenglonesPedido.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_RenglonesPedido.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbl_RenglonesPedido.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_RenglonesPedido.getColumnModel().getColumn(3).setPreferredWidth(130);
    }

    private void setColumnasPedido() {
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

    private void cargarComboBoxClientes() {
        cmb_Cliente.removeAllItems();
        List<Cliente> clientes;
        clientes = clienteService.getClientes(empresaService.getEmpresaActiva().getEmpresa());
        for (Cliente cliente : clientes) {
            cmb_Cliente.addItem(cliente);
        }
    }

    private void cargarComboBoxUsuario() {
        cmb_Vendedor.removeAllItems();
        List<Usuario> usuarios;
        usuarios = usuarioSercice.getUsuarios();
        for (Usuario usuario : usuarios) {
            cmb_Vendedor.addItem(usuario);
        }
    }

    private boolean existeClienteDisponible() {
        return !clienteService.getClientes(empresaService.getEmpresaActiva().getEmpresa()).isEmpty();
    }

}
