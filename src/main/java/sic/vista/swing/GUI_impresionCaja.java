package sic.vista.swing;

import java.awt.Font;
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
import sic.util.ColoresParaImprimir;
import sic.util.Utilidades;

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
        this.prepararConfiguracionPorDefecto();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_TablaGeneral = new javax.swing.JPanel();
        jsp_Impresion = new javax.swing.JScrollPane();
        tbl_Informe = new javax.swing.JTable();
        pnl_ComandosEImpresion = new javax.swing.JPanel();
        chk_Cabecera = new javax.swing.JCheckBox();
        chk_PieDePagina = new javax.swing.JCheckBox();
        txt_Cabecera = new javax.swing.JTextField();
        txt_PieDePagina = new javax.swing.JTextField();
        chk_MostrarDialogo = new javax.swing.JCheckBox();
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
        jsp_Impresion.setViewportView(tbl_Informe);

        javax.swing.GroupLayout pnl_TablaGeneralLayout = new javax.swing.GroupLayout(pnl_TablaGeneral);
        pnl_TablaGeneral.setLayout(pnl_TablaGeneralLayout);
        pnl_TablaGeneralLayout.setHorizontalGroup(
            pnl_TablaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsp_Impresion)
        );
        pnl_TablaGeneralLayout.setVerticalGroup(
            pnl_TablaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TablaGeneralLayout.createSequentialGroup()
                .addComponent(jsp_Impresion, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnl_ComandosEImpresion.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Impresión"));

        chk_Cabecera.setText("Cabecera:");

        chk_PieDePagina.setText("Pie de Página:");

        chk_MostrarDialogo.setText("Mostrar dialogo de Impresión.");

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
                        .addGap(495, 495, 495)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_ComandosEImpresionLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chk_Cabecera, chk_PieDePagina, txt_Cabecera, txt_PieDePagina});

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
        if (chk_Cabecera.isSelected()) {
            header = new MessageFormat(txt_Cabecera.getText());
        }

        MessageFormat footer = null;
        /* Si Vamos a imprimir un pie de pagina */
        if (chk_PieDePagina.isSelected()) {
            /* Crea un MessageFormat alrrededor del pie del texto*/
            footer = new MessageFormat(txt_PieDePagina.getText());
        }
        boolean ajustarAnchoAPagina = true;
        boolean mostrarDialoDeImpresion = chk_MostrarDialogo.isSelected();
        boolean interactivo = chk_MuestraDialogoDeEstado.isSelected();
        JTable.PrintMode mode = ajustarAnchoAPagina ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        tbl_Informe.setFont(new Font("Font", Font.BOLD, 24));
        tbl_Informe.setRowHeight(tbl_Informe.getRowHeight() + 8);

        try {
            /* imprime la tabla */
            boolean completado = tbl_Informe.print(mode, header, footer,
                    mostrarDialoDeImpresion, null,
                    interactivo, null);

            /* si la impresión se completa */
            if (completado) {
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
    }//GEN-LAST:event_btn_imprimirActionPerformed

    public void setColumnasInforme() {
        //sorting
        tbl_Informe.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[3];
        encabezados[0] = "Forma De Pago";
        encabezados[1] = "Fecha";
        encabezados[2] = "Saldo";
        modeloTablaInforme.setColumnIdentifiers(encabezados);
        tbl_Informe.setModel(modeloTablaInforme);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaInforme.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = String.class;
        modeloTablaInforme.setClaseColumnas(tipos);
        tbl_Informe.getTableHeader().setReorderingAllowed(false);
        tbl_Informe.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Informe.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Informe.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_Informe.getColumnModel().getColumn(2).setPreferredWidth(50);

    }

    private void limpiarTablaInforme() {
        modeloTablaInforme = new ModeloTabla();
        tbl_Informe.setModel(modeloTablaInforme);
        this.setColumnasInforme();
    }

    private void cargarDatosInformePorFormaDePago() {
        for (FormaDePago formaDePago : this.formasDePago) {
            List<Factura> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos.addAll(facturas);
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos.addAll(gastos);
            this.cargarMovimientosEnLaTablaInforme(this.listaMovimientos, formaDePago);
        }
        this.cargarFinalInforme();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JCheckBox chk_Cabecera;
    private javax.swing.JCheckBox chk_MostrarDialogo;
    private javax.swing.JCheckBox chk_MuestraDialogoDeEstado;
    private javax.swing.JCheckBox chk_PieDePagina;
    private javax.swing.JScrollPane jsp_Impresion;
    private javax.swing.JPanel pnl_ComandosEImpresion;
    private javax.swing.JPanel pnl_TablaGeneral;
    private javax.swing.JTable tbl_Informe;
    private javax.swing.JTextField txt_Cabecera;
    private javax.swing.JTextField txt_PieDePagina;
    // End of variables declaration//GEN-END:variables

    private void cargarMovimientosEnLaTablaInforme(List<Object> listaMovimientos, FormaDePago formaDePago) {
        Double total = 0.0;
        Object[] encabezado = new Object[3];
        encabezado[0] = formaDePago.getNombre();
        modeloTablaInforme.addRow(encabezado);
        for (Object movimiento : listaMovimientos) {
            Object[] fila = new Object[3];
            if (movimiento instanceof FacturaCompra) {
                fila[1] = Utilidades.darFormatoAFechas(((FacturaCompra) movimiento).getFecha());
            }
            if (movimiento instanceof FacturaVenta) {
                fila[1] = Utilidades.darFormatoAFechas(((FacturaVenta) movimiento).getFecha());
            }
            if (movimiento instanceof Gasto) {
                fila[1] = Utilidades.darFormatoAFechas(((Gasto) movimiento).getFecha());
            }
            if (movimiento instanceof FacturaVenta) {
                fila[2] = ((FacturaVenta) movimiento).getTotal();
                total += ((FacturaVenta) movimiento).getTotal();
            }
            if (movimiento instanceof FacturaCompra) {
                fila[2] = 0 - ((FacturaCompra) movimiento).getTotal();
                total -= ((FacturaCompra) movimiento).getTotal();
            }
            if (movimiento instanceof Gasto) {
                fila[2] = 0 - ((Gasto) movimiento).getMonto();
                total -= ((Gasto) movimiento).getMonto();
            }
            if (movimiento instanceof FacturaVenta || movimiento instanceof FacturaCompra) {
                String tipoFactura;
                if (movimiento instanceof FacturaVenta) {
                    tipoFactura = "Venta   ";
                } else {
                    tipoFactura = "Compra";
                }
                fila[0] = "Factura " + tipoFactura + " Nº " + ((Factura) movimiento).getNumSerie() + " - " + ((Factura) movimiento).getNumFactura();
            } else if (movimiento instanceof Gasto) {
                fila[0] = ((Gasto) movimiento).getConcepto();
            }
            modeloTablaInforme.addRow(fila);
        }
        Object[] totalPorFormaDePago = new Object[3];
        totalPorFormaDePago[1] = "TOTAL:";
        totalPorFormaDePago[2] = Utilidades.darFormatoANumeros(total);
        modeloTablaInforme.addRow(totalPorFormaDePago);
        tbl_Informe.setModel(modeloTablaInforme);
        tbl_Informe.setDefaultRenderer(String.class, new ColoresParaImprimir());

    }

    private void cargarFinalInforme() {
        Object[] resumen = new Object[3];
        resumen[0] = "RESUMEN POR FORMA DE PAGO";
        modeloTablaInforme.addRow(resumen);
        Object[] espacio = new Object[3];
        modeloTablaInforme.addRow(espacio);
        Empresa empresaActiva = empresaService.getEmpresaActiva().getEmpresa();
        double total = this.caja.getSaldoInicial();
        Object[] saldoInicial = new Object[3];
        saldoInicial[0] = "Saldo Apertura";
        saldoInicial[1] = Utilidades.darFormatoAFechas(this.caja.getFechaApertura());
        saldoInicial[2] = total;
        modeloTablaInforme.addRow(saldoInicial);
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaActiva);
        for (FormaDePago formaDePago : formasDePago) {
            Object[] fila = new Object[3];
            List<Factura> facturasPorFormaDePago = facturaService.getFacturasPorFechasYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            fila[0] = formaDePago.getNombre();
            this.listaMovimientos.clear();
            this.listaMovimientos.addAll(facturasPorFormaDePago);
            this.listaMovimientos.addAll(gastos);
            double totalParcial = cajaService.calcularTotalPorMovimiento(this.listaMovimientos);
            fila[1] = Utilidades.darFormatoAFechas(new Date());
            fila[2] = Utilidades.darFormatoANumeros(totalParcial);
            total += totalParcial;
            modeloTablaInforme.addRow(fila);
        }
        Object[] Total = new Object[3];
        Total[1] = "Saldo Total";
        Total[2] = Utilidades.darFormatoANumeros(total);
        modeloTablaInforme.addRow(Total);
        tbl_Informe.setModel(modeloTablaInforme);
    }

    private void prepararConfiguracionPorDefecto() {
        chk_Cabecera.setSelected(true);
        txt_Cabecera.setText(empresaService.getEmpresaActiva().getEmpresa().getNombre());
        chk_MostrarDialogo.setSelected(true);
        chk_MuestraDialogoDeEstado.setSelected(true);
        chk_PieDePagina.setSelected(true);
        txt_PieDePagina.setText("{0}");
        btn_imprimir.requestFocus();
    }
}
