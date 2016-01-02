package sic.vista.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import sic.service.EstadoPedido;

public class ColoresEstadosPedidoRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable tabla,
            Object valor, boolean isSelected, boolean hasFocus,
            int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(tabla, valor, isSelected, hasFocus, row, column);
        if (valor instanceof EstadoPedido) {
            EstadoPedido estado = (EstadoPedido) valor;
            if (estado == EstadoPedido.ABIERTO) {
                cell.setBackground(Color.GREEN);
            }
            if (estado == EstadoPedido.ACTIVO) {
                cell.setBackground(Color.YELLOW);
            }
            if (estado == EstadoPedido.CERRADO) {
                cell.setBackground(Color.PINK);
            }
        }
        return cell;
    }
}
