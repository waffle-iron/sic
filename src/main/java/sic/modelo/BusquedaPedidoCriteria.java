package sic.modelo;

import java.util.Date;

public class BusquedaPedidoCriteria {

    private boolean buscaPorFecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean buscaCliente;
    private Cliente cliente;
    private boolean buscaUsuario;
    private Usuario usuario;
    private boolean buscaPorNumeroPedido;
    private long numPedido;
    private Empresa empresa;
    private int cantRegistros;

    public BusquedaPedidoCriteria() {
    }

    public BusquedaPedidoCriteria(boolean buscaPorFecha, Date fechaDesde, Date fechaHasta,
            boolean buscaCliente, Cliente cliente, boolean buscaUsuario, Usuario usuario,
            boolean buscaPorNumeroPedido, long numPedido,
            Empresa empresa, int cantRegistros) {

        this.buscaPorFecha = buscaPorFecha;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.buscaCliente = buscaCliente;
        this.cliente = cliente;
        this.buscaUsuario = buscaUsuario;
        this.usuario = usuario;
        this.buscaPorNumeroPedido = buscaPorNumeroPedido;
        this.numPedido = numPedido;
        this.empresa = empresa;
        this.cantRegistros = cantRegistros;
    }

    public boolean isBuscaPorFecha() {
        return buscaPorFecha;
    }

    public void setBuscaPorFecha(boolean buscaPorFecha) {
        this.buscaPorFecha = buscaPorFecha;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
    public Long getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(long numPedido) {
        this.numPedido = numPedido;
    }

    public boolean isBuscaCliente() {
        return buscaCliente;
    }

    public void setBuscaCliente(boolean buscaCliente) {
        this.buscaCliente = buscaCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isBuscaUsuario() {
        return buscaUsuario;
    }

    public void setBuscaUsuario(boolean buscaUsuario) {
        this.buscaUsuario = buscaUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isBuscaPorNumeroPedido() {
        return buscaPorNumeroPedido;
    }

    public void setBuscaPorNumeroPedido(boolean buscaPorNumeroPedido) {
        this.buscaPorNumeroPedido = buscaPorNumeroPedido;
    }

    public long getNumIdPedido() {
        return numPedido;
    }

    public void setNumIdPedido(long numIdPedido) {
        this.numPedido = numIdPedido;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }
}
