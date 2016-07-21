package sic.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
@AllArgsConstructor
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

}
