package sic.service;

import java.util.List;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public interface IRenglonDePedidoService {

    RenglonPedido convertirRenglonFacturaARenglonPedido(RenglonFactura renglonFactura, Pedido pedido);

    List<RenglonPedido> convertirRenglonesFacturaARenglonesPedido(List<RenglonFactura> renglonesDeFactura, Pedido pedido);

}
