package sic.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.buscarPorId",
            query = "SELECT f FROM Factura f "
                    + "WHERE f.eliminada = false AND f.id_Factura = :id")
})
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(of = {"fecha", "tipoFactura", "numSerie", "numFactura", "empresa"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_Factura")
public abstract class Factura implements Serializable {

    @Id
    @GeneratedValue
    private long id_Factura;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private char tipoFactura;

    private long numSerie;

    private long numFactura;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    
    @ManyToOne
    @JoinColumn(name = "id_Pedido", referencedColumnName = "id_Pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_Transportista", referencedColumnName = "id_Transportista")
    private Transportista transportista;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_Factura")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<RenglonFactura> renglones;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_Factura")    
    private List<Pago> pagos;

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

    @Column(nullable = false)
    private String observaciones;

    private boolean pagada;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminada;
   
}
