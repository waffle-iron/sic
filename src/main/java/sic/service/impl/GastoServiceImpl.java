package sic.service.impl;

import sic.service.IGastoService;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.Gasto;
import sic.repository.IGastoRepository;
import sic.service.ServiceException;
import sic.util.Utilidades;

@Service
public class GastoServiceImpl implements IGastoService {

    private final IGastoRepository gastoRepository;
    private static final Logger LOGGER = Logger.getLogger(GastoServiceImpl.class.getPackage().getName());

    @Autowired
    public GastoServiceImpl(IGastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    @Override
    public void validarGasto(Gasto gasto) {
        //Entrada de Datos
        //Requeridos
        if (gasto.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_gasto_fecha_vacia"));
        }
        if (gasto.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_gasto_empresa_vacia"));
        }
        if (gasto.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_gasto_usuario_vacio"));
        }
        //Duplicados
        try {
            if (gastoRepository.getCajaPorID(gasto.getId_Gasto(), gasto.getEmpresa().getId_Empresa()) != null) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_gasto_duplicada"));
            }
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void guardar(Gasto gasto) {
        this.validarGasto(gasto);
        try {
            gastoRepository.guardar(gasto);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<Object> getGastosPorFecha(Long id_Empresa, Date desde, Date hasta) {
        try {
            return gastoRepository.getGastosPorFecha(id_Empresa, desde, hasta);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public List<Object> getGastosPorFechaYFormaDePago(Long id_Empresa, Long id_FormaDePago, Date desde, Date hasta) {
        try {
            return gastoRepository.getGastosPorFechaYFormaDePago(id_Empresa, id_FormaDePago, desde, hasta);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    @Transactional
    public void actualizar(Gasto gasto) {
        try {
            gastoRepository.actualizar(gasto);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public long getUltimoNumeroDeCaja(long id_Empresa) {
        try {
            return gastoRepository.getUltimoNumeroDeGasto(id_Empresa);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

    @Override
    public int getUltimoNumeroDeGasto(long id_empresa) {
        try {
            return gastoRepository.getUltimoNumeroDeGasto(id_empresa);
        } catch (PersistenceException ex) {
            throw new ServiceException(Utilidades.escribirLogErrorAccesoDatos(LOGGER), ex);
        }
    }

}
