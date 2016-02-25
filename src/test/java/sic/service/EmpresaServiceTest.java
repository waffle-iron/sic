package sic.service;

import org.junit.Test;
import static org.junit.Assert.*;
import sic.modelo.Empresa;
import sic.modelo.EmpresaActiva;

public class EmpresaServiceTest {

    @Test
    public void testGetEmpresaActiva() {
        Empresa empresa = new Empresa();
        EmpresaService instance = new EmpresaService();
        instance.setEmpresaActiva(empresa);
        Empresa expResult = empresa;
        EmpresaActiva result = instance.getEmpresaActiva();
        assertEquals(expResult, result.getEmpresa());
    }

}