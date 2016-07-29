package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pago", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_FormaDePago", "id_Factura", "eliminado"})
})
@NamedQueries({
    @NamedQuery(name = "Pago.buscarPorFactura",
            query = "SELECT p FROM Pago p "
                     + "WHERE p.factura = :factura AND p.eliminado = false "
                     + "ORDER BY p.fecha ASC")
})
@Data
@EqualsAndHashCode(of = {"formaDePago", "factura", "eliminado"})
public class Pago implements Serializable {

    @Id
    @GeneratedValue
    private long id_Pago;

    @ManyToOne
    @JoinColumn(name = "id_FormaDePago", referencedColumnName = "id_FormaDePago")
    private FormaDePago formaDePago;

    @ManyToOne
    @JoinColumn(name = "id_Factura", referencedColumnName = "id_Factura")
    private Factura factura;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private String nota;

    private boolean eliminado;

}
