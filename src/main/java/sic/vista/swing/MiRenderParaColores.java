package sic.vista.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import sic.service.EstadoPedido;

public class MiRenderParaColores extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable tabla,
            Object valor, boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(tabla, valor, isSelected, hasFocus, row, column);
        int iteraciones = tabla.getRowCount();
        for (int i = 0; i < iteraciones; i++) {
            if (valor instanceof EstadoPedido) {
                EstadoPedido estado = (EstadoPedido) valor;
                if (estado == EstadoPedido.CERRADO) {
                    cell.setBackground(Color.PINK);
                }
                if (estado == EstadoPedido.ENPROCESO) {
                    cell.setBackground(Color.YELLOW);
                }
                if (estado == EstadoPedido.INICIADO) {
                    cell.setBackground(Color.GREEN);
                }
            }
            if (valor instanceof String) {
                cell.setBackground(Color.WHITE);
            }
        }
        return cell;
    }
}
