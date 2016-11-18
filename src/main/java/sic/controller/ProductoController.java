package sic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.PreciosProducto;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Rubro;
import sic.service.IEmpresaService;
import sic.service.IMedidaService;
import sic.service.IProductoService;
import sic.service.IProveedorService;
import sic.service.IRubroService;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {
    
    private final IProductoService productoService;
    private final IEmpresaService empresaService;
    private final IRubroService rubroService;
    private final IProveedorService proveedorService;  
    private final IMedidaService medidaService;
    
    @Autowired
    public ProductoController(IProductoService productoService, IEmpresaService empresaService,
                              IRubroService rubroService, IProveedorService proveedorService,
                              IMedidaService medidaService) {        
       this.productoService = productoService;
       this.empresaService = empresaService;
       this.rubroService = rubroService;
       this.proveedorService = proveedorService;
       this.medidaService = medidaService;
    }
    
    @GetMapping("/productos/{idProducto}")
    @ResponseStatus(HttpStatus.OK)
    public Producto getProductoPorId(@PathVariable long idProducto) {
        return productoService.getProductoPorId(idProducto);
    }
    
    @GetMapping("/productos/busqueda")
    @ResponseStatus(HttpStatus.OK)
    public Producto getProductoPorCodigo(@RequestParam long idEmpresa,
                                         @RequestParam String codigo) {        
        return productoService.getProductoPorCodigo(codigo, empresaService.getEmpresaPorId(idEmpresa));
    }
    
    @GetMapping("/productos/busqueda/criteria") 
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> buscarProductos(@RequestParam(value = "codigo", required = false) String codigo,
                                          @RequestParam(value = "descripcion", required = false) String descripcion,
                                          @RequestParam(value = "idRubro", required = false) Long idRubro,
                                          @RequestParam(value = "idProveedor", required = false) Long idProveedor,
                                          @RequestParam(value = "idEmpresa") long idEmpresa,
                                          @RequestParam(value = "soloFaltantes", required = false) boolean soloFantantes) {        
        Rubro rubro = null;
        if (idRubro != null) {
            rubro = rubroService.getRubroPorId(idRubro);
        }
        Proveedor proveedor = null;
        if (idProveedor != null) {
            proveedor = proveedorService.getProveedorPorId(idProveedor);
        }
        BusquedaProductoCriteria criteria = BusquedaProductoCriteria.builder()
                                            .buscarPorCodigo((codigo!=null))
                                            .codigo(codigo)
                                            .buscarPorDescripcion(descripcion!=null)
                                            .descripcion(descripcion)
                                            .buscarPorRubro(rubro!=null)
                                            .rubro(rubro)
                                            .buscarPorProveedor(proveedor!=null)
                                            .proveedor(proveedor)
                                            .empresa(empresaService.getEmpresaPorId(idEmpresa))
                                            .cantRegistros(0)
                                            .listarSoloFaltantes(soloFantantes)
                                            .build();
        return productoService.buscarProductos(criteria);
    }
        
    @DeleteMapping("/productos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMultiplesProductos(@RequestParam long[] idProducto) {        
        productoService.eliminarMultiplesProductos(idProducto);
    }
    
    @PutMapping("/productos")
    @ResponseStatus(HttpStatus.OK)
    public void actualizar(@RequestBody Producto producto) {
        if (productoService.getProductoPorId(producto.getId_Producto()) != null) {
            productoService.actualizar(producto);
        }
    }
    
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }
    
    @GetMapping("/productos/{idProducto}/stock/disponibilidad")
    @ResponseStatus(HttpStatus.OK)
    public boolean existeStockDisponible(@PathVariable long idProducto,
                                         @RequestParam double cantidad) {
        return productoService.existeStockDisponible(idProducto, cantidad);
    }
    
    @GetMapping("/productos/ganancia-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularGanancia_Neto(@RequestParam double precioCosto, 
                                        @RequestParam double gananciaPorcentaje) {
        return productoService.calcularGanancia_Neto(precioCosto, gananciaPorcentaje);
    }    
    
    @GetMapping("/productos/ganancia-porcentaje")
    @ResponseStatus(HttpStatus.OK)
    public double calcularGanancia_Porcentaje(@RequestParam double precioCosto, 
                                              @RequestParam double pvp){
        return productoService.calcularGanancia_Porcentaje(precioCosto, pvp);
    }
    
    @GetMapping("/productos/iva-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularIVA_Neto(@RequestParam double pvp, 
                                   @RequestParam double ivaPorcentaje) {
        return productoService.calcularIVA_Neto(pvp, ivaPorcentaje);
    }
    
    @GetMapping("/productos/imp-interno-neto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularImpInterno_Neto(@RequestParam double pvp, 
                                          @RequestParam double impInternoPorcentaje){
        return productoService.calcularImpInterno_Neto(pvp, impInternoPorcentaje);
    }
    
    @GetMapping("/productos/pvp")
    @ResponseStatus(HttpStatus.OK)
    public double calcularPVP(@RequestParam double precioCosto, 
                              @RequestParam double gananciaPorcentaje) {
        return productoService.calcularPVP(precioCosto, gananciaPorcentaje);
    }
    
    @GetMapping("/productos/precio-lista")
    @ResponseStatus(HttpStatus.OK)
    public double calcularPrecioLista(@RequestParam double pvp, 
                                      @RequestParam double ivaPorcentaje, 
                                      @RequestParam double impInternoPorcentaje) {
        return productoService.calcularPrecioLista(pvp, ivaPorcentaje, impInternoPorcentaje);
    }

    @GetMapping("/productos/reporte/criteria")
    public ResponseEntity<byte[]> getReporteListaDePrecios(@RequestParam(value = "idEmpresa") long idEmpresa,
                                                           @RequestParam(value = "codigo", required = false) String codigo,
                                                           @RequestParam(value = "descripcion", required = false) String descripcion,
                                                           @RequestParam(value = "idRubro", required = false) Long idRubro,
                                                           @RequestParam(value = "idProveedor", required = false) Long idProveedor,                                                           
                                                           @RequestParam(value = "soloFaltantes", required = false) boolean soloFantantes) {
        Rubro rubro = null;
        if (idRubro != null) {
            rubro = rubroService.getRubroPorId(idRubro);
        }
        Proveedor proveedor = null;
        if (idProveedor != null) {
            proveedor = proveedorService.getProveedorPorId(idProveedor);
        }
        BusquedaProductoCriteria criteria = BusquedaProductoCriteria.builder()
                .buscarPorCodigo((codigo != null))
                .codigo(codigo)
                .buscarPorDescripcion(descripcion != null)
                .descripcion(descripcion)
                .buscarPorRubro(idRubro != null)
                .rubro(rubro)
                .buscarPorProveedor(proveedor != null)
                .proveedor(proveedor)
                .empresa(empresaService.getEmpresaPorId(idEmpresa))
                .cantRegistros(0)
                .listarSoloFaltantes(soloFantantes)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);        
        headers.add("content-disposition", "inline; filename=listaDePrecios.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        byte[] reportePDF = productoService.getReporteListaDePreciosPorEmpresa(productoService.buscarProductos(criteria), idEmpresa);
        return new ResponseEntity<>(reportePDF, headers, HttpStatus.OK);
    }
    
    @PutMapping("/productos/multiples")
    @ResponseStatus(HttpStatus.OK)
    public void modificarMultiplesProductos(@RequestParam(value = "idProducto") long[] idProducto,
                                            @RequestParam(value = "idMedida", required = false) Long idMedida,
                                            @RequestParam(value = "idRubro", required = false) Long idRubro,
                                            @RequestParam(value = "idProveedor", required = false) Long idProveedor,
                                            @RequestBody(required = false) PreciosProducto preciosProducto) {
        productoService.modificarMultiplesProductos(idProducto,
                (preciosProducto != null), preciosProducto,
                (idMedida != null), medidaService.getMedidaPorId(idMedida),
                (idRubro != null), rubroService.getRubroPorId(idRubro),
                (idProveedor != null), proveedorService.getProveedorPorId(idProveedor));
    }
}
