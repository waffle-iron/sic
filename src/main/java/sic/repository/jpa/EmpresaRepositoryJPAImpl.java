package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.Empresa;
import sic.repository.IEmpresaRepository;

@Repository
public class EmpresaRepositoryJPAImpl implements IEmpresaRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Empresa> getEmpresas() {        
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarTodas", Empresa.class);
        List<Empresa> empresas = typedQuery.getResultList();        
        return empresas;
    }

    @Override
    public Empresa getEmpresaPorNombre(String nombre) {        
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarPorNombre", Empresa.class);
        typedQuery.setParameter("nombre", nombre);
        List<Empresa> empresas = typedQuery.getResultList();        
        if (empresas.isEmpty()) {
            return null;
        } else {
            return empresas.get(0);
        }
    }

    @Override
    public Empresa getEmpresaPorCUIP(long cuip) {        
        TypedQuery<Empresa> typedQuery = em.createNamedQuery("Empresa.buscarPorCUIP", Empresa.class);
        typedQuery.setParameter("cuip", cuip);
        List<Empresa> empresas = typedQuery.getResultList();        
        if (empresas.isEmpty()) {
            return null;
        } else {
            return empresas.get(0);
        }
    }

    @Override
    public void guardar(Empresa empresa) {        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(em.merge(empresa));
        tx.commit();        
    }

    @Override
    public void actualizar(Empresa empresa) {        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(empresa);
        tx.commit();        
    }
}