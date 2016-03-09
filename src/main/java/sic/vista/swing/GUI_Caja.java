package sic.vista.swing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import sic.modelo.Caja;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.service.CajaService;
import sic.service.EmpresaService;
import sic.service.FacturaService;
import sic.service.FormaDePagoService;

public class GUI_Caja extends javax.swing.JDialog {

    private final CajaService controlCajaService = new CajaService();
    private final EmpresaService empresaService = new EmpresaService();
    private final FormaDePagoService formaDePagoService = new FormaDePagoService();
    private final FacturaService facturaService = new FacturaService();
    private ModeloTabla modeloTablaBalance;

    public GUI_Caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.limpiarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmb_FormasDePago = new javax.swing.JComboBox<>();
        btn_abrirCaja = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_balance = new javax.swing.JTable();
        lbl_aviso = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_RealizarArqueo = new javax.swing.JButton();
        ftxt_Total = new javax.swing.JFormattedTextField();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel2.setText("Forma de Pago:");

        cmb_FormasDePago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_FormasDePagoItemStateChanged(evt);
            }
        });

        btn_abrirCaja.setText("Abrir Caja");
        btn_abrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrirCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_abrirCaja)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_FormasDePago, jLabel2});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_abrirCaja)))
        );

        jScrollPane1.setViewportView(tbl_balance);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, lbl_aviso});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setText("Total:");

        btn_RealizarArqueo.setText("Realizar Arqueo");
        btn_RealizarArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RealizarArqueoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_RealizarArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_RealizarArqueo, ftxt_Total, jLabel3});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btn_RealizarArqueo)
                    .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_RealizarArqueo, jLabel3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormasDePago.addItem(formaDePago);
        }
        tbl_balance.setEnabled(false);
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_formWindowOpened

    private void btn_abrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrirCajaActionPerformed
        GUI_abrirCaja abrirCaja = new GUI_abrirCaja(this, true, (FormaDePago) cmb_FormasDePago.getSelectedItem());
        abrirCaja.setVisible(true);
        abrirCaja.setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_abrirCajaActionPerformed

    private void cmb_FormasDePagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_FormasDePagoItemStateChanged
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_cmb_FormasDePagoItemStateChanged

    private void btn_RealizarArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RealizarArqueoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_RealizarArqueoActionPerformed

    private void cambiarEstadoAviso() {
        Caja consulta = controlCajaService.getCajaSinArqueoPorFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago());
        if (consulta == null) {
            lbl_aviso.setText("Caja sin abrir");
            lbl_aviso.setForeground(Color.RED);
        } else {
            List<Factura> facturas = facturaService.getFacturasPorFechas(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), consulta.getFechaApertura(), new Date());
            lbl_aviso.setText("Caja abierta -- Saldo Inicial: " + consulta.getSaldoInicial());
            lbl_aviso.setForeground(Color.GREEN);
            this.ftxt_Total.setText(String.valueOf(consulta.getSaldoInicial()));
            this.cargarFacturasEnLaTabla(facturas);
            this.calcularTotal(consulta);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_RealizarArqueo;
    private javax.swing.JButton btn_abrirCaja;
    private javax.swing.JComboBox<FormaDePago> cmb_FormasDePago;
    private javax.swing.JFormattedTextField ftxt_Total;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_aviso;
    private javax.swing.JTable tbl_balance;
    // End of variables declaration//GEN-END:variables

    private void limpiarTabla() {
        modeloTablaBalance = new ModeloTabla();
        tbl_balance.setModel(modeloTablaBalance);
        this.setColumnasDeTabla();
    }

    private void setColumnasDeTabla() {
        //sorting
        tbl_balance.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[6];
        encabezados[0] = "Usuario";
        encabezados[1] = "Fecha";
        encabezados[2] = "Concepto";
        encabezados[3] = "Debe";
        encabezados[4] = "Haber";
        encabezados[5] = "Saldo";
        modeloTablaBalance.setColumnIdentifiers(encabezados);
        tbl_balance.setModel(modeloTablaBalance);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaBalance.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = Date.class;
        tipos[2] = String.class;
        tipos[3] = Double.class;
        tipos[4] = Double.class;
        tipos[5] = Double.class;
        modeloTablaBalance.setClaseColumnas(tipos);
        tbl_balance.getTableHeader().setReorderingAllowed(false);
        tbl_balance.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_balance.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_balance.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbl_balance.getColumnModel().getColumn(5).setPreferredWidth(100);

    }

    private void cargarFacturasEnLaTabla(List<Factura> facturas) {
        this.limpiarTabla();
        for (Factura factura : facturas) {
            Object[] fila = new Object[6];
            if (factura instanceof FacturaVenta) {  //revisar, agrega dificultades
                fila[0] = ((FacturaVenta) factura).getUsuario().getNombre();
            } else {
                fila[0] = ((FacturaCompra) factura).getProveedor().getRazonSocial();
            }

            fila[1] = factura.getFecha();
            fila[2] = factura.getObservaciones();
            if (factura instanceof FacturaVenta) {  //revisar, agrega dificultades
                fila[3] = factura.getTotal();
                fila[4] = 0.0;
            } else {
                fila[3] = 0.0;
                fila[4] = factura.getTotal();
            }
            fila[5] = factura.getTotal();
            modeloTablaBalance.addRow(fila);
        }
        tbl_balance.setModel(modeloTablaBalance);
    }

    private void calcularTotal(Caja caja) {
        double totalDebe = 0;
        double totalHaber = 0;
        int cantidadDeFilas = tbl_balance.getRowCount();
        for (int i = 0; i < cantidadDeFilas; i++) {
            totalDebe += (double) tbl_balance.getValueAt(i, 3);
            totalHaber += (double) tbl_balance.getValueAt(i, 4);
        }
        this.ftxt_Total.setText(String.valueOf(caja.getSaldoInicial() + totalDebe - totalHaber));
    }
}
