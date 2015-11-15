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

public class GUI_Pedidos extends javax.swing.JInternalFrame {

    private EmpresaService empresaService = new EmpresaService();
    private PedidoService pedidoService = new PedidoService();
    private ClienteService clienteService = new ClienteService();
    private UsuarioService usuarioSercice = new UsuarioService();
    private List<Pedido> pedidos;
    private ModeloTabla modeloTablaPedidos;

    public GUI_Pedidos() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chk_Numero = new javax.swing.JCheckBox();
        chk_Fecha = new javax.swing.JCheckBox();
        chk_Cliente = new javax.swing.JCheckBox();
        chk_Vendedor = new javax.swing.JCheckBox();
        tf_Numero = new javax.swing.JTextField();
        dc_Desde = new com.toedter.calendar.JDateChooser();
        dc_Hasta = new com.toedter.calendar.JDateChooser();
        cmb_Cliente = new javax.swing.JComboBox();
        cmb_Vendedor = new javax.swing.JComboBox();
        btn_Buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pedidos = new javax.swing.JTable();
        btn_NuevoPedido = new javax.swing.JButton();
        btn_VerFacturas = new javax.swing.JButton();
        btn_Facturar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        chk_Numero.setText("Numero");

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

        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
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

        btn_NuevoPedido.setText("Nuevo Pedido");

        btn_VerFacturas.setText("Ver Facturas");

        btn_Facturar.setText("Facturar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_Numero)
                            .addComponent(chk_Fecha)
                            .addComponent(chk_Cliente)
                            .addComponent(chk_Vendedor))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_Numero)
                            .addComponent(dc_Desde, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(cmb_Cliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_Vendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_NuevoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_VerFacturas)
                        .addGap(39, 39, 39)
                        .addComponent(btn_Facturar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chk_Numero)
                            .addComponent(tf_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chk_Fecha))
                    .addComponent(dc_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dc_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_Cliente)
                    .addComponent(cmb_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_Vendedor)
                    .addComponent(cmb_Vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_NuevoPedido)
                    .addComponent(btn_VerFacturas)
                    .addComponent(btn_Facturar))
                .addContainerGap(41, Short.MAX_VALUE))
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
            cmb_Cliente.setEnabled(false);
        }
    }//GEN-LAST:event_chk_ClienteItemStateChanged

    private void chk_VendedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_VendedorItemStateChanged
        if (chk_Vendedor.isSelected() == true) {
            cmb_Vendedor.setEnabled(true);
            this.cargarComboBoxUsuario();
            cmb_Vendedor.requestFocus();
        } else {
            cmb_Vendedor.setEnabled(false);
        }
    }//GEN-LAST:event_chk_VendedorItemStateChanged


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
            pedidos = pedidoService.buscarConCriteria(criteria);
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
        //String mensaje = pedidos.size() + " facturas encontradas.";
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

}
