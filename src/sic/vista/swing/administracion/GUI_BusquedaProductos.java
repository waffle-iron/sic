package sic.vista.swing.administracion;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Rubro;
import sic.service.*;
import sic.util.RenderTabla;
import sic.util.Utilidades;
import sic.vista.swing.ModeloTabla;

public class GUI_BusquedaProductos extends JDialog {

    private ModeloTabla modeloTablaResultados;
    private List<Producto> productos;
    private Producto productoSeleccionado;
    private int cantidadResultadosParaMostrar = 500;
    private final RubroService rubroService = new RubroService();
    private final EmpresaService empresaService = new EmpresaService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final ProductoService productoService = new ProductoService();
    private static final Logger log = Logger.getLogger(GUI_BusquedaProductos.class.getPackage().getName());

    public GUI_BusquedaProductos() {
        this.initComponents();
        this.setIcon();
        modeloTablaResultados = new ModeloTabla();
    }

    public Producto getProdSeleccionado() {
        return productoSeleccionado;
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Product_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void cargarComboBoxRubros() {
        Rubro rubroSeleccionado = (Rubro) cmb_Rubro.getSelectedItem();
        List<Rubro> rubros;
        cmb_Rubro.removeAllItems();
        rubros = rubroService.getRubros(empresaService.getEmpresaActiva().getEmpresa());
        for (Rubro r : rubros) {
            cmb_Rubro.addItem(r);
        }
        if (rubroSeleccionado != null && rubros.contains(rubroSeleccionado)) {
            cmb_Rubro.setSelectedItem(rubroSeleccionado);
        }
    }

    private void cargarComboBoxProveedores() {
        Proveedor proveedorSeleccionado = (Proveedor) cmb_Proveedor.getSelectedItem();
        List<Proveedor> proveedores;
        cmb_Proveedor.removeAllItems();
        proveedores = proveedorService.getProveedores(empresaService.getEmpresaActiva().getEmpresa());
        for (Proveedor prov : proveedores) {
            cmb_Proveedor.addItem(prov);
        }
        if (proveedorSeleccionado != null && proveedores.contains(proveedorSeleccionado)) {
            cmb_Proveedor.setSelectedItem(proveedorSeleccionado);
        }
    }

    private void setColumnas() {
        //sorting
        tbl_Resultados.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[23];
        encabezados[0] = "Codigo";
        encabezados[1] = "Descripcion";
        encabezados[2] = "Cantidad";
        encabezados[3] = "Cant. Minima";
        encabezados[4] = "Sin Límite";
        encabezados[5] = "Medida";
        encabezados[6] = "Precio Costo";
        encabezados[7] = "% Ganancia";
        encabezados[8] = "Ganancia";
        encabezados[9] = "PVP";
        encabezados[10] = "% IVA";
        encabezados[11] = "IVA";
        encabezados[12] = "% Imp.Interno";
        encabezados[13] = "Imp.Interno";
        encabezados[14] = "Precio Lista";
        encabezados[15] = "Rubro";
        encabezados[16] = "Fecha U. Modificacion";
        encabezados[17] = "Estanteria";
        encabezados[18] = "Estante";
        encabezados[19] = "Proveedor";
        encabezados[20] = "Fecha Alta";
        encabezados[21] = "Fecha Vencimiento";
        encabezados[22] = "Nota";
        modeloTablaResultados.setColumnIdentifiers(encabezados);
        tbl_Resultados.setModel(modeloTablaResultados);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaResultados.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = Double.class;
        tipos[3] = Double.class;
        tipos[4] = Boolean.class;
        tipos[5] = String.class;
        tipos[6] = Double.class;
        tipos[7] = Double.class;
        tipos[8] = Double.class;
        tipos[9] = Double.class;
        tipos[10] = Double.class;
        tipos[11] = Double.class;
        tipos[12] = Double.class;
        tipos[13] = Double.class;
        tipos[14] = Double.class;
        tipos[15] = String.class;
        tipos[16] = Date.class;
        tipos[17] = String.class;
        tipos[18] = String.class;
        tipos[19] = String.class;
        tipos[20] = Date.class;
        tipos[21] = Date.class;
        tipos[22] = String.class;
        modeloTablaResultados.setClaseColumnas(tipos);
        tbl_Resultados.getTableHeader().setReorderingAllowed(false);
        tbl_Resultados.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Resultados.setDefaultRenderer(Double.class, new RenderTabla());

        //Tamanios de columnas
        tbl_Resultados.getColumnModel().getColumn(0).setPreferredWidth(150);
        tbl_Resultados.getColumnModel().getColumn(1).setPreferredWidth(400);
        tbl_Resultados.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(4).setPreferredWidth(80);
        tbl_Resultados.getColumnModel().getColumn(5).setPreferredWidth(150);
        tbl_Resultados.getColumnModel().getColumn(6).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(7).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(8).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(9).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(10).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(11).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(12).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(13).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(14).setPreferredWidth(100);
        tbl_Resultados.getColumnModel().getColumn(15).setPreferredWidth(200);
        tbl_Resultados.getColumnModel().getColumn(16).setPreferredWidth(200);
        tbl_Resultados.getColumnModel().getColumn(17).setPreferredWidth(200);
        tbl_Resultados.getColumnModel().getColumn(18).setPreferredWidth(200);
        tbl_Resultados.getColumnModel().getColumn(19).setPreferredWidth(450);
        tbl_Resultados.getColumnModel().getColumn(20).setPreferredWidth(150);
        tbl_Resultados.getColumnModel().getColumn(21).setPreferredWidth(150);
        tbl_Resultados.getColumnModel().getColumn(22).setPreferredWidth(1000);
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTable();
        for (Producto producto : productos) {
            Object[] fila = new Object[23];
            fila[0] = producto.getCodigo();
            fila[1] = producto.getDescripcion();
            fila[2] = producto.getCantidad();
            fila[3] = producto.getCantMinima();
            fila[4] = producto.isIlimitado();
            fila[5] = producto.getMedida().getNombre();
            fila[6] = producto.getPrecioCosto();
            fila[7] = producto.getGanancia_porcentaje();
            fila[8] = producto.getGanancia_neto();
            fila[9] = producto.getPrecioVentaPublico();
            fila[10] = producto.getIva_porcentaje();
            fila[11] = producto.getIva_neto();
            fila[12] = producto.getImpuestoInterno_porcentaje();
            fila[13] = producto.getImpuestoInterno_neto();
            fila[14] = producto.getPrecioLista();
            fila[15] = producto.getRubro().getNombre();
            fila[16] = producto.getFechaUltimaModificacion();
            fila[17] = producto.getEstanteria();
            fila[18] = producto.getEstante();
            fila[19] = producto.getProveedor().getRazonSocial();
            fila[20] = producto.getFechaAlta();
            fila[21] = producto.getFechaVencimiento();
            fila[22] = producto.getNota();
            modeloTablaResultados.addRow(fila);
        }
        tbl_Resultados.setModel(modeloTablaResultados);
    }

    private void actualizarContadorDeRegistros() {
        String mensaje;
        mensaje = productos.size() + " productos encontrados";
        lbl_CantRegistrosEncontrados.setText(mensaje);
    }

    private void limpiarJTable() {
        modeloTablaResultados = new ModeloTabla();
        tbl_Resultados.setModel(modeloTablaResultados);
        this.setColumnas();
    }

    private void buscar() {
        BusquedaProductoCriteria criteria = new BusquedaProductoCriteria();
        criteria.setBuscarPorCodigo(chk_Codigo.isSelected());
        criteria.setCodigo(txt_Codigo.getText().trim());
        criteria.setBuscarPorDescripcion(chk_Descripcion.isSelected());
        criteria.setDescripcion(txt_Descripcion.getText().trim());
        criteria.setBuscarPorRubro(chk_Rubro.isSelected());
        criteria.setRubro((Rubro) cmb_Rubro.getSelectedItem());
        criteria.setBuscarPorProveedor(chk_Proveedor.isSelected());
        criteria.setProveedor((Proveedor) cmb_Proveedor.getSelectedItem());
        criteria.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
        criteria.setCantRegistros(cantidadResultadosParaMostrar);
        criteria.setListarSoloFaltantes(false);

        try {
            productos = productoService.buscarProductos(criteria);
            this.cargarResultadosAlTable();
            this.actualizarContadorDeRegistros();

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aceptarProducto() {
        if (tbl_Resultados.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione un Producto de la lista para continuar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            productoSeleccionado = productos.get(Utilidades.getSelectedRowModelIndice(tbl_Resultados));
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFiltros = new javax.swing.JPanel();
        chk_Codigo = new javax.swing.JCheckBox();
        txt_Codigo = new javax.swing.JTextField();
        chk_Proveedor = new javax.swing.JCheckBox();
        cmb_Proveedor = new javax.swing.JComboBox();
        btn_Buscar = new javax.swing.JButton();
        txt_Descripcion = new javax.swing.JTextField();
        chk_Descripcion = new javax.swing.JCheckBox();
        chk_Rubro = new javax.swing.JCheckBox();
        cmb_Rubro = new javax.swing.JComboBox();
        lbl_CantRegistrosEncontrados = new javax.swing.JLabel();
        panelResultados = new javax.swing.JPanel();
        sp_Resultados = new javax.swing.JScrollPane();
        tbl_Resultados = new javax.swing.JTable();
        cmb_CantidadMostrar = new javax.swing.JComboBox();
        lbl_CantidadMostrar = new javax.swing.JLabel();
        btn_Seleccionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busqueda de Productos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros de Busqueda"));

        chk_Codigo.setText("Código:");
        chk_Codigo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_CodigoItemStateChanged(evt);
            }
        });

        txt_Codigo.setEnabled(false);

        chk_Proveedor.setText("Proveedor:");
        chk_Proveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_ProveedorItemStateChanged(evt);
            }
        });

        cmb_Proveedor.setEnabled(false);

        btn_Buscar.setForeground(java.awt.Color.blue);
        btn_Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Search_16x16.png"))); // NOI18N
        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });

        txt_Descripcion.setEnabled(false);

        chk_Descripcion.setText("Descripción:");
        chk_Descripcion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_DescripcionItemStateChanged(evt);
            }
        });

        chk_Rubro.setText("Rubro:");
        chk_Rubro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_RubroItemStateChanged(evt);
            }
        });

        cmb_Rubro.setEnabled(false);

        lbl_CantRegistrosEncontrados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panelFiltrosLayout = new javax.swing.GroupLayout(panelFiltros);
        panelFiltros.setLayout(panelFiltrosLayout);
        panelFiltrosLayout.setHorizontalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_Rubro)
                            .addComponent(chk_Descripcion)
                            .addComponent(chk_Codigo)
                            .addComponent(chk_Proveedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_Descripcion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_Rubro, javax.swing.GroupLayout.Alignment.LEADING, 0, 278, Short.MAX_VALUE)
                            .addComponent(cmb_Proveedor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Codigo))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addComponent(btn_Buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_CantRegistrosEncontrados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelFiltrosLayout.setVerticalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_Codigo)
                    .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_Descripcion)
                    .addComponent(txt_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_Rubro)
                    .addComponent(cmb_Rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_Proveedor)
                    .addComponent(cmb_Proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_CantRegistrosEncontrados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelResultados.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
        tbl_Resultados.setToolTipText("");
        tbl_Resultados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_Resultados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_Resultados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbl_ResultadosFocusGained(evt);
            }
        });
        tbl_Resultados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_ResultadosKeyPressed(evt);
            }
        });
        sp_Resultados.setViewportView(tbl_Resultados);

        cmb_CantidadMostrar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "500", "1000", "5000", "Sin Limite" }));
        cmb_CantidadMostrar.setFocusable(false);
        cmb_CantidadMostrar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_CantidadMostrarItemStateChanged(evt);
            }
        });
        cmb_CantidadMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_CantidadMostrarActionPerformed(evt);
            }
        });

        lbl_CantidadMostrar.setText("Cantidad a mostrar:");

        javax.swing.GroupLayout panelResultadosLayout = new javax.swing.GroupLayout(panelResultados);
        panelResultados.setLayout(panelResultadosLayout);
        panelResultadosLayout.setHorizontalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp_Resultados, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_CantidadMostrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_CantidadMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelResultadosLayout.setVerticalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelResultadosLayout.createSequentialGroup()
                .addGroup(panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_CantidadMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_CantidadMostrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_Resultados, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );

        btn_Seleccionar.setForeground(java.awt.Color.blue);
        btn_Seleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/ArrowRight_16x16.png"))); // NOI18N
        btn_Seleccionar.setText("Siguiente");
        btn_Seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panelResultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_Seleccionar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Seleccionar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chk_CodigoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_CodigoItemStateChanged
        //Pregunta el estado actual del checkBox
        if (chk_Codigo.isSelected() == true) {
            txt_Codigo.setEnabled(true);
            txt_Codigo.requestFocus();
        } else {
            txt_Codigo.setEnabled(false);
        }
    }//GEN-LAST:event_chk_CodigoItemStateChanged

    private void chk_ProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_ProveedorItemStateChanged
        //Pregunta el estado actual del checkBox
        if (chk_Proveedor.isSelected() == true) {
            cmb_Proveedor.setEnabled(true);
            cmb_Proveedor.requestFocus();
        } else {
            cmb_Proveedor.setEnabled(false);
        }
    }//GEN-LAST:event_chk_ProveedorItemStateChanged

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
        this.buscar();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle(
                    "Mensajes").getString("mensaje_busqueda_sin_resultados"),
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_BuscarActionPerformed

    private void chk_DescripcionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_DescripcionItemStateChanged
        //Pregunta el estado actual del checkBox
        if (chk_Descripcion.isSelected() == true) {
            txt_Descripcion.setEnabled(true);
            txt_Descripcion.requestFocus();
        } else {
            txt_Descripcion.setEnabled(false);
        }
    }//GEN-LAST:event_chk_DescripcionItemStateChanged

    private void chk_RubroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_RubroItemStateChanged
        //Pregunta el estado actual del checkBox
        if (chk_Rubro.isSelected() == true) {
            cmb_Rubro.setEnabled(true);
            cmb_Rubro.requestFocus();
        } else {
            cmb_Rubro.setEnabled(false);
        }
    }//GEN-LAST:event_chk_RubroItemStateChanged

    private void cmb_CantidadMostrarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_CantidadMostrarItemStateChanged
        if (cmb_CantidadMostrar.getSelectedItem().equals("Sin Limite")) {
            cantidadResultadosParaMostrar = 0;
        }

        if (cmb_CantidadMostrar.getSelectedItem().equals("500")) {
            cantidadResultadosParaMostrar = 500;
        }

        if (cmb_CantidadMostrar.getSelectedItem().equals("1000")) {
            cantidadResultadosParaMostrar = 1000;
        }

        if (cmb_CantidadMostrar.getSelectedItem().equals("5000")) {
            cantidadResultadosParaMostrar = 5000;
        }
    }//GEN-LAST:event_cmb_CantidadMostrarItemStateChanged

    private void btn_SeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SeleccionarActionPerformed
        this.aceptarProducto();
    }//GEN-LAST:event_btn_SeleccionarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            this.cargarComboBoxRubros();
            this.cargarComboBoxProveedores();
            this.setColumnas();
        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_formWindowOpened

    private void tbl_ResultadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_ResultadosKeyPressed
        //ENTER
        if (evt.getKeyCode() == 10) {
            this.aceptarProducto();
        }
        //TAB
        if (evt.getKeyCode() == 9) {
            btn_Seleccionar.requestFocus();
        }
    }//GEN-LAST:event_tbl_ResultadosKeyPressed

    private void tbl_ResultadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_ResultadosFocusGained
        //Si no hay nada seleccionado y NO esta vacio el table, selecciona la primer fila
        if ((tbl_Resultados.getSelectedRow() == -1) && (tbl_Resultados.getRowCount() != 0)) {
            tbl_Resultados.setRowSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_tbl_ResultadosFocusGained

    private void cmb_CantidadMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_CantidadMostrarActionPerformed
        this.buscar();
    }//GEN-LAST:event_cmb_CantidadMostrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JButton btn_Seleccionar;
    private javax.swing.JCheckBox chk_Codigo;
    private javax.swing.JCheckBox chk_Descripcion;
    private javax.swing.JCheckBox chk_Proveedor;
    private javax.swing.JCheckBox chk_Rubro;
    private javax.swing.JComboBox cmb_CantidadMostrar;
    private javax.swing.JComboBox cmb_Proveedor;
    private javax.swing.JComboBox cmb_Rubro;
    private javax.swing.JLabel lbl_CantRegistrosEncontrados;
    private javax.swing.JLabel lbl_CantidadMostrar;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JScrollPane sp_Resultados;
    private javax.swing.JTable tbl_Resultados;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Descripcion;
    // End of variables declaration//GEN-END:variables
}
