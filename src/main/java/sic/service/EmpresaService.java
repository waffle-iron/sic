package sic.service;

import sic.modelo.EmpresaActiva;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.ConfiguracionDelSistema;
import sic.repository.jpa.EmpresaRepositoryJPAImpl;
import sic.modelo.Empresa;
import sic.repository.jpa.ConfiguracionDelSistemaRepositoryJPAImpl;
import sic.util.Validator;

public class EmpresaService {

    private final EmpresaRepositoryJPAImpl modeloEmpresa = new EmpresaRepositoryJPAImpl();
    private final ConfiguracionDelSistemaRepositoryJPAImpl configuracionDelSistemaRepository = new ConfiguracionDelSistemaRepositoryJPAImpl();

    public List<Empresa> getEmpresas() {
        return modeloEmpresa.getEmpresas();
    }

    public Empresa getEmpresaPorNombre(String nombre) {
        return modeloEmpresa.getEmpresaPorNombre(nombre);
    }

    public Empresa getEmpresaPorCUIP(long cuip) {
        return modeloEmpresa.getEmpresaPorCUIP(cuip);
    }

    public EmpresaActiva getEmpresaActiva() {
        return EmpresaActiva.getInstance();
    }

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
        configuracionDelSistemaRepository.guardar(cds);
    }

    public void guardar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ALTA, empresa);
        modeloEmpresa.guardar(empresa);
        crearConfiguracionDelSistema(empresa);
    }

    public void actualizar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ACTUALIZACION, empresa);
        modeloEmpresa.actualizar(empresa);
    }

    public void eliminar(Empresa empresa) {
        empresa.setEliminada(true);
        modeloEmpresa.actualizar(empresa);
    }
}
