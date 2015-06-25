package sic.modelo;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
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

    public BusquedaProductoCriteria() {
    }

    public BusquedaProductoCriteria(boolean buscarPorCodigo, String codigo, boolean buscarPorDescripcion, String descripcion, boolean buscarPorRubro, Rubro rubro, boolean buscarPorProveedor, Proveedor proveedor, Empresa empresa, int cantRegistros, boolean listarSoloFaltantes) {
        this.buscarPorCodigo = buscarPorCodigo;
        this.codigo = codigo;
        this.buscarPorDescripcion = buscarPorDescripcion;
        this.descripcion = descripcion;
        this.buscarPorRubro = buscarPorRubro;
        this.rubro = rubro;
        this.buscarPorProveedor = buscarPorProveedor;
        this.proveedor = proveedor;
        this.empresa = empresa;
        this.cantRegistros = cantRegistros;
        this.listarSoloFaltantes = listarSoloFaltantes;
    }

    public boolean isBuscarPorCodigo() {
        return buscarPorCodigo;
    }

    public void setBuscarPorCodigo(boolean buscarPorCodigo) {
        this.buscarPorCodigo = buscarPorCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isBuscarPorDescripcion() {
        return buscarPorDescripcion;
    }

    public void setBuscarPorDescripcion(boolean buscarPorDescripcion) {
        this.buscarPorDescripcion = buscarPorDescripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isBuscarPorRubro() {
        return buscarPorRubro;
    }

    public void setBuscarPorRubro(boolean buscarPorRubro) {
        this.buscarPorRubro = buscarPorRubro;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public boolean isBuscarPorProveedor() {
        return buscarPorProveedor;
    }

    public void setBuscarPorProveedor(boolean buscarPorProveedor) {
        this.buscarPorProveedor = buscarPorProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public boolean isListarSoloFaltantes() {
        return listarSoloFaltantes;
    }

    public void setListarSoloFaltantes(boolean listarSoloFaltantes) {
        this.listarSoloFaltantes = listarSoloFaltantes;
    }
}