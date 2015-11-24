package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import lombok.Data;

@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.buscarTodos",
            query = "SELECT c FROM Cliente c WHERE c.empresa = :empresa AND c.eliminado = false ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorId",
            query = "SELECT c FROM Cliente c WHERE c.id_Cliente = :id AND c.eliminado = false"),
    @NamedQuery(name = "Cliente.buscarQueContengaRazonSocialNombreFantasiaIdFiscal",
            query = "SELECT c FROM Cliente c "
            + "WHERE (c.razonSocial LIKE :criteria OR c.nombreFantasia LIKE :criteria OR c.id_Fiscal LIKE :criteria) "
            + "AND c.empresa = :empresa AND c.eliminado = false "
            + "ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorRazonSocial",
            query = "SELECT c FROM Cliente c WHERE c.razonSocial = :razonSocial AND c.empresa = :empresa AND c.eliminado = false "
            + "ORDER BY c.razonSocial ASC"),
    @NamedQuery(name = "Cliente.buscarPorIdFiscal",
            query = "SELECT c FROM Cliente c WHERE c.id_Fiscal = :id_Fiscal AND c.eliminado = false AND c.empresa = :empresa"),
    @NamedQuery(name = "Cliente.buscarPredeterminado",
            query = "SELECT c FROM Cliente c WHERE c.predeterminado = true AND c.eliminado = false AND c.empresa = :empresa")
})
@Data
public class Cliente implements Serializable {

    @Id
    @GeneratedValue
    private long id_Cliente;

    @Column(nullable = false)
    private String razonSocial;

    private String nombreFantasia;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_CondicionIVA", referencedColumnName = "id_CondicionIVA")
    private CondicionIVA condicionIVA;

    @Column(nullable = false)
    private String id_Fiscal;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telPrimario;

    @Column(nullable = false)
    private String telSecundario;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

    private boolean predeterminado;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "cliente")
    private Set<FacturaVenta> facturasVenta;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "cliente")
    private List<Pedido> pedidos;

    @Override
    public String toString() {
        return razonSocial;
    }    
}
