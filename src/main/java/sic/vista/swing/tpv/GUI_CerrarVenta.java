package sic.vista.swing.tpv;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;
import sic.modelo.Factura;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.service.*;
import sic.vista.swing.ModeloTabla;
import sic.vista.swing.administracion.GUI_DetalleCliente;

public class GUI_CerrarVenta extends JDialog {

    private final GUI_PrincipalTPV gui_tpv;
    private boolean exito;
    private final FormaDePagoService formaDePagoService = new FormaDePagoService();
    private final TransportistaService transportistaService = new TransportistaService();
    private final FacturaService facturaService = new FacturaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final HotKeysHandler keyHandler = new HotKeysHandler();
    private static final Logger log = Logger.getLogger(GUI_CerrarVenta.class.getPackage().getName());

    public GUI_CerrarVenta(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIcon();
        this.setLocationRelativeTo(null);
        this.gui_tpv = (GUI_PrincipalTPV) parent;
        lbl_Vendedor.setText("");
        lbl_TotalAPagar.setValue(gui_tpv.getResultadosFactura().getTotal());
        lbl_Vuelto.setValue(0);

        //listeners
        cmb_FormaDePago.addKeyListener(keyHandler);
        cmb_Transporte.addKeyListener(keyHandler);
        txt_AbonaCon.addKeyListener(keyHandler);
        btn_Finalizar.addKeyListener(keyHandler);
    }

    public boolean isExito() {
        return exito;
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void lanzarReporteFactura(Factura factura) throws JRException {
        JasperPrint report = facturaService.getReporteFacturaVenta(factura);
        JDialog viewer = new JDialog(new JFrame(), "Vista Previa", true);
        viewer.setSize(gui_tpv.getWidth() - 25, gui_tpv.getHeight() - 25);
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
        viewer.setIconImage(iconoVentana.getImage());
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(report);
        viewer.getContentPane().add(jrv);
        viewer.setVisible(true);
    }

    private void cargarFormasDePago() {
        cmb_FormaDePago.removeAllItems();
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(gui_tpv.getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormaDePago.addItem(formaDePago);
        }
    }

    private void cargarTransportistas() {
        cmb_Transporte.removeAllItems();
        List<Transportista> transportes = transportistaService.getTransportistas(gui_tpv.getEmpresa());
        for (Transportista transporte : transportes) {
            cmb_Transporte.addItem(transporte);
        }
    }

    private Factura guardarFactura(Factura facturaVenta) throws ServiceException {
        facturaService.guardar(facturaVenta);
        return facturaService.getFacturaVentaPorTipoSerieNum(facturaVenta.getTipoFactura(), facturaVenta.getNumSerie(), facturaVenta.getNumFactura());
    }

    private void calcularVuelto() {
        try {
            txt_AbonaCon.commitEdit();
            double montoAbonado = Double.parseDouble(txt_AbonaCon.getValue().toString());
            double vuelto = facturaService.calcularVuelto(gui_tpv.getResultadosFactura().getTotal(), montoAbonado);
            lbl_Vuelto.setValue(vuelto);

        } catch (ParseException ex) {
            String msjError = "Se produjo un error analizando los campos.";
            log.error(msjError + " - " + ex.getMessage());
        }
    }

    private FacturaVenta construirFactura() {
        FacturaVenta facturaVenta = new FacturaVenta();
        facturaVenta.setFecha(new Date());
        facturaVenta.setTipoFactura(gui_tpv.getTipoDeFactura());
        facturaVenta.setNumSerie(1);
        facturaVenta.setNumFactura(facturaService.calcularNumeroFactura(gui_tpv.getTipoDeFactura(), 1));
        facturaVenta.setFormaPago((FormaDePago) cmb_FormaDePago.getSelectedItem());
        facturaVenta.setFechaVencimiento(new Date());
        facturaVenta.setTransportista((Transportista) cmb_Transporte.getSelectedItem());
        List<RenglonFactura> lineasFactura = new ArrayList<>(gui_tpv.getRenglones());
        facturaVenta.setRenglones(lineasFactura);
        for (RenglonFactura renglon : lineasFactura) {
            renglon.setFactura(facturaVenta);
        }
        facturaVenta.setSubTotal(gui_tpv.getResultadosFactura().getSubTotal());
        facturaVenta.setRecargo_porcentaje(gui_tpv.getResultadosFactura().getRecargo_porcentaje());
        facturaVenta.setRecargo_neto(gui_tpv.getResultadosFactura().getRecargo_neto());
        facturaVenta.setDescuento_porcentaje(0);
        facturaVenta.setDescuento_neto(0);
        facturaVenta.setSubTotal_neto(gui_tpv.getResultadosFactura().getSubTotal_neto());
        facturaVenta.setIva_105_neto(gui_tpv.getResultadosFactura().getIva_105_neto());
        facturaVenta.setIva_21_neto(gui_tpv.getResultadosFactura().getIva_21_neto());
        facturaVenta.setImpuestoInterno_neto(gui_tpv.getResultadosFactura().getImpuestoInterno_neto());
        facturaVenta.setTotal(gui_tpv.getResultadosFactura().getTotal());
        facturaVenta.setObservaciones(gui_tpv.getTxta_Observaciones().getText().trim());
        facturaVenta.setPagada(true);
        facturaVenta.setEmpresa(gui_tpv.getEmpresa());
        facturaVenta.setEliminada(false);
        facturaVenta.setCliente(gui_tpv.getCliente());
        facturaVenta.setUsuario(usuarioService.getUsuarioActivo().getUsuario());

        return facturaVenta;
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

            if (evt.getKeyCode() == KeyEvent.VK_ENTER && evt.getSource() == btn_Finalizar) {
                btn_FinalizarActionPerformed(null);
            }
        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        lbl_FormaDePago = new javax.swing.JLabel();
        cmb_FormaDePago = new javax.swing.JComboBox();
        lbl_Transporte = new javax.swing.JLabel();
        cmb_Transporte = new javax.swing.JComboBox();
        lbl_Cambio = new javax.swing.JLabel();
        lbl_Total = new javax.swing.JLabel();
        lbl_Pago = new javax.swing.JLabel();
        txt_AbonaCon = new javax.swing.JFormattedTextField();
        lbl_Devolucion = new javax.swing.JLabel();
        lbl_Vendor = new javax.swing.JLabel();
        lbl_Vendedor = new javax.swing.JLabel();
        separador = new javax.swing.JSeparator();
        btn_Finalizar = new javax.swing.JButton();
        lbl_TotalAPagar = new javax.swing.JFormattedTextField();
        lbl_Vuelto = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cerrar Venta");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbl_FormaDePago.setText("Forma de Pago:");

        lbl_Transporte.setText("Transporte:");

        lbl_Cambio.setText("Cambio:");

        lbl_Total.setText("Total a pagar:");

        lbl_Pago.setText("Abona con:");

        txt_AbonaCon.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txt_AbonaCon.setText("0");
        txt_AbonaCon.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_AbonaCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AbonaConActionPerformed(evt);
            }
        });
        txt_AbonaCon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AbonaConFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_AbonaConFocusLost(evt);
            }
        });
        txt_AbonaCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AbonaConKeyReleased(evt);
            }
        });

        lbl_Devolucion.setText("Vuelto:");

        lbl_Vendor.setText("Vendedor:");

        lbl_Vendedor.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vendedor.setText("XXXXXXXXXXXXXXXXXXXXXX");

        btn_Finalizar.setForeground(java.awt.Color.blue);
        btn_Finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Finalizar.setText("Finalizar");
        btn_Finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FinalizarActionPerformed(evt);
            }
        });

        lbl_TotalAPagar.setEditable(false);
        lbl_TotalAPagar.setForeground(new java.awt.Color(29, 156, 37));
        lbl_TotalAPagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_TotalAPagar.setText("0.00");
        lbl_TotalAPagar.setFocusable(false);
        lbl_TotalAPagar.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        lbl_Vuelto.setEditable(false);
        lbl_Vuelto.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vuelto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_Vuelto.setText("0.00");
        lbl_Vuelto.setFocusable(false);
        lbl_Vuelto.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Vendor)
                            .addComponent(lbl_FormaDePago)
                            .addComponent(lbl_Transporte))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Vendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addComponent(cmb_FormaDePago, 0, 261, Short.MAX_VALUE)
                            .addComponent(cmb_Transporte, 0, 261, Short.MAX_VALUE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(lbl_Cambio)
                        .addGap(48, 48, 48)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_Pago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Devolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_AbonaCon, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_Vuelto)
                            .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Finalizar)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Vendor)
                    .addComponent(lbl_Vendedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_FormaDePago)
                    .addComponent(cmb_FormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Transporte)
                    .addComponent(cmb_Transporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Cambio)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_Total)
                        .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Pago)
                    .addComponent(txt_AbonaCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Devolucion)
                    .addComponent(lbl_Vuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btn_Finalizar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.cargarFormasDePago();
            this.cargarTransportistas();
            //set predeterminado
            cmb_Transporte.setSelectedIndex(0);
            cmb_FormaDePago.setSelectedItem(formaDePagoService.getFormaDePagoPredeterminada(gui_tpv.getEmpresa()));
            UsuarioService controladorUsuario = new UsuarioService();
            lbl_Vendedor.setText(controladorUsuario.getUsuarioActivo().getUsuario().getNombre());
            txt_AbonaCon.requestFocus();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_FinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FinalizarActionPerformed
        try {
            boolean bandera;
            bandera = false;
            ModeloTabla tablaDeTPV = gui_tpv.getModeloTabla();
            int[] indicesParaDividir = new int[tablaDeTPV.getRowCount()];
            int j = 0;
            for (int i = 0; i < tablaDeTPV.getRowCount(); i++) {
                if ((boolean) tablaDeTPV.getValueAt(i, 0)) {
                    indicesParaDividir[j] = i;
                    j++;
                }
            }
            if (0 != indicesParaDividir.length) {
                bandera = true;
            }
            if (!bandera) {
                FacturaVenta factura = this.construirFactura();
                Factura aux = this.guardarFactura(factura);
                this.lanzarReporteFactura(aux);
                exito = true;
                this.dispose();
            } else {
                List<FacturaVenta> facturasDivididas = facturaService.dividirFactura(this.construirFactura(), indicesParaDividir);
                for (Factura factura : facturasDivididas) {
                    if (facturasDivididas.size() == 2 && !factura.getRenglones().isEmpty()) {
                        this.lanzarReporteFactura(this.guardarFactura(factura));
                        exito = true;
                        this.dispose();
                    }
                }
            }

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (JRException ex) {
            String msjError = "Se produjo un error procesando el reporte.";
            log.error(msjError + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, msjError, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_FinalizarActionPerformed

    private void txt_AbonaConKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AbonaConKeyReleased
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaConKeyReleased

    private void txt_AbonaConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaConFocusGained
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txt_AbonaCon.selectAll();
            }
        });
    }//GEN-LAST:event_txt_AbonaConFocusGained

    private void txt_AbonaConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AbonaConActionPerformed
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaConActionPerformed

    private void txt_AbonaConFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaConFocusLost
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaConFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Finalizar;
    private javax.swing.JComboBox cmb_FormaDePago;
    private javax.swing.JComboBox cmb_Transporte;
    private javax.swing.JLabel lbl_Cambio;
    private javax.swing.JLabel lbl_Devolucion;
    private javax.swing.JLabel lbl_FormaDePago;
    private javax.swing.JLabel lbl_Pago;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JFormattedTextField lbl_TotalAPagar;
    private javax.swing.JLabel lbl_Transporte;
    private javax.swing.JLabel lbl_Vendedor;
    private javax.swing.JLabel lbl_Vendor;
    private javax.swing.JFormattedTextField lbl_Vuelto;
    private javax.swing.JPanel panel1;
    private javax.swing.JSeparator separador;
    private javax.swing.JFormattedTextField txt_AbonaCon;
    // End of variables declaration//GEN-END:variables
}
