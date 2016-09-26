package sic.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.Factura;
import sic.modelo.Pago;
import sic.service.IFacturaService;
import sic.service.IFormaDePagoService;
import sic.service.IPagoService;

@RestController
@RequestMapping("/api/v1")
public class PagoController {
    
    private final IPagoService pagoService;
    private final IFacturaService facturaService;
    private final IFormaDePagoService formaDePagoService;
    
    @Autowired
    public PagoController(IPagoService pagoService, IFacturaService facturaService,
                          IFormaDePagoService formaDePago) {
        this.pagoService = pagoService;
        this.facturaService = facturaService;
        this.formaDePagoService = formaDePago;
    }
    
    @GetMapping("/pagos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pago getProductoPorId(@PathVariable("id") long id) {
        return pagoService.getPagoPorId(id);
    }
    
    @PostMapping("/pagos")
    @ResponseStatus(HttpStatus.CREATED)
    public Pago guardar(@RequestBody Pago pago) {
        pagoService.guardar(pago);
        return pago;
    }
    
    @DeleteMapping("/pagos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") long id) {
        pagoService.eliminar(pagoService.getPagoPorId(id));
    }
    
    @GetMapping("/pagos/facturas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Pago> getPagosDeLaFactura(@PathVariable("id") long id) {
        return pagoService.getPagosDeLaFactura(facturaService.getFacturaPorId(id));
    }
    
    @GetMapping("pagos/facturas/{id}/saldo")
    @ResponseStatus(HttpStatus.OK)
    public double getSaldoAPagar(@PathVariable("id") long id) {
        return pagoService.getSaldoAPagar(facturaService.getFacturaPorId(id));
    }
    
    @GetMapping("pagos/facturas/{id}/total-pagado")
    @ResponseStatus(HttpStatus.OK)
    public double getTotalPagado(@PathVariable("id") long id) {
        return pagoService.getTotalPagado(facturaService.getFacturaPorId(id));
    }
    
    @GetMapping("/pagos")
    @ResponseStatus(HttpStatus.OK)
    public List<Pago> getPagosEntreFechasYFormaDePago(@RequestParam("idEmpresa") long idEmpresa,
                                                      @RequestParam("idFormaDePago") long idFormaDePago,
                                                      @RequestParam("desde") long desde,
                                                      @RequestParam("hasta") long hasta) {
        Calendar fechaDesde = Calendar.getInstance();
        fechaDesde.setTimeInMillis(desde);
        Calendar fechaHasta = Calendar.getInstance();
        fechaHasta.setTimeInMillis(hasta);
        return pagoService.getPagosEntreFechasYFormaDePago(idEmpresa, idFormaDePago,
                fechaDesde.getTime(), fechaHasta.getTime());
    }
    
    @PutMapping("/pagos/pagar-multiples-facturas")
    @ResponseStatus(HttpStatus.OK)
    public void pagarMultiplesFacturas(@RequestParam("id") long[] id,
                                       @RequestParam("monto") double monto,
                                       @RequestParam("idFormaDePago") long idFormaDePago,
                                       @RequestParam("nota") String nota,
                                       @RequestParam("fechaYHora") long date) {
        Calendar fechaYHora = Calendar.getInstance();
        fechaYHora.setTimeInMillis(date);
        List<Factura> facturas = new ArrayList<>();
        for (long i : id) {
            facturas.add(facturaService.getFacturaPorId(i));
        }
        pagoService.pagarMultiplesFacturas(facturas, monto,
                formaDePagoService.getFormasDePagoPorId(idFormaDePago), nota, fechaYHora.getTime());
    }
}
