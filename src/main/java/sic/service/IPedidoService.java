package sic.service;

import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Factura;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public interface IPedidoService {

    void actualizar(Pedido pedido);

    List<Pedido> buscarConCriteria(BusquedaPedidoCriteria criteria);

    long calcularNumeroPedido();

    Pedido calcularTotalActualDePedido(Pedido pedido);

    boolean eliminar(Pedido pedido);

    List<Factura> getFacturasDelPedido(long nroPedido);

    Pedido getPedidoPorNumero(long nroPedido, long idEmpresa);

    Pedido getPedidoPorNumeroConFacturas(long nroPedido);

    Pedido getPedidoPorNumeroConRenglones(long nroPedido, long idEmpresa);

    Pedido getPedidoPorNumeroConRenglonesActualizandoSubtotales(long nroPedido, long idEmpresa);

    HashMap<Long, RenglonFactura> getRenglonesDeFacturasUnificadosPorNroPedido(long nroPedido);

    List<RenglonPedido> getRenglonesDelPedido(long nroPedido);

    JasperPrint getReportePedido(Pedido pedido) throws JRException;

    void guardar(Pedido pedido);

    public RenglonPedido convertirRenglonFacturaARenglonPedido(RenglonFactura renglonFactura, Pedido pedido);

    public List<RenglonPedido> convertirRenglonesFacturaARenglonesPedido(List<RenglonFactura> renglonesDeFactura, Pedido pedido);

}