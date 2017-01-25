package sic.repository;

import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import sic.modelo.BusquedaProductoCriteria;
import sic.modelo.Empresa;
import sic.modelo.Producto;

public interface IProductoRepository extends PagingAndSortingRepository<Producto, Long>, QueryDslPredicateExecutor<Producto>  {

    Producto findOneByCodigoAndEmpresaAndEliminado(String codigo, Empresa empresa, boolean eliminado);
//    
    Producto findByDescripcionAndEmpresaAndEliminado(String descripcion, Empresa empresa, boolean eliminado);
    
//    List<Producto> buscarProductos(BusquedaProductoCriteria criteria); DSL

//    void actualizar(Producto producto);

//    void actualizarMultiplesProductos(List<Producto> productos);

//    Producto getProductoPorCodigo(String codigo, Empresa empresa);

//    Producto getProductoPorDescripcion(String descripcion, Empresa empresa);

//    Producto getProductoPorId(long id_Producto);
    
//    double calcularValorStock(BusquedaProductoCriteria criteria); DSL

//    Producto guardar(Producto producto);
    
}
