package sic.modelo.pojos.jpa;

import sic.modelo.Rubro;
import sic.modelo.Proveedor;
import sic.modelo.Medida;
import sic.modelo.Producto;
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

public class ProductoTest {

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

    public Producto guardarUnProducto() {
        Producto producto = new Producto();
        producto.setCodigo("123");
        producto.setDescripcion("Producto de prueba.");
        producto.setCantidad(2);
        producto.setCantMinima(1);

        //Medida
        Medida medida = new Medida();
        medida.setNombre("UNIDAD");
        producto.setMedida(medida);
        producto.setPrecioCosto(1000.50);
        producto.setGanancia_porcentaje(10);
        producto.setGanancia_neto(100.05);
        producto.setPrecioVentaPublico(1100.55);
        producto.setIva_porcentaje(21);
        producto.setIva_neto(231.1155);
        producto.setImpuestoInterno_porcentaje(0);
        producto.setImpuestoInterno_neto(0);
        producto.setPrecioLista(1331.6655);
        producto.setIlimitado(false);

        //Rubro
        Rubro rubro = new Rubro();
        rubro.setNombre("VARIOS");
        producto.setRubro(rubro);
        producto.setFechaUltimaModificacion(new java.util.Date());
        producto.setEstanteria("");
        producto.setEstante("");
        producto.setFechaAlta(new java.util.Date());

        //Proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo("");
        proveedor.setRazonSocial("Proveedor de ejemplo");
        proveedor.setDireccion("Calle 123");
        proveedor.setId_Fiscal("");
        proveedor.setTelPrimario("");
        proveedor.setTelSecundario("");
        proveedor.setContacto("");
        proveedor.setEmail("");
        proveedor.setWeb("");
        producto.setProveedor(proveedor);
        producto.setNota("");

        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa de ejemplo");
        empresa.setLema("");
        empresa.setDireccion("Calle 123");
        empresa.setCuip(123);
        empresa.setEmail("");
        empresa.setTelefono("");
        producto.setEmpresa(empresa);

        tx.begin();
        em.persist(producto);
        tx.commit();
        return producto;
    }

    @Test
    public void ejecutarConsultaBuscarPorProveedor() {
        Producto producto = this.guardarUnProducto();

        TypedQuery<Producto> query = em.createNamedQuery("Producto.buscarPorProveedor", Producto.class);
        query.setParameter("proveedor", producto.getProveedor());
        query.setParameter("empresa", producto.getEmpresa());
        List<Producto> resul = query.getResultList();
        assertNotNull(resul);
    }
}
