package sic.vista.swing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;
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
import sic.util.Utilidades;

public class GUI_Caja extends javax.swing.JDialog {

    private final CajaService cajaService = new CajaService();
    private final EmpresaService empresaService = new EmpresaService();
    private final FormaDePagoService formaDePagoService = new FormaDePagoService();
    private final FacturaService facturaService = new FacturaService();
    private final GastoService gastoService = new GastoService();
    private ModeloTabla modeloTablaBalance;
    private ModeloTabla modeloTablaResumen;
    private List<Object> listaMovimientos;
    private Caja caja;
    private double totalCaja;
    private static final Logger log = Logger.getLogger(GUI_Caja.class.getPackage().getName());

    public GUI_Caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.limpiarTablaBalance();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pbl_Cabecera = new javax.swing.JPanel();
        lbl_FormaDePago = new javax.swing.JLabel();
        cmb_FormasDePago = new javax.swing.JComboBox<>();
        pnl_Tabla = new javax.swing.JPanel();
        sp_Tabla = new javax.swing.JScrollPane();
        tbl_Balance = new javax.swing.JTable();
        lbl_aviso = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Resumen = new javax.swing.JTable();
        btn_abrirCaja = new javax.swing.JButton();
        btn_AgregarGasto = new javax.swing.JButton();
        ftxt_Total = new javax.swing.JFormattedTextField();
        btn_VerDetalle = new javax.swing.JButton();
        lbl_Total = new javax.swing.JLabel();
        btn_RealizarArqueo = new javax.swing.JButton();

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

        lbl_FormaDePago.setText("Filtrar por forma de pago:");

        cmb_FormasDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_FormasDePagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pbl_CabeceraLayout = new javax.swing.GroupLayout(pbl_Cabecera);
        pbl_Cabecera.setLayout(pbl_CabeceraLayout);
        pbl_CabeceraLayout.setHorizontalGroup(
            pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_FormaDePago)
                    .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pbl_CabeceraLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_FormasDePago, lbl_FormaDePago});

        pbl_CabeceraLayout.setVerticalGroup(
            pbl_CabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_CabeceraLayout.createSequentialGroup()
                .addComponent(lbl_FormaDePago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tbl_Balance.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sp_Tabla.setViewportView(tbl_Balance);

        tbl_Resumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_Resumen);
        tbl_Resumen.getAccessibleContext().setAccessibleParent(sp_Tabla);

        btn_abrirCaja.setText("Abrir Caja");
        btn_abrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrirCajaActionPerformed(evt);
            }
        });

        btn_AgregarGasto.setText("Agregar Gasto");
        btn_AgregarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarGastoActionPerformed(evt);
            }
        });

        ftxt_Total.setEditable(false);
        ftxt_Total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));

        btn_VerDetalle.setText("Ver Detalle");
        btn_VerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VerDetalleActionPerformed(evt);
            }
        });

        lbl_Total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Total.setText("Total:");

        btn_RealizarArqueo.setText("Realizar Arqueo");
        btn_RealizarArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RealizarArqueoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_TablaLayout = new javax.swing.GroupLayout(pnl_Tabla);
        pnl_Tabla.setLayout(pnl_TablaLayout);
        pnl_TablaLayout.setHorizontalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_TablaLayout.createSequentialGroup()
                        .addComponent(btn_abrirCaja)
                        .addGap(0, 0, 0)
                        .addComponent(btn_AgregarGasto)
                        .addGap(4, 4, 4)
                        .addComponent(lbl_aviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                        .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sp_Tabla))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(btn_VerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_Total)
                .addGap(0, 0, 0)
                .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_RealizarArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_RealizarArqueo, btn_VerDetalle, ftxt_Total, lbl_Total});

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_AgregarGasto, btn_abrirCaja});

        pnl_TablaLayout.setVerticalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_abrirCaja)
                        .addComponent(btn_AgregarGasto))
                    .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_RealizarArqueo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ftxt_Total, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_VerDetalle)
                        .addComponent(lbl_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_RealizarArqueo, ftxt_Total, lbl_Total});

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_AgregarGasto, btn_abrirCaja});

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, sp_Tabla});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbl_Cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_Tabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pbl_Cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormasDePago.addItem(formaDePago);
        }
        this.cargarDatosBalance();
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
        GUI_CerrarCaja cerrarCaja = new GUI_CerrarCaja(this, true, cajaACerrar, this.totalCaja);
        cerrarCaja.setLocationRelativeTo(this);
        cerrarCaja.setVisible(true);
        this.limpiarTablaBalance();
        this.cargarDatosBalance();
    }//GEN-LAST:event_btn_RealizarArqueoActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.limpiarTablaBalance();
        this.cargarDatosBalance();
    }//GEN-LAST:event_formWindowGainedFocus

    private void btn_VerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VerDetalleActionPerformed
        if (tbl_Balance.getSelectedRow() != -1) {
            Object movimientoDeTabla = tbl_Balance.getModel().getValueAt(Utilidades.getSelectedRowModelIndice(tbl_Balance), 5);
            if (movimientoDeTabla instanceof FacturaVenta) {
                try {
                    JasperPrint report = facturaService.getReporteFacturaVenta((FacturaVenta) movimientoDeTabla);
                    JDialog viewer = new JDialog(new JFrame(), "Vista Previa", true);
                    viewer.setSize(this.getWidth(), this.getHeight());
                    ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
                    viewer.setIconImage(iconoVentana.getImage());
                    viewer.setLocationRelativeTo(null);
                    JRViewer jrv = new JRViewer(report);
                    viewer.getContentPane().add(jrv);
                    viewer.setVisible(true);
                } catch (JRException ex) {
                    String msjError = "Se produjo un error procesando el reporte.";
                    log.error(msjError + " - " + ex.getMessage());
                    JOptionPane.showInternalMessageDialog(this, msjError, "Error", JOptionPane.ERROR_MESSAGE);

                } catch (PersistenceException ex) {
                    log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                    JOptionPane.showInternalMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (movimientoDeTabla instanceof FacturaCompra) {
                GUI_FormFacturaCompra gui_DetalleFacturaCompra = new GUI_FormFacturaCompra((FacturaCompra) movimientoDeTabla);
                gui_DetalleFacturaCompra.setModal(true);
                gui_DetalleFacturaCompra.setLocationRelativeTo(this);
                gui_DetalleFacturaCompra.setVisible(true);
            }
            if (movimientoDeTabla instanceof Gasto) {
                String mensaje = "En Concepto de: " + ((Gasto) movimientoDeTabla).getConcepto() + "\nMonto: " + ((Gasto) movimientoDeTabla).getMonto() + "\nUsuario: " + ((Gasto) movimientoDeTabla).getUsuario().getNombre();
                JOptionPane.showMessageDialog(this, mensaje, "Resumen de Gasto", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_VerDetalleActionPerformed

    private void btn_AgregarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarGastoActionPerformed
        GUI_AgregarGasto agregarGasto = new GUI_AgregarGasto(this, true);
        agregarGasto.setLocationRelativeTo(null);
        agregarGasto.setVisible(true);
    }//GEN-LAST:event_btn_AgregarGastoActionPerformed

    private void cmb_FormasDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_FormasDePagoActionPerformed
        this.limpiarTablaBalance();
        this.cargarDatosBalance();
    }//GEN-LAST:event_cmb_FormasDePagoActionPerformed

    private void cargarDatosBalance() {
        this.caja = cajaService.getCajaSinArqueo(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
        if (caja == null) {
            lbl_aviso.setText("Caja sin abrir");
            lbl_aviso.setForeground(Color.RED);
            this.ftxt_Total.setText("0.0");
            this.btn_abrirCaja.setEnabled(true);
            this.btn_AgregarGasto.setEnabled(false);
            this.limpiarTablaResumen();
        } else {
            this.btn_abrirCaja.setEnabled(false);
            this.btn_AgregarGasto.setEnabled(true);
            List<Object> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos = facturas;
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), caja.getFechaApertura(), new Date());
            this.listaMovimientos.addAll(gastos);
            lbl_aviso.setText("Caja abierta -- Saldo Inicial: " + caja.getSaldoInicial());
            lbl_aviso.setForeground(Color.GREEN);
            this.cargarMovimientosEnLaTablaBalance(this.listaMovimientos);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AgregarGasto;
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
    private javax.swing.JPanel pnl_Tabla;
    private javax.swing.JScrollPane sp_Tabla;
    javax.swing.JTable tbl_Balance;
    private javax.swing.JTable tbl_Resumen;
    // End of variables declaration//GEN-END:variables

    private void limpiarTablaBalance() {
        modeloTablaBalance = new ModeloTabla();
        tbl_Balance.setModel(modeloTablaBalance);
        this.setColumnasDeTablaFormaDePago();
    }

    private void limpiarTablaResumen() {
        modeloTablaResumen = new ModeloTabla();
        tbl_Resumen.setModel(modeloTablaResumen);
        this.setColumnasDeTablaResumen();
    }

    private void setColumnasDeTablaFormaDePago() {
        //sorting
        tbl_Balance.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[6];
        encabezados[0] = "Fecha y Hora";
        encabezados[1] = "Numero";
        encabezados[2] = "Debe";
        encabezados[3] = "Haber";
        encabezados[4] = "Saldo";
        encabezados[5] = "Movimiento";
        modeloTablaBalance.setColumnIdentifiers(encabezados);
        tbl_Balance.setModel(modeloTablaBalance);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaBalance.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = Double.class;
        tipos[3] = Double.class;
        tipos[4] = Double.class;
        tipos[5] = Object.class;
        modeloTablaBalance.setClaseColumnas(tipos);
        tbl_Balance.getTableHeader().setReorderingAllowed(false);
        tbl_Balance.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Balance.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl_Balance.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbl_Balance.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_Balance.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_Balance.getColumnModel().getColumn(4).setPreferredWidth(100);

    }

    private void cargarMovimientosEnLaTablaBalance(List<Object> movimientos) {
        this.limpiarTablaBalance();
        for (Object movimiento : movimientos) {
            Object[] fila = new Object[6];
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
                fila[1] = ((Factura) movimiento).getNumSerie() + " - " + ((Factura) movimiento).getNumFactura();
            } else if (movimiento instanceof Gasto) {
                fila[1] = ((Gasto) movimiento).getNroGasto();
            }
            fila[4] = (double) fila[2] - (double) fila[3];
            fila[5] = movimiento;
            modeloTablaBalance.addRow(fila);
        }
        this.calcularTotalBalance();
        tbl_Balance.setModel(modeloTablaBalance);
        //Ordena la tabla segun la Fecha
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbl_Balance.getModel());
        tbl_Balance.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        //Hace invisible la última columna de la tabla
        TableColumnModel modeloColumnaTabla = tbl_Balance.getColumnModel();
        modeloColumnaTabla.removeColumn(modeloColumnaTabla.getColumn(5));
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
        saldoInicial[0] = "Saldo Apertura";
        saldoInicial[1] = total;
        modeloTablaResumen.addRow(saldoInicial);
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaActiva);
        for (FormaDePago formaDePago : formasDePago) {
            Object[] fila = new Object[2];
            List<Object> facturasPorFormaDePago = facturaService.getFacturasPorFechasYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), new Date());
            fila[0] = formaDePago.getNombre();
            double totalParcial = cajaService.calcularTotalPorMovimiento(facturasPorFormaDePago) + cajaService.calcularTotalPorMovimiento(gastos);
            fila[1] = totalParcial;
            total += totalParcial;
            modeloTablaResumen.addRow(fila);
        }
        Object[] fila = new Object[2];
        fila[0] = "TOTAL";
        fila[1] = total;
        this.totalCaja = total;
        this.ftxt_Total.setText(String.valueOf(this.totalCaja));
        modeloTablaResumen.addRow(fila);
        tbl_Resumen.setModel(modeloTablaResumen);
        tbl_Resumen.setDefaultRenderer(Double.class, new ColoresTablaResumenCaja());
    }

    private void calcularTotalBalance() {
        Object[] fila = new Object[6];
        double totalDebe = 0.0;
        double totalHaber = 0.0;
        for (int i = 0; i < modeloTablaBalance.getRowCount(); i++) {
            totalDebe += (Double) modeloTablaBalance.getValueAt(i, 2);
            totalHaber += (Double) modeloTablaBalance.getValueAt(i, 3);
        }
        fila[0] = "Total";
        fila[1] = "--";
        fila[2] = totalDebe;
        fila[3] = totalHaber;
        fila[4] = totalDebe - totalHaber;
        modeloTablaBalance.addRow(fila);
    }
}
