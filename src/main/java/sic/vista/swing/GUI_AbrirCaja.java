package sic.vista.swing;

import java.util.Date;
import javax.swing.JDialog;
import sic.modelo.Caja;
import sic.service.CajaService;
import sic.service.EmpresaService;
import sic.service.UsuarioService;

public class GUI_AbrirCaja extends javax.swing.JDialog {

    private final CajaService cajaService = new CajaService();
    private final EmpresaService empresaService = new EmpresaService();
    private final UsuarioService usuarioService = new UsuarioService();

    public GUI_AbrirCaja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public GUI_AbrirCaja(JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public GUI_AbrirCaja() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_AbrirCaja = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ftxt_Monto = new javax.swing.JFormattedTextField();
        btn_Cancelar = new javax.swing.JButton();
        btn_Aceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Abrir Caja");

        jLabel1.setText("Monto para apertura de caja:");

        ftxt_Monto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        ftxt_Monto.setText("0.0");

        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_Aceptar.setText("Aceptar");
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_AbrirCajaLayout = new javax.swing.GroupLayout(pnl_AbrirCaja);
        pnl_AbrirCaja.setLayout(pnl_AbrirCajaLayout);
        pnl_AbrirCajaLayout.setHorizontalGroup(
            pnl_AbrirCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_AbrirCajaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnl_AbrirCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_AbrirCajaLayout.createSequentialGroup()
                        .addComponent(ftxt_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Aceptar))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_AbrirCajaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ftxt_Monto, jLabel1});

        pnl_AbrirCajaLayout.setVerticalGroup(
            pnl_AbrirCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_AbrirCajaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_AbrirCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxt_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Cancelar)
                    .addComponent(btn_Aceptar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnl_AbrirCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_AbrirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        Caja temporal = this.construirCaja();
        cajaService.guardar(temporal);
        this.dispose();
    }//GEN-LAST:event_btn_AceptarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JFormattedTextField ftxt_Monto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnl_AbrirCaja;
    // End of variables declaration//GEN-END:variables

    private Caja construirCaja() {
        Caja nueva = new Caja();
        nueva.setCerrada(false);
        nueva.setConcepto("Apertura De Caja");
        nueva.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
        nueva.setFechaApertura(new Date());
        nueva.setNroCaja(cajaService.getUltimoNumeroDeCaja(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa()) + 1);
        nueva.setSaldoInicial(Double.parseDouble(ftxt_Monto.getValue().toString()));
        nueva.setSaldoFinal(Double.parseDouble(ftxt_Monto.getValue().toString()));
        nueva.setSaldoReal(Double.parseDouble(ftxt_Monto.getValue().toString()));
        nueva.setUsuarioAbreCaja(usuarioService.getUsuarioActivo().getUsuario());
        return nueva;
    }

}
