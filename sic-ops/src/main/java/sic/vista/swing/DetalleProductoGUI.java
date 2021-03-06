package sic.vista.swing;

import java.awt.Color;
import java.awt.event.ItemEvent;
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
import sic.modelo.Medida;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Rubro;
import sic.modelo.TipoDeOperacion;
import sic.util.FormatterFechaHora;

public class DetalleProductoGUI extends JDialog {

    private Producto productoModificar;
    private final TipoDeOperacion operacion;
    private double precioDeCosto;
    private double gananciaPorcentaje;
    private double gananciaNeto;
    private double pvp;
    private double IVANeto;
    private double ImpInternoNeto;
    private double precioDeLista;    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DetalleProductoGUI() {
        this.initComponents();
        this.setIcon();        
        operacion = TipoDeOperacion.ALTA;
        lbl_FUM.setVisible(false);
        lbl_FechaUltimaModificacion.setVisible(false);
        lbl_FA.setVisible(false);
        lbl_FechaAlta.setVisible(false);
    }

    public DetalleProductoGUI(Producto producto) {
        this.initComponents();
        this.setIcon();        
        operacion = TipoDeOperacion.ACTUALIZACION;
        productoModificar = producto;
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(DetalleProductoGUI.class.getResource("/sic/icons/Product_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Guardar = new javax.swing.JButton();
        tp_Tabs = new javax.swing.JTabbedPane();
        panelGeneral = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        lbl_Codigo = new javax.swing.JLabel();
        lbl_Descripcion = new javax.swing.JLabel();
        txt_Descripcion = new javax.swing.JTextField();
        txt_Codigo = new javax.swing.JTextField();
        lbl_Rubro = new javax.swing.JLabel();
        cmb_Rubro = new javax.swing.JComboBox();
        btn_Rubros = new javax.swing.JButton();
        lbl_Proveedor = new javax.swing.JLabel();
        cmb_Proveedor = new javax.swing.JComboBox();
        btn_NuevoProveedor = new javax.swing.JButton();
        btn_Medidas = new javax.swing.JButton();
        cmb_Medida = new javax.swing.JComboBox();
        lbl_Medida = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        lbl_PrecioCosto = new javax.swing.JLabel();
        lbl_Ganancia = new javax.swing.JLabel();
        lbl_PrecioLista = new javax.swing.JLabel();
        lbl_ImpuestoInterno = new javax.swing.JLabel();
        txt_PrecioCosto = new javax.swing.JFormattedTextField();
        txt_Ganancia_Porcentaje = new javax.swing.JFormattedTextField();
        txt_PrecioLista = new javax.swing.JFormattedTextField();
        txt_ImpuestoInterno_Porcentaje = new javax.swing.JFormattedTextField();
        lbl_IVA = new javax.swing.JLabel();
        txt_IVA_Neto = new javax.swing.JFormattedTextField();
        txt_Ganancia_Neto = new javax.swing.JFormattedTextField();
        txt_ImpuestoInterno_Neto = new javax.swing.JFormattedTextField();
        lbl_PVP = new javax.swing.JLabel();
        txt_PVP = new javax.swing.JFormattedTextField();
        cmb_IVA_Porcentaje = new javax.swing.JComboBox();
        panel3 = new javax.swing.JPanel();
        chk_Ilimitado = new javax.swing.JCheckBox();
        lbl_Cantidad = new javax.swing.JLabel();
        txt_Cantidad = new javax.swing.JFormattedTextField();
        txt_CantMinima = new javax.swing.JFormattedTextField();
        lbl_CantMinima = new javax.swing.JLabel();
        panelPropiedades = new javax.swing.JPanel();
        panel5 = new javax.swing.JPanel();
        lbl_Ven = new javax.swing.JLabel();
        lbl_Estanteria = new javax.swing.JLabel();
        txt_Estanteria = new javax.swing.JTextField();
        lbl_Estante = new javax.swing.JLabel();
        txt_Estante = new javax.swing.JTextField();
        dc_Vencimiento = new com.toedter.calendar.JDateChooser();
        lbl_Nota = new javax.swing.JLabel();
        lbl_FA = new javax.swing.JLabel();
        lbl_FUM = new javax.swing.JLabel();
        lbl_FechaUltimaModificacion = new javax.swing.JLabel();
        lbl_FechaAlta = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Nota = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btn_Guardar.setForeground(java.awt.Color.blue);
        btn_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Guardar.setText("Guardar");
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Codigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Codigo.setText("Código:");

        lbl_Descripcion.setForeground(java.awt.Color.red);
        lbl_Descripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Descripcion.setText("* Descripción:");

        lbl_Rubro.setForeground(java.awt.Color.red);
        lbl_Rubro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Rubro.setText("* Rubro:");

        btn_Rubros.setForeground(java.awt.Color.blue);
        btn_Rubros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddBlock.png"))); // NOI18N
        btn_Rubros.setText("Nuevo");
        btn_Rubros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RubrosActionPerformed(evt);
            }
        });

        lbl_Proveedor.setForeground(java.awt.Color.red);
        lbl_Proveedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Proveedor.setText("* Proveedor:");

        btn_NuevoProveedor.setForeground(java.awt.Color.blue);
        btn_NuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddProviderBag_16x16.png"))); // NOI18N
        btn_NuevoProveedor.setText("Nuevo");
        btn_NuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoProveedorActionPerformed(evt);
            }
        });

        btn_Medidas.setForeground(java.awt.Color.blue);
        btn_Medidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddRuler_16x16.png"))); // NOI18N
        btn_Medidas.setText("Nueva");
        btn_Medidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MedidasActionPerformed(evt);
            }
        });

        lbl_Medida.setForeground(java.awt.Color.red);
        lbl_Medida.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Medida.setText("* Unidad de Medida:");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Codigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Medida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Rubro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_Rubro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Proveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Medida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Descripcion)
                    .addComponent(txt_Codigo))
                .addGap(0, 0, 0)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_NuevoProveedor)
                    .addComponent(btn_Medidas)
                    .addComponent(btn_Rubros))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Codigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Descripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Rubros, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Rubro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Proveedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Medidas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_Medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Medida))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_Medidas, btn_NuevoProveedor, btn_Rubros, cmb_Medida, cmb_Proveedor, cmb_Rubro});

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_PrecioCosto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_PrecioCosto.setText("Precio de Costo:");

        lbl_Ganancia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Ganancia.setText("Ganancia (%):");

        lbl_PrecioLista.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_PrecioLista.setText("Precio de Lista:");

        lbl_ImpuestoInterno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_ImpuestoInterno.setText("Impuesto Interno (%):");

        txt_PrecioCosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_PrecioCosto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_PrecioCosto.setText("0");
        txt_PrecioCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PrecioCostoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PrecioCostoFocusLost(evt);
            }
        });
        txt_PrecioCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PrecioCostoActionPerformed(evt);
            }
        });

        txt_Ganancia_Porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_Ganancia_Porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Ganancia_Porcentaje.setText("0");
        txt_Ganancia_Porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Ganancia_PorcentajeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Ganancia_PorcentajeFocusLost(evt);
            }
        });
        txt_Ganancia_Porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Ganancia_PorcentajeActionPerformed(evt);
            }
        });

        txt_PrecioLista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_PrecioLista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_PrecioLista.setText("0");
        txt_PrecioLista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PrecioListaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PrecioListaFocusLost(evt);
            }
        });
        txt_PrecioLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PrecioListaActionPerformed(evt);
            }
        });

        txt_ImpuestoInterno_Porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_ImpuestoInterno_Porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_ImpuestoInterno_Porcentaje.setText("0");
        txt_ImpuestoInterno_Porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_ImpuestoInterno_PorcentajeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_ImpuestoInterno_PorcentajeFocusLost(evt);
            }
        });
        txt_ImpuestoInterno_Porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ImpuestoInterno_PorcentajeActionPerformed(evt);
            }
        });

        lbl_IVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_IVA.setText("I.V.A. (%):");

        txt_IVA_Neto.setEditable(false);
        txt_IVA_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txt_IVA_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_IVA_Neto.setText("0");
        txt_IVA_Neto.setFocusable(false);

        txt_Ganancia_Neto.setEditable(false);
        txt_Ganancia_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txt_Ganancia_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Ganancia_Neto.setText("0");
        txt_Ganancia_Neto.setFocusable(false);

        txt_ImpuestoInterno_Neto.setEditable(false);
        txt_ImpuestoInterno_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txt_ImpuestoInterno_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_ImpuestoInterno_Neto.setText("0");
        txt_ImpuestoInterno_Neto.setFocusable(false);

        lbl_PVP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_PVP.setText("Precio Venta Público:");

        txt_PVP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_PVP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_PVP.setText("0");
        txt_PVP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PVPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PVPFocusLost(evt);
            }
        });
        txt_PVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PVPActionPerformed(evt);
            }
        });

        cmb_IVA_Porcentaje.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_IVA_PorcentajeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_PVP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_ImpuestoInterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_IVA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Ganancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_PrecioCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_PrecioLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_IVA_Porcentaje, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_ImpuestoInterno_Porcentaje)
                    .addComponent(txt_Ganancia_Porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_IVA_Neto, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_Ganancia_Neto)
                            .addComponent(txt_PrecioCosto)
                            .addComponent(txt_ImpuestoInterno_Neto)
                            .addComponent(txt_PVP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txt_PrecioLista))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_PrecioCosto)
                    .addComponent(txt_PrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Ganancia)
                    .addComponent(txt_Ganancia_Porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Ganancia_Neto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_PVP)
                    .addComponent(txt_PVP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_IVA)
                    .addComponent(cmb_IVA_Porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_IVA_Neto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_ImpuestoInterno)
                    .addComponent(txt_ImpuestoInterno_Porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ImpuestoInterno_Neto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_PrecioLista)
                    .addComponent(txt_PrecioLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_IVA_Porcentaje, txt_IVA_Neto});

        panel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_Ganancia_Neto, txt_Ganancia_Porcentaje, txt_PVP, txt_PrecioCosto});

        panel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chk_Ilimitado.setText("Sin Límite");
        chk_Ilimitado.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chk_Ilimitado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_IlimitadoItemStateChanged(evt);
            }
        });

        lbl_Cantidad.setForeground(java.awt.Color.red);
        lbl_Cantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Cantidad.setText("* Cantidad:");

        txt_Cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_Cantidad.setText("0");
        txt_Cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_CantidadFocusGained(evt);
            }
        });

        txt_CantMinima.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        txt_CantMinima.setText("0");
        txt_CantMinima.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_CantMinimaFocusGained(evt);
            }
        });

        lbl_CantMinima.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_CantMinima.setText("Cantidad Mínima:");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chk_Ilimitado)
                    .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_CantMinima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_Cantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Cantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(txt_CantMinima))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chk_Ilimitado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Cantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_CantMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_CantMinima))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelGeneralLayout.setVerticalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tp_Tabs.addTab("General", panelGeneral);

        panel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Ven.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Ven.setText("Vencimiento:");

        lbl_Estanteria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Estanteria.setText("Estantería:");

        lbl_Estante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Estante.setText("Estante:");

        dc_Vencimiento.setDateFormatString("dd/MM/yyyy");

        lbl_Nota.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Nota.setText("Nota ó Comentario:");

        lbl_FA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_FA.setText("Fecha de Alta:");

        lbl_FUM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_FUM.setText("Última Modificación:");

        lbl_FechaUltimaModificacion.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_FechaAlta.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_Nota.setColumns(20);
        txt_Nota.setLineWrap(true);
        txt_Nota.setRows(5);
        jScrollPane1.setViewportView(txt_Nota);

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbl_Estante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_Nota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(16, 16, 16))
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_Ven)
                                    .addComponent(lbl_Estanteria))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dc_Vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Estanteria, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Estante, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_FA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_FUM))
                        .addGap(12, 12, 12)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_FechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_FechaUltimaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Ven)
                    .addComponent(dc_Vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Estanteria)
                    .addComponent(txt_Estanteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Estante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Estante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Nota)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_FUM)
                    .addComponent(lbl_FechaUltimaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_FA)
                    .addComponent(lbl_FechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPropiedadesLayout = new javax.swing.GroupLayout(panelPropiedades);
        panelPropiedades.setLayout(panelPropiedadesLayout);
        panelPropiedadesLayout.setHorizontalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        panelPropiedadesLayout.setVerticalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tp_Tabs.addTab("Propiedades", panelPropiedades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tp_Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Guardar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tp_Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Guardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarProductoParaModificar() {
        txt_Codigo.setText(productoModificar.getCodigo());
        txt_Descripcion.setText(productoModificar.getDescripcion());
        txt_Nota.setText(productoModificar.getNota());
        cmb_Medida.setSelectedItem(productoModificar.getMedida());
        chk_Ilimitado.setSelected(productoModificar.isIlimitado());
        txt_Cantidad.setValue(productoModificar.getCantidad());
        txt_CantMinima.setValue(productoModificar.getCantMinima());
        cmb_Rubro.setSelectedItem(productoModificar.getRubro());
        cmb_Proveedor.setSelectedItem(productoModificar.getProveedor());
        FormatterFechaHora formateador = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_LETRAS);
        lbl_FechaUltimaModificacion.setText(formateador.format(productoModificar.getFechaUltimaModificacion()));
        lbl_FechaAlta.setText(formateador.format(productoModificar.getFechaAlta()));
        dc_Vencimiento.setDate(productoModificar.getFechaVencimiento());
        txt_Estanteria.setText(productoModificar.getEstanteria());
        txt_Estante.setText(productoModificar.getEstante());
        precioDeCosto = productoModificar.getPrecioCosto();
        txt_PrecioCosto.setValue(precioDeCosto);
        gananciaPorcentaje = productoModificar.getGanancia_porcentaje();
        txt_Ganancia_Porcentaje.setValue(gananciaPorcentaje);
        gananciaNeto = productoModificar.getGanancia_neto();
        txt_Ganancia_Neto.setValue(gananciaNeto);
        pvp = productoModificar.getPrecioVentaPublico();
        txt_PVP.setValue(pvp);
        cmb_IVA_Porcentaje.setSelectedItem(productoModificar.getIva_porcentaje());
        IVANeto = productoModificar.getIva_neto();
        txt_IVA_Neto.setValue(IVANeto);
        txt_ImpuestoInterno_Porcentaje.setValue(productoModificar.getImpuestoInterno_porcentaje());
        ImpInternoNeto = productoModificar.getImpuestoInterno_neto();
        txt_ImpuestoInterno_Neto.setValue(ImpInternoNeto);
        precioDeLista = productoModificar.getPrecioLista();
        txt_PrecioLista.setValue(precioDeLista);
    }

    private void prepararComponentes() {
        txt_Cantidad.setValue(0.0);
        txt_CantMinima.setValue(0.0);
        txt_PrecioCosto.setValue(0.0);
        txt_PVP.setValue(0.0);
        txt_IVA_Neto.setValue(0.0);
        txt_ImpuestoInterno_Porcentaje.setValue(0.0);
        txt_ImpuestoInterno_Neto.setValue(0.0);
        txt_Ganancia_Porcentaje.setValue(0.0);
        txt_Ganancia_Neto.setValue(0.0);
        txt_PrecioLista.setValue(0.0);
    }

    private void cargarMedidas() {
        cmb_Medida.removeAllItems();
        try {
            List<Medida> medidas = new ArrayList(Arrays.asList(RestClient.getRestTemplate()
                .getForObject("/medidas/empresas/" + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                Medida[].class)));
            medidas.stream().forEach((m) -> {
                cmb_Medida.addItem(m);
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

    private void cargarRubros() {
        cmb_Rubro.removeAllItems();
        try {
            List<Rubro> rubros = new ArrayList(Arrays.asList(RestClient.getRestTemplate()
                .getForObject("/rubros/empresas/" + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                Rubro[].class)));
            rubros.stream().forEach((r) -> {
                cmb_Rubro.addItem(r);
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

    private void cargarProveedores() {
        cmb_Proveedor.removeAllItems();
        try {
            List<Proveedor> proveedores = new ArrayList(Arrays.asList(RestClient.getRestTemplate()
                .getForObject("/proveedores/empresas/" + EmpresaActiva.getInstance().getEmpresa().getId_Empresa(),
                Proveedor[].class)));
            proveedores.stream().forEach((p) -> {
                cmb_Proveedor.addItem(p);
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

    private void cargarIVAs() {
        cmb_IVA_Porcentaje.removeAllItems();
        cmb_IVA_Porcentaje.addItem((double) 0);
        cmb_IVA_Porcentaje.addItem(10.5);
        cmb_IVA_Porcentaje.addItem((double) 21);
    }

    private void limpiarYRecargarComponentes() {
        txt_Codigo.setText("");
        txt_Descripcion.setText("");
        txt_Cantidad.setValue(0.0);
        txt_CantMinima.setValue(0.0);
        chk_Ilimitado.setSelected(false);
        txt_PrecioCosto.setValue(0.0);
        txt_PVP.setValue(0.0);
        txt_IVA_Neto.setValue(0.0);
        txt_ImpuestoInterno_Porcentaje.setValue(0.0);
        txt_ImpuestoInterno_Neto.setValue(0.0);
        txt_Ganancia_Porcentaje.setValue(0.0);
        txt_Ganancia_Neto.setValue(0.0);
        txt_PrecioLista.setValue(0.0);
        txt_Estanteria.setText("");
        txt_Estante.setText("");
        txt_Nota.setText("");
        dc_Vencimiento.setDate(null);
    }

    private void validarComponentesDePrecios() {
        try {
            txt_PrecioCosto.commitEdit();
            txt_PVP.commitEdit();
            txt_IVA_Neto.commitEdit();
            txt_ImpuestoInterno_Porcentaje.commitEdit();
            txt_ImpuestoInterno_Neto.commitEdit();
            txt_Ganancia_Porcentaje.commitEdit();
            txt_Ganancia_Neto.commitEdit();
            txt_PrecioLista.commitEdit();
        } catch (ParseException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    private void calcularGananciaPorcentaje() {
        pvp = Double.parseDouble(txt_PVP.getValue().toString());
        gananciaPorcentaje = RestClient.getRestTemplate()
                .getForObject("/productos/ganancia-porcentaje?"
                        + "precioCosto=" + Double.parseDouble(txt_PrecioCosto.getValue().toString())
                        + "&pvp=" + pvp,
                        double.class);
        txt_Ganancia_Porcentaje.setValue(gananciaPorcentaje);
    }
    
    private void calcularGananciaNeto() {
        gananciaNeto = RestClient.getRestTemplate()
                .getForObject("/productos/ganancia-neto?"
                        + "precioCosto=" + Double.parseDouble(txt_PrecioCosto.getValue().toString())
                        + "&gananciaPorcentaje=" + gananciaPorcentaje,
                        double.class);
        txt_Ganancia_Neto.setValue(gananciaNeto);
    }
    
    private void calcularPVP() {
        pvp = RestClient.getRestTemplate()
                .getForObject("/productos/pvp?"
                        + "precioCosto=" + Double.parseDouble(txt_PrecioCosto.getValue().toString())
                        + "&gananciaPorcentaje=" + gananciaPorcentaje,
                        double.class);
        txt_PVP.setValue(pvp);
    }
    
    private void calcularIVANeto() {
        IVANeto = RestClient.getRestTemplate()
                .getForObject("/productos/iva-neto?"
                        + "pvp=" + pvp
                        + "&ivaPorcentaje=" + Double.parseDouble(cmb_IVA_Porcentaje.getSelectedItem().toString()),
                        double.class);
        txt_IVA_Neto.setValue(IVANeto);
    }
    
    private void calcularImpInternoNeto() {
        ImpInternoNeto = RestClient.getRestTemplate()
                .getForObject("/productos/imp-interno-neto?"
                        + "pvp=" + pvp
                        + "&impInternoPorcentaje= " + Double.parseDouble(txt_ImpuestoInterno_Porcentaje.getValue().toString()),
                        double.class);
        txt_ImpuestoInterno_Neto.setValue(ImpInternoNeto);
    }
    
    private void calcularPrecioLista() {
        precioDeLista = RestClient.getRestTemplate()
                .getForObject("/productos/precio-lista?"
                        + "pvp=" + pvp
                        + "&ivaPorcentaje=" + Double.parseDouble(cmb_IVA_Porcentaje.getSelectedItem().toString())
                        + "&impInternoPorcentaje=" + Double.parseDouble(txt_ImpuestoInterno_Porcentaje.getValue().toString()),
                        double.class);
        txt_PrecioLista.setValue(precioDeLista);
    }
    
    private void calcularGananciaSegunPrecioDeLista() {      
        gananciaPorcentaje = RestClient.getRestTemplate()
                .getForObject("/productos/ganancia-porcentaje?ascendente=true"
                        + "&precioDeLista=" + Double.parseDouble(txt_PrecioLista.getValue().toString())
                        + "&precioDeListaAnterior=" + precioDeLista
                        + "&pvp=" + pvp
                        + "&ivaPorcentaje=" + Double.parseDouble(cmb_IVA_Porcentaje.getSelectedItem().toString())
                        + "&impInternoPorcentaje=" + Double.parseDouble(txt_ImpuestoInterno_Porcentaje.getValue().toString())
                        + "&precioCosto=" + precioDeCosto,
                        double.class);
        txt_Ganancia_Porcentaje.setValue(gananciaPorcentaje);
    }
    
    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        try {
            if (operacion == TipoDeOperacion.ALTA) {
                Producto producto = new Producto();
                producto.setCodigo(txt_Codigo.getText());
                producto.setDescripcion(txt_Descripcion.getText().trim());
                producto.setCantidad(Double.parseDouble(txt_Cantidad.getValue().toString()));
                producto.setCantMinima(Double.parseDouble(txt_CantMinima.getValue().toString()));
                producto.setMedida((Medida) cmb_Medida.getSelectedItem());
                producto.setPrecioCosto(Double.parseDouble(txt_PrecioCosto.getValue().toString()));
                producto.setGanancia_porcentaje(Double.parseDouble(txt_Ganancia_Porcentaje.getValue().toString()));
                producto.setGanancia_neto(Double.parseDouble(txt_Ganancia_Neto.getValue().toString()));
                producto.setPrecioVentaPublico(Double.parseDouble(txt_PVP.getValue().toString()));
                producto.setIva_porcentaje(Double.parseDouble(cmb_IVA_Porcentaje.getSelectedItem().toString()));
                producto.setIva_neto(Double.parseDouble(txt_IVA_Neto.getValue().toString()));
                producto.setImpuestoInterno_porcentaje(Double.parseDouble(txt_ImpuestoInterno_Porcentaje.getValue().toString()));
                producto.setImpuestoInterno_neto(Double.parseDouble(txt_ImpuestoInterno_Neto.getValue().toString()));
                producto.setPrecioLista(Double.parseDouble(txt_PrecioLista.getValue().toString()));
                producto.setRubro((Rubro) cmb_Rubro.getSelectedItem());
                producto.setIlimitado(chk_Ilimitado.isSelected());
                producto.setFechaUltimaModificacion(new Date());
                producto.setEstanteria(txt_Estanteria.getText().trim());
                producto.setEstante(txt_Estante.getText().trim());
                producto.setProveedor((Proveedor) cmb_Proveedor.getSelectedItem());
                producto.setNota(txt_Nota.getText().trim());
                producto.setFechaAlta(new Date());
                producto.setFechaVencimiento(dc_Vencimiento.getDate());
                producto.setEmpresa(EmpresaActiva.getInstance().getEmpresa());
                RestClient.getRestTemplate().postForObject("/productos", producto, Producto.class);
                LOGGER.warn("El producto " + producto + " se guardó correctamente");
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "El producto se guardó correctamente.\n¿Desea dar de alta otro producto?",
                        "Aviso", JOptionPane.YES_NO_OPTION);
                this.limpiarYRecargarComponentes();
                if (respuesta == JOptionPane.NO_OPTION) {
                    this.dispose();
                }
            }

            if (operacion == TipoDeOperacion.ACTUALIZACION) {
                productoModificar.setCodigo(txt_Codigo.getText());
                productoModificar.setDescripcion(txt_Descripcion.getText().trim());
                productoModificar.setCantidad(Double.parseDouble(txt_Cantidad.getValue().toString()));
                productoModificar.setCantMinima(Double.parseDouble(txt_CantMinima.getValue().toString()));
                productoModificar.setMedida((Medida) cmb_Medida.getSelectedItem());
                productoModificar.setCantidad(Double.parseDouble(txt_Cantidad.getValue().toString()));
                productoModificar.setCantMinima(Double.parseDouble(txt_CantMinima.getValue().toString()));
                productoModificar.setMedida((Medida) cmb_Medida.getSelectedItem());
                productoModificar.setPrecioCosto(Double.parseDouble(txt_PrecioCosto.getValue().toString()));
                productoModificar.setGanancia_porcentaje(Double.parseDouble(txt_Ganancia_Porcentaje.getValue().toString()));
                productoModificar.setGanancia_neto(Double.parseDouble(txt_Ganancia_Neto.getValue().toString()));
                productoModificar.setPrecioVentaPublico(Double.parseDouble(txt_PVP.getValue().toString()));
                productoModificar.setIva_porcentaje(Double.parseDouble(cmb_IVA_Porcentaje.getSelectedItem().toString()));
                productoModificar.setIva_neto(Double.parseDouble(txt_IVA_Neto.getValue().toString()));
                productoModificar.setImpuestoInterno_porcentaje(Double.parseDouble(txt_ImpuestoInterno_Porcentaje.getValue().toString()));
                productoModificar.setImpuestoInterno_neto(Double.parseDouble(txt_ImpuestoInterno_Neto.getValue().toString()));
                productoModificar.setPrecioLista(Double.parseDouble(txt_PrecioLista.getValue().toString()));
                productoModificar.setRubro((Rubro) cmb_Rubro.getSelectedItem());
                productoModificar.setIlimitado(chk_Ilimitado.isSelected());
                productoModificar.setFechaUltimaModificacion(new Date());
                productoModificar.setEstanteria(txt_Estanteria.getText().trim());
                productoModificar.setEstante(txt_Estante.getText().trim());
                productoModificar.setProveedor((Proveedor) cmb_Proveedor.getSelectedItem());
                productoModificar.setNota(txt_Nota.getText().trim());
                productoModificar.setFechaVencimiento(dc_Vencimiento.getDate());
                productoModificar.setEmpresa(EmpresaActiva.getInstance().getEmpresa());
                RestClient.getRestTemplate().put("/productos", productoModificar);
                LOGGER.warn("El producto " + productoModificar + " se modificó correctamente");
                JOptionPane.showMessageDialog(this, "El producto se modificó correctamente.",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.prepararComponentes();
        this.cargarMedidas();
        this.cargarRubros();
        this.cargarProveedores();
        this.cargarIVAs();
        if (operacion == TipoDeOperacion.ALTA) {
            this.setTitle("Nuevo Producto");
        } else if (operacion == TipoDeOperacion.ACTUALIZACION) {
            this.setTitle("Modificar Producto");
            this.cargarProductoParaModificar();
        }
    }//GEN-LAST:event_formWindowOpened

    private void txt_CantMinimaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_CantMinimaFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_CantMinima.selectAll();
        });
    }//GEN-LAST:event_txt_CantMinimaFocusGained

    private void txt_CantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_CantidadFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_Cantidad.selectAll();
        });
    }//GEN-LAST:event_txt_CantidadFocusGained

    private void chk_IlimitadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_IlimitadoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            txt_Cantidad.setEnabled(false);
            lbl_Cantidad.setForeground(Color.LIGHT_GRAY);
            txt_CantMinima.setEnabled(false);
            lbl_CantMinima.setForeground(Color.LIGHT_GRAY);
        } else {
            txt_Cantidad.setEnabled(true);
            lbl_Cantidad.setForeground(Color.RED);
            txt_CantMinima.setEnabled(true);
            lbl_CantMinima.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_chk_IlimitadoItemStateChanged

    private void cmb_IVA_PorcentajeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_IVA_PorcentajeItemStateChanged
        this.validarComponentesDePrecios();
        try {
            this.calcularIVANeto();            
            this.calcularPrecioLista();
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_IVA_PorcentajeItemStateChanged

    private void txt_PVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PVPActionPerformed
        this.validarComponentesDePrecios();
        try {
            pvp = Double.parseDouble(txt_PVP.getValue().toString());
            this.calcularGananciaPorcentaje();
            this.calcularGananciaNeto();
            this.calcularIVANeto();
            this.calcularImpInternoNeto();
            this.calcularPrecioLista();
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_PVPActionPerformed

    private void txt_PVPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PVPFocusLost
        this.txt_PVPActionPerformed(null);
    }//GEN-LAST:event_txt_PVPFocusLost

    private void txt_PVPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PVPFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_PVP.selectAll();
        });
    }//GEN-LAST:event_txt_PVPFocusGained

    private void txt_ImpuestoInterno_PorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ImpuestoInterno_PorcentajeActionPerformed
        this.validarComponentesDePrecios();
        try {
            this.calcularImpInternoNeto();
            this.calcularPrecioLista();            
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_ImpuestoInterno_PorcentajeActionPerformed

    private void txt_ImpuestoInterno_PorcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ImpuestoInterno_PorcentajeFocusLost
        this.txt_ImpuestoInterno_PorcentajeActionPerformed(null);
    }//GEN-LAST:event_txt_ImpuestoInterno_PorcentajeFocusLost

    private void txt_ImpuestoInterno_PorcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ImpuestoInterno_PorcentajeFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_ImpuestoInterno_Porcentaje.selectAll();
        });
    }//GEN-LAST:event_txt_ImpuestoInterno_PorcentajeFocusGained

    private void txt_Ganancia_PorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Ganancia_PorcentajeActionPerformed
        this.validarComponentesDePrecios();
        try {
            gananciaPorcentaje = Double.parseDouble(txt_Ganancia_Porcentaje.getValue().toString());
            this.calcularGananciaNeto();
            this.calcularPVP();
            this.calcularIVANeto();
            this.calcularImpInternoNeto();
            this.calcularPrecioLista();
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_Ganancia_PorcentajeActionPerformed

    private void txt_Ganancia_PorcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Ganancia_PorcentajeFocusLost
        this.txt_Ganancia_PorcentajeActionPerformed(null);
    }//GEN-LAST:event_txt_Ganancia_PorcentajeFocusLost

    private void txt_Ganancia_PorcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Ganancia_PorcentajeFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_Ganancia_Porcentaje.selectAll();
        });
    }//GEN-LAST:event_txt_Ganancia_PorcentajeFocusGained

    private void txt_PrecioCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PrecioCostoActionPerformed
        this.validarComponentesDePrecios();
        try {
            precioDeCosto = Double.parseDouble(txt_PrecioCosto.getValue().toString());
            this.calcularGananciaNeto();
            this.calcularPVP();
            this.calcularIVANeto();
            this.calcularImpInternoNeto();
            this.calcularPrecioLista();
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_PrecioCostoActionPerformed

    private void txt_PrecioCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PrecioCostoFocusLost
        this.txt_PrecioCostoActionPerformed(null);
    }//GEN-LAST:event_txt_PrecioCostoFocusLost

    private void txt_PrecioCostoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PrecioCostoFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_PrecioCosto.selectAll();
        });
    }//GEN-LAST:event_txt_PrecioCostoFocusGained

    private void btn_MedidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MedidasActionPerformed
        DetalleMedidaGUI gui_DetalleMedida = new DetalleMedidaGUI();
        gui_DetalleMedida.setModal(true);
        gui_DetalleMedida.setLocationRelativeTo(this);
        gui_DetalleMedida.setVisible(true);
        this.cargarMedidas();
    }//GEN-LAST:event_btn_MedidasActionPerformed

    private void btn_NuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoProveedorActionPerformed
        DetalleProveedorGUI gui_DetalleProveedor = new DetalleProveedorGUI();
        gui_DetalleProveedor.setModal(true);
        gui_DetalleProveedor.setLocationRelativeTo(this);
        gui_DetalleProveedor.setVisible(true);
        this.cargarProveedores();
    }//GEN-LAST:event_btn_NuevoProveedorActionPerformed

    private void btn_RubrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RubrosActionPerformed
        DetalleRubroGUI gui_DetalleRubro = new DetalleRubroGUI();
        gui_DetalleRubro.setModal(true);
        gui_DetalleRubro.setLocationRelativeTo(this);
        gui_DetalleRubro.setVisible(true);
        this.cargarRubros();
    }//GEN-LAST:event_btn_RubrosActionPerformed

    private void txt_PrecioListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PrecioListaActionPerformed
        this.validarComponentesDePrecios();
        try {
            this.calcularGananciaSegunPrecioDeLista();
            this.calcularGananciaNeto();
            this.calcularPVP();
            this.calcularIVANeto();
            this.calcularImpInternoNeto();
            this.calcularPrecioLista(); 
        } catch (RestClientResponseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ResourceAccessException ex) {
            LOGGER.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_error_conexion"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_PrecioListaActionPerformed

    private void txt_PrecioListaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PrecioListaFocusGained
        SwingUtilities.invokeLater(() -> {
            txt_PrecioLista.selectAll();
        });
    }//GEN-LAST:event_txt_PrecioListaFocusGained

    private void txt_PrecioListaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PrecioListaFocusLost
        this.txt_PrecioListaActionPerformed(null);
    }//GEN-LAST:event_txt_PrecioListaFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_Medidas;
    private javax.swing.JButton btn_NuevoProveedor;
    private javax.swing.JButton btn_Rubros;
    private javax.swing.JCheckBox chk_Ilimitado;
    private javax.swing.JComboBox cmb_IVA_Porcentaje;
    private javax.swing.JComboBox cmb_Medida;
    private javax.swing.JComboBox cmb_Proveedor;
    private javax.swing.JComboBox cmb_Rubro;
    private com.toedter.calendar.JDateChooser dc_Vencimiento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_CantMinima;
    private javax.swing.JLabel lbl_Cantidad;
    private javax.swing.JLabel lbl_Codigo;
    private javax.swing.JLabel lbl_Descripcion;
    private javax.swing.JLabel lbl_Estante;
    private javax.swing.JLabel lbl_Estanteria;
    private javax.swing.JLabel lbl_FA;
    private javax.swing.JLabel lbl_FUM;
    private javax.swing.JLabel lbl_FechaAlta;
    private javax.swing.JLabel lbl_FechaUltimaModificacion;
    private javax.swing.JLabel lbl_Ganancia;
    private javax.swing.JLabel lbl_IVA;
    private javax.swing.JLabel lbl_ImpuestoInterno;
    private javax.swing.JLabel lbl_Medida;
    private javax.swing.JLabel lbl_Nota;
    private javax.swing.JLabel lbl_PVP;
    private javax.swing.JLabel lbl_PrecioCosto;
    private javax.swing.JLabel lbl_PrecioLista;
    private javax.swing.JLabel lbl_Proveedor;
    private javax.swing.JLabel lbl_Rubro;
    private javax.swing.JLabel lbl_Ven;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelPropiedades;
    private javax.swing.JTabbedPane tp_Tabs;
    private javax.swing.JFormattedTextField txt_CantMinima;
    private javax.swing.JFormattedTextField txt_Cantidad;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Descripcion;
    private javax.swing.JTextField txt_Estante;
    private javax.swing.JTextField txt_Estanteria;
    private javax.swing.JFormattedTextField txt_Ganancia_Neto;
    private javax.swing.JFormattedTextField txt_Ganancia_Porcentaje;
    private javax.swing.JFormattedTextField txt_IVA_Neto;
    private javax.swing.JFormattedTextField txt_ImpuestoInterno_Neto;
    private javax.swing.JFormattedTextField txt_ImpuestoInterno_Porcentaje;
    private javax.swing.JTextArea txt_Nota;
    private javax.swing.JFormattedTextField txt_PVP;
    private javax.swing.JFormattedTextField txt_PrecioCosto;
    private javax.swing.JFormattedTextField txt_PrecioLista;
    // End of variables declaration//GEN-END:variables

 
}
