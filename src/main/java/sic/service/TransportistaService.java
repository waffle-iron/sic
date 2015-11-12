package sic.service;

import sic.modelo.BusquedaTransportistaCriteria;
import java.util.List;
import java.util.ResourceBundle;
import sic.repository.jpa.TransportistaRepositoryJPAImpl;
import sic.modelo.Empresa;
import sic.modelo.Transportista;
import sic.util.Validator;

public class TransportistaService {

    private final TransportistaRepositoryJPAImpl modeloTransportista = new TransportistaRepositoryJPAImpl();

    public List<Transportista> getTransportistas(Empresa empresa) {
        return modeloTransportista.getTransportistas(empresa);
    }

    public List<Transportista> buscarTransportistas(BusquedaTransportistaCriteria criteria) {
        //@Todo No debe verificar contra la palabra "Todos/as". Usar el boolean asociado a ese campo
        //Pais
        if (criteria.getPais().getNombre().equals("Todos")) {
            criteria.setBuscarPorPais(false);
        }
        //Provincia
        if (criteria.getProvincia().getNombre().equals("Todas")) {
            criteria.setBuscarPorProvincia(false);
        }
        //Localidad
        if (criteria.getLocalidad().getNombre().equals("Todas")) {
            criteria.setBuscarPorLocalidad(false);
        }
        return modeloTransportista.busquedaPersonalizada(criteria);
    }

    public Transportista getTransportistaPorNombre(String nombre, Empresa empresa) {
        return modeloTransportista.getTransportistaPorNombre(nombre, empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Transportista transportista) {
        //Requeridos
        if (Validator.esVacio(transportista.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_nombre_vacio"));
        }
        if (transportista.getLocalidad() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_localidad_vacia"));
        }
        if (transportista.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_empresa_vacia"));
        }
        //Duplicados
        //Nombre
        Transportista transportistaDuplicado = this.getTransportistaPorNombre(transportista.getNombre(), transportista.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && transportistaDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (transportistaDuplicado != null && transportistaDuplicado.getId_Transportista() != transportista.getId_Transportista()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_transportista_duplicado_nombre"));
            }
        }

    }

    public void guardar(Transportista transportista) {
        this.validarOperacion(TipoDeOperacion.ALTA, transportista);
        modeloTransportista.guardar(transportista);
    }

    public void actualizar(Transportista transportista) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, transportista);
        modeloTransportista.actualizar(transportista);
    }

    public void eliminar(Transportista transportista) {
        transportista.setEliminado(true);
        modeloTransportista.actualizar(transportista);
    }
}