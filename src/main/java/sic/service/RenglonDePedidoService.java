package sic.service;

import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public class RenglonDePedidoService {

    private final ProductoService productoService = new ProductoService();

    public RenglonPedido convertirRenglonFacturaARenglonPedido(RenglonFactura renglonFactura, Pedido pedido) {
        RenglonPedido nuevoRenglon = new RenglonPedido();
        nuevoRenglon.setCantidad(renglonFactura.getCantidad());
        nuevoRenglon.setPedido(pedido);
        nuevoRenglon.setDescuento_porcentaje(renglonFactura.getDescuento_porcentaje());
        nuevoRenglon.setDescuento_neto(renglonFactura.getDescuento_neto());
        Producto producto = productoService.getProductoPorId(renglonFactura.getId_ProductoItem());
        nuevoRenglon.setProducto(producto);
        nuevoRenglon.setSubTotal(renglonFactura.getImporte());
        return nuevoRenglon;
    }

}
