package sic.builder;

import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;

public class ConfiguracionDelSistemaBuilder {

    private long id_ConfiguracionDelSistema;
    private boolean usarFacturaVentaPreImpresa;
    private int cantidadMaximaDeRenglonesEnFactura;
    private Empresa empresa;

    public ConfiguracionDelSistema build() {
        return new ConfiguracionDelSistema(id_ConfiguracionDelSistema, usarFacturaVentaPreImpresa, cantidadMaximaDeRenglonesEnFactura, empresa);
    }

    public ConfiguracionDelSistemaBuilder withIdConfiguracionDelSistema(long idCds) {
        this.id_ConfiguracionDelSistema = idCds;
        return this;
    }

    public ConfiguracionDelSistemaBuilder withUsarFacturaVentaPreImpresa(boolean usarFacturaPreImpresa) {
        this.usarFacturaVentaPreImpresa = usarFacturaPreImpresa;
        return this;
    }

    public ConfiguracionDelSistemaBuilder withCantidadMaximaDeRenglonesEnFactura(int cantidadMaximaDeRenglonesEnFactura) {
        this.cantidadMaximaDeRenglonesEnFactura = cantidadMaximaDeRenglonesEnFactura;
        return this;
    }

    public ConfiguracionDelSistemaBuilder withEmpresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }
}
