package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "renglonPedido")
public class RenglonPedido implements Serializable {

    @Id
    @GeneratedValue
    private long id_RenglonPedido;

    private int cantidad;

    private double subTotal;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Pedido", referencedColumnName = "id_Pedido")
    private Pedido pedido;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Producto", referencedColumnName = "id_Producto")
    private Producto Producto;

    public RenglonPedido() {
    }

    public long getId_RenglonPedido() {
        return id_RenglonPedido;
    }

    public void setId_RenglonPedido(long id_RenglonPedido) {
        this.id_RenglonPedido = id_RenglonPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return Producto;
    }

    public void setProducto(Producto Producto) {
        this.Producto = Producto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id_RenglonPedido ^ (this.id_RenglonPedido >>> 32));
        hash = 79 * hash + this.cantidad;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.subTotal) ^ (Double.doubleToLongBits(this.subTotal) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.pedido);
        hash = 79 * hash + Objects.hashCode(this.Producto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RenglonPedido other = (RenglonPedido) obj;
        if (this.id_RenglonPedido != other.id_RenglonPedido) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (Double.doubleToLongBits(this.subTotal) != Double.doubleToLongBits(other.subTotal)) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.Producto, other.Producto)) {
            return false;
        }
        return true;
    }

}
