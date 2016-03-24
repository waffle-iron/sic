package sic.vista.swing;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import sic.modelo.Caja;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Gasto;
import sic.service.CajaService;
import sic.service.EmpresaService;
import sic.service.FacturaService;
import sic.service.FormaDePagoService;
import sic.service.GastoService;

public class GUI_impresionCaja extends javax.swing.JDialog {

    private ModeloTabla modeloTablaInforme;
    private final FormaDePagoService formaDePagoService = new FormaDePagoService();
    private final EmpresaService empresaService = new EmpresaService();
    private final FacturaService facturaService = new FacturaService();
    private final GastoService gastoService = new GastoService();
    private final CajaService cajaService = new CajaService();
    private List<FormaDePago> formasDePago;
    private List<Object> listaMovimientos;
    private Caja caja;

    public GUI_impresionCaja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public GUI_impresionCaja(javax.swing.JDialog parent, boolean modal, Caja caja) {
        super(parent, modal);
        this.caja = caja;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_TablaGeneral = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Informe = new javax.swing.JTable();
        pnl_ComandosEImpresion = new javax.swing.JPanel();
        chk_Cabecera = new javax.swing.JCheckBox();
        chk_PieDePagina = new javax.swing.JCheckBox();
        txt_Cabecera = new javax.swing.JTextField();
        txt_PieDePagina = new javax.swing.JTextField();
        chk_MostrarDialogo = new javax.swing.JCheckBox();
        chk_AjustarALaPagina = new javax.swing.JCheckBox();
        chk_MuestraDialogoDeEstado = new javax.swing.JCheckBox();
        btn_imprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tbl_Informe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_Informe);

        javax.swing.GroupLayout pnl_TablaGeneralLayout = new javax.swing.GroupLayout(pnl_TablaGeneral);
        pnl_TablaGeneral.setLayout(pnl_TablaGeneralLayout);
        pnl_TablaGeneralLayout.setHorizontalGroup(
            pnl_TablaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        pnl_TablaGeneralLayout.setVerticalGroup(
            pnl_TablaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TablaGeneralLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnl_ComandosEImpresion.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Impresión"));

        chk_Cabecera.setText("Cabecera:");

        chk_PieDePagina.setText("Pie de Página:");

        chk_MostrarDialogo.setText("Mostrar dialogo de Impresión.");

        chk_AjustarALaPagina.setText("Ajustar con la página impresa.");

        chk_MuestraDialogoDeEstado.setText("Interactivo (Muestra dialogo de estado)");

        btn_imprimir.setText("Imprimir");
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_ComandosEImpresionLayout = new javax.swing.GroupLayout(pnl_ComandosEImpresion);
        pnl_ComandosEImpresion.setLayout(pnl_ComandosEImpresionLayout);
        pnl_ComandosEImpresionLayout.setHorizontalGroup(
            pnl_ComandosEImpresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ComandosEImpresionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_ComandosEImpresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_ComandosEImpresionLayout.createSequentialGroup()
                        .addComponent(chk_Cabecera)
                        .addGap(0, 0, 0)
                        .addComponent(txt_Cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(chk_MostrarDialogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chk_MuestraDialogoDeEstado)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_ComandosEImpresionLayout.createSequentialGroup()
                        .addComponent(chk_PieDePagina)
                        .addGap(0, 0, 0)
                        .addComponent(txt_PieDePagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(chk_AjustarALaPagina)
                        .addGap(264, 264, 264)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_ComandosEImpresionLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chk_Cabecera, chk_PieDePagina, txt_Cabecera, txt_PieDePagina});

        pnl_ComandosEImpresionLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chk_AjustarALaPagina, chk_MostrarDialogo});

        pnl_ComandosEImpresionLayout.setVerticalGroup(
            pnl_ComandosEImpresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ComandosEImpresionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_ComandosEImpresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_Cabecera)
                    .addComponent(txt_Cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_MostrarDialogo)
                    .addComponent(chk_MuestraDialogoDeEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_ComandosEImpresionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_PieDePagina)
                    .addComponent(txt_PieDePagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_AjustarALaPagina)
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );

        pnl_ComandosEImpresionLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chk_Cabecera, txt_Cabecera, txt_PieDePagina});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_TablaGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_ComandosEImpresion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_TablaGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_ComandosEImpresion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        this.limpiarTablaInforme();
        this.cargarDatosInformePorFormaDePago();
    }//GEN-LAST:event_formWindowOpened

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        MessageFormat header = null;
        /* Si vamos a imprimir una cabecera */
        if (chk_Cabecera.isSelected()) {
            /* crea un MessageFormat alrrededor de la cabezera del texto */
            header = new MessageFormat(txt_Cabecera.getText());
        }

        MessageFormat footer = null;
        /* Si Vamos a imprimir un pie de pagina */
        if (chk_PieDePagina.isSelected()) {
            /* Crea un MessageFormat alrrededor del pie del texto*/
            footer = new MessageFormat(txt_PieDePagina.getText());
        }
        boolean ajustarAncho = chk_AjustarALaPagina.isSelected();
        boolean mostrarDialoDeImpresion = chk_MostrarDialogo.isSelected();
        boolean interactivo = chk_MuestraDialogoDeEstado.isSelected();
        JTable.PrintMode mode = ajustarAncho ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;

        try {
            /* imprime la tabla */
            boolean complete = tbl_Informe.print(mode, header, footer,
                    mostrarDialoDeImpresion, null,
                    interactivo, null);

            /* si la impresión se completa */
            if (complete) {
                /* muestra un mensaje de exito */
                JOptionPane.showMessageDialog(this,
                        "Impresión completa",
                        "Resultado de la impresión",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                /* muestra un mensaje indicando que la impresión fue cancelada */
                JOptionPane.showMessageDialog(this,
                        "Impresión cancelada",
                        "Resultado de la impresión",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            /* La impresión falló, mostrar al usuario. */
            JOptionPane.showMessageDialog(this,
                    "Impresión fallida: " + pe.getMessage(),
                    "Resultado De La Impresión",
                    JOptionPane.ERROR_MESSAGE);
        }

        //this.cajaService.actualizar(caja);
    }//GEN-LAST:event_btn_imprimirActionPerformed

    public void setColumnasInforme() {
        //sorting
        tbl_Informe.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[5];
        encabezados[0] = "Fecha y Hora";
        encabezados[1] = "Concepto";
        encabezados[2] = "Debe";
        encabezados[3] = "Haber";
        encabezados[4] = "Saldo";
        modeloTablaInforme.setColumnIdentifiers(encabezados);
        tbl_Informe.setModel(modeloTablaInforme);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaInforme.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = Double.class;
        tipos[3] = Double.class;
        tipos[4] = Double.class;
        modeloTablaInforme.setClaseColumnas(tipos);
        tbl_Informe.getTableHeader().setReorderingAllowed(false);
        tbl_Informe.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Informe.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Informe.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_Informe.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_Informe.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_Informe.getColumnModel().getColumn(4).setPreferredWidth(50);
    }

    private void limpiarTablaInforme() {
        modeloTablaInforme = new ModeloTabla();
        tbl_Informe.setModel(modeloTablaInforme);
        this.setColumnasInforme();
    }

    private void cargarDatosInformePorFormaDePago() {
        for (FormaDePago formaDePago : this.formasDePago) {
            List<Object> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos = facturas;
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos.addAll(gastos);
            this.cargarMovimientosEnLaTablaInforme(this.listaMovimientos, formaDePago);
        }
        this.cargarFinalInforme();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JCheckBox chk_AjustarALaPagina;
    private javax.swing.JCheckBox chk_Cabecera;
    private javax.swing.JCheckBox chk_MostrarDialogo;
    private javax.swing.JCheckBox chk_MuestraDialogoDeEstado;
    private javax.swing.JCheckBox chk_PieDePagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_ComandosEImpresion;
    private javax.swing.JPanel pnl_TablaGeneral;
    private javax.swing.JTable tbl_Informe;
    private javax.swing.JTextField txt_Cabecera;
    private javax.swing.JTextField txt_PieDePagina;
    // End of variables declaration//GEN-END:variables

    private void cargarMovimientosEnLaTablaInforme(List<Object> listaMovimientos, FormaDePago formaDePago) {
        Object[] encabezado = new Object[5];
        encabezado[0] = "Forma de Pago ";
        encabezado[1] = formaDePago.getNombre();
        modeloTablaInforme.addRow(encabezado);
        for (Object movimiento : listaMovimientos) {
            Object[] fila = new Object[5];
            if (movimiento instanceof FacturaCompra) {
                fila[0] = ((FacturaCompra) movimiento).getFecha().toString();
            }
            if (movimiento instanceof FacturaVenta) {
                fila[0] = ((FacturaVenta) movimiento).getFecha().toString();
            }
            if (movimiento instanceof Gasto) {
                fila[0] = ((Gasto) movimiento).getFecha().toString();
            }
            if (movimiento instanceof FacturaCompra) {
                fila[2] = 0.0;
                fila[3] = ((FacturaCompra) movimiento).getTotal();
            }
            if (movimiento instanceof FacturaVenta) {
                fila[2] = ((FacturaVenta) movimiento).getTotal();
                fila[3] = 0.0;
            }
            if (movimiento instanceof Gasto) {
                fila[2] = 0.0;
                fila[3] = ((Gasto) movimiento).getMonto();
            }
            if (movimiento instanceof FacturaVenta || movimiento instanceof FacturaCompra) {
                String tipoFactura;
                if (movimiento instanceof FacturaVenta) {
                    tipoFactura = "Venta   ";
                } else {
                    tipoFactura = "Compra";
                }
                fila[1] = "Factura " + tipoFactura + " Nº " + ((Factura) movimiento).getNumSerie() + " - " + ((Factura) movimiento).getNumFactura();
            } else if (movimiento instanceof Gasto) {
                fila[1] = ((Gasto) movimiento).getConcepto();
            }
            fila[4] = (double) fila[2] - (double) fila[3];
            modeloTablaInforme.addRow(fila);
        }
        tbl_Informe.setModel(modeloTablaInforme);
        tbl_Informe.setDefaultRenderer(Double.class, new ColoresTablaResumenCaja());

    }

    private void cargarFinalInforme() {
        Empresa empresaActiva = empresaService.getEmpresaActiva().getEmpresa();
        this.caja = cajaService.getCajaSinArqueo(empresaActiva.getId_Empresa());
        double total = this.caja.getSaldoInicial();
        Object[] saldoInicial = new Object[5];
        saldoInicial[0] = "Saldo Apertura";
        saldoInicial[4] = total;
        modeloTablaInforme.addRow(saldoInicial);
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaActiva);
        for (FormaDePago formaDePago : formasDePago) {
            Object[] fila = new Object[5];
            List<Object> facturasPorFormaDePago = facturaService.getFacturasPorFechasYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            fila[0] = formaDePago.getNombre();
            double totalParcial = cajaService.calcularTotalPorMovimiento(facturasPorFormaDePago) + cajaService.calcularTotalPorMovimiento(gastos);
            fila[4] = totalParcial;
            total += totalParcial;
            modeloTablaInforme.addRow(fila);
        }
        Object[] Total = new Object[5];
        saldoInicial[0] = "Saldo Total";
        saldoInicial[4] = total;
        tbl_Informe.setModel(modeloTablaInforme);
    }
}
