package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.buscarCantidadNroPedidos",
            query = "SELECT count(p) FROM Pedido p WHERE p.empresa.id_Empresa = :idEmpresa"),
    @NamedQuery(name = "Pedido.buscarPedidoConFacturas",
            query = "SELECT f FROM Factura f WHERE f.pedido.nroPedido = :nroPedido"),
    @NamedQuery(name = "Pedido.buscarRenglonesDelPedido",
            query = "SELECT p FROM Pedido p LEFT JOIN FETCH p.renglones WHERE p.id_Pedido = :idPedido"),
    @NamedQuery(name = "Pedido.buscarPorId",
            query = "SELECT p FROM Pedido p WHERE p.id_Pedido = :id"),
    @NamedQuery(name = "Pedido.buscarPorNumero",
            query = "SELECT p FROM Pedido p WHERE p.nroPedido = :nroPedido"),
    @NamedQuery(name = "Pedido.buscarPorNumeroConFacturas",
            query = "SELECT p FROM Pedido p LEFT JOIN FETCH p.facturas WHERE p.nroPedido = :nroPedido"),
    @NamedQuery(name = "Pedido.buscarPorNumeroConRenglones",
            query = "SELECT p FROM Pedido p LEFT JOIN FETCH p.renglones WHERE p.nroPedido = :nroPedido")
})
public class Pedido implements Serializable {

    @Id
    @GeneratedValue
    private long id_Pedido;

    private long nroPedido;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private String historial;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Cliente", referencedColumnName = "id_Cliente")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario")
    private Usuario usuario;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pedido")
    private List<Factura> facturas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pedido")
    private List<RenglonPedido> renglones;

    private double total;

    public Pedido() {
    }

    public long getId_Pedido() {
        return id_Pedido;
    }

    public void setId_Pedido(long id_Pedido) {
        this.id_Pedido = id_Pedido;
    }

    public long getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(long nroPedido) {
        this.nroPedido = nroPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<RenglonPedido> getRenglones() {
        return renglones;
    }

    public void setRenglones(List<RenglonPedido> renglones) {
        this.renglones = renglones;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean isEliminada() {
        return eliminado;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminado = eliminada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.id_Pedido ^ (this.id_Pedido >>> 32));
        hash = 47 * hash + (int) (this.nroPedido ^ (this.nroPedido >>> 32));
        hash = 47 * hash + Objects.hashCode(this.fecha);
        hash = 47 * hash + Objects.hashCode(this.historial);
        hash = 47 * hash + Objects.hashCode(this.empresa);
        hash = 47 * hash + (this.eliminado ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.cliente);
        hash = 47 * hash + Objects.hashCode(this.usuario);
        hash = 47 * hash + Objects.hashCode(this.facturas);
        hash = 47 * hash + Objects.hashCode(this.renglones);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.total) ^ (Double.doubleToLongBits(this.total) >>> 32));
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
        final Pedido other = (Pedido) obj;
        if (this.id_Pedido != other.id_Pedido) {
            return false;
        }
        if (this.nroPedido != other.nroPedido) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.historial, other.historial)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (this.eliminado != other.eliminado) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.facturas, other.facturas)) {
            return false;
        }
        if (!Objects.equals(this.renglones, other.renglones)) {
            return false;
        }
        if (Double.doubleToLongBits(this.total) != Double.doubleToLongBits(other.total)) {
            return false;
        }
        return true;
    }

}
