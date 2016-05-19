package sic.vista.swing;

import sic.util.ColoresNumerosTablaRenderer;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import sic.AppContextProvider;
import sic.modelo.Caja;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Gasto;
import sic.service.EstadoCaja;
import sic.service.ICajaService;
import sic.service.IEmpresaService;
import sic.service.IFacturaService;
import sic.service.IFormaDePagoService;
import sic.service.IGastoService;
import sic.service.IUsuarioService;
import sic.util.FormatoFechasEnTablasRenderer;
import sic.util.FormatterFechaHora;
import sic.util.FormatterNumero;
import sic.util.Utilidades;

public class GUI_Caja extends javax.swing.JDialog {

    private final ApplicationContext appContext = AppContextProvider.getApplicationContext();
    private final ICajaService cajaService = appContext.getBean(ICajaService.class);
    private final IEmpresaService empresaService = appContext.getBean(IEmpresaService.class);
    private final IFormaDePagoService formaDePagoService = appContext.getBean(IFormaDePagoService.class);
    private final IFacturaService facturaService = appContext.getBean(IFacturaService.class);
    private final IGastoService gastoService = appContext.getBean(IGastoService.class);
    private final IUsuarioService usuarioService = appContext.getBean(IUsuarioService.class);
    private final FormatterFechaHora formatoHora = new FormatterFechaHora(FormatterFechaHora.FORMATO_HORA_INTERNACIONAL);
    private ModeloTabla modeloTablaBalance;
    private ModeloTabla modeloTablaResumen;
    private List<Object> listaMovimientos = new ArrayList<>();
    private Caja caja;
    private static final Logger log = Logger.getLogger(GUI_Caja.class.getPackage().getName());

    public GUI_Caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.caja = cajaService.getUltimaCaja(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa());
        this.iniciarTituloVentana();
    }

    public GUI_Caja(java.awt.Frame parent, boolean modal, Caja caja) {
        super(parent, modal);
        initComponents();
        this.caja = caja;
        this.iniciarTituloVentana();
    }

    public GUI_Caja(Caja caja) {
        initComponents();
        this.caja = caja;
        this.iniciarTituloVentana();
    }

    private void cargarDatosBalance() {
        lbl_aviso.setText("Cerrada");
        lbl_aviso.setForeground(Color.RED);
        this.btn_AgregarGasto.setEnabled(false);
        this.btn_EliminarGasto.setEnabled(false);
        if (this.caja != null) {
            if (this.caja.getEstado() == EstadoCaja.ABIERTA) {
                lbl_aviso.setText("Abierta");
                lbl_aviso.setForeground(Color.GREEN);
                this.btn_AgregarGasto.setEnabled(true);
                this.btn_EliminarGasto.setEnabled(true);
            }
            this.listaMovimientos.clear();
            Date hasta = new Date();
            if (this.caja.getEstado() == EstadoCaja.CERRADA) {
                hasta = this.caja.getFechaCierre();
            }
            List<Factura> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), this.caja.getFechaApertura(), hasta);
            this.listaMovimientos.addAll(facturas);
            List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), ((FormaDePago) cmb_FormasDePago.getSelectedItem()).getId_FormaDePago(), this.caja.getFechaApertura(), hasta);
            this.listaMovimientos.addAll(gastos);
            this.cargarMovimientosEnLaTablaBalance(this.listaMovimientos);

        }
    }

    private void limpiarTablaBalance() {
        modeloTablaBalance = new ModeloTabla();
        tbl_Balance.setModel(modeloTablaBalance);
        this.setColumnasDeTablaFormaDePago();
    }

    private void limpiarTablaResumen() {
        modeloTablaResumen = new ModeloTabla();
        tbl_Resumen.setModel(modeloTablaResumen);
        this.setColumnasDeTablaResumenGeneral();
    }

    private void setColumnasDeTablaFormaDePago() {
        //sorting
        tbl_Balance.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[5];
        encabezados[0] = "Fecha";
        encabezados[1] = "Concepto";
        encabezados[2] = "Debe";
        encabezados[3] = "Haber";
        encabezados[4] = "Saldo";
        modeloTablaBalance.setColumnIdentifiers(encabezados);
        tbl_Balance.setModel(modeloTablaBalance);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaBalance.getColumnCount()];
        tipos[0] = Date.class;
        tipos[1] = String.class;
        tipos[2] = Double.class;
        tipos[3] = Double.class;
        tipos[4] = Double.class;
        modeloTablaBalance.setClaseColumnas(tipos);
        tbl_Balance.getTableHeader().setReorderingAllowed(false);
        tbl_Balance.getTableHeader().setResizingAllowed(true);

        //Tamanios de columnas
        tbl_Balance.getColumnModel().getColumn(0).setPreferredWidth(15);
        tbl_Balance.getColumnModel().getColumn(1).setPreferredWidth(150);
        tbl_Balance.getColumnModel().getColumn(2).setPreferredWidth(15);
        tbl_Balance.getColumnModel().getColumn(3).setPreferredWidth(15);
        tbl_Balance.getColumnModel().getColumn(4).setPreferredWidth(15);

    }

    private void cargarMovimientosEnLaTablaBalance(List<Object> movimientos) {
        this.limpiarTablaBalance();
        for (Object movimiento : movimientos) {
            Object[] fila = new Object[5];
            if (movimiento instanceof FacturaCompra) {
                fila[0] = ((FacturaCompra) movimiento).getFecha();
            }
            if (movimiento instanceof FacturaVenta) {
                fila[0] = ((FacturaVenta) movimiento).getFecha();
            }
            if (movimiento instanceof Gasto) {
                fila[0] = ((Gasto) movimiento).getFecha();
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
                    tipoFactura = "Venta";
                } else {
                    tipoFactura = "Compra";
                }
                fila[1] = "Factura " + tipoFactura + " Nº " + ((Factura) movimiento).getNumSerie() + " - " + ((Factura) movimiento).getNumFactura();
            } else if (movimiento instanceof Gasto) {
                fila[1] = ((Gasto) movimiento).getConcepto();
            }
            fila[4] = (double) fila[2] - (double) fila[3];
            modeloTablaBalance.addRow(fila);
        }
        this.calcularTotalBalance();
        tbl_Balance.setModel(modeloTablaBalance);
        tbl_Balance.getColumnModel().getColumn(4).setCellRenderer(new ColoresNumerosTablaRenderer());
        tbl_Balance.getColumnModel().getColumn(0).setCellRenderer(new FormatoFechasEnTablasRenderer());
        //Ordena la tabla segun la Fecha
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbl_Balance.getModel());
        tbl_Balance.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void setColumnasDeTablaResumenGeneral() {
        //sorting
        tbl_Resumen.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[2];
        encabezados[0] = "Forma de Pago";
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
        tbl_Resumen.getColumnModel().getColumn(0).setPreferredWidth(200);
        tbl_Resumen.getColumnModel().getColumn(1).setPreferredWidth(5);

    }

    private void cargarTablaResumenGeneral() {
        if (this.caja != null) {
            Empresa empresaActiva = empresaService.getEmpresaActiva().getEmpresa();
            double total = this.caja.getSaldoInicial();
            Object[] saldoInicial = new Object[2];
            saldoInicial[0] = "Saldo Apertura";
            saldoInicial[1] = total;
            modeloTablaResumen.addRow(saldoInicial);
            List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaActiva);
            for (FormaDePago formaDePago : formasDePago) {
                if (formaDePago.isAfectaCaja()) {
                    Date hasta = new Date();
                    if (this.caja.getEstado() == EstadoCaja.CERRADA) {
                        hasta = this.caja.getFechaCierre();
                    }
                    List<Factura> facturasPorFormaDePago = facturaService.getFacturasPorFechasYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), hasta);
                    List<Object> gastosPorFormaDePago = gastoService.getGastosPorFechaYFormaDePago(empresaActiva.getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), hasta);
                    if (facturasPorFormaDePago.size() > 0 || gastosPorFormaDePago.size() > 0) {
                        Object[] fila = new Object[2];
                        fila[0] = formaDePago.getNombre();
                        this.listaMovimientos.clear();
                        this.listaMovimientos.addAll(facturasPorFormaDePago);
                        this.listaMovimientos.addAll(gastosPorFormaDePago);
                        double totalParcial = cajaService.calcularTotalPorMovimiento(this.listaMovimientos);
                        fila[1] = totalParcial;
                        total += totalParcial;
                        modeloTablaResumen.addRow(fila);
                    }
                }
            }
            this.caja.setSaldoFinal(Math.floor(total * 100) / 100);
            this.ftxt_Total.setValue(this.caja.getSaldoFinal());
            //Guarda el monto final del último calculo en la caja
            cajaService.actualizar(this.caja);
            if (this.caja.getSaldoFinal() < 0) {
                ftxt_Total.setBackground(Color.PINK);
            }
            if (this.caja.getSaldoFinal() > 0) {
                ftxt_Total.setBackground(Color.GREEN);
            }
            tbl_Resumen.setModel(modeloTablaResumen);
            tbl_Resumen.setDefaultRenderer(Double.class, new ColoresNumerosTablaRenderer());
        }
    }

    private void calcularTotalBalance() {
        double totalDebe = 0.0;
        double totalHaber = 0.0;
        for (int i = 0; i < modeloTablaBalance.getRowCount(); i++) {
            totalDebe += (Double) modeloTablaBalance.getValueAt(i, 2);
            totalHaber += (Double) modeloTablaBalance.getValueAt(i, 3);
        }
        ftxt_Detalle.setValue(totalDebe - totalHaber);
        if ((Double) ftxt_Detalle.getValue() < 0) {
            ftxt_Detalle.setBackground(Color.PINK);
        }
        if ((Double) ftxt_Detalle.getValue() > 0) {
            ftxt_Detalle.setBackground(Color.GREEN);
        }
    }

    private void cargarElementosFormaDePago() {
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        if (cmb_FormasDePago.getItemCount() != formasDePago.size()) {
            cmb_FormasDePago.removeAllItems();
            for (FormaDePago formaDePago : formasDePago) {
                cmb_FormasDePago.addItem(formaDePago);
            }
        }
        this.limpiarTablaBalance();
        this.cargarDatosBalance();
    }

    private void limpiarYCargarTablas() {
        this.limpiarTablaResumen();
        this.cargarTablaResumenGeneral();
        this.cargarElementosFormaDePago();
    }

    private void lanzarReporteCaja() throws JRException {
        List<String> dataSource = new ArrayList<>();
        dataSource.add((String) tbl_Resumen.getValueAt(0, 0) + "-" + String.valueOf(FormatterNumero.formatConRedondeo((Number) tbl_Resumen.getValueAt(0, 1))));
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(empresaService.getEmpresaActiva().getEmpresa());
        double totalPorCorte = this.caja.getSaldoInicial();
        for (FormaDePago formaDePago : formasDePago) {
            double totalPorCorteFormaDePago = 0.0;
            if (formaDePago.isAfectaCaja()) {
                List<Factura> facturas = facturaService.getFacturasPorFechasYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), this.caja.getFechaCorteInforme());
                List<Object> gastos = gastoService.getGastosPorFechaYFormaDePago(empresaService.getEmpresaActiva().getEmpresa().getId_Empresa(), formaDePago.getId_FormaDePago(), this.caja.getFechaApertura(), this.caja.getFechaCorteInforme());
                for (Factura factura : facturas) {
                    totalPorCorteFormaDePago += factura.getTotal();
                }
                for (Object gasto : gastos) {
                    totalPorCorteFormaDePago += ((Gasto) gasto).getMonto();
                }
                dataSource.add(formaDePago.getNombre() + "-" + totalPorCorteFormaDePago);
            }
            totalPorCorte += totalPorCorteFormaDePago;
        }
        dataSource.add("Total hasta la hora de control:-" + String.valueOf(FormatterNumero.formatConRedondeo((Number) totalPorCorte)));
        dataSource.add("..........................Corte a las: " + formatoHora.format(this.caja.getFechaCorteInforme()) + "...........................-");

        for (int f = 1; f < tbl_Resumen.getRowCount(); f++) {
            dataSource.add((String) tbl_Resumen.getValueAt(f, 0) + "-" + String.valueOf(FormatterNumero.formatConRedondeo((Number) tbl_Resumen.getValueAt(f, 1))));
        }

        JasperPrint report = cajaService.getReporteCaja(this.caja, dataSource, usuarioService.getUsuarioActivo().getUsuario());
        JDialog viewer = new JDialog(new JFrame(), "Vista Previa", true);
        viewer.setSize(this.getWidth() - 25, this.getHeight() - 25);
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
        viewer.setIconImage(iconoVentana.getImage());
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(report);
        viewer.getContentPane().add(jrv);
        viewer.setVisible(true);
    }

    private void iniciarTituloVentana() {
        if (this.caja != null) {
            this.setTitle("Arqueo de Caja - Apertura: " + formatoHora.format(this.caja.getFechaApertura()));
        } else {
            this.setTitle("Arqueo De Caja");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Tabla = new javax.swing.JPanel();
        sp_Tabla = new javax.swing.JScrollPane();
        tbl_Balance = new javax.swing.JTable();
        btn_VerDetalle = new javax.swing.JButton();
        lbl_total = new javax.swing.JLabel();
        btn_AgregarGasto = new javax.swing.JButton();
        ftxt_Detalle = new javax.swing.JFormattedTextField();
        lbl_FormaDePago = new javax.swing.JLabel();
        cmb_FormasDePago = new javax.swing.JComboBox<>();
        btn_EliminarGasto = new javax.swing.JButton();
        btn_Imprimir = new javax.swing.JButton();
        btn_CerrarCaja = new javax.swing.JButton();
        pnl_Resumen = new javax.swing.JPanel();
        sp_TablaResumen = new javax.swing.JScrollPane();
        tbl_Resumen = new javax.swing.JTable();
        ftxt_Total = new javax.swing.JFormattedTextField();
        lbl_Total = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        lbl_aviso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1050, 678));
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

        pnl_Tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("Movimientos por Forma de Pago"));

        tbl_Balance.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sp_Tabla.setViewportView(tbl_Balance);

        btn_VerDetalle.setForeground(java.awt.Color.blue);
        btn_VerDetalle.setText("Ver Detalle");
        btn_VerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VerDetalleActionPerformed(evt);
            }
        });

        lbl_total.setText("Total:");

        btn_AgregarGasto.setForeground(java.awt.Color.blue);
        btn_AgregarGasto.setText("Agregar Gasto");
        btn_AgregarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarGastoActionPerformed(evt);
            }
        });

        ftxt_Detalle.setEditable(false);
        ftxt_Detalle.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        ftxt_Detalle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxt_Detalle.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        lbl_FormaDePago.setText("Forma de Pago:");

        cmb_FormasDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_FormasDePagoActionPerformed(evt);
            }
        });

        btn_EliminarGasto.setForeground(java.awt.Color.blue);
        btn_EliminarGasto.setText("Eliminar Gasto");
        btn_EliminarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarGastoActionPerformed(evt);
            }
        });

        btn_Imprimir.setForeground(java.awt.Color.blue);
        btn_Imprimir.setText("Imprimir");
        btn_Imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ImprimirActionPerformed(evt);
            }
        });

        btn_CerrarCaja.setForeground(java.awt.Color.blue);
        btn_CerrarCaja.setText("Cerrar Caja");
        btn_CerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CerrarCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_TablaLayout = new javax.swing.GroupLayout(pnl_Tabla);
        pnl_Tabla.setLayout(pnl_TablaLayout);
        pnl_TablaLayout.setHorizontalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp_Tabla)
            .addGroup(pnl_TablaLayout.createSequentialGroup()
                .addComponent(lbl_FormaDePago)
                .addGap(2, 2, 2)
                .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_TablaLayout.createSequentialGroup()
                .addComponent(btn_VerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_AgregarGasto)
                .addGap(0, 0, 0)
                .addComponent(btn_EliminarGasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_total)
                .addGap(2, 2, 2)
                .addComponent(ftxt_Detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_Imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_CerrarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_AgregarGasto, btn_EliminarGasto, btn_VerDetalle});

        pnl_TablaLayout.setVerticalGroup(
            pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_TablaLayout.createSequentialGroup()
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_FormaDePago)
                    .addComponent(cmb_FormasDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_VerDetalle)
                    .addComponent(btn_AgregarGasto)
                    .addComponent(btn_EliminarGasto)
                    .addComponent(lbl_total)
                    .addComponent(ftxt_Detalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnl_TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CerrarCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Imprimir)))
        );

        pnl_TablaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_AgregarGasto, btn_EliminarGasto});

        pnl_Resumen.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen General"));

        tbl_Resumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        sp_TablaResumen.setViewportView(tbl_Resumen);
        tbl_Resumen.getAccessibleContext().setAccessibleParent(sp_Tabla);

        ftxt_Total.setEditable(false);
        ftxt_Total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        ftxt_Total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxt_Total.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        lbl_Total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Total.setText("Total:");

        javax.swing.GroupLayout pnl_ResumenLayout = new javax.swing.GroupLayout(pnl_Resumen);
        pnl_Resumen.setLayout(pnl_ResumenLayout);
        pnl_ResumenLayout.setHorizontalGroup(
            pnl_ResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ResumenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Total)
                .addGap(2, 2, 2)
                .addComponent(ftxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(sp_TablaResumen, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        pnl_ResumenLayout.setVerticalGroup(
            pnl_ResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ResumenLayout.createSequentialGroup()
                .addComponent(sp_TablaResumen, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_ResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxt_Total)
                    .addComponent(lbl_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pnl_ResumenLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftxt_Total, lbl_Total});

        lbl_estado.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_estado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_estado.setText("Estado:");

        lbl_aviso.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lbl_aviso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Resumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_estado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(pnl_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Resumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.limpiarYCargarTablas();
    }//GEN-LAST:event_formWindowOpened

    private void btn_CerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CerrarCajaActionPerformed
        if (this.caja != null) {
            if (this.caja.getEstado() == EstadoCaja.ABIERTA) {
                try {
                    String monto = JOptionPane.showInputDialog(this, "Saldo del Sistema: " + this.caja.getSaldoFinal() + "\nSaldo Real:", "Cerrar Caja", JOptionPane.QUESTION_MESSAGE);
                    if (monto != null) {
                        this.caja.setSaldoReal(Double.parseDouble(monto));
                        this.caja.setFechaCierre(new Date());
                        this.caja.setUsuarioCierraCaja(usuarioService.getUsuarioActivo().getUsuario());
                        this.caja.setEstado(EstadoCaja.CERRADA);
                        this.cajaService.actualizar(caja);
                        try {
                            this.lanzarReporteCaja();
                            this.dispose();
                        } catch (JRException ex) {
                            String msjError = "Se produjo un error procesando el reporte.";
                            log.error(msjError + " - " + ex.getMessage());
                            JOptionPane.showMessageDialog(this, msjError, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (java.lang.NumberFormatException e) {
                    String msjError = "Monto inválido";
                    log.error(msjError + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(this, msjError, "Error", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Caja Cerrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_CerrarCajaActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.limpiarYCargarTablas();
    }//GEN-LAST:event_formWindowGainedFocus

    private void btn_VerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VerDetalleActionPerformed
        if (tbl_Balance.getSelectedRow() != -1) {
            Object movimientoDeTabla = this.listaMovimientos.get(Utilidades.getSelectedRowModelIndice(tbl_Balance));
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
        this.cargarElementosFormaDePago();
    }//GEN-LAST:event_btn_AgregarGastoActionPerformed

    private void cmb_FormasDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_FormasDePagoActionPerformed
        this.limpiarTablaBalance();
        this.cargarDatosBalance();
    }//GEN-LAST:event_cmb_FormasDePagoActionPerformed

    private void btn_ImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ImprimirActionPerformed
        try {
            this.lanzarReporteCaja();
        } catch (JRException ex) {
            String msjError = "Se produjo un error procesando el reporte.";
            log.error(msjError + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, msjError, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_ImprimirActionPerformed

    private void btn_EliminarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarGastoActionPerformed
        if (tbl_Balance.getSelectedRow() != -1) {
            Object movimientoDeTabla = this.listaMovimientos.get(Utilidades.getSelectedRowModelIndice(tbl_Balance));
            if (movimientoDeTabla instanceof Gasto) {
                int confirmacionEliminacion = JOptionPane.showConfirmDialog(this,
                        "¿Esta seguro que desea eliminar el gasto seleccionado?",
                        "Eliminar", JOptionPane.YES_NO_OPTION);
                if (confirmacionEliminacion == JOptionPane.YES_OPTION) {
                    Gasto gasto = (Gasto) movimientoDeTabla;
                    gasto.setEliminado(true);
                    gastoService.actualizar(gasto);
                    this.limpiarYCargarTablas();
                }
            }
        }
    }//GEN-LAST:event_btn_EliminarGastoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AgregarGasto;
    private javax.swing.JButton btn_CerrarCaja;
    private javax.swing.JButton btn_EliminarGasto;
    private javax.swing.JButton btn_Imprimir;
    private javax.swing.JButton btn_VerDetalle;
    private javax.swing.JComboBox<FormaDePago> cmb_FormasDePago;
    private javax.swing.JFormattedTextField ftxt_Detalle;
    private javax.swing.JFormattedTextField ftxt_Total;
    private javax.swing.JLabel lbl_FormaDePago;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_aviso;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel pnl_Resumen;
    private javax.swing.JPanel pnl_Tabla;
    private javax.swing.JScrollPane sp_Tabla;
    private javax.swing.JScrollPane sp_TablaResumen;
    javax.swing.JTable tbl_Balance;
    private javax.swing.JTable tbl_Resumen;
    // End of variables declaration//GEN-END:variables
}
