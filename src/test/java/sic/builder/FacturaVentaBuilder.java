package sic.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;
import sic.modelo.Pago;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.modelo.Usuario;

public class FacturaVentaBuilder {
    
    private long id_Factura = 0L;
    private Date fecha = new Date();
    private char tipoFactura = 'A';
    private long numSerie = 0;
    private long numFactura = 1;
    private Date fechaVencimiento = new Date();    
    private Pedido pedido =  null;
    private Transportista transportista = new TransportistaBuilder().build();
    private List<RenglonFactura> renglones = new ArrayList<>();
    private List<Pago> pagos; // 
    private Cliente cliente = new ClienteBuilder().build();
    private Usuario usuario = new UsuarioBuilder().build();
    private double subTotal = 7885;
    private double recargo_porcentaje = 0.0;
    private double recargo_neto = 0.0;
    private double descuento_porcentaje = 0.0;
    private double descuento_neto = 0.0;
    private double subTotal_neto = 7885;
    private double iva_105_neto = 0.0;
    private double iva_21_neto = 1655.85;
    private double impuestoInterno_neto = 0.0;
    private double total = 7885;
    private String observaciones = "Factura por Default";
    private boolean pagada = true;
    private Empresa empresa = new EmpresaBuilder().build();
    private boolean eliminada = false;
      
    public FacturaVenta build() {
        FacturaVenta factura = new FacturaVenta(cliente, usuario);
        factura.setId_Factura(id_Factura);
        factura.setFecha(fecha);
        factura.setTipoFactura(tipoFactura);
        factura.setNumSerie(numSerie);
        factura.setNumFactura(numFactura);
        factura.setFechaVencimiento(fechaVencimiento);
        factura.setPedido(pedido);
        factura.setTransportista(transportista);
        //renglones
        RenglonFactura rf1 = new RenglonFacturaBuilder().build();
        RenglonFactura rf2 = new RenglonFacturaBuilder().withCantidad(4)
                                                        .withIVAneto(1092)
                                                        .withGananciaNeto(520)
                                                        .withImporte(6812)
                                                        .build();
        this.renglones.add(rf1);
        this.renglones.add(rf2);
        factura.setRenglones(renglones);
        factura.setCliente(cliente);
        factura.setUsuario(usuario);
        factura.setSubTotal(subTotal);
        factura.setRecargo_porcentaje(recargo_porcentaje);
        factura.setRecargo_neto(recargo_neto);
        factura.setDescuento_porcentaje(descuento_porcentaje);
        factura.setDescuento_neto(descuento_neto);
        factura.setSubTotal_neto(subTotal_neto);
        factura.setIva_105_neto(iva_105_neto);
        factura.setIva_21_neto(iva_21_neto);
        factura.setImpuestoInterno_neto(impuestoInterno_neto);
        factura.setTotal(total);
        factura.setObservaciones(observaciones);
        factura.setPagada(pagada);
        factura.setEmpresa(empresa);
        factura.setEliminada(eliminada);
        return factura;
    }
    
    public FacturaVentaBuilder withId_Factura(long idFactura) {
        this.id_Factura = idFactura;
        return this;
    }
    
    public FacturaVentaBuilder withFecha(Date fecha) {
        this.fecha = fecha;
        return this;
    }
    
    public FacturaVentaBuilder withTipoFactura(char tipoFactura) {
        this.tipoFactura = tipoFactura;
        return this;
    }
    
}
