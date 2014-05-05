package sic.modelo;

import java.util.Date;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
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

    public BusquedaFacturaCompraCriteria() {
    }

    public BusquedaFacturaCompraCriteria(boolean buscaPorFecha, Date fechaDesde, Date fechaHasta, boolean buscaPorProveedor, Proveedor proveedor, boolean buscaPorNumeroFactura, int numSerie, int numFactura, boolean buscarSoloInpagas, Empresa empresa, int cantRegistros) {
        this.buscaPorFecha = buscaPorFecha;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.buscaPorProveedor = buscaPorProveedor;
        this.proveedor = proveedor;
        this.buscaPorNumeroFactura = buscaPorNumeroFactura;
        this.numSerie = numSerie;
        this.numFactura = numFactura;
        this.buscarSoloInpagas = buscarSoloInpagas;
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

    public boolean isBuscaPorProveedor() {
        return buscaPorProveedor;
    }

    public void setBuscaPorProveedor(boolean buscaPorProveedor) {
        this.buscaPorProveedor = buscaPorProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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

    public boolean isBuscarSoloInpagas() {
        return buscarSoloInpagas;
    }

    public void setBuscarSoloInpagas(boolean buscarSoloInpagas) {
        this.buscarSoloInpagas = buscarSoloInpagas;
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
