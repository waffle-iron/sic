package sic.modelo;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
public class ResultadosFactura {

    private double subTotal;
    private double recargo_porcentaje;
    private double recargo_neto;
    private double descuento_porcentaje;
    private double descuento_neto;
    private double subTotal_neto;
    private double iva_105_neto;
    private double iva_21_neto;
    private double impuestoInterno_neto;
    private double total;

    public ResultadosFactura() {
    }

    public ResultadosFactura(double subTotal, double recargo_porcentaje, double recargo_neto, double descuento_porcentaje, double descuento_neto, double subTotal_neto, double iva_105_neto, double iva_21_neto, double impuestoInterno_neto, double total) {
        this.subTotal = subTotal;
        this.recargo_porcentaje = recargo_porcentaje;
        this.recargo_neto = recargo_neto;
        this.descuento_porcentaje = descuento_porcentaje;
        this.descuento_neto = descuento_neto;
        this.subTotal_neto = subTotal_neto;
        this.iva_105_neto = iva_105_neto;
        this.iva_21_neto = iva_21_neto;
        this.impuestoInterno_neto = impuestoInterno_neto;
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getRecargo_porcentaje() {
        return recargo_porcentaje;
    }

    public void setRecargo_porcentaje(double recargo_porcentaje) {
        this.recargo_porcentaje = recargo_porcentaje;
    }

    public double getRecargo_neto() {
        return recargo_neto;
    }

    public void setRecargo_neto(double recargo_neto) {
        this.recargo_neto = recargo_neto;
    }

    public double getDescuento_porcentaje() {
        return descuento_porcentaje;
    }

    public void setDescuento_porcentaje(double descuento_porcentaje) {
        this.descuento_porcentaje = descuento_porcentaje;
    }

    public double getDescuento_neto() {
        return descuento_neto;
    }

    public void setDescuento_neto(double descuento_neto) {
        this.descuento_neto = descuento_neto;
    }

    public double getSubTotal_neto() {
        return subTotal_neto;
    }

    public void setSubTotal_neto(double subTotal_neto) {
        this.subTotal_neto = subTotal_neto;
    }

    public double getIva_105_neto() {
        return iva_105_neto;
    }

    public void setIva_105_neto(double iva_105_neto) {
        this.iva_105_neto = iva_105_neto;
    }

    public double getIva_21_neto() {
        return iva_21_neto;
    }

    public void setIva_21_neto(double iva_21_neto) {
        this.iva_21_neto = iva_21_neto;
    }

    public double getImpuestoInterno_neto() {
        return impuestoInterno_neto;
    }

    public void setImpuestoInterno_neto(double impuestoInterno_neto) {
        this.impuestoInterno_neto = impuestoInterno_neto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}