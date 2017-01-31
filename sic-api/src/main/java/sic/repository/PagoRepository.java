package sic.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FormaDePago;
import sic.modelo.Pago;

public interface PagoRepository extends PagingAndSortingRepository<Pago, Long> {
    
      List<Pago> findByFactura(Factura factura);
      
      List<Pago> findByFechaBetweenAndEmpresaAndFormaDePagoAndEliminado(Date desde, Date hasta, Empresa empresa, FormaDePago formaDePago, boolean eliminado);

      Pago findTopByEmpresaOrderByNroPagoDesc(Empresa empresa);
}
