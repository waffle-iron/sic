package sic.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
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
import lombok.Data;

@Entity
@Table(name = "localidad")
@NamedQueries({
    @NamedQuery(name = "Localidad.buscarTodas",
            query = "SELECT l FROM Localidad l WHERE l.eliminada = false ORDER BY l.nombre ASC"),
    @NamedQuery(name = "Localidad.buscarLocalidadesDeLaProvincia",
            query = "SELECT l FROM Localidad l WHERE l.provincia = :provincia AND l.eliminada = false ORDER BY l.nombre ASC"),
    @NamedQuery(name = "Localidad.buscarPorNombre",
            query = "SELECT l FROM Localidad l WHERE l.provincia = :provincia AND l.eliminada = false AND l.nombre = :nombre ORDER BY l.nombre ASC")
})
@Data
public class Localidad implements Serializable {

    @Id
    @GeneratedValue
    private long id_Localidad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "id_Provincia", referencedColumnName = "id_Provincia")
    private Provincia provincia;

    @OneToMany(mappedBy = "localidad")
    private Set<Proveedor> proveedores;

    @OneToMany(mappedBy = "localidad")
    private Set<Transportista> transportistas;

    @OneToMany(mappedBy = "localidad")
    private Set<Empresa> empresas;

    @OneToMany(mappedBy = "localidad")
    private Set<Cliente> clientes;

    private boolean eliminada = false;

    @Override
    public String toString() {
        return nombre;
    }
}
