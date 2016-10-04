package sic.controller;

import java.util.Calendar;
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
import sic.modelo.BusquedaPedidoCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.Pedido;
import sic.modelo.RenglonPedido;
import sic.modelo.Usuario;
import sic.service.IClienteService;
import sic.service.IEmpresaService;
import sic.service.IPedidoService;
import sic.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {
    
    private final IPedidoService pedidoService;
    private final IEmpresaService empresaService;
    private final IUsuarioService usuarioService;
    private final IClienteService clienteService;
    
    @Autowired
    public PedidoController(IPedidoService pedidoService, IEmpresaService empresaService,
                            IUsuarioService usuarioService, IClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }
    
    @GetMapping("/pedidos/{idPedido}")
    @ResponseStatus(HttpStatus.OK)
    public Pedido getPedido(@PathVariable("idPedido") long idPedido) {
        return pedidoService.getPedidoPorId(idPedido);
    }
    
    @GetMapping("/pedidos/{idPedido}/renglones")
    @ResponseStatus(HttpStatus.OK)
    public List<RenglonPedido> getRenglonesDelPedido(@PathVariable("idPedido") long idPedido) {
        return pedidoService.getRenglonesDelPedido(idPedido);
    }
    
    @PutMapping("/pedidos")
    @ResponseStatus(HttpStatus.OK)
    public Pedido actualizar(@RequestBody Pedido pedido) {
        //Las facturas se recuperan para evitar cambios no deseados.
        pedido.setFacturas(pedidoService.getFacturasDelPedido(pedido.getId_Pedido()));
        //Si los renglones vienen null, recupera los renglones del pedido para actualizar
        //caso contrario, ultiliza los renglones del pedido.
        if (pedido.getRenglones() == null) {
            pedido.setRenglones(pedidoService.getRenglonesDelPedido(pedido.getId_Pedido()));
        }
        pedidoService.actualizar(pedido);
        return pedidoService.getPedidoPorId(pedido.getId_Pedido());
    }
    
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido guardar(@RequestBody Pedido pedido) {
        pedidoService.guardar(pedido);
        return pedidoService.getPedidoPorNumeroYEmpresa(pedido.getNroPedido(), pedido.getEmpresa().getId_Empresa());
    }
    
    @GetMapping("/pedidos/busqueda/criteria")
    @ResponseStatus(HttpStatus.OK)
    public List<Pedido> buscarConCriteria(@RequestParam(value = "idEmpresa") Long idEmpresa,
                                          @RequestParam(value = "desde", required = false) Long desde,
                                          @RequestParam(value = "hasta", required = false) Long hasta,
                                          @RequestParam(value = "idCliente", required = false) Long idCliente,
                                          @RequestParam(value = "idUsuario", required = false) Long idUsuario,
                                          @RequestParam(value = "nroPedido", required = false) Long nroPedido) {
        Empresa empresa = empresaService.getEmpresaPorId(idEmpresa);
        Calendar fechaDesde = Calendar.getInstance();
        Calendar fechaHasta = Calendar.getInstance();
        if ((desde != null) && (hasta != null)) {
            fechaDesde.setTimeInMillis(desde);            
            fechaHasta.setTimeInMillis(hasta);
        }
        Usuario usuario = usuarioService.getUsuarioPorId(idUsuario);
        Cliente cliente = clienteService.getClientePorId(idCliente);
        BusquedaPedidoCriteria criteria = BusquedaPedidoCriteria.builder()
                                          .buscaPorFecha((desde != null) && (hasta != null))
                                          .fechaDesde(fechaDesde.getTime())
                                          .fechaHasta(fechaHasta.getTime())
                                          .buscaCliente(cliente != null)
                                          .cliente(cliente)
                                          .buscaUsuario(usuario != null)
                                          .usuario(usuario)
                                          .buscaPorNroPedido(nroPedido != null)
                                          .nroPedido((nroPedido != null)? nroPedido : 0)
                                          .empresa(empresa)
                                          .build();
        return pedidoService.buscarConCriteria(criteria);
    }
    
    @DeleteMapping("/pedidos/{idPedido}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idPedido") long idPedido) {
        pedidoService.eliminar(idPedido);
    }       
        
    @GetMapping("/pedidos/{idPedido}/reporte")
    public ResponseEntity<byte[]> getReportePedido(@PathVariable("idPedido") long idPedido) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("reportePedido.pdf", "ReportePedido.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        byte[] reportePDF = pedidoService.getReportePedido(pedidoService.getPedidoPorId(idPedido));
        return new ResponseEntity<>(reportePDF, headers, HttpStatus.OK);
    }
    
}
