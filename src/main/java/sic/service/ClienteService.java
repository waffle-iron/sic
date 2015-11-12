package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.repository.jpa.ClienteRepositoryJPAImpl;
import sic.modelo.BusquedaClienteCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.util.Validator;

public class ClienteService {

    private final ClienteRepositoryJPAImpl modeloCliente = new ClienteRepositoryJPAImpl();

    public Cliente getClientePorId(long id_Cliente) {
        return modeloCliente.getClientePorId(id_Cliente);
    }

    public List<Cliente> getClientes(Empresa empresa) {
        return modeloCliente.getClientes(empresa);
    }

    public List<Cliente> getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(String criteria, Empresa empresa) {
        return modeloCliente.getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(criteria, empresa);
    }

    public Cliente getClientePorRazonSocial(String razonSocial, Empresa empresa) {
        return modeloCliente.getClientePorRazonSocial(razonSocial, empresa);
    }

    public Cliente getClientePorIdFiscal(String idFiscal, Empresa empresa) {
        return modeloCliente.getClientePorId_Fiscal(idFiscal, empresa);
    }

    public Cliente getClientePredeterminado(Empresa empresa) {
        return modeloCliente.getClientePredeterminado(empresa);
    }

    /**
     * Establece el cliente pasado como parametro como predeterminado. Antes de
     * establecer el cliente como predeterminado, busca si ya existe otro como
     * predeterminado y cambia su estado.
     *
     * @param cliente Cliente candidato a predeterminado.
     */
    public void setClientePredeterminado(Cliente cliente) {
        Cliente clientePredeterminadoAnterior = modeloCliente.getClientePredeterminado(cliente.getEmpresa());
        if (clientePredeterminadoAnterior != null) {
            clientePredeterminadoAnterior.setPredeterminado(false);
            modeloCliente.actualizar(clientePredeterminadoAnterior);
        }
        cliente.setPredeterminado(true);
        modeloCliente.actualizar(cliente);
    }

    public List<Cliente> buscarClientes(BusquedaClienteCriteria criteria) {
        //@TODO No debe verificar contra la palabra "Todos/as". Usar el boolean asociado a ese campo
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
        return modeloCliente.buscarClientes(criteria);
    }

    private void validarOperacion(TipoDeOperacion operacion, Cliente cliente) {
        //Entrada de Datos
        if (!Validator.esEmailValido(cliente.getEmail())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_email_invalido"));
        }
        //Requeridos        
        if (Validator.esVacio(cliente.getRazonSocial())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_razonSocial"));
        }
        if (cliente.getCondicionIVA() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_condicionIVA"));
        }
        if (cliente.getLocalidad() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_localidad"));
        }
        if (cliente.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_vacio_empresa"));
        }
        //Duplicados
        //ID Fiscal
        if (!cliente.getId_Fiscal().equals("")) {
            Cliente clienteDuplicado = this.getClientePorIdFiscal(cliente.getId_Fiscal(), cliente.getEmpresa());
            if (operacion.equals(TipoDeOperacion.ACTUALIZACION)
                    && clienteDuplicado != null
                    && clienteDuplicado.getId_Cliente() != cliente.getId_Cliente()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_cliente_duplicado_idFiscal"));
            }
            if (operacion.equals(TipoDeOperacion.ALTA)
                    && clienteDuplicado != null
                    && !cliente.getId_Fiscal().equals("")) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_cliente_duplicado_idFiscal"));
            }
        }
        //Razon Social
        Cliente clienteDuplicado = this.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa());
        if (operacion.equals(TipoDeOperacion.ALTA) && clienteDuplicado != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_cliente_duplicado_razonSocial"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (clienteDuplicado != null && clienteDuplicado.getId_Cliente() != cliente.getId_Cliente()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_cliente_duplicado_razonSocial"));
            }
        }
    }

    public void guardar(Cliente cliente) {
        this.validarOperacion(TipoDeOperacion.ALTA, cliente);
        modeloCliente.guardar(cliente);
    }

    public void actualizar(Cliente cliente) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, cliente);
        modeloCliente.actualizar(cliente);
    }

    public void eliminar(Cliente cliente) {
        cliente.setEliminado(true);
        modeloCliente.actualizar(cliente);
    }
}
