package sic.service;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.modelo.Gasto;
import sic.modelo.Usuario;
import sic.repository.GastoRepository;

public class GastoService {

    private final GastoRepository gastoRepository = new GastoRepository();
    private final EmpresaService empresaService = new EmpresaService();
    private final UsuarioService usuarioService = new UsuarioService();

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

    public void guardar(Gasto gasto) {
        this.validarGasto(gasto);
        gastoRepository.guardar(gasto);
    }

    public List<Object> getGastosPorFecha(Long id_Empresa, Date desde, Date hasta) {
        return gastoRepository.getGastosPorFecha(id_Empresa, desde, hasta);
    }

    public List<Object> getGastosPorFechaYFormaDePago(Long id_Empresa, Long id_FormaDePago, Date desde, Date hasta) {
        return gastoRepository.getGastosPorFechaYFormaDePago(id_Empresa, id_FormaDePago, desde, hasta);
    }

    public void actualizar(Gasto gasto) {
        gastoRepository.actualizar(gasto);
    }

    public long getUltimoNumeroDeCaja(long id_Empresa) {
        return gastoRepository.getUltimoNumeroDeGasto(id_Empresa);
    }

}
