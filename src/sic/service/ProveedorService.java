package sic.service;

import sic.modelo.BusquedaProveedorCriteria;
import java.util.List;
import java.util.ResourceBundle;
import sic.repository.ProveedorRepository;
import sic.modelo.Empresa;
import sic.modelo.Proveedor;
import sic.util.Validator;

public class ProveedorService {

    private final ProveedorRepository modeloProveedor = new ProveedorRepository();

    public List<Proveedor> getProveedores(Empresa empresa) {
        return modeloProveedor.getProveedores(empresa);
    }

    public List<Proveedor> buscarProveedores(BusquedaProveedorCriteria criteria) {
        //@Todo No debe verificar contra la palabra "Todos/as". Usar el boolean asociado a ese campo
        //Pais
        if (criteria.getPais().getNombre().equals("Todos")) {
            criteria.setBuscaPorPais(false);
        }
        //Provincia
        if (criteria.getProvincia().getNombre().equals("Todas")) {
            criteria.setBuscaPorProvincia(false);
        }
        //Localidad
        if (criteria.getLocalidad().getNombre().equals("Todas")) {
            criteria.setBuscaPorLocalidad(false);
        }
        return modeloProveedor.buscarProveedores(criteria);
    }

    public Proveedor getProveedorPorCodigo(String codigo, Empresa empresa) {
        return modeloProveedor.getProveedorPorCodigo(codigo, empresa);
    }

    public Proveedor getProveedorPorId_Fiscal(String id_Fiscal, Empresa empresa) {
        return modeloProveedor.getProveedorPorId_Fiscal(id_Fiscal, empresa);
    }

    public Proveedor getProveedorPorRazonSocial(String razonSocial, Empresa empresa) {
        return modeloProveedor.getProveedorPorRazonSocial(razonSocial, empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Proveedor proveedor) {
        //Entrada de Datos
        if (!Validator.esEmailValido(proveedor.getEmail())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_email_invalido"));
        }
        //Requeridos
        if (Validator.esVacio(proveedor.getRazonSocial())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_razonSocial_vacia"));
        }
        if (proveedor.getCondicionIVA() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_condicionIVA_vacia"));
        }
        if (proveedor.getLocalidad() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_localidad_vacia"));
        }
        if (proveedor.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_empresa_vacia"));
        }
        //Duplicados
        //Codigo
        if (!proveedor.getCodigo().equals("")) {
            Proveedor proveedorDuplicado = this.getProveedorPorCodigo(proveedor.getCodigo(), proveedor.getEmpresa());
            if (operacion.equals(TipoDeOperacion.ACTUALIZACION)
                    && proveedorDuplicado != null
                    && proveedorDuplicado.getId_Proveedor() != proveedor.getId_Proveedor()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_proveedor_duplicado_codigo"));
            }
            if (operacion.equals(TipoDeOperacion.ALTA)
                    && proveedorDuplicado != null
                    && !proveedor.getCodigo().equals("")) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_proveedor_duplicado_codigo"));
            }
        }
        //ID Fiscal
        if (!proveedor.getId_Fiscal().equals("")) {
            Proveedor proveedorDuplicado = this.getProveedorPorId_Fiscal(proveedor.getId_Fiscal(), proveedor.getEmpresa());
            if (operacion.equals(TipoDeOperacion.ACTUALIZACION)
                    && proveedorDuplicado != null
                    && proveedorDuplicado.getId_Proveedor() != proveedor.getId_Proveedor()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_proveedor_duplicado_idFiscal"));
            }
            if (operacion.equals(TipoDeOperacion.ALTA)
                    && proveedorDuplicado != null
                    && !proveedor.getId_Fiscal().equals("")) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_proveedor_duplicado_idFiscal"));
            }
        }
        //Razon social
        Proveedor proveedorDuplicado = this.getProveedorPorRazonSocial(proveedor.getRazonSocial(), proveedor.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && proveedorDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_proveedor_duplicado_razonSocial"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (proveedorDuplicado != null && proveedorDuplicado.getId_Proveedor() != proveedor.getId_Proveedor()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_proveedor_duplicado_razonSocial"));
            }
        }
    }

    public void guardar(Proveedor proveedor) {
        this.validarOperacion(TipoDeOperacion.ALTA, proveedor);
        modeloProveedor.guardar(proveedor);
    }

    public void actualizar(Proveedor proveedor) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, proveedor);
        modeloProveedor.actualizar(proveedor);
    }

    public void eliminar(Proveedor proveedor) {
        proveedor.setEliminado(true);
        modeloProveedor.actualizar(proveedor);
    }
}