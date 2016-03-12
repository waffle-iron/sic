package sic.vista.swing;

import java.awt.Color;
import java.util.Date;
import java.util.List;
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

        pbl_Cabecera = new javax.swing.JPanel();
        lbl_FormaDePago = new javax.swing.JLabel();
        cmb_FormasDePago = new javax.swing.JComboBox<>();
        btn_abrirCaja = new javax.swing.JButton();
        pnl_Tabla = new javax.swing.JPanel();
        sp_Tabla = new javax.swing.JScrollPane();
        tbl_balance = new javax.swing.JTable();
        lbl_aviso = new javax.swing.JLabel();
        pnl_Arqueo = new javax.swing.JPanel();
        lbl_Total = new javax.swing.JLabel();
        btn_RealizarArqueo = new javax.swing.JButton();
        ftxt_Total = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbl_FormaDePago.setText("Forma de Pago:");

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

        javax.swing.GroupLayout pbl_CabeceraLayout = new javax.swing.GroupLayout(pbl_Cabecera);
        pbl_Cabecera.setLayout(pbl_CabeceraLayout);
        pbl_CabeceraLayout.setHorizontalGroup(
            pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                        .addComponent(lbl_FormaDePago)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                        .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_abrirCaja)))
                .addContainerGap())
        );

        pbl_CabeceraLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_FormasDePago, lbl_FormaDePago});

        pbl_CabeceraLayout.setVerticalGroup(
            pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                .addComponent(lbl_FormaDePago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_abrirCaja)))
        );

        sp_Tabla.setViewportView(tbl_balance);

        lbl_aviso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pnl_TablaLayout = new javax.swing.GroupLayout(pnl_Tabla);
        pnl_Tabla.setLayout(pnl_TablaLayout);
        pnl_TablaLayout.setHorizontalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbl_aviso, sp_Tabla});

        pnl_TablaLayout.setVerticalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lbl_Total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Total.setText("Total:");

        btn_RealizarArqueo.setText("Realizar Arqueo");
        btn_RealizarArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RealizarArqueoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_ArqueoLayout = new javax.swing.GroupLayout(pnl_Arqueo);
        pnl_Arqueo.setLayout(pnl_ArqueoLayout);
        pnl_ArqueoLayout.setHorizontalGroup(
            pnl_ArqueoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ArqueoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Total)
                .addGap(3, 3, 3)
                .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_RealizarArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_ArqueoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_RealizarArqueo, ftxt_Total, lbl_Total});

        pnl_ArqueoLayout.setVerticalGroup(
            pnl_ArqueoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ArqueoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_ArqueoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Total)
                    .addComponent(btn_RealizarArqueo)
                    .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_ArqueoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_RealizarArqueo, lbl_Total});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnl_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbl_Cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_Arqueo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pbl_Cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Arqueo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            if (formaDePago.isAfectaCaja()) {
                cmb_FormasDePago.addItem(formaDePago);
            }
        }
        tbl_balance.setEnabled(false);
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_formWindowOpened

    private void btn_abrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrirCajaActionPerformed
        GUI_AbrirCaja abrirCaja = new GUI_AbrirCaja(this, true, (FormaDePago) cmb_FormasDePago.getSelectedItem());
        abrirCaja.setLocationRelativeTo(this);
        abrirCaja.setVisible(true);
    }//GEN-LAST:event_btn_abrirCajaActionPerformed

    private void cmb_FormasDePagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_FormasDePagoItemStateChanged
        this.limpiarTabla();
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_cmb_FormasDePagoItemStateChanged

    private void btn_RealizarArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RealizarArqueoActionPerformed
        Caja cajaACerrar = controlCajaService.getCajaSinArqueoPorFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago());
        GUI_CerrarCaja cerrarCaja = new GUI_CerrarCaja(this, true, cajaACerrar, this.calcularTotal(cajaACerrar)[2]);
        cerrarCaja.setLocationRelativeTo(this);
        cerrarCaja.setVisible(true);
        this.limpiarTabla();
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_btn_RealizarArqueoActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.limpiarTabla();
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_formWindowGainedFocus

    private void cambiarEstadoAviso() {
        Caja consulta = controlCajaService.getCajaSinArqueoPorFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago());
        if (consulta == null) {
            lbl_aviso.setText("Caja sin abrir");
            lbl_aviso.setForeground(Color.RED);
            this.ftxt_Total.setText("0.0");
            this.btn_abrirCaja.setEnabled(true);
        } else {
            this.btn_abrirCaja.setEnabled(false);
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
    private javax.swing.JLabel lbl_FormaDePago;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_aviso;
    private javax.swing.JPanel pbl_Cabecera;
    private javax.swing.JPanel pnl_Arqueo;
    private javax.swing.JPanel pnl_Tabla;
    private javax.swing.JScrollPane sp_Tabla;
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
            if (factura instanceof FacturaVenta) {
                fila[0] = ((FacturaVenta) factura).getUsuario().getNombre();
            } else {
                fila[0] = ((FacturaCompra) factura).getProveedor().getRazonSocial();
            }

            fila[1] = factura.getFecha();
            fila[2] = factura.getObservaciones();
            if (factura instanceof FacturaVenta) {
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

    private double[] calcularTotal(Caja caja) {
        double totalDebe = 0.0;
        double totalHaber = 0.0;
        double totalBalance = 0.0;
        int cantidadDeFilas = tbl_balance.getRowCount();
        for (int i = 0; i < cantidadDeFilas; i++) {
            totalDebe += (double) tbl_balance.getValueAt(i, 3);
            totalHaber += (double) tbl_balance.getValueAt(i, 4);
        }
        totalBalance = caja.getSaldoInicial() + totalDebe - totalHaber;
        this.ftxt_Total.setText(String.valueOf(totalBalance));
        double valores[] = {totalDebe, totalHaber, totalBalance};
        return valores;
    }
}
