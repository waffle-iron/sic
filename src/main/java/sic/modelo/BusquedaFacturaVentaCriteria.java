package sic.modelo;

import java.util.Date;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
public class BusquedaFacturaVentaCriteria {

    private boolean buscaPorFecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean buscaCliente;
    private Cliente cliente;
    private boolean buscaPorTipoFactura;
    private char tipoFactura;
    private boolean buscaUsuario;
    private Usuario usuario;
    private boolean buscaPorNumeroFactura;
    private int numSerie;
    private int numFactura;
    private boolean buscaSoloInpagas;
    private Empresa empresa;
    private int cantRegistros;
    private boolean buscarPorPedido;
    private Pedido pedido;

    public BusquedaFacturaVentaCriteria() {
    }

    public BusquedaFacturaVentaCriteria(boolean buscaPorFecha, Date fechaDesde, Date fechaHasta,
            boolean buscaCliente, Cliente cliente, boolean buscaPorTipoFactura, char tipoFactura,
            boolean buscaUsuario, Usuario usuario, boolean buscaPorNumeroFactura, int numSerie, int numFactura,
            boolean buscaSoloInpagas, Empresa empresa, int cantRegistros, Pedido pedido) {

        this.buscaPorFecha = buscaPorFecha;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.buscaCliente = buscaCliente;
        this.cliente = cliente;
        this.buscaPorTipoFactura = buscaPorTipoFactura;
        this.tipoFactura = tipoFactura;
        this.buscaUsuario = buscaUsuario;
        this.usuario = usuario;
        this.buscaPorNumeroFactura = buscaPorNumeroFactura;
        this.numSerie = numSerie;
        this.numFactura = numFactura;
        this.buscaSoloInpagas = buscaSoloInpagas;
        this.empresa = empresa;
        this.cantRegistros = cantRegistros;
        this.pedido = pedido;
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

    public boolean isBuscaPorTipoFactura() {
        return buscaPorTipoFactura;
    }

    public void setBuscaPorTipoFactura(boolean buscaPorTipoFactura) {
        this.buscaPorTipoFactura = buscaPorTipoFactura;
    }

    public char getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(char tipoFactura) {
        this.tipoFactura = tipoFactura;
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

    public boolean isBuscaPorNumeroFactura() {
        return buscaPorNumeroFactura;
    }

    public void setBuscaPorNumeroFactura(boolean buscaPorNumeroFactura) {
        this.buscaPorNumeroFactura = buscaPorNumeroFactura;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public boolean isBuscaSoloInpagas() {
        return buscaSoloInpagas;
    }

    public void setBuscaSoloInpagas(boolean buscaSoloInpagas) {
        this.buscaSoloInpagas = buscaSoloInpagas;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public boolean isBuscarPorPedido() {
        return buscarPorPedido;
    }

    public void setBuscarPorPedido(boolean buscarPorPedido) {
        this.buscarPorPedido = buscarPorPedido;
    }
}
