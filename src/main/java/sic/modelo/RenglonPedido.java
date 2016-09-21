package sic.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "renglonpedido")
@Data
//@ToString(exclude= "pedido")
@EqualsAndHashCode(of = "producto")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_RenglonPedido")
public class RenglonPedido implements Serializable {

    @Id
    @GeneratedValue
    private long id_RenglonPedido;

//    @ManyToOne
//    @JoinColumn(name = "id_Pedido", referencedColumnName = "id_Pedido")
//    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_Producto", referencedColumnName = "id_Producto")
    private Producto producto;

    private double cantidad;
    private double descuento_porcentaje;
    private double descuento_neto;
    private double subTotal;

}