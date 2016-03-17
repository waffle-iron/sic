package sic.service;

import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.Caja;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Gasto;
import sic.repository.CajaRepository;

public class CajaService {

    private final CajaRepository CajaRepository = new CajaRepository();

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
        if (CajaRepository.getCajaPorID(caja.getId_Caja(), caja.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_duplicada"));
        }
    }

    public void guardar(Caja caja) {
        this.validarCaja(caja);
        CajaRepository.guardar(caja);
    }

    public void actualizar(Caja caja) {
        CajaRepository.actualizar(caja);
    }

    public Caja getCajaSinArqueo(long id_Empresa) {
        return CajaRepository.getCajaSinArqueo(id_Empresa);
    }

    public int getUltimoNumeroDeCaja(long id_Empresa) {
        return CajaRepository.getUltimoNumeroDeCaja(id_Empresa);
    }

    public double calcularTotalPorMovimiento(List<Object> movimientos) {
        double total = 0.0;
        for (Object movimiento : movimientos) {
            if (movimiento instanceof FacturaVenta) {
                total += ((FacturaVenta) movimiento).getTotal();
            }
            if (movimiento instanceof FacturaCompra) {
                total -= ((FacturaCompra) movimiento).getTotal();
            }
            if (movimiento instanceof Gasto) {
                total -= ((Gasto) movimiento).getMonto();
            }
        }
        return total;
    }

}
