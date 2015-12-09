package sic.service;

/**
 * Describe las distintas operaciones comerciales que se pueden realizar.
 */
public enum EstadoPedido {

    /**
     * Para el caso en que el pedido no haya pasado por ningún proceso de facturación
     */
    INICIADO,
    /**
     * Para el caso en que el pedido fue facturado parcialmente.
     */
    ENPROCESO,
    /**
     * Para el caso en que el pedido fue facturado en su totalidad.
     */
    CERRADO;
}
