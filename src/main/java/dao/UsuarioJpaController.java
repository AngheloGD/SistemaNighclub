/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ANGHELO
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UsuarioJpaController() {
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaNightclub_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCodiUsua()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodiUsua();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodiUsua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario logueo(String usuario, String clave) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Usuario.validar");
            q.setParameter("logiUsua", usuario);
            q.setParameter("passUsua", clave);
            Usuario pers = (Usuario) q.getSingleResult();
            return pers;
        } catch (Exception e) {
            return null;
        }
    }

    public String token(String usuario, String clave) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Usuario.token");
            q.setParameter("logiUsua", usuario);
            q.setParameter("passUsua", clave);
            Usuario pers = (Usuario) q.getSingleResult();
            return pers.getToken().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String clave(String usuario, String clave) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Usuario.clave");
            q.setParameter("logiUsua", usuario);
            q.setParameter("passUsua", clave);
            Usuario pers = (Usuario) q.getSingleResult();
            return pers.getPassUsua().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        UsuarioJpaController usuDao = new UsuarioJpaController();
        Usuario usu = usuDao.logueo("Anghelo", "1234");
        if (usu != null) {
            int codi = usu.getCodiUsua();
            System.out.println("El codigo encontrado es:\n " + codi);
        } else {
            System.out.println("Usuario no encontrado");
        }

    }

    public boolean editarClave(Usuario usuario, String nuevaClave) {
        try {
            // Actualizar la contraseña del usuario
            usuario.setPassUsua(nuevaClave);

            // Guardar los cambios
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();

            // Retornar `true` si la contraseña se cambió correctamente
            return true;
        } catch (Exception e) {
            // Manejar la excepción
            e.printStackTrace();
            return false;
        }
    }
    // Método para generar un token JWT usando la biblioteca jjwt

    public String generateJwtToken(String usuario, String claveMD5) {
        // Clave secreta para firmar el token
        String secretKey = "lafedelcuto"; // Cambia esto por tu clave secreta

        // Crear el token JWT
        String token = Jwts.builder()
                .setSubject(usuario) // Establecer el sujeto del token (en este caso, el usuario)
                .signWith(SignatureAlgorithm.HS256, secretKey) // Firmar el token usando el algoritmo HMAC SHA-256 y la clave secreta
                .setIssuedAt(new Date()) // Establecer la fecha de emisión del token (actual)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Establecer la fecha de expiración (1 día después de la emisión)
                .compact(); // Compactar el token en una cadena

        return token; // Devolver el token generado
    }

}
