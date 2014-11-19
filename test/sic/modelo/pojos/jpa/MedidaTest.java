package sic.modelo.pojos.jpa;

import sic.modelo.CondicionIVA;
import sic.modelo.Medida;
import sic.modelo.Empresa;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sic.modelo.Localidad;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.modelo.XMLFileConfig;
import sic.service.EmpresaService;
import sic.service.MedidaService;

public class MedidaTest {

    MedidaService medidaService = new MedidaService();
    EmpresaService empresaService = new EmpresaService();

    @Before
    public void setDatosConexion() {
        XMLFileConfig.setBdConexion("sic-test");
        XMLFileConfig.setHostConexion("localhost");
        XMLFileConfig.setPuertoConexion(3306);
    }

    @Test
    public void deberiaGuardarUnaMedida() {
        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("SIC Corporation");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCuip(123);
        empresa.setEmail("");
        empresa.setTelefono("");
        //Condicion IVA
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Responsable Inscripto");
        condicionIVA.setDiscriminaIVA(true);
        empresa.setCondicionIVA(condicionIVA);
        //Pais
        Pais pais = new Pais();
        pais.setNombre("USA");
        //Provincia
        Provincia provincia = new Provincia();
        provincia.setNombre("Texas");
        provincia.setPais(pais);
        //Localidad
        Localidad localidad = new Localidad();
        localidad.setNombre("Dallas");
        localidad.setCodigoPostal("");
        localidad.setProvincia(provincia);
        empresa.setLocalidad(localidad);
        empresaService.guardar(empresa);
        //Medida
        Medida medida = new Medida();
        medida.setNombre("DOCENA");
        medida.setEliminada(false);
        empresa = empresaService.getEmpresaPorNombre(empresa.getNombre());
        medida.setEmpresa(empresa);
        medidaService.guardar(medida);
        assertNotNull("El id de la medida no debe ser null.", medidaService.getMedidaPorNombre("DOCENA", empresa));
    }
}