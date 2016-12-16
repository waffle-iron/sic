package sic.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.RenglonFactura;
import sic.service.BusinessServiceException;

public class RenglonFacturaBuilder {
    
    private long id_RenglonFactura = 0L;
    private long id_ProductoItem =0L;
    private String codigoItem = "aca.va.el.c0d1g0";
    private String descripcionItem = "Ventiladores de pie";
    private String medidaItem = "UNIDAD";
    private double cantidad = 10;
    private double precioUnitario = 1300;
    private double descuento_porcentaje = 0.0;
    private double descuento_neto = 0.0;
    private double iva_porcentaje = 21.00;
    private double iva_neto = 273;
    private double impuesto_porcentaje = 0.0;
    private double impuesto_neto = 0;
    private double ganancia_porcentaje = 10;
    private double ganancia_neto = 140;
    private double importe = 1713;
    
    public RenglonFactura build() {
        return new RenglonFactura(id_RenglonFactura, id_ProductoItem, codigoItem, 
                descripcionItem, medidaItem, cantidad, precioUnitario, descuento_porcentaje, 
                descuento_neto, iva_porcentaje, iva_neto, impuesto_porcentaje, impuesto_neto,
                ganancia_porcentaje, ganancia_neto, importe);
    }
    
    public RenglonFacturaBuilder withId_Transportista(long idRenglonFactura) {
        this.id_RenglonFactura = idRenglonFactura;
        return this;
    }
    
    public RenglonFacturaBuilder withId_ProductoItem(long idProductoItem) {
        this.id_ProductoItem = idProductoItem;
        return this;
    }
    
    public RenglonFacturaBuilder withCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
        return this;
    }
    
    public RenglonFacturaBuilder withDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
        return this;
    }
    
    public RenglonFacturaBuilder withMedidaItem(String medidaItem) {
        this.medidaItem = medidaItem;
        return this;
    }
    
    public RenglonFacturaBuilder withCantidad(double cantidad) {
        this.cantidad = cantidad;
        return this;
    }
    
    public RenglonFacturaBuilder withPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }
    
    public RenglonFacturaBuilder withDescuentoPorcentaje(double descuentoPorcentaje) {
        this.descuento_porcentaje = descuentoPorcentaje;
        return this;
    }
    
    public RenglonFacturaBuilder withDescuentoNeto(double descuentoNeto) {
        this.descuento_neto = descuentoNeto;
        return this;
    }
    
    public RenglonFacturaBuilder withIVAPorcentaje(double ivaPorcentaje) {
        this.iva_porcentaje = ivaPorcentaje;
        return this;
    }
    
    public RenglonFacturaBuilder withIVAneto(double ivaNeto) {
        this.iva_neto = ivaNeto;
        return this;
    }
    
    public RenglonFacturaBuilder withImpuestoPorcentaje(double impuestoPorcentaje) {
        this.impuesto_porcentaje = impuestoPorcentaje;
        return this;
    }
    
    public RenglonFacturaBuilder withImpuestoNeto(double impuestoNeto) {
        this.impuesto_neto = impuestoNeto;
        return this;
    }
    
    public RenglonFacturaBuilder withGananciaPorcentaje(double gananciaPorcentaje) {
        this.ganancia_porcentaje = gananciaPorcentaje;
        return this;
    }
    
    public RenglonFacturaBuilder withGananciaNeto(double gananciaNeto) {
        this.ganancia_neto = gananciaNeto;
        return this;
    }
    
    public RenglonFacturaBuilder withImporte(double importe) {
        this.importe = importe;
        return this;
    }
    
    public List<RenglonFactura> listWithCantidadRenglonesYCantidadesEnUnidades(int cantRenglones, double[] cantUnidades) {
        List<RenglonFactura> renglones = new ArrayList<>();
        if (cantRenglones == cantUnidades.length) {
            for (int i = 0; i < cantRenglones; i++) {
                renglones.add(this.withCantidad(cantUnidades[i]).build());
            }
        } else {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_renglones_cantidades_no_coincidentes"));
        }
        return renglones;
    }
}
