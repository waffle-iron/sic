package sic.modelo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaFacturaCompraCriteria {

    private boolean buscaPorFecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean buscaPorProveedor;
    private Proveedor proveedor;
    private boolean buscaPorNumeroFactura;
    private int numSerie;
    private int numFactura;
    private boolean buscarSoloInpagas;
    private Empresa empresa;
    private int cantRegistros;

}
