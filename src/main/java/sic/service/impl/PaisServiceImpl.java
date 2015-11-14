package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.modelo.Pais;
import sic.repository.IPaisRepository;
import sic.service.IPaisService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class PaisServiceImpl implements IPaisService {

    private final IPaisRepository paisRepository;

    @Autowired
    public PaisServiceImpl(IPaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }    
    
    @Override
    public List<Pais> getPaises() {
        return paisRepository.getPaises();
    }

    @Override
    public Pais getPaisPorNombre(String nombre) {
        return paisRepository.getPaisPorNombre(nombre);
    }

    private void validarOperacion(TipoDeOperacion operacion, Pais pais) {
        //Obligatorios
        if (Validator.esVacio(pais.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pais_vacio_nombre"));
        }
        //Duplicados
        //Nombre
        Pais paisDuplicado = this.getPaisPorNombre(pais.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && paisDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_pais_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (paisDuplicado != null && paisDuplicado.getId_Pais() != pais.getId_Pais()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_pais_duplicado_nombre"));
            }
        }
    }

    @Override
    public void eliminar(Pais pais) {
        pais.setEliminado(true);
        paisRepository.actualizar(pais);
    }

    @Override
    public void actualizar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, pais);
        paisRepository.actualizar(pais);
    }

    @Override
    public void guardar(Pais pais) {
        this.validarOperacion(TipoDeOperacion.ALTA, pais);
        paisRepository.guardar(pais);
    }
}