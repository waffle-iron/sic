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
import sic.modelo.CondicionIVA;
import sic.service.ICondicionIVAService;

@RestController
@RequestMapping("/api/v1")
public class CondicionIVAControllers {
    
    private final ICondicionIVAService condicionIVAService;
    
    @Autowired
    public CondicionIVAControllers(ICondicionIVAService condicionIVAService) {
        this.condicionIVAService = condicionIVAService;
    }
    
    @GetMapping("/condicionesIVA/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CondicionIVA getCondicionIVAPorId(@PathVariable long id) {
        return condicionIVAService.getCondicionIVAPorId(id);
    }
    
    @PutMapping("/condicionesIVA")
    @ResponseStatus(HttpStatus.OK)
    public CondicionIVA actualizar(@RequestBody CondicionIVA condicionIVA) {
        if(condicionIVAService.getCondicionIVAPorId(condicionIVA.getId_CondicionIVA()) != null) {
            condicionIVAService.actualizar(condicionIVA);
        }
        return condicionIVAService.getCondicionIVAPorId(condicionIVA.getId_CondicionIVA());
    }
    
    @DeleteMapping("/condicionesIVA")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") long id) {
        CondicionIVA condicionIVA = condicionIVAService.getCondicionIVAPorId(id);
        condicionIVA.setEliminada(true);
        condicionIVAService.actualizar(condicionIVA);
    }
    
    @GetMapping("/condicionesIVA")
    @ResponseStatus(HttpStatus.OK)
    public List<CondicionIVA> getCondicionesIVA() {
        return condicionIVAService.getCondicionesIVA();
    }
    
    @PostMapping("/condicionesIVA")
    @ResponseStatus(HttpStatus.CREATED)
    public CondicionIVA guardar(@RequestBody CondicionIVA condicionIVA) {
        condicionIVAService.guardar(condicionIVA);
        return condicionIVAService.getCondicionIVAPorId(condicionIVA.getId_CondicionIVA());
    }
    
}
