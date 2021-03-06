package sic.service.impl;

import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import sic.modelo.BusquedaTransportistaCriteria;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Empresa;
import sic.modelo.Producto;
import sic.modelo.QTransportista;
import sic.modelo.Transportista;
import sic.service.ITransportistaService;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;
import sic.util.Validator;
import sic.repository.TransportistaRepository;

@Service
public class TransportistaServiceImpl implements ITransportistaService {

    private final TransportistaRepository transportistaRepository;
    private static final Logger LOGGER = Logger.getLogger(TransportistaServiceImpl.class.getPackage().getName());

    @Autowired
    public TransportistaServiceImpl(TransportistaRepository transportistaRepository) {
        this.transportistaRepository = transportistaRepository;
    }
    
    @Override
    public Transportista getTransportistaPorId(long idTransportista) {
        Transportista transportista = transportistaRepository.findOne(idTransportista);
        if (transportista == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_no_existente"));
        }
        return transportista;
    }

    @Override
    public List<Transportista> getTransportistas(Empresa empresa) {
        List<Transportista> transportista =  transportistaRepository.findAllByAndEmpresaAndEliminadoOrderByNombreAsc(empresa, false);
        if (transportista == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_ninguno_cargado"));
        }
        return transportista;
    }

    @Override
    public List<Transportista> buscarTransportistas(BusquedaTransportistaCriteria criteria) {
        //Empresa
        if (criteria.getEmpresa() == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
        }
        QTransportista qtransportista = QTransportista.transportista;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qtransportista.empresa.eq(criteria.getEmpresa()).and(qtransportista.eliminado.eq(false)));
        //Nombre
        if (criteria.isBuscarPorNombre() == true) {
            builder.and(this.buildPredicadoNombre(criteria.getNombre(), qtransportista));
        }
        //Localidad
        if (criteria.isBuscarPorLocalidad() == true) {
            builder.and(qtransportista.localidad.eq(criteria.getLocalidad()));
        }
        //Provincia
        if (criteria.isBuscarPorProvincia() == true) {
            builder.and(qtransportista.localidad.provincia.eq(criteria.getProvincia()));
        }
        //Pais
        if (criteria.isBuscarPorPais() == true) {
            builder.and(qtransportista.localidad.provincia.pais.eq(criteria.getPais()));
        }
        List<Transportista> list = new ArrayList<>();
        transportistaRepository.findAll(builder, new Sort(Sort.Direction.ASC, "nombre")).iterator().forEachRemaining(list::add);
        return list;
    }
    
    private BooleanBuilder buildPredicadoNombre(String nombre, QTransportista qtransportista) {
        String[] terminos = nombre.split(" ");
        BooleanBuilder descripcionProducto = new BooleanBuilder();
        for (String termino : terminos) {
            descripcionProducto.and(qtransportista.nombre.containsIgnoreCase(termino));
        }
        return descripcionProducto;
    }

    @Override
    public Transportista getTransportistaPorNombre(String nombre, Empresa empresa) {
        return transportistaRepository.findByNombreAndEmpresaAndEliminado(nombre, empresa, false);
    }

    private void validarOperacion(TipoDeOperacion operacion, Transportista transportista) {
        //Requeridos
        if (Validator.esVacio(transportista.getNombre())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_nombre_vacio"));
        }
        if (transportista.getLocalidad() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_localidad_vacia"));
        }
        if (transportista.getEmpresa() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_empresa_vacia"));
        }
        //Duplicados
        //Nombre
        Transportista transportistaDuplicado = this.getTransportistaPorNombre(transportista.getNombre(), transportista.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && transportistaDuplicado != null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (transportistaDuplicado != null && transportistaDuplicado.getId_Transportista() != transportista.getId_Transportista()) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_transportista_duplicado_nombre"));
            }
        }
    }

    @Override
    @Transactional
    public Transportista guardar(Transportista transportista) {
        this.validarOperacion(TipoDeOperacion.ALTA, transportista);
        transportista = transportistaRepository.save(transportista);
        LOGGER.warn("El Transportista " + transportista + " se guardó correctamente.");
        return transportista;
    }

    @Override
    @Transactional
    public void actualizar(Transportista transportista) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, transportista);
        transportistaRepository.save(transportista);        
    }

    @Override
    @Transactional
    public void eliminar(long idTransportista) {
        Transportista transportista = this.getTransportistaPorId(idTransportista);
        if (transportista == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_transportista_no_existente"));
        }
        transportista.setEliminado(true);
        transportistaRepository.save(transportista);
    }
}
