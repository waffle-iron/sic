package sic.vista.swing;

import java.util.Calendar;
import java.util.Date;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.springframework.context.ApplicationContext;
import sic.AppContextProvider;
import sic.modelo.Caja;
import sic.service.EstadoCaja;
import sic.service.ICajaService;
import sic.service.IEmpresaService;
import sic.service.IUsuarioService;

public class GUI_AbrirCaja extends javax.swing.JDialog {

    private final ApplicationContext appContext = AppContextProvider.getApplicationContext();
    private final IEmpresaService empresaService = appContext.getBean(IEmpresaService.class);
    private final IUsuarioService usuarioService = appContext.getBean(IUsuarioService.class);
    private final ICajaService cajaService = appContext.getBean(ICajaService.class);

    public GUI_AbrirCaja(boolean modal) {
        this.setModal(true);
        initComponents();
        this.setModelSpinner();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_separador = new javax.swing.JPanel();
        spinner_Hora = new javax.swing.JSpinner();
        spinner_Minutos = new javax.swing.JSpinner();
        lbl_CorteControl = new javax.swing.JLabel();
        lbl_separador = new javax.swing.JLabel();
        ftxt_Monto = new javax.swing.JFormattedTextField();
        lbl_monto = new javax.swing.JLabel();
        pnl_Botones = new javax.swing.JPanel();
        btn_AbrirCaja = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Abrir Caja");
        setResizable(false);

        lbl_CorteControl.setText("Hora de Control:");

        lbl_separador.setText(":");

        ftxt_Monto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        ftxt_Monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ftxt_MontoKeyTyped(evt);
            }
        });

        lbl_monto.setText("Monto:");

        javax.swing.GroupLayout pnl_separadorLayout = new javax.swing.GroupLayout(pnl_separador);
        pnl_separador.setLayout(pnl_separadorLayout);
        pnl_separadorLayout.setHorizontalGroup(
            pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_separadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_separadorLayout.createSequentialGroup()
                        .addComponent(lbl_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxt_Monto))
                    .addGroup(pnl_separadorLayout.createSequentialGroup()
                        .addComponent(lbl_CorteControl, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_Hora)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_separador)
                        .addGap(0, 0, 0)
                        .addComponent(spinner_Minutos)))
                .addContainerGap())
        );

        pnl_separadorLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbl_CorteControl, lbl_monto});

        pnl_separadorLayout.setVerticalGroup(
            pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_separadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinner_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinner_Minutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_separador))
                    .addComponent(lbl_CorteControl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(pnl_separadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_monto)
                    .addComponent(ftxt_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_separadorLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftxt_Monto, lbl_CorteControl, lbl_monto, spinner_Hora, spinner_Minutos});

        btn_AbrirCaja.setText("Abrir Caja");
        btn_AbrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AbrirCajaActionPerformed(evt);
            }
        });

        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_BotonesLayout = new javax.swing.GroupLayout(pnl_Botones);
        pnl_Botones.setLayout(pnl_BotonesLayout);
        pnl_BotonesLayout.setHorizontalGroup(
            pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_BotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_AbrirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btn_Cancelar)
                .addContainerGap())
        );

        pnl_BotonesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_AbrirCaja, btn_Cancelar});

        pnl_BotonesLayout.setVerticalGroup(
            pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_BotonesLayout.createSequentialGroup()
                .addGroup(pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_AbrirCaja)
                    .addComponent(btn_Cancelar))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_separador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_separador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Botones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ftxt_MontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftxt_MontoKeyTyped
        char tecla = evt.getKeyChar();
        if (Character.isLetter(tecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_ftxt_MontoKeyTyped

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_AbrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AbrirCajaActionPerformed
        cajaService.guardar(this.construirCaja(((Long) ftxt_Monto.getValue()).doubleValue()));
        GUI_Caja abrirCaja = new GUI_Caja(cajaService.getUltimaCaja(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa()));
        abrirCaja.setModal(true);
        abrirCaja.setLocationRelativeTo(this);
        abrirCaja.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_AbrirCajaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AbrirCaja;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JFormattedTextField ftxt_Monto;
    private javax.swing.JLabel lbl_CorteControl;
    private javax.swing.JLabel lbl_monto;
    private javax.swing.JLabel lbl_separador;
    private javax.swing.JPanel pnl_Botones;
    private javax.swing.JPanel pnl_separador;
    private javax.swing.JSpinner spinner_Hora;
    private javax.swing.JSpinner spinner_Minutos;
    // End of variables declaration//GEN-END:variables

    private void setModelSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 24, 1);
        this.spinner_Hora.setModel(spinnerModel);
        spinnerModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.MINUTE), 59, 1);
        this.spinner_Minutos.setModel(spinnerModel);
    }

    private Caja construirCaja(Double monto) {
        Caja caja = new Caja();
        caja.setEstado(EstadoCaja.ABIERTA);
        caja.setObservacion("Apertura De Caja");
        caja.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
        caja.setFechaApertura(new Date());
        Calendar corte = Calendar.getInstance();
        corte.set(Calendar.HOUR_OF_DAY, (int) spinner_Hora.getValue());
        corte.set(Calendar.MINUTE, (int) spinner_Minutos.getValue());
        caja.setFechaCorteInforme(corte.getTime());
        caja.setNroCaja(cajaService.getUltimoNumeroDeCaja(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa()) + 1);
        caja.setSaldoInicial(monto);
        caja.setSaldoFinal(monto);
        caja.setSaldoReal(monto);
        caja.setUsuarioAbreCaja(usuarioService.getUsuarioActivo().getUsuario());
        return caja;
    }
}
