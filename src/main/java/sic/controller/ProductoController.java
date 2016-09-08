package sic.controller;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
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
import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.Rubro;
import sic.service.IEmpresaService;
import sic.service.IFacturaService;
import sic.service.IProductoService;
import sic.service.IProveedorService;
import sic.service.IRubroService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {
    
    private final IProductoService productoService;
    private final IEmpresaService empresaService;
    private final IRubroService rubroService;
    private final IProveedorService proveedorService;
    private final IFacturaService facturaService;
    
    @Autowired
    public ProductoController(IProductoService productoService, IEmpresaService empresaService,
                              IRubroService rubroService, IProveedorService proveedorService, 
                              IFacturaService facturaService) {
        
       this.productoService = productoService;
       this.empresaService = empresaService;
       this.rubroService = rubroService;
       this.proveedorService = proveedorService;
       this.facturaService = facturaService;
    }
    
    @GetMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Producto getProductoPorId(@PathVariable("id") long id){
        return productoService.getProductoPorId(id);
    }
    
    @GetMapping("/productos/busqueda")
    @ResponseStatus(HttpStatus.OK)
    public Producto getProductoPorCodigo(@RequestParam(value = "idEmpresa", required = true)  long idEmpresa,
                                         @RequestParam(value = "codigoProducto", required = true) String codigoProducto) {
        
        
        return productoService.getProductoPorCodigo(codigoProducto, empresaService.getEmpresaPorId(idEmpresa));
    }
    
    @GetMapping("/productos/busqueda/criteria") 
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> buscarProductos(@RequestParam(value = "codigo", required = false) String codigo,
                                          @RequestParam(value = "descripcion", required = false) String descripcion,
                                          @RequestParam(value = "idRubro", required = false) Long idRubro,
                                          @RequestParam(value = "idProveedor", required = false) Long idProveedor,
                                          @RequestParam(value = "idEmpresa") long idEmpresa,
                                          @RequestParam(value = "soloFaltantes", required = false) boolean soloFantantes) {
        
        Rubro rubro = rubroService.getRubroPorId(idRubro);
        Proveedor proveedor = proveedorService.getProveedorPorId(idProveedor);
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
    
    @DeleteMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") long id) {
        
        Producto producto = productoService.getProductoPorId(id);
        producto.setEliminado(true);
        productoService.actualizar(producto);
    }
    
    @DeleteMapping("/productos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMultiplesProductos(
            @RequestParam(value = "id", required = true) long[] id) {
        
             List<Producto> productos = new ArrayList<>();
             for(Long Id : id){
                  productos.add(productoService.getProductoPorId(Id));
             }
            productoService.eliminarMultiplesProductos(productos);
    }
    
    @PutMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Producto actualizar(@PathVariable("id") long id, @RequestBody Producto producto){
        if(productoService.getProductoPorId(id) != null){
            productoService.actualizar(producto);
        }
        return productoService.getProductoPorId(producto.getId_Producto());
    }
    
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardar(@RequestBody Producto producto){
        productoService.guardar(producto);
        return productoService.getProductoPorId(producto.getId_Producto());
    }
    
    @PutMapping("/productos/stock/factura/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void actualizarStock(@PathVariable("id") long id_Factura, @RequestBody TipoDeOperacion tipoDeOperacion){
        productoService.actualizarStock(facturaService.getFacturaPorId(id_Factura), tipoDeOperacion);
    }
    
    @GetMapping("/productos/gananciaNeto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularGanancia_Neto(@RequestParam(value = "precioCosto", required = true) double precioCosto, 
                                        @RequestParam(value = "gananciaPorcentaje", required = true) double gananciaPorcentaje){
    return productoService.calcularGanancia_Neto(precioCosto, gananciaPorcentaje);
    }
    
    @GetMapping("/productos/{id}/stock/disponibilidad")
    @ResponseStatus(HttpStatus.OK)
    public boolean existeStockDisponible(@RequestParam(value = "cantidad", required = true) double cantidad, 
                                         @PathVariable("Id") long id){
     return productoService.existeStockDisponible(id, cantidad);
    }
    
    @GetMapping("/productos/gananciaPorcentaje")
    @ResponseStatus(HttpStatus.OK)
    public double calcularGanancia_Porcentaje(@RequestParam(value = "precioCosto", required = true) double precioCosto, 
                                              @RequestParam(value = "pvp", required = true) double pvp){
     return productoService.calcularGanancia_Porcentaje(precioCosto, pvp);
    }
    
    @GetMapping("/productos/IVANeto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularIVA_Neto(@RequestParam(value = "precioCosto", required = true) double precioCosto, 
                                   @RequestParam(value = "iva_porcentaje", required = true) double iva_porcentaje){
     return productoService.calcularIVA_Neto(precioCosto, iva_porcentaje);
    }
    
    @GetMapping("/productos/impInternoNeto")
    @ResponseStatus(HttpStatus.OK)
    public double calcularImpInterno_Neto(@RequestParam(value = "precioCosto", required = true) double precioCosto, 
                                          @RequestParam(value = "impInterno_porcentaje", required = true) double impInterno_porcentaje){
     return productoService.calcularImpInterno_Neto(precioCosto, impInterno_porcentaje);
    }
    
    @GetMapping("/productos/PVP")
    @ResponseStatus(HttpStatus.OK)
    public double calcularPVP(@RequestParam(value = "precioCosto", required = true) double precioCosto, 
                              @RequestParam(value = "ganancia_porcentaje", required = true) double ganancia_porcentaje){
     return productoService.calcularPVP(precioCosto, ganancia_porcentaje);
    }
    
    @GetMapping("/productos/precioLista")
    @ResponseStatus(HttpStatus.OK)
    public double calcularPrecioLista(@RequestParam(value = "pvp", required = true) double PVP, 
                                      @RequestParam(value = "iva_porcentaje", required = true) double iva_porcentaje, 
                                      @RequestParam(value = "impInterno_porcentaje", required = true) double impInterno_porcentaje){
     return productoService.calcularPrecioLista(PVP, iva_porcentaje, impInterno_porcentaje);
    }

    @GetMapping("/productos/reporte/criteria")
    public ResponseEntity<byte[]> getReporteListaDePrecios(@RequestParam(value = "codigo", required = false) String codigo,
                                                           @RequestParam(value = "descripcion", required = false) String descripcion,
                                                           @RequestParam(value = "idRubro", required = false) Long idRubro,
                                                           @RequestParam(value = "idProveedor", required = false) Long idProveedor,
                                                           @RequestParam(value = "idEmpresa") long idEmpresa,
                                                           @RequestParam(value = "soloFaltantes", required = false) boolean soloFantantes) {
        try {
            Rubro rubro = rubroService.getRubroPorId(idRubro);
            Proveedor proveedor = proveedorService.getProveedorPorId(idProveedor);
            BusquedaProductoCriteria criteria = BusquedaProductoCriteria.builder()
                    .buscarPorCodigo((codigo != null))
                    .codigo(codigo)
                    .buscarPorDescripcion(descripcion != null)
                    .descripcion(descripcion)
                    .buscarPorRubro(rubro != null)
                    .rubro(rubro)
                    .buscarPorProveedor(proveedor != null)
                    .proveedor(proveedor)
                    .empresa(empresaService.getEmpresaPorId(idEmpresa))
                    .cantRegistros(0)
                    .listarSoloFaltantes(soloFantantes)
                    .build();
            return productoService.getReporteListaDePreciosPorEmpresa(productoService.buscarProductos(criteria), idEmpresa);

        } catch (JRException ex) {
            throw new ServiceException(ex);
        }
    }
}
