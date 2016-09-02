package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.BusquedaClienteCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.repository.IClienteRepository;
import sic.service.IClienteService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Utilidades;
import sic.util.Validator;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;
    private static final Logger LOGGER = Logger.getLogger(ClienteServiceImpl.class.getPackage().getName());

    @Autowired
    public ClienteServiceImpl(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente getClientePorId(long id_Cliente) {
        try {
            return clienteRepository.getClientePorId(id_Cliente);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<Cliente> getClientes(Empresa empresa) {
        try {
            return clienteRepository.getClientes(empresa);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<Cliente> getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(String criteria, Empresa empresa) {
        try {
            return clienteRepository.getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(criteria, empresa);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Cliente getClientePorRazonSocial(String razonSocial, Empresa empresa) {
        try {
            return clienteRepository.getClientePorRazonSocial(razonSocial, empresa);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Cliente getClientePorIdFiscal(String idFiscal, Empresa empresa) {
        try{
        return clienteRepository.getClientePorId_Fiscal(idFiscal, empresa);
        
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public Cliente getClientePredeterminado(Empresa empresa) {
        try {
            return clienteRepository.getClientePredeterminado(empresa);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    /**
     * Establece el cliente pasado como parametro como predeterminado. Antes de
     * establecer el cliente como predeterminado, busca si ya existe otro como
     * predeterminado y cambia su estado.
     *
     * @param cliente Cliente candidato a predeterminado.
     */
    @Override
    @Transactional
    public void setClientePredeterminado(Cliente cliente) {
        try {
            Cliente clientePredeterminadoAnterior = clienteRepository.getClientePredeterminado(cliente.getEmpresa());
            if (clientePredeterminadoAnterior != null) {
                clientePredeterminadoAnterior.setPredeterminado(false);
                clienteRepository.actualizar(clientePredeterminadoAnterior);
            }
            cliente.setPredeterminado(true);
            clienteRepository.actualizar(cliente);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
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
        try {
            return clienteRepository.buscarClientes(criteria);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public void validarOperacion(TipoDeOperacion operacion, Cliente cliente) {
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

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        try {
            this.validarOperacion(TipoDeOperacion.ALTA, cliente);
            clienteRepository.guardar(cliente);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(Cliente cliente) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, cliente);
        try {
            clienteRepository.actualizar(cliente);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        cliente.setEliminado(true);
        try {
            clienteRepository.actualizar(cliente);
            
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }
}
