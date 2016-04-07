package sic.service;

import java.util.List;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public interface IRenglonDeFacturaService {

    RenglonFactura calcularRenglon(String tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje);

    List<RenglonFactura> convertirRenglonesPedidoARenglonesFactura(Pedido pedido, String tipoComprobante);

    RenglonFactura getRenglonFacturaPorRenglonPedido(RenglonPedido renglon, String tipoComprobante);

}
