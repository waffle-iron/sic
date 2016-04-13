package sic.service.impl;

import sic.service.IGastoService;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.modelo.Gasto;
import sic.repository.IGastoRepository;
import sic.service.ServiceException;

@Service
public class GastoServiceImpl implements IGastoService {

    private final IGastoRepository gastoRepository;

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
        if (gastoRepository.getCajaPorID(gasto.getId_Gasto(), gasto.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_gasto_duplicada"));
        }
    }

    @Override
    public void guardar(Gasto gasto) {
        this.validarGasto(gasto);
        gastoRepository.guardar(gasto);
    }

    @Override
    public List<Object> getGastosPorFecha(Long id_Empresa, Date desde, Date hasta) {
        return gastoRepository.getGastosPorFecha(id_Empresa, desde, hasta);
    }

    @Override
    public List<Object> getGastosPorFechaYFormaDePago(Long id_Empresa, Long id_FormaDePago, Date desde, Date hasta) {
        return gastoRepository.getGastosPorFechaYFormaDePago(id_Empresa, id_FormaDePago, desde, hasta);
    }

    @Override
    public void actualizar(Gasto gasto) {
        gastoRepository.actualizar(gasto);
    }

    @Override
    public long getUltimoNumeroDeCaja(long id_Empresa) {
        return gastoRepository.getUltimoNumeroDeGasto(id_Empresa);
    }

}
