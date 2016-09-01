package sic.service.impl;

import sic.modelo.EmpresaActiva;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.IConfiguracionDelSistemaRepository;
import sic.repository.IEmpresaRepository;
import sic.service.IEmpresaService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final IConfiguracionDelSistemaRepository configuracionDelSistemaRepository;
    private static final Logger LOGGER = Logger.getLogger(EmpresaServiceImpl.class.getPackage().getName());

    @Autowired
    public EmpresaServiceImpl(IEmpresaRepository empresaRepository,
            IConfiguracionDelSistemaRepository configuracionDelSistemaRepository) {

        this.empresaRepository = empresaRepository;
        this.configuracionDelSistemaRepository = configuracionDelSistemaRepository;
    }

    @Override
    public List<Empresa> getEmpresas() {
        try {
            return empresaRepository.getEmpresas();

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Empresa getEmpresaPorNombre(String nombre) {
        try {
            return empresaRepository.getEmpresaPorNombre(nombre);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Empresa getEmpresaPorCUIP(long cuip) {
        try {
            return empresaRepository.getEmpresaPorCUIP(cuip);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public EmpresaActiva getEmpresaActiva() {
        return EmpresaActiva.getInstance();
    }

    @Override
    public void setEmpresaActiva(Empresa empresa) {
        EmpresaActiva empresaActiva = EmpresaActiva.getInstance();
        empresaActiva.setEmpresa(empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Empresa empresa) {
        //Entrada de Datos
        if (!Validator.esEmailValido(empresa.getEmail())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_email_invalido"));
        }
        //Requeridos
        if (Validator.esVacio(empresa.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_nombre"));
        }
        if (Validator.esVacio(empresa.getDireccion())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_direccion"));
        }
        if (empresa.getCondicionIVA() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_condicionIVA"));
        }
        if (empresa.getLocalidad() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_localidad"));
        }
        //Duplicados
        //Nombre
        Empresa empresaDuplicada = this.getEmpresaPorNombre(empresa.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_empresa_duplicado_nombre"));
            }
        }
        //CUIP
        empresaDuplicada = this.getEmpresaPorCUIP(empresa.getCuip());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null && empresa.getCuip() != 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_cuip"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa() && empresa.getCuip() != 0) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_empresa_duplicado_cuip"));
            }
        }
    }

    private void crearConfiguracionDelSistema(Empresa empresa) {
        ConfiguracionDelSistema cds = new ConfiguracionDelSistema();
        cds.setCantidadMaximaDeRenglonesEnFactura(28);
        cds.setUsarFacturaVentaPreImpresa(false);
        cds.setEmpresa(getEmpresaPorNombre(empresa.getNombre()));
        try {
            configuracionDelSistemaRepository.guardar(cds);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ALTA, empresa);
        try {
            empresaRepository.guardar(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
        crearConfiguracionDelSistema(empresa);
    }

    @Override
    @Transactional
    public void actualizar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ACTUALIZACION, empresa);
        try {
            empresaRepository.actualizar(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(Empresa empresa) {
        empresa.setEliminada(true);
        try {
            empresaRepository.actualizar(empresa);

        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
