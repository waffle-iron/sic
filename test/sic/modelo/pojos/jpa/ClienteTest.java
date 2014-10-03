package sic.modelo.pojos.jpa;

import sic.modelo.CondicionIVA;
import sic.modelo.Cliente;
import sic.modelo.Provincia;
import sic.modelo.Pais;
import sic.modelo.Localidad;
import sic.modelo.Empresa;
import java.util.Date;
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

public class ClienteTest {

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

    private Cliente crearUnCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente de ejemplo");
        cliente.setDireccion("Calle 123");

        //Condicion IVA
        CondicionIVA condicionIVA = new CondicionIVA();
        condicionIVA.setNombre("Responsable Inscripto");
        condicionIVA.setDiscriminaIVA(true);

        cliente.setCondicionIVA(condicionIVA);
        cliente.setId_Fiscal("3");
        cliente.setEmail("");
        cliente.setTelPrimario("");
        cliente.setTelSecundario("");
        cliente.setContacto("");
        cliente.setFechaAlta(new Date());

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
        cliente.setLocalidad(localidad);

        //Empresa
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCondicionIVA(condicionIVA);
        empresa.setEmail("");
        empresa.setTelefono("");

        cliente.setPredeterminado(false);
        cliente.setEmpresa(empresa);
        return cliente;
    }

    @Test
    public void ejecutarConsultaBuscarTodos() {
        tx.begin();
        Cliente cliente = this.crearUnCliente();
        em.persist(cliente);
        tx.commit();

        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.buscarTodos", Cliente.class);
        query.setParameter("empresa", cliente.getEmpresa());
        List<Cliente> clientes = query.getResultList();
        assertNotNull(clientes);
    }

    @Test
    public void ejecutarConsultaBuscarPorId() {
        tx.begin();
        Cliente cliente = this.crearUnCliente();
        em.persist(cliente);
        tx.commit();

        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.buscarPorId", Cliente.class);
        query.setParameter("id", 1L);
        List<Cliente> clientes = query.getResultList();
        assertEquals("Debe existir solo un resultado para la consulta.", clientes.size(), 1);
    }
}
