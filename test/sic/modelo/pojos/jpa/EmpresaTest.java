package sic.modelo.pojos.jpa;

import sic.modelo.CondicionIVA;
import sic.modelo.Provincia;
import sic.modelo.Pais;
import sic.modelo.Empresa;
import sic.modelo.Localidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpresaTest {

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

    public void guardarUnaEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCuip(123);

        //Condicion IVA
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Responsable Inscripto");
        condicionIVA.setDiscriminaIVA(true);

        empresa.setCondicionIVA(condicionIVA);
        empresa.setEmail("");
        empresa.setTelefono("");

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

        tx.begin();
        em.persist(empresa);
        tx.commit();
        assertNotNull("El Id de la empresa no debe ser nulo.", empresa.getId_Empresa());
    }

    @Test
    public void ejecutarConsultaBuscarPorCUIP() {
        this.guardarUnaEmpresa();

        TypedQuery<Empresa> query = em.createNamedQuery("Empresa.buscarPorCUIP", Empresa.class);
        query.setParameter("cuip", 12L);
        List<Empresa> resul = query.getResultList();
        assertNotNull(resul);
    }
}
