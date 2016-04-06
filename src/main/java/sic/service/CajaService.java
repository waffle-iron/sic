package sic.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import sic.modelo.BusquedaCajaCriteria;
import sic.modelo.Caja;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.Gasto;
import sic.repository.CajaRepository;

public class CajaService {

    private final CajaRepository cajaRepository = new CajaRepository();

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
        if (caja.getUsuarioAbreCaja() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_usuario_vacio"));
        }
        //Duplicados
        if (cajaRepository.getCajaPorID(caja.getId_Caja(), caja.getEmpresa().getId_Empresa()) != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_duplicada"));
        }
    }

    public void guardar(Caja caja) {
        this.validarCaja(caja);
        cajaRepository.guardar(caja);
    }

    public void actualizar(Caja caja) {
        cajaRepository.actualizar(caja);
    }

    public Caja getUltimaCaja(long id_Empresa) {
        return cajaRepository.getUltimaCaja(id_Empresa);
    }

    public Caja getCajaPorId(long id_Caja, long id_Empresa) {
        return cajaRepository.getCajaPorID(id_Caja, id_Empresa);
    }

    public int getUltimoNumeroDeCaja(long id_Empresa) {
        return cajaRepository.getUltimoNumeroDeCaja(id_Empresa);
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

    public List<Caja> getCajas(long id_Empresa, Date desde, Date hasta) {
        return cajaRepository.getCajas(id_Empresa, desde, hasta);
    }

    public List<Caja> getCajasCriteria(BusquedaCajaCriteria criteria) {
        if (criteria.isBuscaPorFecha() == true & (criteria.getFechaDesde() == null | criteria.getFechaHasta() == null)) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_fechas_invalidas"));
        }
        if (criteria.isBuscaPorFecha() == true) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(criteria.getFechaDesde());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            criteria.setFechaDesde(cal.getTime());
            cal.setTime(criteria.getFechaHasta());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            criteria.setFechaHasta(cal.getTime());
        }
        return cajaRepository.getCajasCriteria(criteria);
    }

}
