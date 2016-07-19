package sic.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaTransportistaCriteria {

    private boolean buscarPorNombre;
    private String nombre;
    private boolean buscarPorPais;
    private Pais pais;
    private boolean buscarPorProvincia;
    private Provincia provincia;
    private boolean buscarPorLocalidad;
    private Localidad localidad;
    private Empresa empresa;
}
