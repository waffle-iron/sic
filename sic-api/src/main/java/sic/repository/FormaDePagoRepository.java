package sic.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;

public interface FormaDePagoRepository extends PagingAndSortingRepository<FormaDePago, Long> {

//    void actualizar(FormaDePago formaDePago);
//
//    FormaDePago getFormaDePagoPorId(long id);
//
      FormaDePago findByNombreAndEmpresaAndEliminada(String nombre, Empresa empresa, boolean eliminada);
//    FormaDePago getFormaDePagoPorNombreYEmpresa(String nombre, Long idEmpresa);
//
      FormaDePago findByAndEmpresaAndPredeterminadoAndEliminada(Empresa empresa, boolean predeterminado, boolean eliminada);
//    FormaDePago getFormaDePagoPredeterminada(Empresa empresa);
//
      List<FormaDePago> findAllByAndEmpresaAndEliminada(Empresa empresa, boolean eliminada);
//    List<FormaDePago> getFormasDePago(Empresa empresa);
//
//    FormaDePago guardar(FormaDePago formaDePago);
    
}
