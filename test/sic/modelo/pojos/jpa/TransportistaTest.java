package sic.modelo.pojos.jpa;

import sic.modelo.Provincia;
import sic.modelo.Pais;
import sic.modelo.Transportista;
import sic.modelo.Localidad;
import sic.modelo.Empresa;
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

public class TransportistaTest {

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

    public Transportista guardarUnTransportista() {
        Transportista transportista = new Transportista();
        transportista.setNombre("Transportista de ejemplo");
        transportista.setDireccion("Calle 123");

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

        transportista.setLocalidad(localidad);
        transportista.setWeb("");
        transportista.setTelefono("");

        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setEmail("");
        empresa.setTelefono("");
        transportista.setEmpresa(empresa);

        tx.begin();
        em.persist(transportista);
        tx.commit();
        return transportista;
    }

    @Test
    public void ejecutarConsultaBuscarTodos() {
        Transportista trans = this.guardarUnTransportista();
        TypedQuery<Transportista> query = em.createNamedQuery("Transportista.buscarTodos", Transportista.class);
        query.setParameter("empresa", trans.getEmpresa());
        List<Transportista> resul = query.getResultList();
        assertNotNull(resul);
    }
}
