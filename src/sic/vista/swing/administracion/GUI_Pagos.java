package sic.vista.swing.administracion;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.FacturaCompra;
import sic.modelo.PagoFacturaCompra;
import sic.service.PagoFacturaDeCompraService;
import sic.service.ServiceException;
import sic.util.FormatterFechaHora;
import sic.util.RenderTabla;
import sic.util.Utilidades;
import sic.vista.swing.ModeloTabla;

public class GUI_Pagos extends JDialog {

    private ModeloTabla modeloTablaResultados;
    private List<PagoFacturaCompra> pagos;
    private FacturaCompra facturaRelacionada;
    private PagoFacturaDeCompraService pagoFacturaDeCompraService = new PagoFacturaDeCompraService();
    private static final Logger log = Logger.getLogger(GUI_Pagos.class.getPackage().getName());

    public GUI_Pagos(FacturaCompra factura) {
        this.initComponents();
        this.setIcon();
        modeloTablaResultados = new ModeloTabla();
        txt_Monto.setValue(0.00);
        txt_TotalAdeudado.setValue(0.00);
        txt_TotalPagado.setValue(0.00);
        txt_SaldoAPagar.setValue(0.00);
        facturaRelacionada = factura;
        FormatterFechaHora formateador = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHA_HISPANO);
        String tituloVentana;
        if (factura.getNumSerie() == 0 && factura.getNumFactura() == 0) {
            tituloVentana = "Pagos de la Factura Nº: (sin numero) con Fecha: " + formateador.format(factura.getFecha());
        } else {
            tituloVentana = "Pagos de la Factura Nº: " + factura.getNumSerie() + " - " + factura.getNumFactura()
                    + " con Fecha: " + formateador.format(factura.getFecha());
        }
        this.setTitle(tituloVentana);
        this.setColumnas();
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Stamp_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void getPagosDeLaFactura() {
        pagos = pagoFacturaDeCompraService.getPagosDeLaFactura(facturaRelacionada);
    }

    private void setColumnas() {
        //sorting
        tbl_Resultados.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[3];
        encabezados[0] = "Fecha";
        encabezados[1] = "Monto";
        encabezados[2] = "Nota";
        modeloTablaResultados.setColumnIdentifiers(encabezados);
        tbl_Resultados.setModel(modeloTablaResultados);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaResultados.getColumnCount()];
        tipos[0] = Date.class;
        tipos[1] = Double.class;
        tipos[2] = String.class;
        modeloTablaResultados.setClaseColumnas(tipos);
        tbl_Resultados.getTableHeader().setReorderingAllowed(false);
        tbl_Resultados.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Resultados.setDefaultRenderer(Double.class, new RenderTabla());

        //Tama�os de columnas        
        tbl_Resultados.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(0).setMaxWidth(100);
        tbl_Resultados.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(1).setMaxWidth(100);
        tbl_Resultados.getColumnModel().getColumn(2).setPreferredWidth(300);
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTable();
        for (PagoFacturaCompra pago : pagos) {
            Object[] fila = new Object[3];
            fila[0] = pago.getFecha();
            fila[1] = pago.getMonto();
            fila[2] = pago.getNota();
            modeloTablaResultados.addRow(fila);
        }
        tbl_Resultados.setModel(modeloTablaResultados);
    }

    private void limpiarJTable() {
        modeloTablaResultados = new ModeloTabla();
        tbl_Resultados.setModel(modeloTablaResultados);
        this.setColumnas();
    }

    private void agregarPago() {
        try {
            PagoFacturaCompra pago = new PagoFacturaCompra();
            pago.setFecha(dc_Fecha.getDate());
            pago.setMonto(Double.parseDouble(txt_Monto.getValue().toString()));
            pago.setNota(txt_Nota.getText().trim());
            pago.setFacturaCompra(facturaRelacionada);
            pagoFacturaDeCompraService.guardar(pago);
            dc_Fecha.setDate(null);
            txt_Monto.setValue(0.00);
            txt_Nota.setText("");
            this.getPagosDeLaFactura();
            this.cargarResultadosAlTable();
            this.verificarYSetearEstadoPagoFactura();
            txt_TotalPagado.setValue(pagoFacturaDeCompraService.getTotalPagado(facturaRelacionada));
            txt_SaldoAPagar.setValue(pagoFacturaDeCompraService.getSaldoAPagar(facturaRelacionada));

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPago() {
        if (tbl_Resultados.getSelectedRow() != -1) {
            int indexFilaSeleccionada = Utilidades.getSelectedRowModelIndice(tbl_Resultados);
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Esta seguro que desea eliminar el pago seleccionado?",
                    "Eliminar", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    pagoFacturaDeCompraService.eliminar(pagos.get(indexFilaSeleccionada));
                    pagos.remove(indexFilaSeleccionada);
                    this.cargarResultadosAlTable();
                    this.verificarYSetearEstadoPagoFactura();
                    txt_TotalPagado.setValue(pagoFacturaDeCompraService.getTotalPagado(facturaRelacionada));
                    txt_SaldoAPagar.setValue(pagoFacturaDeCompraService.getSaldoAPagar(facturaRelacionada));

                } catch (PersistenceException ex) {
                    log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void verificarYSetearEstadoPagoFactura() {
        pagoFacturaDeCompraService.setFacturaEstadoDePago(facturaRelacionada);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp_Resultado = new javax.swing.JScrollPane();
        tbl_Resultados = new javax.swing.JTable();
        panel1 = new javax.swing.JPanel();
        lbl_Fecha = new javax.swing.JLabel();
        dc_Fecha = new com.toedter.calendar.JDateChooser();
        lbl_Monto = new javax.swing.JLabel();
        txt_Monto = new javax.swing.JFormattedTextField();
        lbl_Nota = new javax.swing.JLabel();
        txt_Nota = new javax.swing.JTextField();
        panel2 = new javax.swing.JPanel();
        lbl_TA = new javax.swing.JLabel();
        lbl_TP = new javax.swing.JLabel();
        lbl_Saldo = new javax.swing.JLabel();
        txt_TotalAdeudado = new javax.swing.JFormattedTextField();
        txt_TotalPagado = new javax.swing.JFormattedTextField();
        txt_SaldoAPagar = new javax.swing.JFormattedTextField();
        btn_Agregar = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        lbl_AvisoPagado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pagos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tbl_Resultados.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_Resultados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sp_Resultado.setViewportView(tbl_Resultados);

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Fecha.setForeground(java.awt.Color.red);
        lbl_Fecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Fecha.setText("* Fecha:");

        dc_Fecha.setDateFormatString("dd/MM/yyyy");

        lbl_Monto.setForeground(java.awt.Color.red);
        lbl_Monto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Monto.setText("* Monto:");

        txt_Monto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,###,##0.00"))));

        lbl_Nota.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Nota.setText("Nota:");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Fecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Monto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Nota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Nota, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_Monto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dc_Fecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dc_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Fecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Monto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Nota)))
        );

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_TA.setForeground(java.awt.Color.red);
        lbl_TA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_TA.setText("Total Adeudado:");

        lbl_TP.setForeground(java.awt.Color.green);
        lbl_TP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_TP.setText("Total Pagado:");

        lbl_Saldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Saldo.setText("Saldo a Pagar:");

        txt_TotalAdeudado.setEditable(false);
        txt_TotalAdeudado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        txt_TotalAdeudado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_TotalAdeudado.setFocusable(false);

        txt_TotalPagado.setEditable(false);
        txt_TotalPagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        txt_TotalPagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_TotalPagado.setFocusable(false);

        txt_SaldoAPagar.setEditable(false);
        txt_SaldoAPagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        txt_SaldoAPagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_SaldoAPagar.setFocusable(false);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_TA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TotalAdeudado, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addGap(41, 41, 41)
                .addComponent(lbl_TP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TotalPagado, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(lbl_Saldo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_SaldoAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TA)
                    .addComponent(txt_TotalAdeudado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Saldo)
                    .addComponent(txt_SaldoAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TP)
                    .addComponent(txt_TotalPagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Agregar.setForeground(java.awt.Color.blue);
        btn_Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddStamp_16x16.png"))); // NOI18N
        btn_Agregar.setText("Agregar");
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });

        btn_Eliminar.setForeground(java.awt.Color.blue);
        btn_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/DeleteStamp_16x16.png"))); // NOI18N
        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });

        lbl_AvisoPagado.setText("NOTA: Cuando el total pagado cumpla con el valor de la factura, se marcará automaticamente como pagada.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_Resultado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btn_Agregar)
                                .addGap(0, 0, 0)
                                .addComponent(btn_Eliminar))
                            .addComponent(lbl_AvisoPagado, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_Agregar, btn_Eliminar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_AvisoPagado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Resultado, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Agregar)
                    .addComponent(btn_Eliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_Agregar, btn_Eliminar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        this.eliminarPago();
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        this.agregarPago();
    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.getPagosDeLaFactura();
            this.cargarResultadosAlTable();
            txt_TotalAdeudado.setValue(facturaRelacionada.getTotal());
            txt_TotalPagado.setValue(pagoFacturaDeCompraService.getTotalPagado(facturaRelacionada));
            txt_SaldoAPagar.setValue(pagoFacturaDeCompraService.getSaldoAPagar(facturaRelacionada));

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JButton btn_Eliminar;
    private com.toedter.calendar.JDateChooser dc_Fecha;
    private javax.swing.JLabel lbl_AvisoPagado;
    private javax.swing.JLabel lbl_Fecha;
    private javax.swing.JLabel lbl_Monto;
    private javax.swing.JLabel lbl_Nota;
    private javax.swing.JLabel lbl_Saldo;
    private javax.swing.JLabel lbl_TA;
    private javax.swing.JLabel lbl_TP;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane sp_Resultado;
    private javax.swing.JTable tbl_Resultados;
    private javax.swing.JFormattedTextField txt_Monto;
    private javax.swing.JTextField txt_Nota;
    private javax.swing.JFormattedTextField txt_SaldoAPagar;
    private javax.swing.JFormattedTextField txt_TotalAdeudado;
    private javax.swing.JFormattedTextField txt_TotalPagado;
    // End of variables declaration//GEN-END:variables
}
