package sic.service;

import java.util.List;
import sic.modelo.CondicionIVA;

public interface ICondicionIVAService {
    
    CondicionIVA getCondicionIVAPorId(long idCondicionIVA);

    void actualizar(CondicionIVA condicionIVA);

    void eliminar(Long idCondicionIVA);

    CondicionIVA getCondicionIVAPorNombre(String nombre);

    List<CondicionIVA> getCondicionesIVA();

    void guardar(CondicionIVA condicionIVA);

    void validarOperacion(TipoDeOperacion operacion, CondicionIVA condicionIVA);

}
