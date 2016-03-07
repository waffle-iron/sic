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
@Table(name = "controlcaja")  //cambiar de nombre a Caja, controlar las query
@NamedQueries({
    @NamedQuery(name = "ControlCaja.cajaSinArqueo",
            query = "SELECT c FROM ControlCaja c WHERE c.cerrada = false AND c.empresa.id_Empresa = :id_Empresa"),
    @NamedQuery(name = "ControlCaja.buscarPorNumero",
            query = "SELECT c FROM ControlCaja c WHERE c.nroCaja = :nroCaja AND c.empresa.id_Empresa = :id_Empresa ORDER BY c.fechaApertura ASC"),
    @NamedQuery(name = "ControlCaja.cajaSinArqueoPorFormaDepago",
            query = "SELECT c FROM ControlCaja c WHERE c.empresa.id_Empresa = :id_Empresa AND c.formaDePago.id_FormaDePago = :idFormaDePago AND c.cerrada = false ORDER BY c.fechaApertura ASC")
})
@Data
public class ControlCaja implements Serializable {

    @Id
    @GeneratedValue
    private long id_ControlCaja;

    private int nroCaja;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    @OneToOne
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private String concepto;

    @OneToOne
    @JoinColumn(name = "id_FormaDePago", referencedColumnName = "id_FormaDePAgo")
    private FormaDePago formaDePago;

    private boolean cerrada;

    private boolean saldoInicial;

    private boolean saldoFinal;

    private double totalEfectivo;

}
