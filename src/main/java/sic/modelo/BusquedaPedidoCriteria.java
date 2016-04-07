package sic.modelo;

import java.util.Date;
import lombok.Data;

@Data
public class BusquedaPedidoCriteria {

    private boolean buscaPorFecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean buscaCliente;
    private Cliente cliente;
    private boolean buscaUsuario;
    private Usuario usuario;
    private boolean buscaPorNroPedido;
    private long nroPedido;
    private Empresa empresa;
    private int cantRegistros;    
}
