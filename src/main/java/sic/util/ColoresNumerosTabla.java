package sic.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ColoresNumerosTabla extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable tabla,
            Object valor, boolean isSelected, boolean hasFocus,
            int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(tabla, valor, isSelected, hasFocus, row, column);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        if (valor instanceof Double) {
            Double numero = (Double) valor;
            cell.setText(Utilidades.formatoNumeros(numero));
            if (numero > 0) {
                cell.setForeground(Color.GREEN);
                cell.setFont(new Font("Font", Font.BOLD, 12));
            }
            if (numero <= 0) {
                cell.setForeground(Color.RED);
                cell.setFont(new Font("Font", Font.BOLD, 12));
            }
        }
        return cell;
    }

}
