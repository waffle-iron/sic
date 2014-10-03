package sic.modelo.pojos.jpa;

import sic.modelo.Proveedor;
import sic.modelo.CondicionIVA;
import sic.modelo.Provincia;
import sic.modelo.Pais;
import sic.modelo.Localidad;
import sic.modelo.Empresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProveedorTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManager() {
        emf = Persistence.createEntityManagerFactory("SIC-Test-PU");
        em = emf.createEntityManager();

    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }

    public Proveedor guardarUnProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("456");
        proveedor.setRazonSocial("Proveedor de ejemplo");
        proveedor.setDireccion("Calle 123");

        //Condicion IVA
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Monotributo");
        condicionIVA.setDiscriminaIVA(false);
        proveedor.setCondicionIVA(condicionIVA);
        proveedor.setId_Fiscal("234");
        proveedor.setTelPrimario("");
        proveedor.setTelSecundario("");
        proveedor.setContacto("");
        proveedor.setEmail("");
        proveedor.setWeb("");

        //Pais
        Pais pais = new Pais();
        pais.setNombre("Argentina");
        //Provincia
        Provincia provincia = new Provincia();
        provincia.setNombre("Corrientes");
        provincia.setPais(pais);

        //Localidad
        Localidad localidad = new Localidad();
        localidad.setNombre("Bella Vista");
        localidad.setCodigoPostal("3400");
        localidad.setProvincia(provincia);
        proveedor.setLocalidad(localidad);

        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCondicionIVA(condicionIVA);
        empresa.setEmail("");
        empresa.setTelefono("");
        proveedor.setEmpresa(empresa);

        tx.begin();
        em.persist(proveedor);
        tx.commit();
        return proveedor;
    }

    @Test
    public void ejecutarConsultaBuscarPorIdFiscal() {
        Proveedor prov = this.guardarUnProveedor();
        TypedQuery<Proveedor> query = em.createNamedQuery("Proveedor.buscarPorIdFiscal", Proveedor.class);
        query.setParameter("empresa", prov.getEmpresa());
        query.setParameter("idFiscal", "24");
        List<Proveedor> result = query.getResultList();
        assertNotNull(result);
    }
}
