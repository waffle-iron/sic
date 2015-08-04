package sic.vista.swing.administracion;

import java.awt.Point;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;
import sic.modelo.Cliente;
import sic.modelo.Factura;
import sic.modelo.FacturaVenta;
import sic.modelo.FormaDePago;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.service.ClienteService;
import sic.service.EmpresaService;
import sic.service.FacturaService;
import sic.service.FormaDePagoService;
import sic.service.Movimiento;
import sic.service.ProductoService;
import sic.service.RenglonDeFacturaService;
import sic.service.ServiceException;
import sic.service.TransportistaService;
import sic.service.UsuarioService;
import sic.util.RenderTabla;
import sic.vista.swing.ModeloTabla;

public class GUI_FormFacturaVenta extends JDialog {

    private ModeloTabla modeloTablaRenglones = new ModeloTabla();
    private List<RenglonFactura> renglones;
    private final EmpresaService empresaService = new EmpresaService();
    private final ClienteService clienteService = new ClienteService();
    private final TransportistaService transportistaService = new TransportistaService();
    private final FacturaService facturaService = new FacturaService();
    private final ProductoService productoService = new ProductoService();
    private final RenglonDeFacturaService renglonDeFacturaService = new RenglonDeFacturaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private char tipoDeFactura;
    private static final Logger log = Logger.getLogger(GUI_FormFacturaVenta.class.getPackage().getName());

    public GUI_FormFacturaVenta() {
        this.initComponents();
        this.setIcon();
        renglones = new ArrayList<>();
        this.prepararComponentes();

    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void prepararComponentes() {
        this.setSize(1000, 600);
        txt_SubTotal.setValue(new Double("0.0"));
        txt_Recargo_Porcentaje.setValue(new Double("0.0"));
        txt_Recargo_Neto.setValue(new Double("0.0"));
        txt_SubTotal_Neto.setValue(new Double("0.0"));
        txt_IVA_105.setValue(new Double("0.0"));
        txt_IVA_21.setValue(new Double("0.0"));
        txt_ImpInterno_Neto.setValue(new Double("0.0"));
        txt_Total.setValue(new Double("0.0"));
        dc_FechaFactura.setDate(new Date());
        dc_FechaVencimiento.setDate(new Date());
        ImageIcon iconoNoMarcado = new ImageIcon(getClass().getResource("/sic/icons/chkNoMarcado_16x16.png"));
        this.marcarDesmarcar.setIcon(iconoNoMarcado);

    }

    private void llamarVentanaRenglonFactura(Producto producto) {
        if (this.existeProductoCargado(producto)) {
            JOptionPane.showMessageDialog(this,
                    "Ya esta cargado el producto \"" + producto.getDescripcion()
                    + "\" en los renglones de la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            GUI_RenglonFactura gui_Renglon = new GUI_RenglonFactura(producto, tipoDeFactura, Movimiento.VENTA);
            gui_Renglon.setModal(true);
            gui_Renglon.setLocationRelativeTo(this);
            gui_Renglon.setVisible(true);
            if (gui_Renglon.debeCargarRenglon()) {
                this.agregarRenglon(gui_Renglon.getRenglon(), false);
            }
        }
    }

    private void agregarRenglon(RenglonFactura renglon, boolean marcado) {
        Object[] lineaDeFactura = new Object[8];
        lineaDeFactura[0] = marcado;
        lineaDeFactura[1] = renglon.getCodigoItem();
        lineaDeFactura[2] = renglon.getDescripcionItem();
        lineaDeFactura[3] = renglon.getMedidaItem();
        lineaDeFactura[4] = renglon.getCantidad();
        lineaDeFactura[5] = renglon.getPrecioUnitario();
        lineaDeFactura[6] = renglon.getDescuento_porcentaje();
        lineaDeFactura[7] = renglon.getImporte();
        modeloTablaRenglones.addRow(lineaDeFactura);
        renglones.add(renglon);
        this.calcularResultados();

        //para que baje solo el scroll vertical
        Point p = new Point(0, tbl_Renglones.getHeight());
        sp_Renglones.getViewport().setViewPosition(p);
    }

    private void quitarRenglonFactura() {
        int[] indicesParaEliminar = new int[tbl_Renglones.getRowCount()];
        int punteroIndices = 0;
        int cantidadAEliminar = 0;
        for (int i = 0; i < tbl_Renglones.getRowCount(); i++) {
            if ((boolean) tbl_Renglones.getValueAt(i, 0)) {
                indicesParaEliminar[punteroIndices] = i;
                punteroIndices++;
                cantidadAEliminar++;
            }
        }
        if (cantidadAEliminar != 0) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Esta seguro que desea eliminar el renglon de factura seleccionado?",
                    "Eliminar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                List<RenglonFactura> paraBorrar = new ArrayList<>();
                int corrimientoRenglones = 0;
                for (int i = 0; i < cantidadAEliminar; i++) {
                    paraBorrar.add(renglones.get(indicesParaEliminar[i]));
                    modeloTablaRenglones.removeRow(indicesParaEliminar[i] - corrimientoRenglones);
                    corrimientoRenglones++;
                }
                for (RenglonFactura renglon : paraBorrar) {
                    renglones.remove(renglon);
                }
                this.calcularResultados();
            }
        }
    }

    private void cargarComboBoxClientes() {
        cmb_Cliente.removeAllItems();
        List<Cliente> clientes;
        clientes = clienteService.getClientes(empresaService.getEmpresaActiva().getEmpresa());
        for (Cliente cliente : clientes) {
            cmb_Cliente.addItem(cliente);
        }
    }

    private void cargarComboBoxTransportistas() {
        cmb_Transportista.removeAllItems();
        List<Transportista> transportistas;
        transportistas = transportistaService.getTransportistas(empresaService.getEmpresaActiva().getEmpresa());
        for (Transportista trans : transportistas) {
            cmb_Transportista.addItem(trans);
        }
    }

    private void cargarComboBoxFormasDePago() {
        cmb_FormaDePago.removeAllItems();
        FormaDePagoService formaDePagoService = new FormaDePagoService();
        EmpresaService controladorEmpresa = new EmpresaService();
        List<FormaDePago> formas;
        formas = formaDePagoService.getFormasDePago(controladorEmpresa.getEmpresaActiva().getEmpresa());
        for (FormaDePago f : formas) {
            cmb_FormaDePago.addItem(f);
        }
    }

    private Factura guardarFactura(Factura facturaVenta) throws ServiceException {
        facturaService.guardar(facturaVenta);
        return facturaService.getFacturaVentaPorTipoSerieNum(facturaVenta.getTipoFactura(), facturaVenta.getNumSerie(), facturaVenta.getNumFactura());
    }

    private FacturaVenta construirFactura() {
        FacturaVenta facturaVenta = new FacturaVenta();
        facturaVenta.setFecha(dc_FechaFactura.getDate());
        facturaVenta.setTipoFactura(tipoDeFactura);
        facturaVenta.setNumSerie(1);
        facturaVenta.setNumFactura(facturaService.calcularNumeroFactura(tipoDeFactura, 1));
        facturaVenta.setFormaPago((FormaDePago) cmb_FormaDePago.getSelectedItem());
        facturaVenta.setFechaVencimiento(dc_FechaVencimiento.getDate());
        facturaVenta.setTransportista((Transportista) cmb_Transportista.getSelectedItem());
        List<RenglonFactura> lineasFactura = new ArrayList<>(renglones);
        facturaVenta.setRenglones(lineasFactura);
        for (RenglonFactura renglon : lineasFactura) {
            renglon.setFactura(facturaVenta);
        }
        facturaVenta.setSubTotal(Double.parseDouble(txt_SubTotal.getValue().toString()));
        facturaVenta.setRecargo_porcentaje(Double.parseDouble(txt_Recargo_Porcentaje.getValue().toString()));
        facturaVenta.setRecargo_neto(Double.parseDouble(txt_Recargo_Neto.getValue().toString()));
        facturaVenta.setDescuento_porcentaje(0);
        facturaVenta.setDescuento_neto(0);
        facturaVenta.setSubTotal_neto(Double.parseDouble(txt_SubTotal_Neto.getValue().toString()));
        facturaVenta.setIva_105_neto(Double.parseDouble(txt_IVA_105.getValue().toString()));
        facturaVenta.setIva_21_neto(Double.parseDouble(txt_IVA_21.getValue().toString()));
        facturaVenta.setImpuestoInterno_neto(Double.parseDouble(txt_ImpInterno_Neto.getValue().toString()));
        facturaVenta.setTotal(Double.parseDouble(txt_Total.getValue().toString()));
        facturaVenta.setObservaciones(txta_Observaciones.getText().trim());
        facturaVenta.setPagada(true);
        facturaVenta.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
        facturaVenta.setEliminada(false);
        facturaVenta.setCliente((Cliente) cmb_Cliente.getSelectedItem());
        facturaVenta.setUsuario(usuarioService.getUsuarioActivo().getUsuario());
        return facturaVenta;
    }

    private void lanzarReporteFactura(Factura factura) throws JRException {
        JasperPrint report = facturaService.getReporteFacturaVenta(factura);
        JDialog viewer = new JDialog(new JFrame(), "Vista Previa", true);
        viewer.setSize(this.getWidth() - 25, this.getHeight() - 25);
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
        viewer.setIconImage(iconoVentana.getImage());
        viewer.setLocationRelativeTo(null);
        JRViewer jrv = new JRViewer(report);
        viewer.getContentPane().add(jrv);
        viewer.setVisible(true);
    }

    private void limpiarYRecargarComponentes() {
        renglones = new ArrayList<>();
        modeloTablaRenglones = new ModeloTabla();
        this.setColumnas();
        dc_FechaFactura.setDate(new Date());
        dc_FechaVencimiento.setDate(null);
        cmb_FormaDePago.setSelectedIndex(0);
        txt_CodigoProducto.setText("");
        txta_Observaciones.setText("");
        txt_SubTotal.setValue(0.0);
        txt_Recargo_Porcentaje.setValue(0.0);
        txt_Recargo_Neto.setValue(0.0);
        txt_SubTotal_Neto.setValue(0.0);
        txt_IVA_105.setValue(0.0);
        txt_IVA_21.setValue(0.0);
        txt_ImpInterno_Neto.setValue(0.0);
        txt_Total.setValue(0.0);
    }

    private void buscarProductoPorCodigo(String codigoProducto) {
        try {
            Producto producto = productoService.getProductoPorCodigo(codigoProducto,
                    empresaService.getEmpresaActiva().getEmpresa());
            if (producto != null) {
                this.llamarVentanaRenglonFactura(producto);
                txt_CodigoProducto.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el Producto con el Codigo ingresado!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarComponentesDeResultados() {
        try {
            txt_SubTotal.commitEdit();
            txt_Recargo_Porcentaje.commitEdit();
            txt_Recargo_Neto.commitEdit();
            txt_SubTotal_Neto.commitEdit();
            txt_IVA_105.commitEdit();
            txt_IVA_21.commitEdit();
            txt_ImpInterno_Neto.commitEdit();
            txt_Total.commitEdit();

        } catch (ParseException ex) {
            String msjError = "Se produjo un error analizando los campos.";
            log.error(msjError + " - " + ex.getMessage());
        }
    }

    private void calcularResultados() {
        double subTotal;
        double recargo_porcentaje;
        double recargo_neto;
        double subTotal_neto;
        double iva105_neto;
        double iva21_neto;
        double impInterno_neto;
        double total;
        this.validarComponentesDeResultados();
        //subtotal        
        subTotal = facturaService.calcularSubTotal(renglones);
        txt_SubTotal.setValue(subTotal);

        //recargo
        recargo_porcentaje = Double.parseDouble(txt_Recargo_Porcentaje.getValue().toString());
        recargo_neto = facturaService.calcularRecargo_neto(subTotal, recargo_porcentaje);
        txt_Recargo_Neto.setValue(recargo_neto);

        //subtotal neto
        subTotal_neto = facturaService.calcularSubTotal_neto(subTotal, recargo_neto, 0);
        txt_SubTotal_Neto.setValue(subTotal_neto);

        //IVA 10,5% neto
        iva105_neto = facturaService.calcularIva_neto(tipoDeFactura, 0, recargo_porcentaje, renglones, 10.5);
        txt_IVA_105.setValue(iva105_neto);

        //IVA 21% neto
        iva21_neto = facturaService.calcularIva_neto(tipoDeFactura, 0, recargo_porcentaje, renglones, 21.0);
        txt_IVA_21.setValue(iva21_neto);

        //imp. Interno
        impInterno_neto = facturaService.calcularImpInterno_neto(tipoDeFactura, 0, recargo_porcentaje, renglones);
        txt_ImpInterno_Neto.setValue(impInterno_neto);

        //total
        total = facturaService.calcularTotal(subTotal, 0, recargo_neto, iva105_neto, iva21_neto, impInterno_neto);
        txt_Total.setValue(total);
    }

    private void setColumnas() {
        //nombres de columnas
        String[] encabezados = new String[8];
        encabezados[0] = " ";
        encabezados[1] = "Codigo";
        encabezados[2] = "Descripcion";
        encabezados[3] = "Unidad";
        encabezados[4] = "Cantidad";
        encabezados[5] = "P. Unitario";
        encabezados[6] = "% Descuento";
        encabezados[7] = "Importe";
        modeloTablaRenglones.setColumnIdentifiers(encabezados);
        tbl_Renglones.setModel(modeloTablaRenglones);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaRenglones.getColumnCount()];
        tipos[0] = Boolean.class;
        tipos[1] = String.class;
        tipos[2] = String.class;
        tipos[3] = String.class;
        tipos[4] = Double.class;
        tipos[5] = Double.class;
        tipos[6] = Double.class;
        tipos[7] = Double.class;
        modeloTablaRenglones.setClaseColumnas(tipos);
        tbl_Renglones.getTableHeader().setReorderingAllowed(false);
        tbl_Renglones.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Renglones.setDefaultRenderer(Double.class, new RenderTabla());

        //Tamanios de columnas
        tbl_Renglones.getColumnModel().getColumn(0).setPreferredWidth(25);
        tbl_Renglones.getColumnModel().getColumn(1).setPreferredWidth(170);
        tbl_Renglones.getColumnModel().getColumn(2).setPreferredWidth(580);
        tbl_Renglones.getColumnModel().getColumn(3).setPreferredWidth(120);
        tbl_Renglones.getColumnModel().getColumn(4).setPreferredWidth(120);
        tbl_Renglones.getColumnModel().getColumn(5).setPreferredWidth(120);
        tbl_Renglones.getColumnModel().getColumn(6).setPreferredWidth(120);
        tbl_Renglones.getColumnModel().getColumn(7).setPreferredWidth(120);
    }

    private boolean existeProductoCargado(Producto producto) {
        for (RenglonFactura renglon : renglones) {
            if (renglon.getDescripcionItem().equals(producto.getDescripcion())) {
                return true;
            }
        }
        return false;
    }

    private void cargarTiposDeFacturaDisponibles() {
        char[] tiposFactura = facturaService.getTipoFacturaVenta(empresaService.getEmpresaActiva().getEmpresa(), (Cliente) cmb_Cliente.getSelectedItem());
        cmb_TipoFactura.removeAllItems();
        for (int i = 0; tiposFactura.length > i; i++) {
            cmb_TipoFactura.addItem(tiposFactura[i]);
        }
    }

    private void recargarRenglonesSegunTipoDeFactura() {
        //resguardo de renglones
        List<RenglonFactura> resguardoRenglones = renglones;
        renglones = new ArrayList<>();
        boolean[] marcasIndices = new boolean[modeloTablaRenglones.getRowCount()];
        for (int i = 0; i < modeloTablaRenglones.getRowCount(); i++) {
            marcasIndices[i] = (boolean) modeloTablaRenglones.getValueAt(i, 0);
        }
        modeloTablaRenglones = new ModeloTabla();
        this.setColumnas();
        int indiceParaMarcas = 0;
        for (RenglonFactura renglon : resguardoRenglones) {
            Producto producto = productoService.getProductoPorId(renglon.getId_ProductoItem());
            RenglonFactura nuevoRenglon = renglonDeFacturaService.calcularRenglon(tipoDeFactura, Movimiento.VENTA, renglon.getCantidad(), producto, renglon.getDescuento_porcentaje());
            this.agregarRenglon(nuevoRenglon, marcasIndices[indiceParaMarcas]);
            indiceParaMarcas++;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatosComprobante = new javax.swing.JPanel();
        cmb_FormaDePago = new javax.swing.JComboBox();
        lbl_FormaDePago = new javax.swing.JLabel();
        lbl_Transporte = new javax.swing.JLabel();
        cmb_Transportista = new javax.swing.JComboBox();
        btn_NuevoTransportista = new javax.swing.JButton();
        lbl_Cliente = new javax.swing.JLabel();
        cmb_Cliente = new javax.swing.JComboBox();
        btn_NuevoCliente = new javax.swing.JButton();
        btn_NuevoFormaDePago = new javax.swing.JButton();
        panelRenglones = new javax.swing.JPanel();
        sp_Renglones = new javax.swing.JScrollPane();
        tbl_Renglones = new javax.swing.JTable();
        lbl_CodigoProducto = new javax.swing.JLabel();
        btn_IngresarCodigoProducto = new javax.swing.JButton();
        btn_BuscarProducto = new javax.swing.JButton();
        btn_NuevoProducto = new javax.swing.JButton();
        btn_QuitarDeLista = new javax.swing.JButton();
        txt_CodigoProducto = new javax.swing.JTextField();
        marcarDesmarcar = new javax.swing.JToggleButton();
        panelResultados = new javax.swing.JPanel();
        lbl_SubTotal = new javax.swing.JLabel();
        lbl_Total = new javax.swing.JLabel();
        txt_SubTotal = new javax.swing.JFormattedTextField();
        txt_ImpInterno_Neto = new javax.swing.JFormattedTextField();
        txt_Total = new javax.swing.JFormattedTextField();
        lbl_ImpInterno = new javax.swing.JLabel();
        lbl_IVA_105 = new javax.swing.JLabel();
        txt_IVA_105 = new javax.swing.JFormattedTextField();
        lbl_Recargo = new javax.swing.JLabel();
        txt_Recargo_Porcentaje = new javax.swing.JFormattedTextField();
        txt_Recargo_Neto = new javax.swing.JFormattedTextField();
        lbl_SubTotalNeto = new javax.swing.JLabel();
        txt_SubTotal_Neto = new javax.swing.JFormattedTextField();
        lbl_105 = new javax.swing.JLabel();
        lbl_IVA_21 = new javax.swing.JLabel();
        lbl_21 = new javax.swing.JLabel();
        txt_IVA_21 = new javax.swing.JFormattedTextField();
        panelMisc = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_Observaciones = new javax.swing.JTextArea();
        btn_Guardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_TipoFactura = new javax.swing.JLabel();
        cmb_TipoFactura = new javax.swing.JComboBox();
        lbl_Fecha = new javax.swing.JLabel();
        dc_FechaFactura = new com.toedter.calendar.JDateChooser();
        lbl_FechaVto = new javax.swing.JLabel();
        dc_FechaVencimiento = new com.toedter.calendar.JDateChooser();
        chk_condicionDividir = new javax.swing.JCheckBox();

        setTitle("Nueva Factura de Venta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelDatosComprobante.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_FormaDePago.setForeground(java.awt.Color.red);
        lbl_FormaDePago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_FormaDePago.setText("* Forma de Pago:");

        lbl_Transporte.setForeground(java.awt.Color.red);
        lbl_Transporte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Transporte.setText("* Transporte:");

        btn_NuevoTransportista.setForeground(java.awt.Color.blue);
        btn_NuevoTransportista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddTruck_16x16.png"))); // NOI18N
        btn_NuevoTransportista.setText("Nuevo");
        btn_NuevoTransportista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoTransportistaActionPerformed(evt);
            }
        });

        lbl_Cliente.setForeground(java.awt.Color.red);
        lbl_Cliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Cliente.setText("* Cliente:");

        cmb_Cliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_ClienteItemStateChanged(evt);
            }
        });

        btn_NuevoCliente.setForeground(java.awt.Color.blue);
        btn_NuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddClient_16x16.png"))); // NOI18N
        btn_NuevoCliente.setText("Nuevo");
        btn_NuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoClienteActionPerformed(evt);
            }
        });

        btn_NuevoFormaDePago.setForeground(java.awt.Color.blue);
        btn_NuevoFormaDePago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddWallet_16x16.png"))); // NOI18N
        btn_NuevoFormaDePago.setText("Nueva");
        btn_NuevoFormaDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoFormaDePagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosComprobanteLayout = new javax.swing.GroupLayout(panelDatosComprobante);
        panelDatosComprobante.setLayout(panelDatosComprobanteLayout);
        panelDatosComprobanteLayout.setHorizontalGroup(
            panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosComprobanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_FormaDePago, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(lbl_Transporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_Transportista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_FormaDePago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_Cliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_NuevoFormaDePago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_NuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_NuevoTransportista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDatosComprobanteLayout.setVerticalGroup(
            panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosComprobanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_NuevoCliente)
                    .addComponent(cmb_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Cliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_NuevoFormaDePago)
                    .addComponent(cmb_FormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_FormaDePago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmb_Transportista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Transporte)
                    .addComponent(btn_NuevoTransportista))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRenglones.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tbl_Renglones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_Renglones.setFocusable(false);
        tbl_Renglones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_Renglones.getTableHeader().setReorderingAllowed(false);
        tbl_Renglones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_RenglonesMouseClicked(evt);
            }
        });
        sp_Renglones.setViewportView(tbl_Renglones);

        lbl_CodigoProducto.setText("Código Producto:");

        btn_IngresarCodigoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/16x16.png"))); // NOI18N
        btn_IngresarCodigoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IngresarCodigoProductoActionPerformed(evt);
            }
        });

        btn_BuscarProducto.setForeground(java.awt.Color.blue);
        btn_BuscarProducto.setText("Buscar Producto");
        btn_BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarProductoActionPerformed(evt);
            }
        });

        btn_NuevoProducto.setForeground(java.awt.Color.blue);
        btn_NuevoProducto.setText("Nuevo Producto");
        btn_NuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoProductoActionPerformed(evt);
            }
        });

        btn_QuitarDeLista.setForeground(java.awt.Color.blue);
        btn_QuitarDeLista.setText("Quitar de la lista");
        btn_QuitarDeLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuitarDeListaActionPerformed(evt);
            }
        });

        txt_CodigoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CodigoProductoActionPerformed(evt);
            }
        });

        marcarDesmarcar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                marcarDesmarcarStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelRenglonesLayout = new javax.swing.GroupLayout(panelRenglones);
        panelRenglones.setLayout(panelRenglonesLayout);
        panelRenglonesLayout.setHorizontalGroup(
            panelRenglonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp_Renglones)
            .addGroup(panelRenglonesLayout.createSequentialGroup()
                .addComponent(marcarDesmarcar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_CodigoProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_CodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_IngresarCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_BuscarProducto)
                .addGap(0, 0, 0)
                .addComponent(btn_NuevoProducto)
                .addGap(0, 0, 0)
                .addComponent(btn_QuitarDeLista)
                .addContainerGap(265, Short.MAX_VALUE))
        );

        panelRenglonesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_BuscarProducto, btn_NuevoProducto, btn_QuitarDeLista});

        panelRenglonesLayout.setVerticalGroup(
            panelRenglonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRenglonesLayout.createSequentialGroup()
                .addComponent(sp_Renglones, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRenglonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_IngresarCodigoProducto)
                    .addComponent(txt_CodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_CodigoProducto)
                    .addComponent(btn_BuscarProducto)
                    .addComponent(btn_NuevoProducto)
                    .addComponent(btn_QuitarDeLista)
                    .addComponent(marcarDesmarcar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelRenglonesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_IngresarCodigoProducto, txt_CodigoProducto});

        panelRenglonesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_BuscarProducto, btn_NuevoProducto, btn_QuitarDeLista});

        panelResultados.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        lbl_SubTotal.setText("SubTotal");

        lbl_Total.setText("TOTAL");

        txt_SubTotal.setEditable(false);
        txt_SubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_SubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_SubTotal.setFocusable(false);
        txt_SubTotal.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        txt_ImpInterno_Neto.setEditable(false);
        txt_ImpInterno_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_ImpInterno_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_ImpInterno_Neto.setFocusable(false);
        txt_ImpInterno_Neto.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        txt_Total.setEditable(false);
        txt_Total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_Total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Total.setFocusable(false);
        txt_Total.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        lbl_ImpInterno.setText("Imp. Interno");

        lbl_IVA_105.setText("I.V.A.");

        txt_IVA_105.setEditable(false);
        txt_IVA_105.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_IVA_105.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_IVA_105.setFocusable(false);
        txt_IVA_105.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        lbl_Recargo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Recargo.setText("Recargo (%)");

        txt_Recargo_Porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,###,##0.00"))));
        txt_Recargo_Porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Recargo_Porcentaje.setText("0");
        txt_Recargo_Porcentaje.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        txt_Recargo_Porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Recargo_PorcentajeFocusLost(evt);
            }
        });
        txt_Recargo_Porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Recargo_PorcentajeActionPerformed(evt);
            }
        });

        txt_Recargo_Neto.setEditable(false);
        txt_Recargo_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_Recargo_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Recargo_Neto.setFocusable(false);
        txt_Recargo_Neto.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        lbl_SubTotalNeto.setText("SubTotal Neto");

        txt_SubTotal_Neto.setEditable(false);
        txt_SubTotal_Neto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_SubTotal_Neto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_SubTotal_Neto.setFocusable(false);
        txt_SubTotal_Neto.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        lbl_105.setText("10.5 %");

        lbl_IVA_21.setText("I.V.A.");

        lbl_21.setText("21 %");

        txt_IVA_21.setEditable(false);
        txt_IVA_21.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_IVA_21.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_IVA_21.setFocusable(false);
        txt_IVA_21.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N

        javax.swing.GroupLayout panelResultadosLayout = new javax.swing.GroupLayout(panelResultados);
        panelResultados.setLayout(panelResultadosLayout);
        panelResultadosLayout.setHorizontalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_SubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(txt_SubTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Recargo, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(txt_Recargo_Neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Recargo_Porcentaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_SubTotal_Neto)
                    .addComponent(lbl_SubTotalNeto, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_105, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(lbl_IVA_105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_IVA_105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_IVA_21)
                    .addComponent(lbl_IVA_21, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(lbl_21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_ImpInterno, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txt_ImpInterno_Neto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Total)
                    .addComponent(lbl_Total, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelResultadosLayout.setVerticalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_SubTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Recargo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_SubTotalNeto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_IVA_105, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_IVA_21, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_ImpInterno, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Total, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Recargo_Porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_105)
                    .addComponent(lbl_21))
                .addGap(5, 5, 5)
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_SubTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Recargo_Neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_SubTotal_Neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_IVA_105, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_IVA_21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ImpInterno_Neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelResultadosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_IVA_105, txt_IVA_21, txt_ImpInterno_Neto, txt_Recargo_Neto, txt_SubTotal, txt_SubTotal_Neto, txt_Total});

        panelMisc.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));

        txta_Observaciones.setColumns(20);
        txta_Observaciones.setLineWrap(true);
        txta_Observaciones.setRows(5);
        jScrollPane1.setViewportView(txta_Observaciones);

        javax.swing.GroupLayout panelMiscLayout = new javax.swing.GroupLayout(panelMisc);
        panelMisc.setLayout(panelMiscLayout);
        panelMiscLayout.setHorizontalGroup(
            panelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMiscLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        panelMiscLayout.setVerticalGroup(
            panelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiscLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btn_Guardar.setForeground(java.awt.Color.blue);
        btn_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Guardar.setText("Guardar");
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_TipoFactura.setForeground(java.awt.Color.red);
        lbl_TipoFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_TipoFactura.setText("* Tipo de Factura:");

        cmb_TipoFactura.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        cmb_TipoFactura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_TipoFacturaItemStateChanged(evt);
            }
        });

        lbl_Fecha.setForeground(java.awt.Color.red);
        lbl_Fecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Fecha.setText("* Fecha de Factura:");

        dc_FechaFactura.setDateFormatString("dd/MM/yyyy");

        lbl_FechaVto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_FechaVto.setText("Fecha de Vencimiento:");

        dc_FechaVencimiento.setDateFormatString("dd/MM/yyyy");

        chk_condicionDividir.setText("Dividir Factura");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_FechaVto, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(lbl_TipoFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dc_FechaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmb_TipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chk_condicionDividir))
                    .addComponent(dc_FechaVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_TipoFactura)
                    .addComponent(cmb_TipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chk_condicionDividir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Fecha)
                    .addComponent(dc_FechaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dc_FechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_FechaVto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Guardar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelMisc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRenglones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(panelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 64, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(panelDatosComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDatosComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRenglones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_Guardar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_NuevoTransportistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoTransportistaActionPerformed
        GUI_DetalleTransportista gui_DetalleTransportista = new GUI_DetalleTransportista();
        gui_DetalleTransportista.setModal(true);
        gui_DetalleTransportista.setLocationRelativeTo(this);
        gui_DetalleTransportista.setVisible(true);

        try {
            this.cargarComboBoxTransportistas();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }

}//GEN-LAST:event_btn_NuevoTransportistaActionPerformed

    private void btn_NuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoClienteActionPerformed
        GUI_DetalleCliente gui_DetalleCliente = new GUI_DetalleCliente();
        gui_DetalleCliente.setModal(true);
        gui_DetalleCliente.setLocationRelativeTo(this);
        gui_DetalleCliente.setVisible(true);

        try {
            this.cargarComboBoxClientes();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevoClienteActionPerformed

    private void btn_NuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoProductoActionPerformed
        GUI_DetalleProducto gui_DetalleProducto = new GUI_DetalleProducto();
        gui_DetalleProducto.setModal(true);
        gui_DetalleProducto.setLocationRelativeTo(this);
        gui_DetalleProducto.setVisible(true);
    }//GEN-LAST:event_btn_NuevoProductoActionPerformed

    private void btn_BuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarProductoActionPerformed
        if (facturaService.validarCantidadMaximaDeRenglones(renglones.size())) {
            GUI_BusquedaProductos gui_BusquedaProductos = new GUI_BusquedaProductos();
            gui_BusquedaProductos.setModal(true);
            gui_BusquedaProductos.setLocationRelativeTo(this);
            gui_BusquedaProductos.setVisible(true);
            if (gui_BusquedaProductos.getProdSeleccionado() != null) {
                this.llamarVentanaRenglonFactura(gui_BusquedaProductos.getProdSeleccionado());
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("Mensajes").getString("mensaje_maxima_cantidad_de_renglones"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_BuscarProductoActionPerformed

    private void btn_IngresarCodigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IngresarCodigoProductoActionPerformed
        this.buscarProductoPorCodigo(txt_CodigoProducto.getText().trim());
    }//GEN-LAST:event_btn_IngresarCodigoProductoActionPerformed

    private void btn_QuitarDeListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QuitarDeListaActionPerformed
        this.quitarRenglonFactura();
    }//GEN-LAST:event_btn_QuitarDeListaActionPerformed

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        try {
            boolean dividir;
            dividir = false;
            int[] indicesParaDividir = null;
            ModeloTabla tablaDeTPV = (ModeloTabla) tbl_Renglones.getModel();
            FacturaVenta factura = this.construirFactura();
            if (chk_condicionDividir.isSelected() && (factura.getTipoFactura() == 'A' || factura.getTipoFactura() == 'B' || factura.getTipoFactura() == 'C')) {
                indicesParaDividir = new int[tablaDeTPV.getRowCount()];
                int j = 0;
                for (int i = 0; i < tablaDeTPV.getRowCount(); i++) {
                    if ((boolean) tablaDeTPV.getValueAt(i, 0)) {
                        indicesParaDividir[j] = i;
                        j++;
                    }
                }
                if (0 != indicesParaDividir.length) {
                    dividir = true;
                }
            }
            if (!dividir) {
                Factura aux = this.guardarFactura(factura);
                this.lanzarReporteFactura(aux);
                this.dispose();
            } else {
                List<FacturaVenta> facturasDivididas = facturaService.dividirFactura(this.construirFactura(), indicesParaDividir);
                for (Factura facturaDividida : facturasDivididas) {
                    if (facturasDivididas.size() == 2 && !facturaDividida.getRenglones().isEmpty()) {
                        this.lanzarReporteFactura(this.guardarFactura(facturaDividida));
                        this.dispose();
                    }
                }
            }

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "La Factura se guardó correctamente!\n¿Desea dar de alta otra Factura?",
                    "Aviso", JOptionPane.YES_NO_OPTION);
            this.limpiarYRecargarComponentes();
            if (respuesta == JOptionPane.NO_OPTION) {
                this.dispose();
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
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void txt_CodigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CodigoProductoActionPerformed
        this.buscarProductoPorCodigo(txt_CodigoProducto.getText().trim());
    }//GEN-LAST:event_txt_CodigoProductoActionPerformed

    private void txt_Recargo_PorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Recargo_PorcentajeActionPerformed
        this.calcularResultados();
    }//GEN-LAST:event_txt_Recargo_PorcentajeActionPerformed

    private void txt_Recargo_PorcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Recargo_PorcentajeFocusLost
        this.calcularResultados();
    }//GEN-LAST:event_txt_Recargo_PorcentajeFocusLost

    private void btn_NuevoFormaDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoFormaDePagoActionPerformed
        GUI_FormasDePago gui_FormasDePago = new GUI_FormasDePago();
        gui_FormasDePago.setModal(true);
        gui_FormasDePago.setLocationRelativeTo(this);
        gui_FormasDePago.setVisible(true);

        try {
            this.cargarComboBoxFormasDePago();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevoFormaDePagoActionPerformed

    private void cmb_ClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_ClienteItemStateChanged
        try {
            //para evitar que pase null cuando esta recargando el comboBox
            if (cmb_Cliente.getSelectedItem() != null) {
                this.cargarTiposDeFacturaDisponibles();
            }

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_ClienteItemStateChanged

    private void cmb_TipoFacturaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_TipoFacturaItemStateChanged
        //para evitar que pase null cuando esta recargando el comboBox
        try {
            if (cmb_TipoFactura.getSelectedItem() != null) {
                tipoDeFactura = cmb_TipoFactura.getSelectedItem().toString().charAt(0);
                this.recargarRenglonesSegunTipoDeFactura();
            }

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_TipoFacturaItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.cargarComboBoxFormasDePago();
            this.cargarComboBoxClientes();
            this.cargarComboBoxTransportistas();
            this.cargarTiposDeFacturaDisponibles();
            this.setColumnas();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void tbl_RenglonesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_RenglonesMouseClicked
        int fila = tbl_Renglones.getSelectedRow();
        int columna = tbl_Renglones.getSelectedColumn();
        if (columna == 0) {
            tbl_Renglones.setValueAt(!(boolean) tbl_Renglones.getValueAt(fila, columna), fila, columna);
        }
    }//GEN-LAST:event_tbl_RenglonesMouseClicked

    private void marcarDesmarcarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_marcarDesmarcarStateChanged
        int cantidadDeFilas = tbl_Renglones.getRowCount();
        if (this.marcarDesmarcar.isSelected()) {
            ImageIcon iconoMarcado = new ImageIcon(getClass().getResource("/sic/icons/chkMarca_16x16.png"));
            this.marcarDesmarcar.setIcon(iconoMarcado);
            for (int i = 0; i < cantidadDeFilas; i++) {
                tbl_Renglones.setValueAt(true, i, 0);
            }
        } else {
            ImageIcon iconoNoMarcado = new ImageIcon(getClass().getResource("/sic/icons/chkNoMarcado_16x16.png"));
            this.marcarDesmarcar.setIcon(iconoNoMarcado);
            for (int i = 0; i < cantidadDeFilas; i++) {
                tbl_Renglones.setValueAt(false, i, 0);
            }
        }
    }//GEN-LAST:event_marcarDesmarcarStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_BuscarProducto;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_IngresarCodigoProducto;
    private javax.swing.JButton btn_NuevoCliente;
    private javax.swing.JButton btn_NuevoFormaDePago;
    private javax.swing.JButton btn_NuevoProducto;
    private javax.swing.JButton btn_NuevoTransportista;
    private javax.swing.JButton btn_QuitarDeLista;
    private javax.swing.JCheckBox chk_condicionDividir;
    private javax.swing.JComboBox cmb_Cliente;
    private javax.swing.JComboBox cmb_FormaDePago;
    private javax.swing.JComboBox cmb_TipoFactura;
    private javax.swing.JComboBox cmb_Transportista;
    private com.toedter.calendar.JDateChooser dc_FechaFactura;
    private com.toedter.calendar.JDateChooser dc_FechaVencimiento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_105;
    private javax.swing.JLabel lbl_21;
    private javax.swing.JLabel lbl_Cliente;
    private javax.swing.JLabel lbl_CodigoProducto;
    private javax.swing.JLabel lbl_Fecha;
    private javax.swing.JLabel lbl_FechaVto;
    private javax.swing.JLabel lbl_FormaDePago;
    private javax.swing.JLabel lbl_IVA_105;
    private javax.swing.JLabel lbl_IVA_21;
    private javax.swing.JLabel lbl_ImpInterno;
    private javax.swing.JLabel lbl_Recargo;
    private javax.swing.JLabel lbl_SubTotal;
    private javax.swing.JLabel lbl_SubTotalNeto;
    private javax.swing.JLabel lbl_TipoFactura;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_Transporte;
    private javax.swing.JToggleButton marcarDesmarcar;
    private javax.swing.JPanel panelDatosComprobante;
    private javax.swing.JPanel panelMisc;
    private javax.swing.JPanel panelRenglones;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JScrollPane sp_Renglones;
    private javax.swing.JTable tbl_Renglones;
    private javax.swing.JTextField txt_CodigoProducto;
    private javax.swing.JFormattedTextField txt_IVA_105;
    private javax.swing.JFormattedTextField txt_IVA_21;
    private javax.swing.JFormattedTextField txt_ImpInterno_Neto;
    private javax.swing.JFormattedTextField txt_Recargo_Neto;
    private javax.swing.JFormattedTextField txt_Recargo_Porcentaje;
    private javax.swing.JFormattedTextField txt_SubTotal;
    private javax.swing.JFormattedTextField txt_SubTotal_Neto;
    private javax.swing.JFormattedTextField txt_Total;
    private javax.swing.JTextArea txta_Observaciones;
    // End of variables declaration//GEN-END:variables
}
