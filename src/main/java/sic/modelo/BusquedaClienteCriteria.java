package sic.modelo;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
public class BusquedaClienteCriteria {

    private boolean buscaPorNombre;
    private String nombre;
    private boolean buscaPorId_Fiscal;
    private String id_Fiscal;
    private boolean buscaPorPais;
    private Pais pais;
    private boolean buscaPorProvincia;
    private Provincia provincia;
    private boolean buscaPorLocalidad;
    private Localidad localidad;
    private Empresa empresa;

    public BusquedaClienteCriteria() {
    }

    public BusquedaClienteCriteria(boolean buscaPorNombre, String nombre, boolean buscaPorId_Fiscal, String id_Fiscal, boolean buscaPorPais, Pais pais, boolean buscaPorProvincia, Provincia provincia, boolean buscaPorLocalidad, Localidad localidad, Empresa empresa) {
        this.buscaPorNombre = buscaPorNombre;
        this.nombre = nombre;
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

    public boolean getBuscaPorNombre() {
        return buscaPorNombre;
    }

    public void setBuscaPorNombre(boolean buscaPorNombre) {
        this.buscaPorNombre = buscaPorNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getBuscaPorId_Fiscal() {
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

    public boolean getBuscaPorPais() {
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

    public boolean getBuscaPorProvincia() {
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

    public boolean getBuscaPorLocalidad() {
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
}