package sic.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;
import sic.service.IProductoService;
import sic.service.IRenglonDePedidoService;

@Service
public class RenglonDePedidoServiceImpl implements IRenglonDePedidoService {

    private final IProductoService productoService;

    @Autowired
    RenglonDePedidoServiceImpl(IProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
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

    @Override
    public List<RenglonPedido> convertirRenglonesFacturaARenglonesPedido(List<RenglonFactura> renglonesDeFactura, Pedido pedido) {
        List<RenglonPedido> renglonesPedido = new ArrayList();
        for (RenglonFactura renglonFactura : renglonesDeFactura) {
            renglonesPedido.add(this.convertirRenglonFacturaARenglonPedido(renglonFactura, pedido));
        }
        return renglonesPedido;
    }
}
