package sic.repository;

import java.util.Date;
import java.util.List;
import sic.modelo.Gasto;

public interface IGastoRepository {
    
    Gasto getGastoPorId(Long id);

    void actualizar(Gasto gasto);

    Gasto getGastoPorIdYEmpresa(long id_Gasto, long id_Empresa);

    List<Object> getGastosPorFecha(long id_Empresa, Date desde, Date hasta);

    List<Gasto> getGastosPorFechaYFormaDePago(long id_Empresa, long id_FormaDePago, Date desde, Date hasta);

    int getUltimoNumeroDeGasto(long idEmpresa);

    void guardar(Gasto gasto);

}
