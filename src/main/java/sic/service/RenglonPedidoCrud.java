package sic.service;

import org.springframework.data.repository.CrudRepository;
import sic.modelo.RenglonPedido;

public interface RenglonPedidoCrud extends CrudRepository<RenglonPedido, Long>  {
    
}
