package sic.vista.swing;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import sic.modelo.Caja;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.service.CajaService;
import sic.service.EmpresaService;
import sic.service.FacturaService;
import sic.service.FormaDePagoService;

public class GUI_Caja extends javax.swing.JDialog {

    private final CajaService cajaService = new CajaService();
    private final EmpresaService empresaService = new EmpresaService();
    private final FormaDePagoService formaDePagoService = new FormaDePagoService();
    private final FacturaService facturaService = new FacturaService();
    private ModeloTabla modeloTablaBalance;
    private ModeloTabla modeloTablaResumen;
    private List<Object> listaMovimientos;
    private Caja caja;

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Resumen = new javax.swing.JTable();
        pnl_Arqueo = new javax.swing.JPanel();
        lbl_Total = new javax.swing.JLabel();
        btn_RealizarArqueo = new javax.swing.JButton();
        ftxt_Total = new javax.swing.JFormattedTextField();
        btn_VerDetalle = new javax.swing.JButton();

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

        cmb_FormasDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_FormasDePagoActionPerformed(evt);
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

        tbl_Resumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_Resumen);

        javax.swing.GroupLayout pnl_TablaLayout = new javax.swing.GroupLayout(pnl_Tabla);
        pnl_Tabla.setLayout(pnl_TablaLayout);
        pnl_TablaLayout.setHorizontalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(lbl_aviso, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(sp_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, lbl_aviso, sp_Tabla});

        pnl_TablaLayout.setVerticalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, sp_Tabla});

        lbl_Total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Total.setText("Total:");

        btn_RealizarArqueo.setText("Realizar Arqueo");
        btn_RealizarArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RealizarArqueoActionPerformed(evt);
            }
        });

        btn_VerDetalle.setText("Ver Detalle");
        btn_VerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VerDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_ArqueoLayout = new javax.swing.GroupLayout(pnl_Arqueo);
        pnl_Arqueo.setLayout(pnl_ArqueoLayout);
        pnl_ArqueoLayout.setHorizontalGroup(
            pnl_ArqueoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ArqueoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_VerDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(43, 43, 43)
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
                .addContainerGap()
                .addGroup(pnl_ArqueoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Total)
                    .addComponent(btn_RealizarArqueo)
                    .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_VerDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl_ArqueoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_RealizarArqueo, lbl_Total});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbl_Cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_Arqueo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pbl_Cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Arqueo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        this.limpiarTablaResumen();
        if (this.caja != null) {
            this.cargarTablaResumen();
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_abrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrirCajaActionPerformed
        GUI_AbrirCaja abrirCaja = new GUI_AbrirCaja(this, true);
        abrirCaja.setLocationRelativeTo(this);
        abrirCaja.setVisible(true);
    }//GEN-LAST:event_btn_abrirCajaActionPerformed

    private void btn_RealizarArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RealizarArqueoActionPerformed
        Caja cajaACerrar = cajaService.getCajaSinArqueo(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
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

    private void cmb_FormasDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_FormasDePagoActionPerformed
        this.limpiarTabla();
        this.cambiarEstadoAviso();
    }//GEN-LAST:event_cmb_FormasDePagoActionPerformed

    private void btn_VerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VerDetalleActionPerformed

    }//GEN-LAST:event_btn_VerDetalleActionPerformed

    private void cambiarEstadoAviso() {
        this.caja = cajaService.getCajaSinArqueo(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
        if (caja == null) {
            lbl_aviso.setText("Caja sin abrir");
            lbl_aviso.setForeground(Color.RED);
            this.ftxt_Total.setText("0.0");
            this.btn_abrirCaja.setEnabled(true);
            this.limpiarTablaResumen();
        } else {
            this.btn_abrirCaja.setEnabled(false);
            List<Object> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos = facturas;
            lbl_aviso.setText("Caja abierta -- Saldo Inicial: " + caja.getSaldoInicial());
            lbl_aviso.setForeground(Color.GREEN);
            this.ftxt_Total.setText(String.valueOf(caja.getSaldoInicial()));
            this.cargarMovimientosEnLaTabla(this.listaMovimientos);
            this.calcularTotal(caja);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_RealizarArqueo;
    private javax.swing.JButton btn_VerDetalle;
    private javax.swing.JButton btn_abrirCaja;
    private javax.swing.JComboBox<FormaDePago> cmb_FormasDePago;
    private javax.swing.JFormattedTextField ftxt_Total;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_FormaDePago;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_aviso;
    private javax.swing.JPanel pbl_Cabecera;
    private javax.swing.JPanel pnl_Arqueo;
    private javax.swing.JPanel pnl_Tabla;
    private javax.swing.JScrollPane sp_Tabla;
    private javax.swing.JTable tbl_Resumen;
    private javax.swing.JTable tbl_balance;
    // End of variables declaration//GEN-END:variables

    private void limpiarTabla() {
        modeloTablaBalance = new ModeloTabla();
        tbl_balance.setModel(modeloTablaBalance);
        this.setColumnasDeTablaFormaDePago();
    }

    private void limpiarTablaResumen() {
        modeloTablaResumen = new ModeloTabla();
        tbl_Resumen.setModel(modeloTablaResumen);
        this.setColumnasDeTablaResumen();
    }

    private void setColumnasDeTablaFormaDePago() {
        //sorting
        tbl_balance.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[5];
        encabezados[0] = "Fecha";
        encabezados[1] = "Numero De Factura";
        encabezados[2] = "Debe";
        encabezados[3] = "Haber";
        encabezados[4] = "Saldo";
        modeloTablaBalance.setColumnIdentifiers(encabezados);
        tbl_balance.setModel(modeloTablaBalance);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaBalance.getColumnCount()];
        tipos[0] = Date.class;
        tipos[1] = String.class;
        tipos[2] = Double.class;
        tipos[3] = Double.class;
        tipos[4] = Double.class;
        modeloTablaBalance.setClaseColumnas(tipos);
        tbl_balance.getTableHeader().setReorderingAllowed(false);
        tbl_balance.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_balance.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_balance.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_balance.getColumnModel().getColumn(4).setPreferredWidth(100);

    }

    private void cargarMovimientosEnLaTabla(List<Object> movimientos) {
        this.limpiarTabla();
        for (Object movimiento : movimientos) {
            Object[] fila = new Object[5];
            if (movimiento instanceof FacturaCompra) {
                fila[0] = ((FacturaCompra) movimiento).getFecha();
            }
            if (movimiento instanceof FacturaVenta) {
                fila[0] = ((FacturaVenta) movimiento).getFecha();
            }
            if (movimiento instanceof FacturaCompra) {
                fila[2] = 0.0;
                fila[3] = ((FacturaCompra) movimiento).getTotal();
            }
            if (movimiento instanceof FacturaVenta) {
                fila[2] = ((FacturaVenta) movimiento).getTotal();
                fila[3] = 0.0;
            }
            fila[1] = ((Factura) movimiento).getNumSerie() + " - " + ((Factura) movimiento).getNumFactura();
            fila[4] = (double) fila[2] - (double) fila[3];
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
            totalDebe += (double) tbl_balance.getValueAt(i, 2);
            totalHaber += (double) tbl_balance.getValueAt(i, 3);
        }
        totalBalance = caja.getSaldoInicial() + totalDebe - totalHaber;
        this.ftxt_Total.setText(String.valueOf(totalBalance));
        double valores[] = {totalDebe, totalHaber, totalBalance};
        return valores;
    }

    private void setColumnasDeTablaResumen() {
        //sorting
        tbl_Resumen.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[2];
        encabezados[0] = "Resumen";
        encabezados[1] = "Monto";
        modeloTablaResumen.setColumnIdentifiers(encabezados);
        tbl_Resumen.setModel(modeloTablaResumen);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaResumen.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = Double.class;
        modeloTablaResumen.setClaseColumnas(tipos);
        tbl_Resumen.getTableHeader().setReorderingAllowed(false);
        tbl_Resumen.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Resumen.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Resumen.getColumnModel().getColumn(1).setPreferredWidth(50);

    }

    private void cargarTablaResumen() {
        Empresa empresaActiva = empresaService.getEmpresaActiva().getEmpresa();
        this.caja = cajaService.getCajaSinArqueo(empresaActiva.getId_Empresa());
        double total = this.caja.getSaldoInicial();
        Object[] saldoInicial = new Object[2];
        saldoInicial[0] = "Saldo Inicial";
        saldoInicial[1] = total;
        modeloTablaResumen.addRow(saldoInicial);
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaActiva);
        for (FormaDePago formaDePago : formasDePago) {
            Object[] fila = new Object[2];
            List<Object> facturasPorFormaDePago = facturaService.getFacturasPorFechasYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            fila[0] = formaDePago.getNombre();
            double totalParcial = cajaService.calcularTotalPorMovimiento(facturasPorFormaDePago);
            fila[1] = totalParcial;
            total += totalParcial;
            modeloTablaResumen.addRow(fila);
        }
        Object[] fila = new Object[2];
        fila[0] = "TOTAL";
        fila[1] = total;
        modeloTablaResumen.addRow(fila);
        tbl_Resumen.setModel(modeloTablaResumen);
    }
}
