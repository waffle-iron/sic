package sic.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "renglonPedido")
public class RenglonPedido implements Serializable {

    @Id
    @GeneratedValue
    private long id_RenglonPedido;

    private double cantidad;

    private double subTotal;
    
    private double descuento_procentaje;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Pedido", referencedColumnName = "id_Pedido")
    private Pedido pedido;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Producto", referencedColumnName = "id_Producto")
    private Producto Producto;

}
