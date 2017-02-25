package sic.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sic.modelo.Empresa;
import sic.modelo.Factura;
import sic.modelo.FormaDePago;
import sic.modelo.Pago;

public interface PagoRepository extends PagingAndSortingRepository<Pago, Long> {

    @Query("SELECT p FROM Pago p WHERE p.id_Pago = :idPago AND p.eliminado = :eliminado")
    Pago findOne(@Param("idPago") long idPago, @Param("eliminado") boolean eliminado);
    
    List<Pago> findByFacturaAndEliminado(Factura factura, boolean eliminado);

    List<Pago> findByFechaBetweenAndEmpresaAndFormaDePagoAndEliminado(Date desde, Date hasta, Empresa empresa, FormaDePago formaDePago, boolean eliminado);

    Pago findTopByEmpresaOrderByNroPagoDesc(Empresa empresa);
}
