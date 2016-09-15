package sic.controller;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.Gasto;
import sic.service.IGastoService;

@RestController
@RequestMapping("/api/v1")
public class GastoController {
    
    private final IGastoService gastoService;
    
    @Autowired
    public GastoController(IGastoService gastoService) {
        this.gastoService = gastoService;
    }
    
    @GetMapping("/gastos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Gasto getProductoPorId(@PathVariable("id") long id) {
        return gastoService.getGastoPorId(id);
    }
    
    @PutMapping("/gastos")
    @ResponseStatus(HttpStatus.OK)
    public Gasto actualizar(@RequestBody Gasto gasto) {
        if(gastoService.getGastoPorId(gasto.getId_Gasto()) != null) {
            gastoService.actualizar(gasto);
        }
        return gastoService.getGastoPorId(gasto.getId_Gasto());
    }
    
    @GetMapping("/gastos/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<Gasto> getGastosPorFechaYFormaDePago(@RequestParam("idEmpresa") long idEmpresa,
                                                     @RequestParam("idFormaDePago") long idFormaDePago,
                                                     @RequestParam("desde") Long desde,
                                                     @RequestParam(value = "hasta", required = false) Long hasta) {
        Calendar fechaDesde = Calendar.getInstance();
        fechaDesde.setTimeInMillis(desde);
        Calendar fechaHasta = Calendar.getInstance();
        fechaHasta.setTimeInMillis(hasta);
        return gastoService.getGastosPorFechaYFormaDePago(idEmpresa, idFormaDePago, fechaDesde.getTime(), fechaHasta.getTime());
    }
    
    @PostMapping("/gastos")
    @ResponseStatus(HttpStatus.CREATED)
    public Gasto guardar(@RequestBody Gasto gasto) {
        gastoService.guardar(gasto);
        return gastoService.getGastoPorId(gasto.getId_Gasto());
    }
    
    @GetMapping("/gastos/ultimo-numero")
    @ResponseStatus(HttpStatus.OK)
    public int getUltimoNumeroDeGasto(@RequestParam("idEmpresa") long id_empresa) {
        return gastoService.getUltimoNumeroDeGasto(id_empresa);
    }
}
