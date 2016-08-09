package sic.vista.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import sic.AppContextProvider;
import sic.modelo.Factura;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Pago;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.service.IFacturaService;
import sic.service.IFormaDePagoService;
import sic.service.ITransportistaService;
import sic.service.IUsuarioService;
import sic.service.ServiceException;
import sic.service.*;

public class GUI_CerrarVenta extends JDialog {

    private final GUI_PuntoDeVenta gui_puntoDeVenta;
    private boolean exito;
    private final ApplicationContext appContext = AppContextProvider.getApplicationContext();
    private final IFormaDePagoService formaDePagoService = appContext.getBean(IFormaDePagoService.class);
    private final ITransportistaService transportistaService = appContext.getBean(ITransportistaService.class);
    private final IFacturaService facturaService = appContext.getBean(IFacturaService.class);
    private final IUsuarioService usuarioService = appContext.getBean(IUsuarioService.class);
    private final IPedidoService pedidoService = appContext.getBean(IPedidoService.class);
    private final IPagoService pagoService = appContext.getBean(IPagoService.class);
    private final HotKeysHandler keyHandler = new HotKeysHandler();

    private static final Logger log = Logger.getLogger(GUI_CerrarVenta.class.getPackage().getName());

    public GUI_CerrarVenta(JDialog parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIcon();
        this.setLocationRelativeTo(null);
        this.gui_puntoDeVenta = (GUI_PuntoDeVenta) parent;
        lbl_Vendedor.setText("");
        lbl_TotalAPagar.setValue(gui_puntoDeVenta.getResultadosFactura().getTotal());
        lbl_Vuelto.setValue(0);

        //listeners
        cmb_FormaDePago3.addKeyListener(keyHandler);
        cmb_Transporte.addKeyListener(keyHandler);
        txt_AbonaCon1.addKeyListener(keyHandler);
        btn_Finalizar.addKeyListener(keyHandler);
        if (gui_puntoDeVenta.getTipoDeComprobante().equals("Factura A") || gui_puntoDeVenta.getTipoDeComprobante().equals("Factura B") || gui_puntoDeVenta.getTipoDeComprobante().equals("Factura C")) {
            this.chk_condicionDividir.setEnabled(true);
        }
    }

    public boolean isExito() {
        return exito;
    }

    public void actualizarEstadoPedido(Pedido pedido) {
        if (facturaService.convertirRenglonesPedidoARenglonesFactura(gui_puntoDeVenta.getPedido(), "Factura A").isEmpty()) {
            pedido.setEstado(EstadoPedido.CERRADO);
        } else {
            pedido.setEstado(EstadoPedido.ACTIVO);
        }
        pedidoService.actualizar(pedido);
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void lanzarReporteFactura(Factura factura) throws JRException {
        JasperPrint report = facturaService.getReporteFacturaVenta(factura);
        JDialog viewer = new JDialog(new JFrame(), "Vista Previa", true);
        viewer.setSize(gui_puntoDeVenta.getWidth() - 25, gui_puntoDeVenta.getHeight() - 25);
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
        viewer.setIconImage(iconoVentana.getImage());
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(report);
        viewer.getContentPane().add(jrv);
        viewer.setVisible(true);
    }

    private void cargarFormasDePago() {
        cmb_FormaDePago1.removeAllItems();
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(gui_puntoDeVenta.getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormaDePago1.addItem(formaDePago);
        }
    }

    private void cargarTransportistas() {
        cmb_Transporte.removeAllItems();
        List<Transportista> transportes = transportistaService.getTransportistas(gui_puntoDeVenta.getEmpresa());
        for (Transportista transporte : transportes) {
            cmb_Transporte.addItem(transporte);
        }
    }

    private Factura guardarFactura(Factura facturaVenta) throws ServiceException {
        facturaService.guardar(facturaVenta);
        Factura facturaGuardada = facturaService.getFacturaVentaPorTipoSerieNum(facturaVenta.getTipoFactura(), facturaVenta.getNumSerie(), facturaVenta.getNumFactura());
        facturaGuardada.setPagos(pagoService.getPagosDeLaFactura(facturaGuardada));
        return facturaGuardada;
    }

    private void calcularVuelto() {
        try {
            txt_AbonaCon1.commitEdit();
            txt_AbonaCon2.commitEdit();
            txt_AbonaCon3.commitEdit();
            double montoAbonado = 0.0;
            if (chk_FormaDePago1.isSelected()) {
                montoAbonado += Double.parseDouble((txt_AbonaCon1.getText()).replaceAll(",", ""));
            }
            if (chk_FormaDePago2.isSelected()) {
                montoAbonado += Double.parseDouble((txt_AbonaCon2.getText()).replaceAll(",", ""));
            }
            if (chk_FormaDePago3.isSelected()) {
                montoAbonado += Double.parseDouble((txt_AbonaCon3.getText()).replaceAll(",", ""));
            }
            double vuelto = facturaService.calcularVuelto(gui_puntoDeVenta.getResultadosFactura().getTotal(), montoAbonado);
            lbl_Vuelto.setValue(vuelto);

        } catch (ParseException ex) {
            String msjError = "Se produjo un error analizando los campos.";
            log.error(msjError + " - " + ex.getMessage());
        }
    }

    private FacturaVenta construirFactura() {
        FacturaVenta facturaVenta = new FacturaVenta();
        facturaVenta.setFecha(gui_puntoDeVenta.getFechaFactura());
        facturaVenta.setTipoFactura(gui_puntoDeVenta.getTipoDeComprobante().charAt(gui_puntoDeVenta.getTipoDeComprobante().length() - 1));
        facturaVenta.setNumSerie(1);
        facturaVenta.setNumFactura(facturaService.calcularNumeroFactura(gui_puntoDeVenta.getTipoDeComprobante(), 1));
        List<Pago> pagos = this.construirListaPagos();
        double montoPagado = 0.0;
        for (Pago pago : pagos) {
            pago.setFactura(facturaVenta);
            montoPagado += pago.getMonto();
        }
        facturaVenta.setPagos(pagos);
        facturaVenta.setFechaVencimiento(gui_puntoDeVenta.getFechaVencimiento());
        facturaVenta.setTransportista((Transportista) cmb_Transporte.getSelectedItem());
        List<RenglonFactura> lineasFactura = new ArrayList<>(gui_puntoDeVenta.getRenglones());
        facturaVenta.setRenglones(lineasFactura);
        for (RenglonFactura renglon : lineasFactura) {
            renglon.setFactura(facturaVenta);
        }
        facturaVenta.setSubTotal(gui_puntoDeVenta.getResultadosFactura().getSubTotal());
        facturaVenta.setRecargo_porcentaje(gui_puntoDeVenta.getResultadosFactura().getRecargo_porcentaje());
        facturaVenta.setRecargo_neto(gui_puntoDeVenta.getResultadosFactura().getRecargo_neto());
        facturaVenta.setDescuento_porcentaje(0);
        facturaVenta.setDescuento_neto(0);
        facturaVenta.setSubTotal_neto(gui_puntoDeVenta.getResultadosFactura().getSubTotal_neto());
        facturaVenta.setIva_105_neto(gui_puntoDeVenta.getResultadosFactura().getIva_105_neto());
        facturaVenta.setIva_21_neto(gui_puntoDeVenta.getResultadosFactura().getIva_21_neto());
        facturaVenta.setImpuestoInterno_neto(gui_puntoDeVenta.getResultadosFactura().getImpuestoInterno_neto());
        facturaVenta.setTotal(gui_puntoDeVenta.getResultadosFactura().getTotal());
        facturaVenta.setObservaciones(gui_puntoDeVenta.getTxta_Observaciones().getText().trim());
        facturaVenta.setPagada((facturaVenta.getTotal() - montoPagado) <= 0);
        facturaVenta.setEmpresa(gui_puntoDeVenta.getEmpresa());
        facturaVenta.setEliminada(false);
        facturaVenta.setCliente(gui_puntoDeVenta.getCliente());
        facturaVenta.setUsuario(usuarioService.getUsuarioActivo().getUsuario());

        return facturaVenta;
    }

    private void estadosCmbFormaDePago() {
        chk_FormaDePago1.setSelected(true);
        cmb_FormaDePago1.setSelectedItem(formaDePagoService.getFormaDePagoPredeterminada(gui_puntoDeVenta.getEmpresa()));
        cmb_FormaDePago2.setEnabled(false);
        txt_AbonaCon2.setEnabled(false);
        cmb_FormaDePago2.setSelectedItem(formaDePagoService.getFormaDePagoPredeterminada(gui_puntoDeVenta.getEmpresa()));
        cmb_FormaDePago3.setSelectedItem(formaDePagoService.getFormaDePagoPredeterminada(gui_puntoDeVenta.getEmpresa()));
        cmb_FormaDePago3.setEnabled(false);
        txt_AbonaCon3.setEnabled(false);

    }

    private List<Pago> construirListaPagos() {
        List<Pago> pagos = new ArrayList<>();
        if (chk_FormaDePago1.isSelected()) {
            Pago pago1 = new Pago();
            pago1.setEmpresa(gui_puntoDeVenta.getEmpresa());
            pago1.setFormaDePago((FormaDePago) cmb_FormaDePago1.getSelectedItem());
            pago1.setFecha(new Date());
            pago1.setMonto(Double.parseDouble(txt_AbonaCon1.getValue().toString()));
            pago1.setNota("");
            pagos.add(pago1);
        }
        if (chk_FormaDePago2.isSelected()) {
            Pago pago2 = new Pago();
            pago2.setEmpresa(gui_puntoDeVenta.getEmpresa());
            pago2.setFormaDePago((FormaDePago) cmb_FormaDePago2.getSelectedItem());
            pago2.setFecha(new Date());
            pago2.setMonto(Double.parseDouble(txt_AbonaCon2.getValue().toString()));
            pago2.setNota("");
            pagos.add(pago2);
        }
        if (chk_FormaDePago3.isSelected()) {
            Pago pago3 = new Pago();
            pago3.setEmpresa(gui_puntoDeVenta.getEmpresa());
            pago3.setFormaDePago((FormaDePago) cmb_FormaDePago3.getSelectedItem());
            pago3.setFecha(new Date());
            pago3.setMonto(Double.parseDouble(txt_AbonaCon3.getValue().toString()));
            pago3.setNota("");
            pagos.add(pago3);
        }
        return pagos;
    }

    private void finalizarVenta() {
        try {
            boolean dividir = false;
            int[] indicesParaDividir = null;
            if (chk_condicionDividir.isSelected()
                    && (gui_puntoDeVenta.getTipoDeComprobante().equals("Factura A")
                    || gui_puntoDeVenta.getTipoDeComprobante().equals("Factura B")
                    || gui_puntoDeVenta.getTipoDeComprobante().equals("Factura C"))) {

                ModeloTabla modeloTablaPuntoDeVenta = gui_puntoDeVenta.getModeloTabla();
                indicesParaDividir = new int[modeloTablaPuntoDeVenta.getRowCount()];
                int j = 0;
                boolean tieneRenglonesMarcados = false;
                for (int i = 0; i < modeloTablaPuntoDeVenta.getRowCount(); i++) {
                    if ((boolean) modeloTablaPuntoDeVenta.getValueAt(i, 0)) {
                        indicesParaDividir[j] = i;
                        j++;
                        tieneRenglonesMarcados = true;
                    }
                }
                if (indicesParaDividir.length != 0 && tieneRenglonesMarcados) {
                    dividir = true;
                }
            }
            if (!dividir) {
                FacturaVenta factura = this.construirFactura();
                if (gui_puntoDeVenta.getPedido() != null) {
                    factura.setPedido(pedidoService.getPedidoPorNumero(gui_puntoDeVenta.getPedido().getNroPedido(), gui_puntoDeVenta.getEmpresa().getId_Empresa()));
                }
                this.lanzarReporteFactura(this.guardarFactura(factura));
                exito = true;
            } else {
                List<FacturaVenta> facturasDivididas = facturaService.dividirFactura(this.construirFactura(), indicesParaDividir);
                for (Factura factura : facturasDivididas) {
                    if (facturasDivididas.size() == 2 && !factura.getRenglones().isEmpty()) {
                        if (gui_puntoDeVenta.getPedido() != null) {
                            factura.setPedido(pedidoService.getPedidoPorNumero(gui_puntoDeVenta.getPedido().getNroPedido(), gui_puntoDeVenta.getEmpresa().getId_Empresa()));
                        }
                        this.lanzarReporteFactura(this.guardarFactura(factura));
                        exito = true;
                    }
                }
            }
            if (gui_puntoDeVenta.getPedido() != null) {
                this.actualizarEstadoPedido(gui_puntoDeVenta.getPedido());
                gui_puntoDeVenta.dispose();
            }
            this.dispose();

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (JRException ex) {
            String msjError = "Se produjo un error procesando el reporte.";
            log.error(msjError + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, msjError, "Error", JOptionPane.ERROR_MESSAGE);
        }
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

        panelGeneral = new javax.swing.JPanel();
        cmb_FormaDePago3 = new javax.swing.JComboBox();
        lbl_Transporte = new javax.swing.JLabel();
        cmb_Transporte = new javax.swing.JComboBox();
        lbl_Vendor = new javax.swing.JLabel();
        lbl_Vendedor = new javax.swing.JLabel();
        separador = new javax.swing.JSeparator();
        chk_FormaDePago3 = new javax.swing.JCheckBox();
        chk_FormaDePago1 = new javax.swing.JCheckBox();
        cmb_FormaDePago1 = new javax.swing.JComboBox();
        chk_FormaDePago2 = new javax.swing.JCheckBox();
        cmb_FormaDePago2 = new javax.swing.JComboBox();
        lbl_Total = new javax.swing.JLabel();
        lbl_Cambio = new javax.swing.JLabel();
        lbl_TotalAPagar = new javax.swing.JFormattedTextField();
        txt_AbonaCon1 = new javax.swing.JFormattedTextField();
        lbl_Devolucion = new javax.swing.JLabel();
        lbl_Vuelto = new javax.swing.JFormattedTextField();
        chk_condicionDividir = new javax.swing.JCheckBox();
        btn_Finalizar = new javax.swing.JButton();
        separador1 = new javax.swing.JSeparator();
        txt_AbonaCon2 = new javax.swing.JFormattedTextField();
        txt_AbonaCon3 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cerrar Venta");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbl_Transporte.setText("Transporte:");

        lbl_Vendor.setText("Vendedor:");

        lbl_Vendedor.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vendedor.setText("XXXXXXXXXXXXXXXXXXXXXX");

        chk_FormaDePago3.setText("Forma de Pago #3:");
        chk_FormaDePago3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago3ItemStateChanged(evt);
            }
        });

        chk_FormaDePago1.setText("Forma de Pago #1:");
        chk_FormaDePago1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago1ItemStateChanged(evt);
            }
        });

        chk_FormaDePago2.setText("Forma de Pago #2:");
        chk_FormaDePago2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago2ItemStateChanged(evt);
            }
        });

        lbl_Total.setText("Total a pagar:");

        lbl_Cambio.setText("Cambio:");

        lbl_TotalAPagar.setEditable(false);
        lbl_TotalAPagar.setForeground(new java.awt.Color(29, 156, 37));
        lbl_TotalAPagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_TotalAPagar.setText("0.00");
        lbl_TotalAPagar.setFocusable(false);
        lbl_TotalAPagar.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        txt_AbonaCon1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txt_AbonaCon1.setText("0.00");
        txt_AbonaCon1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_AbonaCon1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AbonaCon1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_AbonaCon1FocusLost(evt);
            }
        });
        txt_AbonaCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AbonaCon1ActionPerformed(evt);
            }
        });
        txt_AbonaCon1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AbonaCon1KeyReleased(evt);
            }
        });

        lbl_Devolucion.setText("Vuelto:");

        lbl_Vuelto.setEditable(false);
        lbl_Vuelto.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vuelto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_Vuelto.setText("0.00");
        lbl_Vuelto.setFocusable(false);
        lbl_Vuelto.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        chk_condicionDividir.setText("Dividir Factura");
        chk_condicionDividir.setEnabled(false);

        btn_Finalizar.setForeground(java.awt.Color.blue);
        btn_Finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Finalizar.setText("Finalizar");
        btn_Finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FinalizarActionPerformed(evt);
            }
        });

        txt_AbonaCon2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txt_AbonaCon2.setText("0.00");
        txt_AbonaCon2.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_AbonaCon2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AbonaCon2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_AbonaCon2FocusLost(evt);
            }
        });
        txt_AbonaCon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AbonaCon2ActionPerformed(evt);
            }
        });
        txt_AbonaCon2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AbonaCon2KeyReleased(evt);
            }
        });

        txt_AbonaCon3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txt_AbonaCon3.setText("0.00");
        txt_AbonaCon3.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_AbonaCon3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AbonaCon3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_AbonaCon3FocusLost(evt);
            }
        });
        txt_AbonaCon3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AbonaCon3ActionPerformed(evt);
            }
        });
        txt_AbonaCon3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AbonaCon3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Transporte, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Vendor, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Vendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelGeneralLayout.createSequentialGroup()
                                .addComponent(cmb_Transporte, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGeneralLayout.createSequentialGroup()
                        .addComponent(lbl_Cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Devolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Vuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Finalizar)))
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelGeneralLayout.createSequentialGroup()
                                    .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cmb_FormaDePago1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGeneralLayout.createSequentialGroup()
                                                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(panelGeneralLayout.createSequentialGroup()
                                                        .addComponent(chk_FormaDePago3)
                                                        .addGap(6, 6, 6))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGeneralLayout.createSequentialGroup()
                                                        .addComponent(chk_FormaDePago2)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cmb_FormaDePago2, 0, 212, Short.MAX_VALUE)
                                                    .addComponent(cmb_FormaDePago3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addComponent(chk_FormaDePago1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_AbonaCon3)
                                        .addComponent(txt_AbonaCon2)
                                        .addComponent(txt_AbonaCon1)))
                                .addComponent(separador, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(chk_condicionDividir))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelGeneralLayout.setVerticalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Vendor)
                    .addComponent(lbl_Vendedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Transporte)
                    .addComponent(cmb_Transporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_AbonaCon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_FormaDePago1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_FormaDePago1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_FormaDePago2)
                    .addComponent(cmb_FormaDePago2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_AbonaCon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_FormaDePago3)
                    .addComponent(cmb_FormaDePago3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_AbonaCon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGeneralLayout.createSequentialGroup()
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_Total)
                            .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_Devolucion)
                            .addComponent(lbl_Vuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Finalizar)
                            .addComponent(chk_condicionDividir)))
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbl_Cambio)
                        .addGap(74, 74, 74)))
                .addGap(80, 80, 80))
        );

        panelGeneralLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_AbonaCon1, txt_AbonaCon3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.cargarFormasDePago();
            this.cargarTransportistas();
            this.estadosCmbFormaDePago();
            //set predeterminado
            cmb_Transporte.setSelectedIndex(0);
            lbl_Vendedor.setText(usuarioService.getUsuarioActivo().getUsuario().getNombre());
            txt_AbonaCon1.requestFocus();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_FinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FinalizarActionPerformed
        double totalPagos = 0.0;
        if (chk_FormaDePago1.isSelected()) {
            totalPagos += Double.parseDouble((txt_AbonaCon1.getText()).replaceAll(",", ""));
        }
        if (chk_FormaDePago2.isSelected()) {
            totalPagos += Double.parseDouble((txt_AbonaCon2.getText()).replaceAll(",", ""));
        }
        if (chk_FormaDePago3.isSelected()) {
            totalPagos += Double.parseDouble((txt_AbonaCon3.getText()).replaceAll(",", ""));
        }
        double totalAPagar = Double.parseDouble((lbl_TotalAPagar.getText().substring(1)).replaceAll(",", ""));
        if (totalPagos < totalAPagar) {
            int reply = JOptionPane.showConfirmDialog(this, "El monto pagado es inferior al total a pagar.\n ¿Desea continuar?", "Aviso", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                this.finalizarVenta();
            }
        } else {
            this.finalizarVenta();
        }
    }//GEN-LAST:event_btn_FinalizarActionPerformed

    private void txt_AbonaCon1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AbonaCon1KeyReleased
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon1KeyReleased

    private void txt_AbonaCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AbonaCon1ActionPerformed
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon1ActionPerformed

    private void txt_AbonaCon1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon1FocusLost
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon1FocusLost

    private void txt_AbonaCon1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon1FocusGained
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txt_AbonaCon1.selectAll();
            }
        });
    }//GEN-LAST:event_txt_AbonaCon1FocusGained

    private void txt_AbonaCon2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon2FocusGained
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon2FocusGained

    private void txt_AbonaCon2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon2FocusLost
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon2FocusLost

    private void txt_AbonaCon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AbonaCon2ActionPerformed
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon2ActionPerformed

    private void txt_AbonaCon2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AbonaCon2KeyReleased
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon2KeyReleased

    private void txt_AbonaCon3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon3FocusGained
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon3FocusGained

    private void txt_AbonaCon3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaCon3FocusLost
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon3FocusLost

    private void txt_AbonaCon3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AbonaCon3ActionPerformed
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon3ActionPerformed

    private void txt_AbonaCon3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AbonaCon3KeyReleased
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaCon3KeyReleased

    private void chk_FormaDePago1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago1ItemStateChanged
        cmb_FormaDePago1.setEnabled(chk_FormaDePago1.isSelected());
    }//GEN-LAST:event_chk_FormaDePago1ItemStateChanged

    private void chk_FormaDePago2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago2ItemStateChanged
        cmb_FormaDePago2.setEnabled(chk_FormaDePago2.isSelected());
        cmb_FormaDePago2.removeAllItems();
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(gui_puntoDeVenta.getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormaDePago2.addItem(formaDePago);
        }
        txt_AbonaCon2.setEnabled(!txt_AbonaCon2.isEnabled());
    }//GEN-LAST:event_chk_FormaDePago2ItemStateChanged

    private void chk_FormaDePago3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago3ItemStateChanged
        cmb_FormaDePago3.setEnabled(chk_FormaDePago3.isSelected());
        cmb_FormaDePago3.removeAllItems();
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(gui_puntoDeVenta.getEmpresa());
        for (FormaDePago formaDePago : formasDePago) {
            cmb_FormaDePago3.addItem(formaDePago);
        }
        txt_AbonaCon3.setEnabled(!txt_AbonaCon3.isEnabled());
    }//GEN-LAST:event_chk_FormaDePago3ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Finalizar;
    private javax.swing.JCheckBox chk_FormaDePago1;
    private javax.swing.JCheckBox chk_FormaDePago2;
    private javax.swing.JCheckBox chk_FormaDePago3;
    private javax.swing.JCheckBox chk_condicionDividir;
    private javax.swing.JComboBox cmb_FormaDePago1;
    private javax.swing.JComboBox cmb_FormaDePago2;
    private javax.swing.JComboBox cmb_FormaDePago3;
    private javax.swing.JComboBox cmb_Transporte;
    private javax.swing.JLabel lbl_Cambio;
    private javax.swing.JLabel lbl_Devolucion;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JFormattedTextField lbl_TotalAPagar;
    private javax.swing.JLabel lbl_Transporte;
    private javax.swing.JLabel lbl_Vendedor;
    private javax.swing.JLabel lbl_Vendor;
    private javax.swing.JFormattedTextField lbl_Vuelto;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JSeparator separador;
    private javax.swing.JSeparator separador1;
    private javax.swing.JFormattedTextField txt_AbonaCon1;
    private javax.swing.JFormattedTextField txt_AbonaCon2;
    private javax.swing.JFormattedTextField txt_AbonaCon3;
    // End of variables declaration//GEN-END:variables
}
