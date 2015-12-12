package sic.vista.swing;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import sic.modelo.RenglonFactura;
import sic.service.ProductoService;
import sic.util.RenderTabla;

public class GUI_MensajeProductosFaltantes extends JDialog {

    private ModeloTabla modeloTablaFaltantes;
    private final List<RenglonFactura> faltantes;
    private final ProductoService productoService = new ProductoService();

    public GUI_MensajeProductosFaltantes(List<RenglonFactura> faltantes) {
        initComponents();
        this.faltantes = faltantes;
        modeloTablaFaltantes = new ModeloTabla();
        this.setColumnas();
        this.cargarResultadosAlTable();
        ImageIcon iconoVentana = new ImageIcon(GUI_PuntoDeVenta.class.getResource("/sic/icons/SIC_24_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void setColumnas() {
        //nombres de columnas
        String[] encabezados = new String[4];
        encabezados[0] = "Codigo";
        encabezados[1] = "Descripcion";
        encabezados[2] = "Cantidad Requerida";
        encabezados[3] = "Cantidad Disponible";
        modeloTablaFaltantes.setColumnIdentifiers(encabezados);
        tbl_Faltantes.setModel(modeloTablaFaltantes);

        //tipo de dato columnas
        Class[] tipos = new Class[modeloTablaFaltantes.getColumnCount()];
        tipos[0] = String.class;
        tipos[1] = String.class;
        tipos[2] = String.class;
        tipos[3] = String.class;
        modeloTablaFaltantes.setClaseColumnas(tipos);
        tbl_Faltantes.getTableHeader().setReorderingAllowed(false);
        tbl_Faltantes.getTableHeader().setResizingAllowed(true);

        //render para los tipos de datos
        tbl_Faltantes.setDefaultRenderer(Double.class, new RenderTabla());

        //tamanios de columnas
        tbl_Faltantes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbl_Faltantes.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbl_Faltantes.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_Faltantes.getColumnModel().getColumn(3).setPreferredWidth(50);
    }

    private void limpiarJTables() {
        modeloTablaFaltantes = new ModeloTabla();
        tbl_Faltantes.setModel(modeloTablaFaltantes);;
        this.setColumnas();
    }

    private void cargarResultadosAlTable() {
        this.limpiarJTables();
        for (RenglonFactura renglonProductoFaltante : faltantes) {
            Object[] fila = new Object[4];
            fila[0] = renglonProductoFaltante.getCodigoItem();
            fila[1] = renglonProductoFaltante.getDescripcionItem();
            fila[2] = renglonProductoFaltante.getCantidad();
            fila[3] = productoService.getProductoPorId(renglonProductoFaltante.getId_ProductoItem()).getCantidad();
            modeloTablaFaltantes.addRow(fila);
        }
        tbl_Faltantes.setModel(modeloTablaFaltantes);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLista = new javax.swing.JPanel();
        btn_volver = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Faltantes = new javax.swing.JTable();
        panelEncabezado = new javax.swing.JPanel();
        lbl_faltantes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista Faltantes");
        setIconImage(null);

        panelLista.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLista.setToolTipText("Lista");
        panelLista.setName("Lista"); // NOI18N

        btn_volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/volver_16x16.png"))); // NOI18N
        btn_volver.setText("VOLVER");
        btn_volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_volverActionPerformed(evt);
            }
        });

        tbl_Faltantes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_Faltantes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelListaLayout = new javax.swing.GroupLayout(panelLista);
        panelLista.setLayout(panelListaLayout);
        panelListaLayout.setHorizontalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelListaLayout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(btn_volver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelListaLayout.setVerticalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_volver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEncabezado.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_faltantes.setText("Los siguientes productos no poseen stock suficiente para continuar con la operación:");

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbl_faltantes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEncabezadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_faltantes))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_volverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_volverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_volver;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_faltantes;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelLista;
    private javax.swing.JTable tbl_Faltantes;
    // End of variables declaration//GEN-END:variables
}
