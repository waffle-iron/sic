package sic.builder;

import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;

public class ConfiguracionDelSistemaBuilder {

    private long id_ConfiguracionDelSistema = 0L;
    private boolean usarFacturaVentaPreImpresa = true;
    private int cantidadMaximaDeRenglonesEnFactura = 28;
    private boolean facturaElectronicaHabilitada = false;    
    private String pathCertificadoAfip = "";    
    private String firmanteCertificadoAfip = "";    
    private String passwordCertificadoAfip = "";
    private Empresa empresa = new EmpresaBuilder().build();

    public ConfiguracionDelSistema build() {
        return new ConfiguracionDelSistema(id_ConfiguracionDelSistema, usarFacturaVentaPreImpresa,
                cantidadMaximaDeRenglonesEnFactura, facturaElectronicaHabilitada, pathCertificadoAfip,
                firmanteCertificadoAfip, passwordCertificadoAfip, empresa);
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

    public ConfiguracionDelSistemaBuilder withFacturaElectronicaHabilitada(boolean facturaElectronicaHabilitada) {
        this.facturaElectronicaHabilitada = facturaElectronicaHabilitada;
        return this;
    }
    
    public ConfiguracionDelSistemaBuilder withPathCertificadoAfip(String pathCertificadoAfip) {
        this.pathCertificadoAfip = pathCertificadoAfip;
        return this;
    }
    
    public ConfiguracionDelSistemaBuilder withFirmanteCertificadoAfip(String firmanteCertificadoAfip) {
        this.firmanteCertificadoAfip = firmanteCertificadoAfip;
        return this;
    }
    
    public ConfiguracionDelSistemaBuilder withPasswordCertificadoAfip(String passwordCertificadoAfip) {
        this.passwordCertificadoAfip = passwordCertificadoAfip;
        return this;
    }
    
    public ConfiguracionDelSistemaBuilder withEmpresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }
}
