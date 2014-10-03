package sic.modelo.pojos.jpa;

import sic.modelo.Usuario;
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

public class UsuarioTest {

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

    public void guardarUnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("miUsuario");
        usuario.setPassword("miPassword");
        usuario.setPermisosAdministrador(true);
        tx.begin();
        em.persist(usuario);
        tx.commit();
    }

    @Test
    public void ejecutarConsultaBuscarPorNombreContrasenia() {
        this.guardarUnUsuario();
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.buscarPorNombreContrasenia", Usuario.class);
        query.setParameter("nombre", "miUsuario");
        query.setParameter("password", "miPassword");
        List<Usuario> resul = query.getResultList();
        assertNotNull(resul);
    }
}
