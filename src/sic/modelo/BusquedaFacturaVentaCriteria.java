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
    private boolean buscaUsuario;
    private Usuario usuario;
    private boolean buscaPorNumeroFactura;
    private int numSerie;
    private int numFactura;
    private boolean buscaSoloInpagas;
    private Empresa empresa;
    private int cantRegistros;

    public BusquedaFacturaVentaCriteria() {
    }

    public BusquedaFacturaVentaCriteria(boolean buscaPorFecha, Date fechaDesde, Date fechaHasta, boolean buscaCliente, Cliente cliente, boolean buscaUsuario, Usuario usuario, boolean buscaPorNumeroFactura, int numSerie, int numFactura, boolean buscaSoloInpagas, Empresa empresa, int cantRegistros) {
        this.buscaPorFecha = buscaPorFecha;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.buscaCliente = buscaCliente;
        this.cliente = cliente;
        this.buscaUsuario = buscaUsuario;
        this.usuario = usuario;
        this.buscaPorNumeroFactura = buscaPorNumeroFactura;
        this.numSerie = numSerie;
        this.numFactura = numFactura;
        this.buscaSoloInpagas = buscaSoloInpagas;
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
}
