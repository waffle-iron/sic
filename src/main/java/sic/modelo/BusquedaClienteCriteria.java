package sic.modelo;

import lombok.Data;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
public class BusquedaClienteCriteria {

    private boolean buscaPorRazonSocial;
    private String razonSocial;
    private boolean buscaPorNombreFantasia;
    private String nombreFantasia;
    private boolean buscaPorId_Fiscal;
    private String id_Fiscal;
    private boolean buscaPorPais;
    private Pais pais;
    private boolean buscaPorProvincia;
    private Provincia provincia;
    private boolean buscaPorLocalidad;
    private Localidad localidad;
    private Empresa empresa;

    public BusquedaClienteCriteria(boolean buscaPorRazonSocial, String razonSocial,
            boolean buscaPorNombreFantasia, String nombreFantasia,
            boolean buscaPorId_Fiscal, String id_Fiscal,
            boolean buscaPorPais, Pais pais,
            boolean buscaPorProvincia, Provincia provincia,
            boolean buscaPorLocalidad, Localidad localidad,
            Empresa empresa) {

        this.buscaPorRazonSocial = buscaPorRazonSocial;
        this.razonSocial = razonSocial;
        this.buscaPorNombreFantasia = buscaPorNombreFantasia;
        this.nombreFantasia = nombreFantasia;
        this.buscaPorId_Fiscal = buscaPorId_Fiscal;
        this.id_Fiscal = id_Fiscal;
        this.buscaPorPais = buscaPorPais;
        this.pais = pais;
        this.buscaPorProvincia = buscaPorProvincia;
        this.provincia = provincia;
        this.buscaPorLocalidad = buscaPorLocalidad;
        this.localidad = localidad;
        this.empresa = empresa;
    }

    public boolean buscaPorRazonSocial() {
        return buscaPorRazonSocial;
    }

    public boolean buscaPorNombreFantasia() {
        return buscaPorNombreFantasia;
    }

    public boolean buscaPorId_Fiscal() {
        return buscaPorId_Fiscal;
    }

    public boolean buscaPorPais() {
        return buscaPorPais;
    }

    public boolean buscaPorProvincia() {
        return buscaPorProvincia;
    }

    public boolean buscaPorLocalidad() {
        return buscaPorLocalidad;
    }

}
