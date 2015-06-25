package sic.modelo;

/**
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
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

    public PreciosProducto() {
    }

    public PreciosProducto(double precioCosto, double ganancia_porcentaje,
            double ganancia_neto, double precioVentaPublico,
            double iva_porcentaje, double iva_neto,
            double impuestoInterno_porcentaje,
            double impuestoInterno_neto, double precioLista) {

        this.precioCosto = precioCosto;
        this.ganancia_porcentaje = ganancia_porcentaje;
        this.ganancia_neto = ganancia_neto;
        this.precioVentaPublico = precioVentaPublico;
        this.iva_porcentaje = iva_porcentaje;
        this.iva_neto = iva_neto;
        this.impuestoInterno_porcentaje = impuestoInterno_porcentaje;
        this.impuestoInterno_neto = impuestoInterno_neto;
        this.precioLista = precioLista;
    }

    public double getGanancia_neto() {
        return ganancia_neto;
    }

    public void setGanancia_neto(double ganancia_neto) {
        this.ganancia_neto = ganancia_neto;
    }

    public double getGanancia_porcentaje() {
        return ganancia_porcentaje;
    }

    public void setGanancia_porcentaje(double ganancia_porcentaje) {
        this.ganancia_porcentaje = ganancia_porcentaje;
    }

    public double getImpuestoInterno_neto() {
        return impuestoInterno_neto;
    }

    public void setImpuestoInterno_neto(double impuestoInterno_neto) {
        this.impuestoInterno_neto = impuestoInterno_neto;
    }

    public double getImpuestoInterno_porcentaje() {
        return impuestoInterno_porcentaje;
    }

    public void setImpuestoInterno_porcentaje(double impuestoInterno_porcentaje) {
        this.impuestoInterno_porcentaje = impuestoInterno_porcentaje;
    }

    public double getIva_neto() {
        return iva_neto;
    }

    public void setIva_neto(double iva_neto) {
        this.iva_neto = iva_neto;
    }

    public double getIva_porcentaje() {
        return iva_porcentaje;
    }

    public void setIva_porcentaje(double iva_porcentaje) {
        this.iva_porcentaje = iva_porcentaje;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(double precioLista) {
        this.precioLista = precioLista;
    }

    public double getPrecioVentaPublico() {
        return precioVentaPublico;
    }

    public void setPrecioVentaPublico(double precioVentaPublico) {
        this.precioVentaPublico = precioVentaPublico;
    }
}
