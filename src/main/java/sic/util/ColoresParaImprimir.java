package sic.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ColoresParaImprimir extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable tabla,
            Object celda, boolean isSelected, boolean hasFocus,
            int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(tabla, celda, isSelected, hasFocus, row, column);
        this.setHorizontalAlignment(SwingConstants.LEADING);
        if (cell.getText().length() > 0) {
            cell.setBackground(Color.WHITE);
            cell.setFont(new Font("Font", Font.BOLD, 14));
        } else {
            cell.setBackground(Color.gray);
        }
        return cell;
    }
}
