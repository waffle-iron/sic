package sic.repository;

import java.util.List;
import sic.modelo.Empresa;
import sic.modelo.FormaDePago;

public interface IFormaDePagoRepository {

    void actualizar(FormaDePago formaDePago);

    FormaDePago getFormaDePagoPorId(long id);

    FormaDePago getFormaDePagoPorNombreYEmpresa(String nombre, Long idEmpresa);

    FormaDePago getFormaDePagoPredeterminada(Empresa empresa);

    List<FormaDePago> getFormasDePago(Empresa empresa);

    FormaDePago guardar(FormaDePago formaDePago);
    
}
