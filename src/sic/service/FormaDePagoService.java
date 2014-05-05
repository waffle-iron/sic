package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.FormaDePagoRepository;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.util.Validator;

public class FormaDePagoService {

    private FormaDePagoRepository modeloFormaDePago = new FormaDePagoRepository();

    public List<FormaDePago> getFormasDePago(Empresa empresa) {
        return modeloFormaDePago.getFormasDePago(empresa);
    }

    public FormaDePago getFormasDePagoPorId(long id) {
        return modeloFormaDePago.getFormaDePagoPorId(id);
    }

    public FormaDePago getFormaDePagoPredeterminada(Empresa empresa) {
        return modeloFormaDePago.getFormaDePagoPredeterminado(empresa);
    }

    public void setFormaDePagoPredeterminada(FormaDePago formaDePago) {
        //antes de setear como predeterminado, busca si ya existe
        //otro como predeterminado y cambia su estado.
        FormaDePago formaPredeterminadaAnterior = modeloFormaDePago.getFormaDePagoPredeterminado(formaDePago.getEmpresa());
        if (formaPredeterminadaAnterior != null) {
            formaPredeterminadaAnterior.setPredeterminado(false);
            modeloFormaDePago.actualizar(formaPredeterminadaAnterior);
        }
        formaDePago.setPredeterminado(true);
        modeloFormaDePago.actualizar(formaDePago);
    }

    private void validarOperacion(FormaDePago formaDePago) {
        //Requeridos
        if (Validator.esVacio(formaDePago.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_formaDePago_vacio_nombre"));
        }
        if (formaDePago.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_formaDePago_empresa_vacio"));
        }
        //Duplicados
        //Nombre
        if (modeloFormaDePago.getFormaDePagoPorNombre(formaDePago.getNombre(), formaDePago.getEmpresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_formaDePago_duplicado_nombre"));
        }
    }

    public void guardar(FormaDePago formaDePago) {
        this.validarOperacion(formaDePago);
        modeloFormaDePago.guardar(formaDePago);
    }

    public void eliminar(FormaDePago formaDePago) {
        formaDePago.setEliminada(true);
        modeloFormaDePago.actualizar(formaDePago);
    }
}