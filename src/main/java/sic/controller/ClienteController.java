package sic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import sic.modelo.BusquedaClienteCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.Localidad;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.service.IClienteService;
import sic.service.IEmpresaService;
import sic.service.ILocalidadService;
import sic.service.IPaisService;
import sic.service.IProvinciaService;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {
    
    private final IClienteService clienteService;
    private final IEmpresaService empresaService;
    private final IPaisService paisService;
    private final IProvinciaService provinciaService;
    private final ILocalidadService localidadService;
    
    @Autowired
    public ClienteController(IClienteService clienteService, IEmpresaService empresaService,IPaisService paisService, 
            IProvinciaService provinciaService, ILocalidadService localidadService){
        this.clienteService = clienteService;
        this.empresaService = empresaService;
        this.paisService = paisService;
        this.provinciaService = provinciaService;
        this.localidadService = localidadService;
    }
  
    @GetMapping(value = "/clientes/{Id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getCliente(@PathVariable("Id") long Id) {
        Cliente cliente = clienteService.getClientePorId(Id);
        return cliente;
    }
    
    @GetMapping(value = "/clientes/criteria") 
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente>  buscarConCriteria(@RequestParam(value = "razonSocial", required = false) String razonSocial,
                                  @RequestParam(value = "nombreFantasia", required = false) String nombreFantasia,
                                  @RequestParam(value = "idFiscal", required = false) String idFiscal,
                                  @RequestParam(value = "Pais", required = false) String NombrePais,
                                  @RequestParam(value = "nombreProvincia", required = false) String nombreProvincia, 
                                  @RequestParam(value = "nombreLocalidad", required = false) String nombreLocalidad, 
                                  @RequestParam(value = "nombreEmpresa", required = false) String nombreEmpresa) {
        Empresa empresa = empresaService.getEmpresaPorNombre(nombreEmpresa);
        Pais pais = new Pais();
        if(NombrePais == null) { pais.setNombre("Todos");} else { pais = paisService.getPaisPorNombre(NombrePais);}
        Provincia provincia = new Provincia();
        if (nombreProvincia == null) {
            provincia.setNombre("Todas");
        } else {
            provincia = provinciaService.getProvinciaPorNombre(nombreProvincia, pais);
        }
        Localidad localidad = new Localidad();
        if (nombreLocalidad == null) {
            localidad.setNombre("Todas");
        } else {
            localidad = localidadService.getLocalidadPorNombre(nombreLocalidad, provincia);
        }
        List<Cliente> clientes = clienteService.buscarClientes(
                                  new BusquedaClienteCriteria(
                                  (razonSocial!=null), razonSocial, 
                                  (nombreFantasia!=null), nombreFantasia, 
                                  (idFiscal!=null), idFiscal, 
                                  (pais!=null), pais, 
                                  (provincia!=null), provincia, 
                                  (localidad!=null), localidad,
                                  empresa)
                                  ); 
        return clientes;
    } 
       
    @GetMapping(value = "/clientes")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente>getClientes(@RequestParam(value = "nombreEmpresa", required = false) String nombreEmpresa){
        List<Cliente> clientes = clienteService.getClientes(empresaService.getEmpresaPorNombre(nombreEmpresa));
        return clientes;
    }
    
    @GetMapping(value = "/clientes/predeterminado")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClientePredeterminado(@RequestParam(value = "nombreEmpresa", required = false) String nombreEmpresa){
     return clienteService.getClientePredeterminado(empresaService.getEmpresaPorNombre(nombreEmpresa));
    }
    
    
    @DeleteMapping(value = "/clientes/{Id}")
    @ResponseStatus(HttpStatus.OK)
    public void eliminar(@PathVariable("Id") long Id){
        Cliente cliente = clienteService.getClientePorId(Id);
        clienteService.eliminar(cliente);
    }
    
    @PostMapping(value = "/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente guardar(@RequestBody Cliente cliente) {
        clienteService.guardar(cliente);
        cliente = clienteService.getClientePorRazonSocial(cliente.getRazonSocial(), cliente.getEmpresa());
        return cliente;
    }
    
    @PutMapping("/clientes")
    @ResponseStatus(HttpStatus.OK)
    public Cliente actualizar(@RequestBody Cliente cliente){
       clienteService.actualizar(cliente);
       return clienteService.getClientePorId(cliente.getId_Cliente());
    }
    
    @PutMapping("/clientes/predeterminado")
    @ResponseStatus(HttpStatus.OK)
    public Cliente setClientePredeterminado(@RequestBody Cliente cliente){
       clienteService.setClientePredeterminado(cliente);
       return clienteService.getClientePorId(cliente.getId_Cliente());
    }
}
