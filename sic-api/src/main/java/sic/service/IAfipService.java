package sic.service;

import sic.modelo.AfipWSAACredencial;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;

public interface IAfipService {

    AfipWSAACredencial getAfipWSAACredencial(String afipNombreServicio, Empresa empresa);
    
    void autorizarFacturaVenta(FacturaVenta factura);
    
}
