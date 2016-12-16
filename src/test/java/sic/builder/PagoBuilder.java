package sic.builder;

import sic.modelo.FormaDePago;

public class PagoBuilder {

    private Long id_Pago = 0L;   
    private long nroPago = 1L;
    private FormaDePago formaDePago = new FormaDePagoBuilder().build();
    private Factura factura;
    private double monto;
    private Date fecha;
    private String nota;
    private Empresa empresa;
    private boolean eliminado; 
    
}
