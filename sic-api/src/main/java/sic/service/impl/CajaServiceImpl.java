package sic.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import sic.service.ICajaService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.BusquedaCajaCriteria;
import sic.modelo.Caja;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;
import sic.modelo.Gasto;
import sic.modelo.Pago;
import sic.modelo.EstadoCaja;
import sic.modelo.FacturaCompra;
import sic.modelo.FacturaVenta;
import sic.modelo.QCaja;
import sic.modelo.Rol;
import sic.service.BusinessServiceException;
import sic.service.IEmpresaService;
import sic.service.IFormaDePagoService;
import sic.service.IGastoService;
import sic.service.IPagoService;
import sic.service.IUsuarioService;
import sic.util.FormatterFechaHora;
import sic.util.Validator;
import sic.repository.CajaRepository;

@Service
public class CajaServiceImpl implements ICajaService {

    private final CajaRepository cajaRepository;
    private final IFormaDePagoService formaDePagoService;
    private final IPagoService pagoService;
    private final IGastoService gastoService;
    private final IEmpresaService empresaService;
    private final IUsuarioService usuarioService;    
    private static final Logger LOGGER = Logger.getLogger(CajaServiceImpl.class.getPackage().getName());    

    @Autowired
    public CajaServiceImpl(CajaRepository cajaRepository, IFormaDePagoService formaDePagoService,
                           IPagoService pagoService, IGastoService gastoService,
                           IEmpresaService empresaService, IUsuarioService usuarioService) {
        this.cajaRepository = cajaRepository;
        this.formaDePagoService = formaDePagoService;
        this.pagoService = pagoService;
        this.gastoService = gastoService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public void validarCaja(Caja caja) {
        //Entrada de Datos
        //Requeridos
        if (caja.getFechaApertura() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_fecha_vacia"));
        }
        if (caja.getEmpresa() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_empresa_vacia"));
        }
        if (caja.getUsuarioAbreCaja() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_usuario_vacio"));
        }
        //Administrador
        if (!usuarioService.getUsuariosPorRol(Rol.ADMINISTRADOR).contains(caja.getUsuarioAbreCaja())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_usuario_no_administrador"));
        }
        //Hora de Corte
        if (caja.getFechaCorteInforme().before(new Date())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_fecha_corte_no_valida"));
        }
        //Una Caja por dia
        Caja ultimaCaja = this.getUltimaCaja(caja.getEmpresa().getId_Empresa());
        if(ultimaCaja != null && ultimaCaja.getEstado() == EstadoCaja.ABIERTA) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_anterior_abierta"));
        }
        if (ultimaCaja != null && Validator.compararDias(ultimaCaja.getFechaApertura(), caja.getFechaApertura()) >= 0) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_fecha_apertura_no_valida"));
        }
        //Duplicados        
        if (cajaRepository.findById(caja.getId_Caja()) != null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_duplicada"));
        }        
    }

    @Override
    @Transactional
    public Caja guardar(Caja caja) {
        caja.setFechaApertura(new Date());
        this.validarCaja(caja);
        caja.setNroCaja(this.getUltimoNumeroDeCaja(caja.getEmpresa().getId_Empresa()) + 1);        
        caja = cajaRepository.save(caja);
        LOGGER.warn("La Caja " + caja + " se guardó correctamente." );
        return caja;
    }

    @Override
    @Transactional
    public void actualizar(Caja caja) {        
        cajaRepository.save(caja);
    }
    
    @Override
    @Transactional
    public void eliminar(Long idCaja) {
        Caja caja = this.getCajaPorId(idCaja);
        if (caja == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_no_existente"));
        }
        caja.setEliminada(true);
        this.actualizar(caja);
    }

    @Override
    public Caja getUltimaCaja(long id_Empresa) {        
        return cajaRepository.findTopByEmpresaAndEliminadaOrderByFechaAperturaDesc(empresaService.getEmpresaPorId(id_Empresa), false);        
    }
    
    @Override
    public Caja getCajaPorId(Long id) {
        Caja caja = cajaRepository.findOne(id);
        if (caja == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_caja_no_existente"));
        }
        caja = this.cargarPagosyGastos(caja);
        caja.setTotalAfectaCaja(this.getTotalCaja(caja, true));
        caja.setTotalGeneral(this.getTotalCaja(caja, false));
        caja.setSaldoFinal(caja.getTotalGeneral());
        this.actualizar(caja);
        return caja;
    }
    
    @Override
    public int getUltimoNumeroDeCaja(long idEmpresa) {
        Caja caja = this.getUltimaCaja(idEmpresa);
        if (caja == null) {
            return 0;
        } else {
            return caja.getNroCaja();
        }
    }

    @Override
    public List<Caja> getCajas(long idEmpresa, Date desde, Date hasta) {        
        return cajaRepository.findAllByFechaAperturaBetweenAndEmpresaAndEliminada(desde, hasta, empresaService.getEmpresaPorId(idEmpresa), false);        
    }

    @Override
    public List<Caja> getCajasCriteria(BusquedaCajaCriteria criteria) {
        //Empresa
        if (criteria.getEmpresa() == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
        }
        //Fecha
        if (criteria.isBuscaPorFecha() == true && (criteria.getFechaDesde() == null || criteria.getFechaHasta() == null)) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
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
        if (criteria.getEmpresa() == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
        }
        QCaja qcaja = QCaja.caja;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qcaja.empresa.eq(criteria.getEmpresa()).and(qcaja.eliminada.eq(false)));
        if (criteria.isBuscaPorUsuario() == true) {
            builder.and(qcaja.usuarioCierraCaja.eq(criteria.getUsuario()));
        }
        if (criteria.isBuscaPorFecha() == true) {
            FormatterFechaHora formateadorFecha = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHAHORA_INTERNACIONAL);
            DateExpression<Date> fDesde = Expressions.dateTemplate(Date.class, "convert({0}, datetime)", formateadorFecha.format(criteria.getFechaDesde()));
            DateExpression<Date> fHasta = Expressions.dateTemplate(Date.class, "convert({0}, datetime)", formateadorFecha.format(criteria.getFechaHasta()));
            builder.and(qcaja.fechaApertura.between(fDesde, fHasta));
        }
        List<Caja> cajas = new ArrayList<>();
        cajaRepository.findAll(builder, new Sort(Sort.Direction.DESC, "fechaApertura")).iterator().forEachRemaining(cajas::add);
        return cajas;        
    }
  
    @Override
    @Transactional
    public Caja cerrarCaja(long idCaja, double monto, Long idUsuario, boolean scheduling) {
        Caja cajaACerrar = this.getCajaPorId(idCaja);
        cajaACerrar.setSaldoFinal(this.getTotalCaja(cajaACerrar, false));
        cajaACerrar.setSaldoReal(monto);
        if (scheduling) {
            LocalDateTime fechaCierre = LocalDateTime.ofInstant(cajaACerrar.getFechaApertura().toInstant(), ZoneId.systemDefault());
            fechaCierre = fechaCierre.withHour(23);
            fechaCierre = fechaCierre.withMinute(59);
            fechaCierre = fechaCierre.withSecond(59);
            cajaACerrar.setFechaCierre(Date.from(fechaCierre.atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            cajaACerrar.setFechaCierre(new Date());
        }
        if (idUsuario != null) {
            cajaACerrar.setUsuarioCierraCaja(usuarioService.getUsuarioPorId(idUsuario));
        }
        cajaACerrar.setEstado(EstadoCaja.CERRADA);
        this.actualizar(cajaACerrar);
        LOGGER.warn("La Caja " + cajaACerrar + " se cerró correctamente." );
        return cajaACerrar;
    }    

    @Scheduled(cron = "0 5 0 * * *") // Todos los dias a las 00:05:00
    public void cerrarCajas() {
        LOGGER.warn("Cierre automático de Cajas." + LocalDateTime.now());
        List<Empresa> empresas = this.empresaService.getEmpresas();
        for (Empresa empresa : empresas) {
            Caja ultimaCajaDeEmpresa = this.getUltimaCaja(empresa.getId_Empresa());
            if ((ultimaCajaDeEmpresa != null) && (ultimaCajaDeEmpresa.getEstado() == EstadoCaja.ABIERTA)) {
                LocalDate fechaActual = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
                Calendar fechaHoraCaja = new GregorianCalendar();
                fechaHoraCaja.setTime(ultimaCajaDeEmpresa.getFechaApertura());
                LocalDate fechaCaja = LocalDate.of(fechaHoraCaja.get(Calendar.YEAR), fechaHoraCaja.get(Calendar.MONTH) + 1, fechaHoraCaja.get(Calendar.DAY_OF_MONTH));
                if (fechaCaja.compareTo(fechaActual) < 0) {
                    this.cerrarCaja(ultimaCajaDeEmpresa.getId_Caja(), this.getTotalCaja(ultimaCajaDeEmpresa, true), ultimaCajaDeEmpresa.getUsuarioAbreCaja().getId_Usuario(), true);
                }
            }
        }
    }
    
    @Override
    public double getTotalCaja(Caja caja, boolean soloAfectaCaja) {
        List<FormaDePago> formasDePago = formaDePagoService.getFormasDePago(caja.getEmpresa());        
        double total = caja.getSaldoInicial();        
        for (FormaDePago fp : formasDePago) {
            if (soloAfectaCaja && fp.isAfectaCaja()) {
                total += this.getTotalMovimientosPorFormaDePago(caja, fp);                
            } else if (!soloAfectaCaja) {
                total += this.getTotalMovimientosPorFormaDePago(caja, fp);
            }
        }
        return total;
    }
    
    private double getTotalMovimientosPorFormaDePago(Caja caja, FormaDePago fdp) {
        double pagosVentasTotal = 0.0;
        double pagosComprasTotal = 0.0;
        double gastosTotal = 0.0;
        LocalDateTime ldt = caja.getFechaApertura().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (caja.getFechaCierre() == null) {
            ldt = ldt.withHour(23);
            ldt = ldt.withMinute(59);
            ldt = ldt.withSecond(59);
        } else {
            ldt = caja.getFechaCierre().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        List<Pago> pagos = pagoService.getPagosEntreFechasYFormaDePago(caja.getEmpresa().getId_Empresa(), fdp.getId_FormaDePago(),
                caja.getFechaApertura(), Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
        List<Gasto> gastos = gastoService.getGastosEntreFechasYFormaDePago(caja.getEmpresa().getId_Empresa(), fdp.getId_FormaDePago(),
                caja.getFechaApertura(), Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
        for (Pago pago : pagos) {
            if (pago.getFactura() instanceof FacturaVenta) {
                pagosVentasTotal += pago.getMonto();
            } else if (pago.getFactura() instanceof FacturaCompra) {
                pagosComprasTotal += pago.getMonto();
            }
        }
        gastosTotal = gastos.stream()
                            .map((gasto) -> gasto.getMonto())
                            .reduce(gastosTotal, (accumulator, _item) -> accumulator + _item);
        return pagosVentasTotal - pagosComprasTotal - gastosTotal;
    }

    private Caja cargarPagosyGastos(Caja caja) {
        Map<Long, Double> totalesPorFomaDePago = new HashMap<>();
        for (FormaDePago fdp : formaDePagoService.getFormasDePago(caja.getEmpresa())) {
            double total = this.getTotalMovimientosPorFormaDePago(caja, fdp);
            if (total != 0) {
                totalesPorFomaDePago.put(fdp.getId_FormaDePago(), total);
            }
        }
        caja.setTotalesPorFomaDePago(totalesPorFomaDePago);
        return caja;
    }

}
