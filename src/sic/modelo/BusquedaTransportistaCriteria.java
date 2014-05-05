package sic.modelo;

/*
 * Se utiliza como estructura de transporte, para no estar pasando los campos de
 * manera individual.
 */
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

    public BusquedaTransportistaCriteria() {
    }

    public BusquedaTransportistaCriteria(boolean buscarPorNombre, String nombre, boolean buscarPorPais, Pais pais, boolean buscarPorProvincia, Provincia provincia, boolean buscarPorLocalidad, Localidad localidad, Empresa empresa) {
        this.buscarPorNombre = buscarPorNombre;
        this.nombre = nombre;
        this.buscarPorPais = buscarPorPais;
        this.pais = pais;
        this.buscarPorProvincia = buscarPorProvincia;
        this.provincia = provincia;
        this.buscarPorLocalidad = buscarPorLocalidad;
        this.localidad = localidad;
        this.empresa = empresa;
    }

    public boolean isBuscarPorNombre() {
        return buscarPorNombre;
    }

    public void setBuscarPorNombre(boolean buscarPorNombre) {
        this.buscarPorNombre = buscarPorNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isBuscarPorPais() {
        return buscarPorPais;
    }

    public void setBuscarPorPais(boolean buscarPorPais) {
        this.buscarPorPais = buscarPorPais;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public boolean isBuscarPorProvincia() {
        return buscarPorProvincia;
    }

    public void setBuscarPorProvincia(boolean buscarPorProvincia) {
        this.buscarPorProvincia = buscarPorProvincia;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public boolean isBuscarPorLocalidad() {
        return buscarPorLocalidad;
    }

    public void setBuscarPorLocalidad(boolean buscarPorLocalidad) {
        this.buscarPorLocalidad = buscarPorLocalidad;
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