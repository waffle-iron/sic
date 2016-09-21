package sic.service;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import sic.modelo.Pedido;

public interface PedidoCrud extends CrudRepository<Pedido, Long> {
    
    
}
