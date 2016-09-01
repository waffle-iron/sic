package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.repository.IFormaDePagoRepository;
import sic.service.IFormaDePagoService;
import sic.service.ServiceException;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class FormaDePagoServiceImpl implements IFormaDePagoService {

    private final IFormaDePagoRepository formaDePagoRepository;
    private static final Logger LOGGER = Logger.getLogger(FormaDePagoServiceImpl.class.getPackage().getName());

    @Autowired
    public FormaDePagoServiceImpl(IFormaDePagoRepository formaDePagoRepository) {
        try {
            this.formaDePagoRepository = formaDePagoRepository;

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<FormaDePago> getFormasDePago(Empresa empresa) {
        try {
            return formaDePagoRepository.getFormasDePago(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public FormaDePago getFormasDePagoPorId(long id) {
        try {
            return formaDePagoRepository.getFormaDePagoPorId(id);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public FormaDePago getFormaDePagoPredeterminada(Empresa empresa) {
        try {
            return formaDePagoRepository.getFormaDePagoPredeterminado(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void setFormaDePagoPredeterminada(FormaDePago formaDePago) {
        //antes de setear como predeterminado, busca si ya existe
        //otro como predeterminado y cambia su estado.
        try {
            FormaDePago formaPredeterminadaAnterior = formaDePagoRepository.getFormaDePagoPredeterminado(formaDePago.getEmpresa());
            if (formaPredeterminadaAnterior != null) {
                formaPredeterminadaAnterior.setPredeterminado(false);
                formaDePagoRepository.actualizar(formaPredeterminadaAnterior);
            }
            formaDePago.setPredeterminado(true);
            formaDePagoRepository.actualizar(formaDePago);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
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
        try {
            if (formaDePagoRepository.getFormaDePagoPorNombre(formaDePago.getNombre(), formaDePago.getEmpresa()) != null) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_formaDePago_duplicado_nombre"));
            }
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(FormaDePago formaDePago) {
        this.validarOperacion(formaDePago);
        try {
            formaDePagoRepository.guardar(formaDePago);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(FormaDePago formaDePago) {
        formaDePago.setEliminada(true);
        try {
            formaDePagoRepository.actualizar(formaDePago);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
