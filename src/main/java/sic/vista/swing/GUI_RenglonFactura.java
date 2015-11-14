package sic.vista.swing;

import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.service.Movimiento;
import sic.service.impl.RenglonDeFacturaServiceImpl;

public class GUI_RenglonFactura extends JDialog {

    private RenglonFactura renglon;
    private final Producto producto;
    private boolean cargarRenglon;
    private final char tipoDeFactura;
    private final Movimiento movimiento;
    private final RenglonDeFacturaServiceImpl renglonDeFacturaService = new RenglonDeFacturaServiceImpl();
    private static final Logger log = Logger.getLogger(GUI_RenglonFactura.class.getPackage().getName());

    public GUI_RenglonFactura(Producto producto, char tipoDeFactura, Movimiento movimiento) {
        this.initComponents();
        this.setIcon();
        renglon = new RenglonFactura();
        this.prepararComponentes();
        this.producto = producto;
        this.tipoDeFactura = tipoDeFactura;
        this.movimiento = movimiento;
        cargarRenglon = false;
    }

    public boolean debeCargarRenglon() {
        return cargarRenglon;
    }

    public RenglonFactura getRenglon() {
        return renglon;
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/SIC_16_square.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void prepararComponentes() {
        txt_Cantidad.setValue(new Double("1.0"));
        txt_Precio.setValue(new Double("0.0"));
        txt_Descuento_porcentaje.setValue(new Double("0.0"));
    }

    private void validarComponentes() {
        try {
            txt_Cantidad.commitEdit();
            txt_Descuento_porcentaje.commitEdit();

        } catch (ParseException ex) {
            String msjError = "Se produjo un error analizando los campos.";
            log.error(msjError + " - " + ex.getMessage());
        }
    }

    private void actualizarCampos() {
        this.validarComponentes();
        renglon = renglonDeFacturaService.calcularRenglon(tipoDeFactura, movimiento, Double.parseDouble(txt_Cantidad.getValue().toString()),
                producto, Double.parseDouble(txt_Descuento_porcentaje.getValue().toString()));
    }

    private boolean existeStockDisponible(double cantRequerida, Producto producto) {
        if (producto.isIlimitado() == false) {
            if (cantRequerida > producto.getCantidad()) {
                JOptionPane.showMessageDialog(this,
                        "La cantidad ingresada es mayor a la disponible para el producto seleccionado.\n"
                        + "La cantidad disponible es de " + producto.getCantidad() + " unidades.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        lbl_Cantidad = new javax.swing.JLabel();
        lbl_Unidad = new javax.swing.JLabel();
        lbl_Descuento = new javax.swing.JLabel();
        lbl_Descripcion = new javax.swing.JLabel();
        txt_Descripcion = new javax.swing.JTextField();
        lbl_Codigo = new javax.swing.JLabel();
        lbl_Precio = new javax.swing.JLabel();
        txt_Cantidad = new javax.swing.JFormattedTextField();
        txt_Descuento_porcentaje = new javax.swing.JFormattedTextField();
        txt_Precio = new javax.swing.JFormattedTextField();
        txt_Codigo = new javax.swing.JTextField();
        btn_Agregar = new javax.swing.JButton();
        lbl_Indicaciones = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Renglon de Factura");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Cantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Cantidad.setText("Cantidad:");

        lbl_Unidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Unidad.setText("XXXXXXXXXXXXXX");

        lbl_Descuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Descuento.setText("Descuento (%):");

        lbl_Descripcion.setText("Descripción:");

        txt_Descripcion.setEditable(false);
        txt_Descripcion.setFocusable(false);

        lbl_Codigo.setText("Código:");

        lbl_Precio.setText("XXXXXXXXXXXXX");

        txt_Cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,###,##0.00"))));
        txt_Cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_CantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_CantidadFocusLost(evt);
            }
        });
        txt_Cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CantidadActionPerformed(evt);
            }
        });

        txt_Descuento_porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,###,##0.00"))));
        txt_Descuento_porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Descuento_porcentajeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Descuento_porcentajeFocusLost(evt);
            }
        });
        txt_Descuento_porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Descuento_porcentajeActionPerformed(evt);
            }
        });

        txt_Precio.setEditable(false);
        txt_Precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$##,###,##0.00"))));
        txt_Precio.setFocusable(false);

        txt_Codigo.setEditable(false);
        txt_Codigo.setFocusable(false);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Precio)
                    .addComponent(lbl_Codigo)
                    .addComponent(lbl_Descripcion)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_Cantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_Descuento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Unidad, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                    .addComponent(txt_Descuento_porcentaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(txt_Precio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(txt_Codigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Codigo)
                    .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Descripcion)
                    .addComponent(txt_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl_Cantidad)
                    .addComponent(lbl_Unidad)
                    .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Precio)
                    .addComponent(txt_Precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Descuento)
                    .addComponent(txt_Descuento_porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Agregar.setForeground(java.awt.Color.blue);
        btn_Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/ArrowRight_16x16.png"))); // NOI18N
        btn_Agregar.setText("Agregar");
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });

        lbl_Indicaciones.setText("<html>Ingrese los datos para el renglon de la Factura:</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Indicaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Agregar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Indicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Agregar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        if (renglon.getCantidad() > 0) {
            if (movimiento == Movimiento.VENTA) {
                if (this.existeStockDisponible(renglon.getCantidad(), producto)) {
                    cargarRenglon = true;
                    this.dispose();
                }
            } else {
                cargarRenglon = true;
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "La cantidad ingresada debe ser mayor a 0.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void txt_CantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CantidadActionPerformed
        this.actualizarCampos();
    }//GEN-LAST:event_txt_CantidadActionPerformed

    private void txt_Descuento_porcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Descuento_porcentajeActionPerformed
        this.actualizarCampos();
    }//GEN-LAST:event_txt_Descuento_porcentajeActionPerformed

    private void txt_CantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_CantidadFocusLost
        this.actualizarCampos();
    }//GEN-LAST:event_txt_CantidadFocusLost

    private void txt_Descuento_porcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Descuento_porcentajeFocusLost
        this.actualizarCampos();
    }//GEN-LAST:event_txt_Descuento_porcentajeFocusLost

    private void txt_CantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_CantidadFocusGained
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txt_Cantidad.selectAll();
            }
        });
    }//GEN-LAST:event_txt_CantidadFocusGained

    private void txt_Descuento_porcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Descuento_porcentajeFocusGained
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txt_Descuento_porcentaje.selectAll();
            }
        });
    }//GEN-LAST:event_txt_Descuento_porcentajeFocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txt_Codigo.setText(String.valueOf(producto.getCodigo()));
        txt_Descripcion.setText(producto.getDescripcion());
        txt_Cantidad.setValue(txt_Cantidad.getValue());
        lbl_Unidad.setText(producto.getMedida().getNombre());
        if (movimiento == Movimiento.COMPRA) {
            lbl_Precio.setText("Precio de Costo:");
            txt_Precio.setValue(producto.getPrecioCosto());
        } else {
            lbl_Precio.setText("Precio de Lista:");
            txt_Precio.setValue(producto.getPrecioLista());
        }
        this.actualizarCampos();
    }//GEN-LAST:event_formWindowOpened
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JLabel lbl_Cantidad;
    private javax.swing.JLabel lbl_Codigo;
    private javax.swing.JLabel lbl_Descripcion;
    private javax.swing.JLabel lbl_Descuento;
    private javax.swing.JLabel lbl_Indicaciones;
    private javax.swing.JLabel lbl_Precio;
    private javax.swing.JLabel lbl_Unidad;
    private javax.swing.JPanel panel1;
    private javax.swing.JFormattedTextField txt_Cantidad;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Descripcion;
    private javax.swing.JFormattedTextField txt_Descuento_porcentaje;
    private javax.swing.JFormattedTextField txt_Precio;
    // End of variables declaration//GEN-END:variables
}
