package sic.vista.swing;

import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import sic.RestClient;
import sic.modelo.EmpresaActiva;
import sic.modelo.Factura;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Pago;
import sic.modelo.RenglonFactura;
import sic.modelo.TipoDeComprobante;
import sic.modelo.Transportista;
import sic.modelo.UsuarioActivo;

public class CerrarVentaGUI extends JDialog {

    private boolean exito;
    private final PuntoDeVentaGUI gui_puntoDeVenta;
    private final HotKeysHandler keyHandler = new HotKeysHandler();
    private int[] indicesParaDividir = null;
    private boolean dividir = false;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public CerrarVentaGUI(JDialog parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIcon();
        this.setLocationRelativeTo(null);
        this.gui_puntoDeVenta = (PuntoDeVentaGUI) parent;
        //listeners
        cmb_FormaDePago3.addKeyListener(keyHandler);
        cmb_Transporte.addKeyListener(keyHandler);
        txt_MontoPago1.addKeyListener(keyHandler);
        btn_Finalizar.addKeyListener(keyHandler);
    }

    public boolean isExito() {
        return exito;
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(CerrarVentaGUI.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void lanzarReporteFactura(Factura factura, String nombreReporte) {
        if (Desktop.isDesktopSupported()) {
            try {
                byte[] reporte = RestClient.getRestTemplate()
                        .getForObject("/facturas/" + factura.getId_Factura() + "/reporte",
                                byte[].class);
                File f = new File(nombreReporte + ".pdf");
                Files.write(f.toPath(), reporte);
                Desktop.getDesktop().open(f);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("Mensajes").getString("mensaje_error_IOException"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RestClientResponseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ResourceAccessException ex) {
                LOGGER.error(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_plataforma_no_soportada"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarFormasDePago() {
        try {
            cmb_FormaDePago1.removeAllItems();
            cmb_FormaDePago2.removeAllItems();
            cmb_FormaDePago3.removeAllItems();
            List<FormaDePago> formasDePago = Arrays.asList(RestClient.getRestTemplate()
                    .getForObject("/formas-de-pago/empresas/" + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                            FormaDePago[].class));
            formasDePago.stream().map((f) -> {
                cmb_FormaDePago1.addItem(f);
                return f;
            }).map((f) -> {
                cmb_FormaDePago2.addItem(f);
                return f;
            }).forEach((f) -> {
                cmb_FormaDePago3.addItem(f);
            });
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTransportistas() {
        try {
            cmb_Transporte.removeAllItems();
            List<Transportista> transportes = Arrays.asList(RestClient.getRestTemplate()
                    .getForObject("/transportistas/empresas/" + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                            Transportista[].class));
            transportes.stream().forEach((t) -> {
                cmb_Transporte.addItem(t);
            });
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularVuelto() {
        try {
            txt_AbonaCon.commitEdit();
            double montoRecibido = Double.parseDouble((txt_AbonaCon.getText()));
            double vuelto = montoRecibido - gui_puntoDeVenta.getTotal();
            if (vuelto >= 0) {
                lbl_Vuelto.setValue(vuelto);
            } else {
                lbl_Vuelto.setValue(0.0);
            }            
        } catch (ParseException ex) {
            String mensaje = "Se produjo un error analizando los campos.";
            LOGGER.error(mensaje + " - " + ex.getMessage());
        }
    }

    private void setEstadoFormasDePago() {
        try {
            chk_FormaDePago1.setSelected(true);
            FormaDePago formaDePagoPredeterminada = RestClient.getRestTemplate()
                    .getForObject("/formas-de-pago/predeterminada/empresas/"
                            + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                            FormaDePago.class);
            cmb_FormaDePago1.setSelectedItem(formaDePagoPredeterminada);
            cmb_FormaDePago2.setEnabled(false);
            txt_MontoPago2.setEnabled(false);
            cmb_FormaDePago2.setSelectedItem(formaDePagoPredeterminada);
            cmb_FormaDePago3.setSelectedItem(formaDePagoPredeterminada);
            cmb_FormaDePago3.setEnabled(false);
            txt_MontoPago3.setEnabled(false);
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finalizarVenta() {
        FacturaVenta facturaVenta = gui_puntoDeVenta.construirFactura();
        facturaVenta.setTransportista((Transportista) cmb_Transporte.getSelectedItem());
        facturaVenta = this.agregarPagosAFactura(facturaVenta);
        try {
            String uri = "/facturas?idPedido=";
            if (gui_puntoDeVenta.getPedido() != null) {
                uri += gui_puntoDeVenta.getPedido().getId_Pedido();
            }
            if (dividir) {
                String indices = "&indices=" + Arrays.toString(indicesParaDividir).substring(1, Arrays.toString(indicesParaDividir).length() - 1);
                List<Factura> facturasDivididas = Arrays.asList(RestClient.getRestTemplate()
                        .postForObject(uri + indices, facturaVenta, Factura[].class));
                int indice = facturasDivididas.size();
                for (int i = 0; i < indice; i++) {
                    facturasDivididas.get(i).setRenglones(Arrays.asList(RestClient.getRestTemplate()
                            .getForObject("/facturas/" + facturasDivididas.get(i).getId_Factura() + "/renglones",
                                    RenglonFactura[].class)));
                    if (facturasDivididas.size() == 2 && !facturasDivididas.get(i).getRenglones().isEmpty()) {
                        if (i == 0) {
                            this.lanzarReporteFactura(facturasDivididas.get(i), "ComprobanteX");
                        } else {
                            this.lanzarReporteFactura(facturasDivididas.get(i), "Factura");
                        }
                        exito = true;
                    } else if (facturasDivididas.size() == 1 && !facturasDivididas.get(i).getRenglones().isEmpty()) {
                        this.lanzarReporteFactura(facturasDivididas.get(i), "Factura");
                    }
                }
            } else {
                this.lanzarReporteFactura(Arrays.asList(RestClient.getRestTemplate()
                        .postForObject(uri, facturaVenta, FacturaVenta[].class)).get(0), "Factura");
                exito = true;
            }
            if (gui_puntoDeVenta.getPedido() != null) {
                gui_puntoDeVenta.dispose();
            }
            this.dispose();
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private FacturaVenta agregarPagosAFactura(FacturaVenta facturaVenta) {        
        List<Pago> pagos = new ArrayList<>();
        if (chk_FormaDePago1.isSelected() && chk_FormaDePago1.isEnabled()) {
            Pago pago1 = new Pago();
            pago1.setEmpresa(EmpresaActiva.getInstance().getEmpresa());
            pago1.setFormaDePago((FormaDePago) cmb_FormaDePago1.getSelectedItem());
            pago1.setFecha(new Date());
            pago1.setMonto(Double.parseDouble(txt_MontoPago1.getValue().toString()));            
            pago1.setNota("");
            pago1.setFactura(facturaVenta);
            pagos.add(pago1);
        }
        if (chk_FormaDePago2.isSelected() && chk_FormaDePago2.isEnabled()) {
            Pago pago2 = new Pago();
            pago2.setEmpresa(EmpresaActiva.getInstance().getEmpresa());
            pago2.setFormaDePago((FormaDePago) cmb_FormaDePago2.getSelectedItem());
            pago2.setFecha(new Date());
            pago2.setMonto(Double.parseDouble(txt_MontoPago2.getValue().toString()));            
            pago2.setNota("");
            pago2.setFactura(facturaVenta);
            pagos.add(pago2);
        }
        if (chk_FormaDePago3.isSelected() && chk_FormaDePago3.isEnabled()) {
            Pago pago3 = new Pago();
            pago3.setEmpresa(EmpresaActiva.getInstance().getEmpresa());
            pago3.setFormaDePago((FormaDePago) cmb_FormaDePago3.getSelectedItem());
            pago3.setFecha(new Date());
            pago3.setMonto(Double.parseDouble(txt_MontoPago3.getValue().toString()));            
            pago3.setNota("");
            pago3.setFactura(facturaVenta);
            pagos.add(pago3);
        }
        facturaVenta.setPagos(pagos);
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

        btn_Finalizar = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        lbl_Vendor = new javax.swing.JLabel();
        lbl_Vendedor = new javax.swing.JLabel();
        lbl_Transporte = new javax.swing.JLabel();
        separador1 = new javax.swing.JSeparator();
        chk_FormaDePago1 = new javax.swing.JCheckBox();
        cmb_FormaDePago1 = new javax.swing.JComboBox();
        txt_MontoPago1 = new javax.swing.JFormattedTextField();
        chk_FormaDePago2 = new javax.swing.JCheckBox();
        cmb_FormaDePago2 = new javax.swing.JComboBox();
        txt_MontoPago2 = new javax.swing.JFormattedTextField();
        chk_FormaDePago3 = new javax.swing.JCheckBox();
        cmb_FormaDePago3 = new javax.swing.JComboBox();
        txt_MontoPago3 = new javax.swing.JFormattedTextField();
        separador2 = new javax.swing.JSeparator();
        lbl_Cambio = new javax.swing.JLabel();
        lbl_Total = new javax.swing.JLabel();
        lbl_TotalAPagar = new javax.swing.JFormattedTextField();
        lbl_Total1 = new javax.swing.JLabel();
        txt_AbonaCon = new javax.swing.JFormattedTextField();
        lbl_Devolucion = new javax.swing.JLabel();
        lbl_Vuelto = new javax.swing.JFormattedTextField();
        separador3 = new javax.swing.JSeparator();
        lbl_Dividido = new javax.swing.JLabel();
        cmb_Transporte = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cerrar Venta");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btn_Finalizar.setForeground(java.awt.Color.blue);
        btn_Finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Finalizar.setText("Finalizar");
        btn_Finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FinalizarActionPerformed(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Vendor.setText("Vendedor:");

        lbl_Vendedor.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vendedor.setText("XXXXXXXXXXXXXXXXXXXXXX");

        lbl_Transporte.setText("Transporte:");

        chk_FormaDePago1.setText("Forma de Pago #1:");
        chk_FormaDePago1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago1ItemStateChanged(evt);
            }
        });

        txt_MontoPago1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_MontoPago1.setText("0");
        txt_MontoPago1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_MontoPago1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MontoPago1FocusGained(evt);
            }
        });

        chk_FormaDePago2.setText("Forma de Pago #2:");
        chk_FormaDePago2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago2ItemStateChanged(evt);
            }
        });

        txt_MontoPago2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_MontoPago2.setText("0");
        txt_MontoPago2.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_MontoPago2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MontoPago2FocusGained(evt);
            }
        });

        chk_FormaDePago3.setText("Forma de Pago #3:");
        chk_FormaDePago3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FormaDePago3ItemStateChanged(evt);
            }
        });

        txt_MontoPago3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_MontoPago3.setText("0");
        txt_MontoPago3.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_MontoPago3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MontoPago3FocusGained(evt);
            }
        });

        lbl_Cambio.setText("Cambio:");

        lbl_Total.setText("Total a pagar:");

        lbl_TotalAPagar.setEditable(false);
        lbl_TotalAPagar.setForeground(new java.awt.Color(29, 156, 37));
        lbl_TotalAPagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_TotalAPagar.setText("0");
        lbl_TotalAPagar.setFocusable(false);
        lbl_TotalAPagar.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        lbl_Total1.setText("Abona con:");

        txt_AbonaCon.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_AbonaCon.setText("0");
        txt_AbonaCon.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txt_AbonaCon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_AbonaConFocusGained(evt);
            }
        });
        txt_AbonaCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AbonaConKeyReleased(evt);
            }
        });

        lbl_Devolucion.setText("Vuelto:");

        lbl_Vuelto.setEditable(false);
        lbl_Vuelto.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Vuelto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lbl_Vuelto.setText("0");
        lbl_Vuelto.setFocusable(false);
        lbl_Vuelto.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        lbl_Dividido.setForeground(new java.awt.Color(29, 156, 37));
        lbl_Dividido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Dividido.setText("ATENCION: Este comprobante será dividido");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(chk_FormaDePago2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chk_FormaDePago1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chk_FormaDePago3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmb_FormaDePago3, 0, 250, Short.MAX_VALUE)
                                .addComponent(cmb_FormaDePago2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmb_FormaDePago1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_MontoPago3)
                                .addComponent(txt_MontoPago1)
                                .addComponent(txt_MontoPago2)))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(lbl_Cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_Devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_Total1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(txt_AbonaCon, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl_Vuelto, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_Transporte, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(lbl_Vendor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmb_Transporte, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_Vendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(separador1)
                        .addComponent(separador2)
                        .addComponent(separador3))
                    .addComponent(lbl_Dividido))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Vendor)
                    .addComponent(lbl_Vendedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Transporte)
                    .addComponent(cmb_Transporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_MontoPago1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_FormaDePago1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_FormaDePago1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_FormaDePago2)
                    .addComponent(cmb_FormaDePago2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_MontoPago2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_MontoPago3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_FormaDePago3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_FormaDePago3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Cambio)
                    .addComponent(lbl_Total)
                    .addComponent(lbl_TotalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Total1)
                    .addComponent(txt_AbonaCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Devolucion)
                    .addComponent(lbl_Vuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_Dividido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_MontoPago1, txt_MontoPago3});

        panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lbl_Cambio, lbl_Devolucion, lbl_Total, lbl_Total1, lbl_TotalAPagar, lbl_Vuelto, txt_AbonaCon});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Finalizar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Finalizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.cargarFormasDePago();
        this.cargarTransportistas();
        this.setEstadoFormasDePago();
        cmb_Transporte.setSelectedIndex(0);
        lbl_Vendedor.setText(UsuarioActivo.getInstance().getUsuario().getNombre());
        txt_AbonaCon.requestFocus();
        lbl_TotalAPagar.setValue(gui_puntoDeVenta.getTotal());
        lbl_Vuelto.setValue(0);
        txt_MontoPago1.setValue(gui_puntoDeVenta.getTotal());
        indicesParaDividir = new int[gui_puntoDeVenta.getModeloTabla().getRowCount()];
        if ((indicesParaDividir.length > 0) 
                && (gui_puntoDeVenta.getTipoDeComprobante().equals(TipoDeComprobante.FACTURA_A)
                || gui_puntoDeVenta.getTipoDeComprobante().equals(TipoDeComprobante.FACTURA_B)
                || gui_puntoDeVenta.getTipoDeComprobante().equals(TipoDeComprobante.FACTURA_C))) {            
            int j = 0;
            boolean tieneRenglonesMarcados = false;
            for (int i = 0; i < gui_puntoDeVenta.getModeloTabla().getRowCount(); i++) {
                if ((boolean) gui_puntoDeVenta.getModeloTabla().getValueAt(i, 0)) {
                    indicesParaDividir[j] = i;
                    j++;
                    tieneRenglonesMarcados = true;
                }
            }
            if (indicesParaDividir.length != 0 && tieneRenglonesMarcados) {
                dividir = true;
            }
        }
        if (dividir) {
            chk_FormaDePago1.setEnabled(false);
            chk_FormaDePago2.setEnabled(false);
            chk_FormaDePago3.setEnabled(false);
            cmb_FormaDePago1.setEnabled(false);
            cmb_FormaDePago2.setEnabled(false);
            cmb_FormaDePago3.setEnabled(false);
            txt_MontoPago1.setEnabled(false);
            txt_MontoPago2.setEnabled(false);
            txt_MontoPago3.setEnabled(false);
            txt_AbonaCon.setEnabled(false);
        } else {            
            lbl_Dividido.setText("");
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_FinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FinalizarActionPerformed
        if (dividir) {
            this.finalizarVenta();
        } else {
            double totalPagos = 0.0;
            if (chk_FormaDePago1.isSelected() && chk_FormaDePago1.isEnabled()) {
                  totalPagos += Double.parseDouble(txt_MontoPago1.getValue().toString());
            }
            if (chk_FormaDePago2.isSelected() && chk_FormaDePago2.isEnabled()) {                
                totalPagos += Double.parseDouble(txt_MontoPago2.getValue().toString());
            }
            if (chk_FormaDePago3.isSelected() && chk_FormaDePago3.isEnabled()) {
                totalPagos += Double.parseDouble(txt_MontoPago3.getValue().toString());
            }
            double totalAPagar = Math.floor(Double.parseDouble(lbl_TotalAPagar.getValue().toString()) * 100) / 100; 
            if (totalPagos < totalAPagar) {
                int reply = JOptionPane.showConfirmDialog(this,
                        ResourceBundle.getBundle("Mensajes").getString("mensaje_montos_insuficientes"),
                        "Aviso", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    this.finalizarVenta();
                }
            } else {
                this.finalizarVenta();
            }
        }
    }//GEN-LAST:event_btn_FinalizarActionPerformed

    private void txt_MontoPago1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MontoPago1FocusGained
        SwingUtilities.invokeLater(() -> {
            txt_MontoPago1.selectAll();
        });
    }//GEN-LAST:event_txt_MontoPago1FocusGained

    private void txt_MontoPago3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MontoPago3FocusGained
        SwingUtilities.invokeLater(() -> {
            txt_MontoPago3.selectAll();
        });
    }//GEN-LAST:event_txt_MontoPago3FocusGained

    private void chk_FormaDePago1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago1ItemStateChanged
        cmb_FormaDePago1.setEnabled(chk_FormaDePago1.isSelected());
        txt_MontoPago1.setEnabled(chk_FormaDePago1.isSelected());
    }//GEN-LAST:event_chk_FormaDePago1ItemStateChanged

    private void chk_FormaDePago2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago2ItemStateChanged
        cmb_FormaDePago2.setEnabled(chk_FormaDePago2.isSelected());
        txt_MontoPago2.setEnabled(chk_FormaDePago2.isSelected());
    }//GEN-LAST:event_chk_FormaDePago2ItemStateChanged

    private void chk_FormaDePago3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FormaDePago3ItemStateChanged
        cmb_FormaDePago3.setEnabled(chk_FormaDePago3.isSelected());
        txt_MontoPago3.setEnabled(chk_FormaDePago3.isSelected());
    }//GEN-LAST:event_chk_FormaDePago3ItemStateChanged

    private void txt_AbonaConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_AbonaConFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_AbonaCon.selectAll();
        });
    }//GEN-LAST:event_txt_AbonaConFocusGained

    private void txt_MontoPago2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MontoPago2FocusGained
        SwingUtilities.invokeLater(() -> {
            txt_MontoPago2.selectAll();
        });
    }//GEN-LAST:event_txt_MontoPago2FocusGained

    private void txt_AbonaConKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AbonaConKeyReleased
        this.calcularVuelto();
    }//GEN-LAST:event_txt_AbonaConKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Finalizar;
    private javax.swing.JCheckBox chk_FormaDePago1;
    private javax.swing.JCheckBox chk_FormaDePago2;
    private javax.swing.JCheckBox chk_FormaDePago3;
    private javax.swing.JComboBox cmb_FormaDePago1;
    private javax.swing.JComboBox cmb_FormaDePago2;
    private javax.swing.JComboBox cmb_FormaDePago3;
    private javax.swing.JComboBox cmb_Transporte;
    private javax.swing.JLabel lbl_Cambio;
    private javax.swing.JLabel lbl_Devolucion;
    private javax.swing.JLabel lbl_Dividido;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_Total1;
    private javax.swing.JFormattedTextField lbl_TotalAPagar;
    private javax.swing.JLabel lbl_Transporte;
    private javax.swing.JLabel lbl_Vendedor;
    private javax.swing.JLabel lbl_Vendor;
    private javax.swing.JFormattedTextField lbl_Vuelto;
    private javax.swing.JPanel panel;
    private javax.swing.JSeparator separador1;
    private javax.swing.JSeparator separador2;
    private javax.swing.JSeparator separador3;
    private javax.swing.JFormattedTextField txt_AbonaCon;
    private javax.swing.JFormattedTextField txt_MontoPago1;
    private javax.swing.JFormattedTextField txt_MontoPago2;
    private javax.swing.JFormattedTextField txt_MontoPago3;
    // End of variables declaration//GEN-END:variables
}
