package sic.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "configuraciondelsistema")
@NamedQueries({
    @NamedQuery(name = "ConfiguracionDelSistema.buscarPorId",
            query = "SELECT cds FROM ConfiguracionDelSistema cds WHERE cds.id_ConfiguracionDelSistema = :id"),
    @NamedQuery(name = "ConfiguracionDelSistema.buscarPorEmpresa",
            query = "SELECT cds FROM ConfiguracionDelSistema cds WHERE cds.empresa = :empresa")
})
public class ConfiguracionDelSistema implements Serializable {

    @Id
    @GeneratedValue
    private long id_ConfiguracionDelSistema;

    private boolean usarFacturaVentaPreImpresa;

    private int cantidadMaximaDeRenglonesEnFactura;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    public ConfiguracionDelSistema() {
    }

    public long getId_ConfiguracionDelSistema() {
        return id_ConfiguracionDelSistema;
    }

    public void setId_ConfiguracionDelSistema(long id_ConfiguracionDelSistema) {
        this.id_ConfiguracionDelSistema = id_ConfiguracionDelSistema;
    }

    public boolean usarFacturaVentaPreImpresa() {
        return usarFacturaVentaPreImpresa;
    }

    public void setUsarFacturaVentaPreImpresa(boolean usarFacturaVentaPreImpresa) {
        this.usarFacturaVentaPreImpresa = usarFacturaVentaPreImpresa;
    }

    public int getCantidadMaximaDeRenglonesEnFactura() {
        return cantidadMaximaDeRenglonesEnFactura;
    }

    public void setCantidadMaximaDeRenglonesEnFactura(int cantidadMaximaDeRenglonesEnFactura) {
        this.cantidadMaximaDeRenglonesEnFactura = cantidadMaximaDeRenglonesEnFactura;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id_ConfiguracionDelSistema ^ (this.id_ConfiguracionDelSistema >>> 32));
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
        final ConfiguracionDelSistema other = (ConfiguracionDelSistema) obj;
        if (this.id_ConfiguracionDelSistema != other.id_ConfiguracionDelSistema) {
            return false;
        }
        return true;
    }

}
