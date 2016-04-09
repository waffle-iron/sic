package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.repository.IMedidaRepository;
import sic.service.IMedidaService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class MedidaServiceImpl implements IMedidaService {

    private final IMedidaRepository medidaRepository;

    @Autowired
    public MedidaServiceImpl(IMedidaRepository medidaRepository) {
        this.medidaRepository = medidaRepository;
    }

    @Override
    public List<Medida> getUnidadMedidas(Empresa empresa) {
        return medidaRepository.getUnidadMedidas(empresa);
    }

    @Override
    public Medida getMedidaPorNombre(String nombre, Empresa empresa) {
        return medidaRepository.getMedidaPorNombre(nombre, empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Medida medida) {
        //Requeridos
        if (Validator.esVacio(medida.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_medida_vacio_nombre"));
        }
        //Duplicados
        //Nombre
        Medida medidaDuplicada = this.getMedidaPorNombre(medida.getNombre(), medida.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && medidaDuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_medida_duplicada_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (medidaDuplicada != null && medidaDuplicada.getId_Medida() != medida.getId_Medida()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_medida_duplicada_nombre"));
            }
        }
    }

    @Override
    @Transactional
    public void actualizar(Medida medida) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, medida);
        medidaRepository.actualizar(medida);
    }

    @Override
    @Transactional
    public void guardar(Medida medida) {
        this.validarOperacion(TipoDeOperacion.ALTA, medida);
        medidaRepository.guardar(medida);
    }

    @Override
    @Transactional
    public void eliminar(Medida medida) {
        medida.setEliminada(true);
        medidaRepository.actualizar(medida);
    }
}
