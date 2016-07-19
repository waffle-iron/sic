package sic.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusquedaProductoCriteria {

    private boolean buscarPorCodigo;
    private String codigo;
    private boolean buscarPorDescripcion;
    private String descripcion;
    private boolean buscarPorRubro;
    private Rubro rubro;
    private boolean buscarPorProveedor;
    private Proveedor proveedor;
    private Empresa empresa;
    private int cantRegistros;
    private boolean listarSoloFaltantes;

}
