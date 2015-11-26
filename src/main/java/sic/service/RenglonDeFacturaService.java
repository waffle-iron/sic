package sic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sic.modelo.Pedido;
import sic.modelo.Producto;
import sic.modelo.RenglonFactura;
import sic.modelo.RenglonPedido;

public class RenglonDeFacturaService {
    
    private final PedidoService pedidoService = new PedidoService();

    public RenglonFactura calcularRenglon(char tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje) {
        RenglonFactura nuevoRenglon = new RenglonFactura();
        nuevoRenglon.setId_ProductoItem(producto.getId_Producto());
        nuevoRenglon.setCodigoItem(producto.getCodigo());
        nuevoRenglon.setDescripcionItem(producto.getDescripcion());
        nuevoRenglon.setMedidaItem(producto.getMedida().getNombre());
        nuevoRenglon.setCantidad(cantidad);
        nuevoRenglon.setPrecioUnitario(this.calcularPrecioUnitario(movimiento, tipoDeFactura, producto));
        nuevoRenglon.setDescuento_porcentaje(descuento_porcentaje);
        nuevoRenglon.setDescuento_neto(this.calcularDescuento_neto(nuevoRenglon.getPrecioUnitario(), descuento_porcentaje));
        nuevoRenglon.setIva_porcentaje(producto.getIva_porcentaje());
        if (tipoDeFactura == 'Y') {
            nuevoRenglon.setIva_porcentaje(producto.getIva_porcentaje() / 2);
        }
        nuevoRenglon.setIva_neto(this.calcularIVA_neto(movimiento, producto, nuevoRenglon.getDescuento_neto()));
        nuevoRenglon.setImpuesto_porcentaje(producto.getImpuestoInterno_porcentaje());
        nuevoRenglon.setImpuesto_neto(this.calcularImpInterno_neto(movimiento, producto, nuevoRenglon.getDescuento_neto()));
        nuevoRenglon.setGanancia_porcentaje(producto.getGanancia_porcentaje());
        nuevoRenglon.setGanancia_neto(producto.getGanancia_neto());
        nuevoRenglon.setImporte(this.calcularImporte(cantidad, nuevoRenglon.getPrecioUnitario(), nuevoRenglon.getDescuento_neto()));
        return nuevoRenglon;
    }

    private double calcularDescuento_neto(double precioUnitario, double descuento_porcentaje) {
        double resultado;
        resultado = (precioUnitario * descuento_porcentaje) / 100;
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    private double calcularIVA_neto(Movimiento movimiento, Producto producto, double descuento_neto) {
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            resultado = ((producto.getPrecioCosto() - descuento_neto) * producto.getIva_porcentaje()) / 100;
        }

        if (movimiento == Movimiento.VENTA) {
            resultado = ((producto.getPrecioVentaPublico() - descuento_neto) * producto.getIva_porcentaje()) / 100;
        }
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    private double calcularImpInterno_neto(Movimiento movimiento, Producto producto, double descuento_neto) {
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            resultado = ((producto.getPrecioCosto() - descuento_neto) * producto.getImpuestoInterno_porcentaje()) / 100;
        }

        if (movimiento == Movimiento.VENTA) {
            resultado = ((producto.getPrecioVentaPublico() - descuento_neto) * producto.getImpuestoInterno_porcentaje()) / 100;
        }
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    private double calcularPrecioUnitario(Movimiento movimiento, char tipoDeFactura, Producto producto) {
        double iva_resultado;
        double impInterno_resultado;
        double resultado = 0;

        if (movimiento == Movimiento.COMPRA) {
            if (tipoDeFactura == 'A' || tipoDeFactura == 'X') {
                resultado = producto.getPrecioCosto();
            } else {
                iva_resultado = (producto.getPrecioCosto() * producto.getIva_porcentaje()) / 100;
                impInterno_resultado = (producto.getPrecioCosto() * producto.getImpuestoInterno_porcentaje()) / 100;
                resultado = producto.getPrecioCosto() + iva_resultado + impInterno_resultado;
            }
        }

        if (movimiento == Movimiento.VENTA) {
            if (tipoDeFactura == 'A' || tipoDeFactura == 'X') {
                resultado = producto.getPrecioVentaPublico();
            } else {
                if (tipoDeFactura == 'Y') {
                    iva_resultado = (producto.getPrecioVentaPublico() * producto.getIva_porcentaje() / 2) / 100;
                    impInterno_resultado = (producto.getPrecioVentaPublico() * producto.getImpuestoInterno_porcentaje()) / 100;
                    resultado = producto.getPrecioVentaPublico() + iva_resultado + impInterno_resultado;
                } else {
                    resultado = producto.getPrecioLista();
                }
            }
        }
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    private double calcularImporte(double cantidad, double precioUnitario, double descuento_neto) {
        double resultado = (precioUnitario - descuento_neto) * cantidad;
        return Math.round(resultado * 1000.0) / 1000.0;
    }

    public RenglonFactura getRenglonFacturaPorRenglonPedido(RenglonPedido renglon) {
        return this.calcularRenglon('P', Movimiento.PEDIDO, renglon.getCantidad(), renglon.getProducto(), 0.0);
    }

    public List<RenglonFactura> getRenglonesDePedidoComoRenglonesFactura(List<RenglonPedido> renglonPedido) {
        List<RenglonFactura> renglonesDeFactura = new ArrayList<>();
        for (RenglonPedido renglon : renglonPedido) {
            renglonesDeFactura.add(this.getRenglonFacturaPorRenglonPedido(renglon));
        }
        return renglonesDeFactura;
    }
    
     public List<RenglonFactura> getRenglonesParaFacturarDelPedido(Pedido pedido) {
        List<RenglonFactura> renglonesDelPedidoParaFacturar = new ArrayList<>();
        HashMap<Long, RenglonFactura> renglonesDeFacturas = pedidoService.getRenglonesUnificadosPorIdProducto(pedido.getNroPedido());
        List<RenglonPedido> renglonesDelPedido = pedidoService.getRenglonesDelPedido(pedido.getId_Pedido());
        for (RenglonPedido renglon : renglonesDelPedido) {
            if (renglonesDeFacturas.containsKey(renglon.getProducto().getId_Producto())) {
                if (renglon.getCantidad() > renglonesDeFacturas.get(renglon.getProducto().getId_Producto()).getCantidad()) {
                    renglon.setCantidad(renglon.getCantidad() - renglonesDeFacturas.get(renglon.getProducto().getId_Producto()).getCantidad());
                    renglonesDelPedidoParaFacturar.add(this.getRenglonFacturaPorRenglonPedido(renglon));
                }
            } else {
                renglonesDelPedidoParaFacturar.add(this.getRenglonFacturaPorRenglonPedido(renglon));
            }
        }
        return renglonesDelPedidoParaFacturar;

    }
}
