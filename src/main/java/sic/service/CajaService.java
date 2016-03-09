package sic.service;

import java.util.ResourceBundle;
import sic.modelo.Caja;
import sic.repository.CajaRepository;

public class CajaService {

    private final CajaRepository controlCajaRepository = new CajaRepository();

    public void validarCaja(Caja caja) {
        //Entrada de Datos
        //Requeridos
        if (caja.getFechaApertura() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_fecha_vacia"));
        }
        if (caja.getEmpresa() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_empresa_vacia"));
        }
        if (caja.getUsuario() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_usuario_vacio"));
        }
        //Duplicados
        if (controlCajaRepository.getCajaPorID(caja.getId_Caja(), caja.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_duplicada"));
        }
    }

    public void guardar(Caja caja) {
        this.validarCaja(caja);
        controlCajaRepository.guardar(caja);
    }

    public void actualizar(Caja caja) {
        controlCajaRepository.actualizar(caja);
    }

    public Caja getCajaSinArqueo(long id_Empresa) {
        return controlCajaRepository.getControlCajaSinArqueo(id_Empresa);
    }

    public Caja getCajaSinArqueoPorFormaDePago(long id_Empresa, long id_FormaDePago) {
        return controlCajaRepository.getCajaPorFormaDePago(id_Empresa, id_FormaDePago);
    }

    public int getUltimoNumeroDeCaja(long id_Empresa) {
        return controlCajaRepository.getUltimoNumeroDeCaja(id_Empresa);
    }

}
