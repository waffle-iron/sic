package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.ICondicionIVAService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class CondicionDeIVAServiceImpl implements ICondicionIVAService {

    private final ICondicionIVARepository condicionIVARepository;

    @Autowired
    public CondicionDeIVAServiceImpl(ICondicionIVARepository condicionIVARepository) {
        this.condicionIVARepository = condicionIVARepository;
    }

    @Override
    public List<CondicionIVA> getCondicionesIVA() {
        return condicionIVARepository.getCondicionesIVA();
    }

    @Override
    @Transactional
    public void actualizar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVA);
        condicionIVARepository.actualizar(condicionIVA);
    }

    @Override
    @Transactional
    public void guardar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);
        condicionIVARepository.guardar(condicionIVA);
    }

    @Override
    @Transactional
    public void eliminar(CondicionIVA condicionIVA) {
        condicionIVA.setEliminada(true);
        condicionIVARepository.actualizar(condicionIVA);
    }

    @Override
    public CondicionIVA getCondicionIVAPorNombre(String nombre) {
        return condicionIVARepository.getCondicionIVAPorNombre(nombre);
    }

    @Override
    public void validarOperacion(TipoDeOperacion operacion, CondicionIVA condicionIVA) {
        //Requeridos
        if (Validator.esVacio(condicionIVA.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_requerido"));
        }
        //Duplicados
        CondicionIVA condicionIVADuplicada = this.getCondicionIVAPorNombre(condicionIVA.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && condicionIVADuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_duplicado"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (condicionIVADuplicada != null && condicionIVADuplicada.getId_CondicionIVA() != condicionIVA.getId_CondicionIVA()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_condicionIVA_nombre_duplicado"));
            }
        }
    }
}
