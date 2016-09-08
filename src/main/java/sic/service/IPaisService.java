package sic.service;

import java.util.List;
import sic.modelo.Pais;

public interface IPaisService {
    
     Pais getPaisPorId(long id_Pais);

    void actualizar(Pais pais);

    void eliminar(Pais pais);

    Pais getPaisPorNombre(String nombre);

    List<Pais> getPaises();

    void guardar(Pais pais);
    
}
