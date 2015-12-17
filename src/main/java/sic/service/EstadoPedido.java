package sic.service;

/**
 * Describe los distintos estados de un Pedido.
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
