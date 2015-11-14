package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.modelo.Empresa;
import sic.modelo.Rubro;
import sic.repository.IRubroRepository;
import sic.service.IRubroService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class RubroServiceImpl implements IRubroService {

    private final IRubroRepository rubroRepository;

    @Autowired
    public RubroServiceImpl(IRubroRepository rubroRepository) {
        this.rubroRepository = rubroRepository;
    }       

    @Override
    public List<Rubro> getRubros(Empresa empresa) {
        return rubroRepository.getRubros(empresa);
    }

    @Override
    public Rubro getRubroPorNombre(String nombre, Empresa empresa) {
        return rubroRepository.getRubroPorNombre(nombre, empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Rubro rubro) {
        //Requeridos
        if (Validator.esVacio(rubro.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle(
                    "Mensajes").getString("mensaje_rubro_nombre_vacio"));
        }
        //Duplicados
        //Nombre
        Rubro rubroDuplicado = this.getRubroPorNombre(rubro.getNombre(), rubro.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && rubroDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_rubro_nombre_duplicado"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (rubroDuplicado != null && rubroDuplicado.getId_Rubro() != rubro.getId_Rubro()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_rubro_nombre_duplicado"));
            }
        }
    }

    @Override
    public void actualizar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, rubro);
        rubroRepository.actualizar(rubro);
    }

    @Override
    public void guardar(Rubro rubro) {
        this.validarOperacion(TipoDeOperacion.ALTA, rubro);
        rubroRepository.guardar(rubro);
    }

    @Override
    public void eliminar(Rubro rubro) {
        rubro.setEliminado(true);
        rubroRepository.actualizar(rubro);
    }
}