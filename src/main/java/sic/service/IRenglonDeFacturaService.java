package sic.service;

import sic.modelo.Producto;
import sic.modelo.RenglonFactura;

public interface IRenglonDeFacturaService {

    RenglonFactura calcularRenglon(char tipoDeFactura, Movimiento movimiento, double cantidad, Producto producto, double descuento_porcentaje);

}
