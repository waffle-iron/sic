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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "gasto")
@NamedQueries({
    @NamedQuery(name = "Gasto.gastoSinArqueoPorFormaDePago",
            query = "SELECT g FROM Gasto g WHERE g.eliminado = false AND g.empresa.id_Empresa = :id_Empresa AND g.formaDePago.id_FormaDePago = :id_FormaDePago"),
    @NamedQuery(name = "Gasto.gastosSinArqueoPorFormaDePagoYFecha",
            query = "SELECT g FROM Gasto g WHERE g.eliminado = false AND g.empresa.id_Empresa = :id_Empresa AND g.formaDePago.id_FormaDePago = :id_FormaDePago AND g.fecha BETWEEN :desde AND :hasta"),
    @NamedQuery(name = "Gasto.gastosSinArqueoPorFecha",
            query = "SELECT g FROM Gasto g WHERE g.eliminado = false AND g.empresa.id_Empresa = :id_Empresa AND g.fecha BETWEEN :desde AND :hasta"),
    @NamedQuery(name = "Gasto.gastoPorId",
            query = "SELECT g FROM Gasto g WHERE g.id_Gasto = :id_Gasto AND g.empresa.id_Empresa = :id_Empresa ORDER BY g.fecha ASC"),
    @NamedQuery(name = "Gasto.ultimoNumeroDeCaja",
            query = "SELECT max(g.nroGasto) FROM Gasto g WHERE g.empresa.id_Empresa = :id_Empresa")
})
@Data
public class Gasto implements Serializable {

    @Id
    @GeneratedValue
    private long id_Gasto;

    private int nroGasto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    @OneToOne
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "id_FormaDePago", referencedColumnName = "id_FormaDePAgo")
    private FormaDePago formaDePago;

    private double monto;

    private boolean eliminado;

}
