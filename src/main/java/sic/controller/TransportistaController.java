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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.BusquedaTransportistaCriteria;
import sic.modelo.Transportista;
import sic.service.IEmpresaService;
import sic.service.ILocalidadService;
import sic.service.IPaisService;
import sic.service.IProvinciaService;
import sic.service.ITransportistaService;

@RestController
@RequestMapping("/api/v1")
public class TransportistaController {
    
    private final ITransportistaService transportistaService;
    private final IEmpresaService empresaService;
    private final IPaisService paisService;
    private final IProvinciaService provinciaService;
    private final ILocalidadService localidadService;
    
    @Autowired
    public TransportistaController(ITransportistaService transportistaService, IEmpresaService empresaService,
                                   IPaisService paisService, IProvinciaService provinciaService,
                                   ILocalidadService localidadService) {
        this.transportistaService = transportistaService;
        this.empresaService = empresaService;
        this.paisService = paisService;
        this.provinciaService = provinciaService;
        this.localidadService = localidadService;
    }
    
    @GetMapping("/transportistas/{idTransportista}")
    @ResponseStatus(HttpStatus.OK)
    public Transportista getTransportistaPorId(@PathVariable long idTransportista) {
        return transportistaService.getTransportistaPorId(idTransportista);
    }
    
    @PutMapping("/transportistas")
    @ResponseStatus(HttpStatus.OK)
    public Transportista actualizar(@RequestBody Transportista transportista) {
        if(transportistaService.getTransportistaPorId(transportista.getId_Transportista()) != null) {
            transportistaService.actualizar(transportista);
        }
        return transportistaService.getTransportistaPorId(transportista.getId_Transportista());
    }
    
    @GetMapping("/transportistas/busqueda/criteria") 
    @ResponseStatus(HttpStatus.OK)
    public List<Transportista> buscarTransportista(@RequestParam(value = "idEmpresa") long idEmpresa,
                                                   @RequestParam(value = "nombre", required = false) String nombre,
                                                   @RequestParam(value = "idPais", required = false) Long idPais,
                                                   @RequestParam(value = "idProvincia", required = false) Long idProvincia,
                                                   @RequestParam(value = "idLocalidad", required = false) Long idLocalidad) { 
        BusquedaTransportistaCriteria criteria = new BusquedaTransportistaCriteria(
                                                     (nombre != null),nombre,
                                                     (idPais != null),paisService.getPaisPorId(idPais),
                                                     (idProvincia != null),provinciaService.getProvinciaPorId(idProvincia),
                                                     (idLocalidad != null),localidadService.getLocalidadPorId(idLocalidad),
                                                     empresaService.getEmpresaPorId(idEmpresa));
        return transportistaService.buscarTransportistas(criteria);       
    }
    
    @DeleteMapping("/transportistas/{idTransportista}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable long idTransportista) {
        transportistaService.eliminar(idTransportista);
    }
    
    @GetMapping("/transportistas/empresas/{idEmpresa}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transportista> getTransportistas(@PathVariable long idEmpresa) {
        return transportistaService.getTransportistas(empresaService.getEmpresaPorId(idEmpresa));
    }
    
    @PostMapping("/transportistas")
    @ResponseStatus(HttpStatus.OK)
    public Transportista guardar(@RequestBody Transportista transportista) {
        transportistaService.guardar(transportista);
        return transportistaService.getTransportistaPorNombre(transportista.getNombre() , transportista.getEmpresa());
    }
    
}
