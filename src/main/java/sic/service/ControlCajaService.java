package sic.service;

import java.util.ResourceBundle;
import sic.modelo.ControlCaja;
import sic.repository.ControlCajaRepository;

public class ControlCajaService {

    private final ControlCajaRepository controlCajaRepository = new ControlCajaRepository();

    public void validarControlCaja(ControlCaja controlCaja) {
        //Entrada de Datos
        //Requeridos
        if (controlCaja.getFecha() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_fecha_vacia"));
        }
        if (controlCaja.getFacturas().isEmpty()) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_facturas_vacias"));
        }
        if (controlCaja.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_empresa_vacia"));
        }
        if (controlCaja.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_usuario_vacio"));
        }
        //Duplicados
        if (controlCajaRepository.getCajaPorNro(controlCaja.getNroCaja(), controlCaja.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_duplicada"));
        }
    }

    public void guardar(ControlCaja controlCaja) {
        this.validarControlCaja(controlCaja);
        controlCajaRepository.guardar(controlCaja);
    }

    public ControlCaja getControlCajaSinArqueo(long idEmpresa) {
        return controlCajaRepository.getControlCajaSinArqueo(idEmpresa);
    }

}
