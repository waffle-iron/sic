package sic.repository;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import sic.modelo.Usuario;
import sic.service.ServiceException;
import sic.util.PersistenceUtil;

public class UsuarioRepository {

    public List<Usuario> getUsuarios() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> typedQuery = em.createNamedQuery("Usuario.buscarTodos", Usuario.class);
        List<Usuario> usuarios = typedQuery.getResultList();
        em.close();
        return usuarios;
    }

    public Usuario getUsuarioPorNombre(String nombre) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> typedQuery = em.createNamedQuery("Usuario.buscarPorNombre", Usuario.class);
        typedQuery.setParameter("nombre", nombre);
        List<Usuario> usuarios = typedQuery.getResultList();
        em.close();
        if (usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }

    public List<Usuario> getUsuariosAdministradores() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> typedQuery = em.createNamedQuery("Usuario.buscarUsuariosAdministradores", Usuario.class);
        List<Usuario> usuarios = typedQuery.getResultList();
        em.close();
        return usuarios;
    }

    public Usuario getUsuarioPorNombreContrasenia(String nombre, String contrasenia) throws ServiceException {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> typedQuery = em.createNamedQuery("Usuario.buscarPorNombreContrasenia", Usuario.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("password", contrasenia);
        List<Usuario> usuarios = typedQuery.getResultList();
        em.close();
        if (usuarios.isEmpty()) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes").getString("mensaje_usuario_logInInvalido"));
        } else {
            return usuarios.get(0);
        }
    }

    public void actualizar(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(usuario);
        tx.commit();
        em.close();
    }

    public void guardar(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(usuario);
        tx.commit();
        em.close();
    }
}