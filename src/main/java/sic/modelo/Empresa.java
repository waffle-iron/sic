package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.buscarTodas",
            query = "SELECT e FROM Empresa e "
                    + "WHERE e.eliminada = false ORDER BY e.nombre ASC"),
    @NamedQuery(name = "Empresa.buscarPorId",
            query = "SELECT e FROM Empresa e "
                    + "WHERE e.id_Empresa = :id AND e.eliminada = false"),
    @NamedQuery(name = "Empresa.buscarPorNombre",
            query = "SELECT e FROM Empresa e "
                    + "WHERE e.nombre LIKE :nombre AND e.eliminada = false ORDER BY e.nombre ASC"),
    @NamedQuery(name = "Empresa.buscarPorCUIP",
            query = "SELECT e FROM Empresa e "
                    + "WHERE e.cuip = :cuip AND e.eliminada = false")
})
@Data
@EqualsAndHashCode(of = {"nombre"})
public class Empresa implements Serializable {

    @Id
    @GeneratedValue
    private long id_Empresa;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String lema;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_CondicionIVA", referencedColumnName = "id_CondicionIVA")
    private CondicionIVA condicionIVA;

    private long cuip;

    private long ingresosBrutos;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioActividad;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_Localidad", referencedColumnName = "id_Localidad")
    private Localidad localidad;

    @Lob
    private byte[] logo;

    @OneToMany(mappedBy = "empresa")
    private Set<Producto> productos;

    @OneToMany(mappedBy = "empresa")
    private Set<Proveedor> proveedores;

    @OneToMany(mappedBy = "empresa")
    private Set<Factura> facturas;

    @OneToMany(mappedBy = "empresa")
    private Set<Transportista> transportistas;

    @OneToMany(mappedBy = "empresa")
    private Set<Cliente> clientes;

    @OneToMany(mappedBy = "empresa")
    private Set<FormaDePago> formasDePago;

    @OneToMany(mappedBy = "empresa")
    private Set<Medida> medidas;

    @OneToMany(mappedBy = "empresa")
    private Set<Rubro> rubros;

    @OneToMany(mappedBy = "empresa")
    private Set<ConfiguracionDelSistema> configuraciones;

    private boolean eliminada;

    @Override
    public String toString() {
        return nombre;
    }

}
