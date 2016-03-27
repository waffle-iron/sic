package sic.vista.swing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;
import sic.modelo.BusquedaCajaCriteria;
import sic.modelo.Caja;
import sic.modelo.Usuario;
import sic.service.CajaService;
import sic.service.EmpresaService;
import sic.service.ServiceException;
import sic.service.UsuarioService;
import sic.util.RenderTabla;
import sic.util.Utilidades;

public class GUI_Cajas extends javax.swing.JInternalFrame {

    private ModeloTabla modeloTablaCajas = new ModeloTabla();
    private final CajaService cajaService = new CajaService();
    private final EmpresaService empresaService = new EmpresaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private static final Logger log = Logger.getLogger(GUI_Cajas.class.getPackage().getName());
    private List<Caja> cajas;

    public GUI_Cajas() {
        initComponents();
        this.setColumnasCaja();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Filtros = new javax.swing.JPanel();
        chk_Fecha = new javax.swing.JCheckBox();
        dc_FechaDesde = new com.toedter.calendar.JDateChooser();
        dc_FechaHasta = new com.toedter.calendar.JDateChooser();
        lbl_Hasta = new javax.swing.JLabel();
        lbl_Desde = new javax.swing.JLabel();
        btn_buscar = new javax.swing.JButton();
        cmb_paginado = new javax.swing.JComboBox<>();
        chk_Usuario = new javax.swing.JCheckBox();
        cmb_Usuarios = new javax.swing.JComboBox<>();
        pnl_Cajas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Cajas = new javax.swing.JTable();
        pnl_Botones = new javax.swing.JPanel();
        btn_verDetalle = new javax.swing.JButton();
        btn_eliminarCaja = new javax.swing.JButton();
        pb_barra = new javax.swing.JProgressBar();

        setClosable(true);
        setMaximizable(true);
        setTitle("Cajas");

        pnl_Filtros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        chk_Fecha.setText("Fecha Caja");
        chk_Fecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_FechaItemStateChanged(evt);
            }
        });

        dc_FechaDesde.setDateFormatString("dd/MM/yyyy");
        dc_FechaDesde.setEnabled(false);

        dc_FechaHasta.setDateFormatString("dd/MM/yyyy");
        dc_FechaHasta.setEnabled(false);

        lbl_Hasta.setText("Hasta:");
        lbl_Hasta.setEnabled(false);

        lbl_Desde.setText("Desde:");
        lbl_Desde.setEnabled(false);

        btn_buscar.setForeground(java.awt.Color.blue);
        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        cmb_paginado.setForeground(java.awt.Color.blue);
        cmb_paginado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "50", "100", "150", "300", "500" }));

        chk_Usuario.setText("Usuario");
        chk_Usuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chk_UsuarioItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnl_FiltrosLayout = new javax.swing.GroupLayout(pnl_Filtros);
        pnl_Filtros.setLayout(pnl_FiltrosLayout);
        pnl_FiltrosLayout.setHorizontalGroup(
            pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_FiltrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_buscar)
                    .addGroup(pnl_FiltrosLayout.createSequentialGroup()
                        .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chk_Fecha)
                            .addComponent(chk_Usuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_FiltrosLayout.createSequentialGroup()
                                .addComponent(lbl_Desde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dc_FechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmb_Usuarios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Hasta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_FechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmb_paginado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pnl_FiltrosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chk_Fecha, chk_Usuario});

        pnl_FiltrosLayout.setVerticalGroup(
            pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_FiltrosLayout.createSequentialGroup()
                .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chk_Fecha)
                    .addComponent(lbl_Desde)
                    .addComponent(dc_FechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Hasta)
                    .addComponent(dc_FechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chk_Usuario)
                    .addComponent(cmb_Usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_FiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buscar)
                    .addComponent(cmb_paginado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_Cajas.setBorder(javax.swing.BorderFactory.createTitledBorder("Cajas"));

        tbl_Cajas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_Cajas);

        javax.swing.GroupLayout pnl_CajasLayout = new javax.swing.GroupLayout(pnl_Cajas);
        pnl_Cajas.setLayout(pnl_CajasLayout);
        pnl_CajasLayout.setHorizontalGroup(
            pnl_CajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_CajasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnl_CajasLayout.setVerticalGroup(
            pnl_CajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_CajasLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        btn_verDetalle.setForeground(java.awt.Color.blue);
        btn_verDetalle.setText("Ver Caja");
        btn_verDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verDetalleActionPerformed(evt);
            }
        });

        btn_eliminarCaja.setForeground(java.awt.Color.blue);
        btn_eliminarCaja.setText("Eliminar Caja");
        btn_eliminarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_BotonesLayout = new javax.swing.GroupLayout(pnl_Botones);
        pnl_Botones.setLayout(pnl_BotonesLayout);
        pnl_BotonesLayout.setHorizontalGroup(
            pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_BotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_verDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_eliminarCaja)
                .addGap(469, 469, 469)
                .addComponent(pb_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_BotonesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_eliminarCaja, btn_verDetalle, pb_barra});

        pnl_BotonesLayout.setVerticalGroup(
            pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_BotonesLayout.createSequentialGroup()
                .addGroup(pnl_BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminarCaja, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_verDetalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pb_barra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pnl_BotonesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_eliminarCaja, btn_verDetalle, pb_barra});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Filtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Cajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_Filtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Cajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_Botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chk_FechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_FechaItemStateChanged
        //Pregunta el estado actual del checkBox
        if (chk_Fecha.isSelected() == true) {
            dc_FechaDesde.setEnabled(true);
            dc_FechaHasta.setEnabled(true);
            lbl_Desde.setEnabled(true);
            lbl_Hasta.setEnabled(true);
            dc_FechaDesde.requestFocus();
        } else {
            dc_FechaDesde.setEnabled(false);
            dc_FechaHasta.setEnabled(false);
            lbl_Desde.setEnabled(false);
            lbl_Hasta.setEnabled(false);
        }
    }//GEN-LAST:event_chk_FechaItemStateChanged

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        this.limpiarResultados();
        this.buscar();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_verDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verDetalleActionPerformed
        if (tbl_Cajas.getSelectedRow() != -1) {
            int indiceDelModel = Utilidades.getSelectedRowModelIndice(tbl_Cajas);
            GUI_Caja caja = new GUI_Caja(this.cajas.get(indiceDelModel));
            caja.setLocationRelativeTo(this);
            caja.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una caja", "Atención", INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_verDetalleActionPerformed

    private void btn_eliminarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarCajaActionPerformed
        if (tbl_Cajas.getSelectedRow() != -1) {
            int confirmacionEliminacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la caja?", "Atención", 1);
            if (confirmacionEliminacion == JOptionPane.YES_OPTION) {
                int indiceDelModel = Utilidades.getSelectedRowModelIndice(tbl_Cajas);
                this.cajas.get(indiceDelModel).setEliminada(true);
                cajaService.actualizar(this.cajas.get(indiceDelModel));
                JOptionPane.showMessageDialog(null, "Caja Eliminada");
            }
            this.limpiarResultados();
            this.buscar();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una caja", "Atención", INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_eliminarCajaActionPerformed

    private void chk_UsuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chk_UsuarioItemStateChanged
        if (chk_Usuario.isSelected() == true) {
            cmb_Usuarios.setEnabled(true);
            for (Usuario usuario : usuarioService.getUsuarios()) {
                cmb_Usuarios.addItem(usuario);
            }
        } else {
            cmb_Usuarios.setEnabled(false);
        }
    }//GEN-LAST:event_chk_UsuarioItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_eliminarCaja;
    private javax.swing.JButton btn_verDetalle;
    private javax.swing.JCheckBox chk_Fecha;
    private javax.swing.JCheckBox chk_Usuario;
    private javax.swing.JComboBox<Usuario> cmb_Usuarios;
    private javax.swing.JComboBox<String> cmb_paginado;
    private com.toedter.calendar.JDateChooser dc_FechaDesde;
    private com.toedter.calendar.JDateChooser dc_FechaHasta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Desde;
    private javax.swing.JLabel lbl_Hasta;
    private javax.swing.JProgressBar pb_barra;
    private javax.swing.JPanel pnl_Botones;
    private javax.swing.JPanel pnl_Cajas;
    private javax.swing.JPanel pnl_Filtros;
    private javax.swing.JTable tbl_Cajas;
    // End of variables declaration//GEN-END:variables

    private void setColumnasCaja() {
        //sorting
        tbl_Cajas.setAutoCreateRowSorter(true);

        //nombres de columnas
        String[] encabezados = new String[7];
        encabezados[0] = "Fecha Apertura";
        encabezados[1] = "Fecha Cierre";
        encabezados[2] = "Usuario";
        encabezados[3] = "Estado";
        encabezados[4] = "Saldo Apertura";
        encabezados[5] = "Saldo Final";
        encabezados[6] = "Saldo Cierre";
        modeloTablaCajas.setColumnIdentifiers(encabezados);
        tbl_Cajas.setModel(modeloTablaCajas);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaCajas.getColumnCount()];
        tipos[0] = Date.class;
        tipos[1] = Date.class;
        tipos[2] = String.class;
        tipos[3] = String.class;
        tipos[4] = Double.class;
        tipos[5] = Double.class;
        tipos[6] = Double.class;
        modeloTablaCajas.setClaseColumnas(tipos);
        tbl_Cajas.getTableHeader().setReorderingAllowed(false);
        tbl_Cajas.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Cajas.setDefaultRenderer(Double.class, new RenderTabla());

        //Tamanios de columnas
        tbl_Cajas.getColumnModel().getColumn(0).setPreferredWidth(110);
        tbl_Cajas.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbl_Cajas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_Cajas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_Cajas.getColumnModel().getColumn(4).setPreferredWidth(90);
        tbl_Cajas.getColumnModel().getColumn(5).setPreferredWidth(90);
        tbl_Cajas.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    private void buscar() {
        pb_barra.setIndeterminate(true);
        SwingWorker<List<Caja>, Void> worker = new SwingWorker<List<Caja>, Void>() {
            @Override
            protected List<Caja> doInBackground() throws Exception {
                try {
                    BusquedaCajaCriteria criteria = new BusquedaCajaCriteria();
                    criteria.setBuscaPorFecha(chk_Fecha.isSelected());
                    criteria.setFechaDesde(dc_FechaDesde.getDate());
                    criteria.setFechaHasta(dc_FechaHasta.getDate());
                    criteria.setEmpresa(empresaService.getEmpresaActiva().getEmpresa());
                    criteria.setCantidadDeRegistros(Integer.parseInt(cmb_paginado.getSelectedItem().toString()));
                    criteria.setBuscaPorUsuario(chk_Usuario.isSelected());
                    criteria.setUsuario((Usuario) cmb_Usuarios.getSelectedItem());
                    List<Caja> cajasResultantes = cajaService.getCajasCriteria(criteria);
                    cajas = cajasResultantes;
                    return cajas;

                } catch (ServiceException ex) {
                    JOptionPane.showInternalMessageDialog(getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                } catch (PersistenceException ex) {
                    log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                    JOptionPane.showInternalMessageDialog(getParent(), ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
                }
                cajas = new ArrayList<>();
                return cajas;
            }

            @Override
            protected void done() {
                pb_barra.setIndeterminate(false);
                try {
                    if (get().isEmpty()) {
                        JOptionPane.showInternalMessageDialog(getParent(),
                                ResourceBundle.getBundle("Mensajes")
                                .getString("mensaje_busqueda_sin_resultados"),
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        cargarResultadosAlTable(cajas);
                    }

                } catch (InterruptedException ex) {
                    String msjError = "La tarea que se estaba realizando fue interrumpida. Intente nuevamente.";
                    log.error(msjError + " - " + ex.getMessage());
                    JOptionPane.showInternalMessageDialog(getParent(), msjError, "Error", JOptionPane.ERROR_MESSAGE);

                } catch (ExecutionException ex) {
                    String msjError = "Se produjo un error en la ejecución de la tarea solicitada. Intente nuevamente.";
                    log.error(msjError + " - " + ex.getMessage());
                    JOptionPane.showInternalMessageDialog(getParent(), msjError, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void cargarResultadosAlTable(List<Caja> cajas) {
        for (Caja caja : cajas) {
            Object[] fila = new Object[7];
            fila[0] = caja.getFechaApertura();
            if (caja.getFechaCierre() != null) {
                fila[1] = caja.getFechaCierre();
            }
            fila[2] = caja.getUsuario();
            fila[3] = (caja.isCerrada() ? "Caja Cerrada" : "Caja Abierta");
            fila[4] = caja.getSaldoInicial();
            fila[5] = (caja.isCerrada() ? caja.getSaldoFinal() : 0.0);
            fila[6] = (caja.isCerrada() ? caja.getSaldoReal() : 0.0);
            modeloTablaCajas.addRow(fila);
        }
        tbl_Cajas.setModel(modeloTablaCajas);

    }

    private void limpiarResultados() {
        modeloTablaCajas = new ModeloTabla();
        tbl_Cajas.setModel(modeloTablaCajas);
        this.setColumnasCaja();
    }
}
