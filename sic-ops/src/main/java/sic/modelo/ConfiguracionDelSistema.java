package sic.modelo;

import java.io.Serializable;
import lombok.Data;

@Data
public class ConfiguracionDelSistema implements Serializable {

    private long id_ConfiguracionDelSistema;
    private boolean usarFacturaVentaPreImpresa;
    private int cantidadMaximaDeRenglonesEnFactura; 
    private boolean facturaElectronicaHabilitada;    
    private String pathCertificadoAfip;    
    private String firmanteCertificadoAfip;    
    private String passwordCertificadoAfip;
    private Empresa empresa;
}
