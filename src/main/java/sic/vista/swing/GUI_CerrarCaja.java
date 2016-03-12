package sic.vista.swing;

import java.util.Date;
import sic.modelo.Caja;
import sic.service.CajaService;

public class GUI_CerrarCaja extends javax.swing.JDialog {

    private Caja caja;
    private final CajaService cajaService = new CajaService();

    public GUI_CerrarCaja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public GUI_CerrarCaja(javax.swing.JDialog parent, boolean modal, Caja caja, double saldoFinal) {
        super(parent, modal);
        initComponents();
        this.caja = caja;
        this.ftxt_SaldoDelSistema.setText(String.valueOf(saldoFinal));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_SaldoDelSistema = new javax.swing.JLabel();
        lbl_SaldoReal = new javax.swing.JLabel();
        ftxt_SaldoReal = new javax.swing.JFormattedTextField();
        ftxt_SaldoDelSistema = new javax.swing.JFormattedTextField();
        btn_Aceptar = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_SaldoDelSistema.setText("Saldo del Sistema");

        lbl_SaldoReal.setText("Saldo Real");

        ftxt_SaldoReal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        ftxt_SaldoReal.setText("0.0");

        ftxt_SaldoDelSistema.setEditable(false);
        ftxt_SaldoDelSistema.setEnabled(false);

        btn_Aceptar.setText("Aceptar");
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_SaldoDelSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxt_SaldoDelSistema))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_SaldoReal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_Aceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Cancelar))
                            .addComponent(ftxt_SaldoReal))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbl_SaldoDelSistema, lbl_SaldoReal});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_SaldoDelSistema)
                    .addComponent(ftxt_SaldoDelSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_SaldoReal)
                    .addComponent(ftxt_SaldoReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Aceptar)
                    .addComponent(btn_Cancelar))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftxt_SaldoDelSistema, ftxt_SaldoReal});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        this.caja.setSaldoReal(Double.parseDouble(this.ftxt_SaldoReal.getValue().toString()));
        this.caja.setFechaCierre(new Date());
        this.caja.setCerrada(true);
        cajaService.actualizar(caja);
        this.dispose();
    }//GEN-LAST:event_btn_AceptarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JFormattedTextField ftxt_SaldoDelSistema;
    private javax.swing.JFormattedTextField ftxt_SaldoReal;
    private javax.swing.JLabel lbl_SaldoDelSistema;
    private javax.swing.JLabel lbl_SaldoReal;
    // End of variables declaration//GEN-END:variables
}
