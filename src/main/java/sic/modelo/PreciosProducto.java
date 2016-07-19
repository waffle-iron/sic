package sic.modelo;

import lombok.Data;

/**
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
public class PreciosProducto {

    private double precioCosto;
    private double ganancia_porcentaje;
    private double ganancia_neto;
    private double precioVentaPublico;
    private double iva_porcentaje;
    private double iva_neto;
    private double impuestoInterno_porcentaje;
    private double impuestoInterno_neto;
    private double precioLista;

}
