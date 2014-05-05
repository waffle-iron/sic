package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.MedidaRepository;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.util.Validator;

public class MedidaService {

    private MedidaRepository modeloMedida = new MedidaRepository();

    public List<Medida> getUnidadMedidas(Empresa empresa) {
        return modeloMedida.getUnidadMedidas(empresa);
    }

    public Medida getMedidaPorNombre(String nombre, Empresa empresa) {
        return modeloMedida.getMedidaPorNombre(nombre, empresa);
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

    public void actualizar(Medida medida) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, medida);
        modeloMedida.actualizar(medida);
    }

    public void guardar(Medida medida) {
        this.validarOperacion(TipoDeOperacion.ALTA, medida);
        modeloMedida.guardar(medida);
    }

    public void eliminar(Medida medida) {
        medida.setEliminada(true);
        modeloMedida.actualizar(medida);
    }
}