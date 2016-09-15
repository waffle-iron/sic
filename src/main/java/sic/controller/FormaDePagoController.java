package sic.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.FormaDePago;
import sic.service.IEmpresaService;
import sic.service.IFormaDePagoService;

@RestController
@RequestMapping("/api/v1")
public class FormaDePagoController {
    
    private final IFormaDePagoService formaDePagoService;
    private final IEmpresaService empresaService;
    
    @Autowired
    public FormaDePagoController(IFormaDePagoService formaDePagoService, 
                                 IEmpresaService empresaService) {
        this.formaDePagoService = formaDePagoService;
        this.empresaService = empresaService;
    }
    
    @GetMapping("/formas-de-pago/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaDePago getFormaDePagoPorId(@PathVariable long id) {
        return formaDePagoService.getFormasDePagoPorId(id);
    }
    
    @DeleteMapping("formas-de-pago/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void eliminar(@PathVariable long id) {
        formaDePagoService.eliminar(formaDePagoService.getFormasDePagoPorId(id));
    }
    
    @GetMapping("/formas-de-pago/predeterminada/empresa/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaDePago getFormaDePagoPredeterminada(@PathVariable long id) {
        return formaDePagoService.getFormaDePagoPredeterminada(empresaService.getEmpresaPorId(id));
    }
    
    @GetMapping("/formas-de-pago/empresa/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<FormaDePago> getFormasDePago(@PathVariable long id) {
        return formaDePagoService.getFormasDePago(empresaService.getEmpresaPorId(id));
    }
    
    @PostMapping("/formas-de-pago")
    @ResponseStatus(HttpStatus.CREATED)
    public FormaDePago guardar(@RequestBody FormaDePago formaDePago) {
        formaDePagoService.guardar(formaDePago);
        return formaDePagoService.getFormasDePagoPorId(formaDePago.getId_FormaDePago());
    }
    
    @PutMapping("/formas-de-pago/predeterminada/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaDePago setFormaDePagoPredeterminada(@PathVariable long id) {
        formaDePagoService.setFormaDePagoPredeterminada(formaDePagoService.getFormasDePagoPorId(id));
        return formaDePagoService.getFormasDePagoPorId(id);
    }
    
}
