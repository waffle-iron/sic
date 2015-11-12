package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.Pais;
import sic.repository.IPaisRepository;

@Repository
public class PaisRepositoryJPAImpl implements IPaisRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Pais> getPaises() {        
        TypedQuery<Pais> typedQuery = em.createNamedQuery("Pais.buscarTodos", Pais.class);
        List<Pais> paises = typedQuery.getResultList();        
        return paises;
    }

    @Override
    public Pais getPaisPorNombre(String nombre) {        
        TypedQuery<Pais> typedQuery = em.createNamedQuery("Pais.buscarPorNombre", Pais.class);
        typedQuery.setParameter("nombre", nombre);
        List<Pais> paises = typedQuery.getResultList();        
        if (paises.isEmpty()) {
            return null;
        } else {
            return paises.get(0);
        }
    }

    @Override
    public void actualizar(Pais pais) {        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pais);
        tx.commit();        
    }

    @Override
    public void guardar(Pais pais) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(pais);
        tx.commit();        
    }
}