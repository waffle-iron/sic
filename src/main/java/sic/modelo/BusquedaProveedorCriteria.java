package sic.modelo;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
public class BusquedaProveedorCriteria {

    private boolean buscaPorCodigo;
    private String codigo;
    private boolean buscaPorRazonSocial;
    private String razonSocial;
    private boolean buscaPorId_Fiscal;
    private String id_Fiscal;
    private boolean buscaPorPais;
    private Pais pais;
    private boolean buscaPorProvincia;
    private Provincia provincia;
    private boolean buscaPorLocalidad;
    private Localidad localidad;
    private Empresa empresa;
    private int cantRegistros;

    public BusquedaProveedorCriteria() {
    }

    public BusquedaProveedorCriteria(boolean buscaPorCodigo, String codigo, boolean buscaPorRazonSocial, String razonSocial, boolean buscaPorId_Fiscal, String id_Fiscal, boolean buscaPorPais, Pais pais, boolean buscaPorProvincia, Provincia provincia, boolean buscaPorLocalidad, Localidad localidad, Empresa empresa, int cantRegistros) {
        this.buscaPorCodigo = buscaPorCodigo;
        this.codigo = codigo;
        this.buscaPorRazonSocial = buscaPorRazonSocial;
        this.razonSocial = razonSocial;
        this.buscaPorId_Fiscal = buscaPorId_Fiscal;
        this.id_Fiscal = id_Fiscal;
        this.buscaPorPais = buscaPorPais;
        this.pais = pais;
        this.buscaPorProvincia = buscaPorProvincia;
        this.provincia = provincia;
        this.buscaPorLocalidad = buscaPorLocalidad;
        this.localidad = localidad;
        this.empresa = empresa;
        this.cantRegistros = cantRegistros;
    }

    public boolean isBuscaPorCodigo() {
        return buscaPorCodigo;
    }

    public void setBuscaPorCodigo(boolean buscaPorCodigo) {
        this.buscaPorCodigo = buscaPorCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isBuscaPorRazonSocial() {
        return buscaPorRazonSocial;
    }

    public void setBuscaPorRazonSocial(boolean buscaPorRazonSocial) {
        this.buscaPorRazonSocial = buscaPorRazonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public boolean isBuscaPorId_Fiscal() {
        return buscaPorId_Fiscal;
    }

    public void setBuscaPorId_Fiscal(boolean buscaPorId_Fiscal) {
        this.buscaPorId_Fiscal = buscaPorId_Fiscal;
    }

    public String getId_Fiscal() {
        return id_Fiscal;
    }

    public void setId_Fiscal(String id_Fiscal) {
        this.id_Fiscal = id_Fiscal;
    }

    public boolean isBuscaPorPais() {
        return buscaPorPais;
    }

    public void setBuscaPorPais(boolean buscaPorPais) {
        this.buscaPorPais = buscaPorPais;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public boolean isBuscaPorProvincia() {
        return buscaPorProvincia;
    }

    public void setBuscaPorProvincia(boolean buscaPorProvincia) {
        this.buscaPorProvincia = buscaPorProvincia;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public boolean isBuscaPorLocalidad() {
        return buscaPorLocalidad;
    }

    public void setBuscaPorLocalidad(boolean buscaPorLocalidad) {
        this.buscaPorLocalidad = buscaPorLocalidad;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
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
}