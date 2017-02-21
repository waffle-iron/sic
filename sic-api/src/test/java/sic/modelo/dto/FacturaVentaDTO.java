package sic.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sic.builder.ClienteBuilder;
import sic.builder.EmpresaBuilder;
import sic.builder.TransportistaBuilder;
import sic.builder.UsuarioBuilder;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.Pago;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.modelo.Usuario;

@Data
@EqualsAndHashCode(of = {"fecha", "tipoFactura", "numSerie", "numFactura", "empresa"})
public class FacturaVentaDTO implements Serializable {

    private String type = "FacturaVenta";
    private long id_Factura = 0L;
    private Date fecha = new Date();
    private char tipoFactura = 'A';
    private long numSerie = 0;
    private long numFactura = 1;
    private Date fechaVencimiento = new Date();    
    private Pedido pedido =  null;
    private Transportista transportista = new TransportistaBuilder().build();
    private List<RenglonFactura> renglones;
    private List<Pago> pagos;
    private Cliente cliente = new ClienteBuilder().build();
    private Usuario usuario = new UsuarioBuilder().build();
    private double subTotal = 6500;
    private double recargo_porcentaje = 0.0;
    private double recargo_neto = 0.0;
    private double descuento_porcentaje = 0.0;
    private double descuento_neto = 0.0;
    private double subTotal_neto = 6500;
    private double iva_105_neto = 0.0;
    private double iva_21_neto = 1365;
    private double impuestoInterno_neto = 0.0;
    private double total = 7865;
    private String observaciones = "Factura por Default";
    private boolean pagada = false;
    private Empresa empresa = new EmpresaBuilder().build();
    private boolean eliminada = false;

}
