package sic.service;

import sic.modelo.AfipWSAACredencial;
import sic.modelo.FacturaVenta;

public interface IAfipService {

    AfipWSAACredencial getAfipWSAACredencial(String afipNombreServicio);
    
    void autorizarFacturaVenta(AfipWSAACredencial afipCredencial, FacturaVenta factura);
    
}
