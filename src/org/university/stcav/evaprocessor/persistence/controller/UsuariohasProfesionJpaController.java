/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.university.stcav.evaprocessor.persistence.controller.exceptions.NonexistentEntityException;
import org.university.stcav.evaprocessor.persistence.controller.exceptions.PreexistingEntityException;
import org.university.stcav.evaprocessor.persistence.entities.UsuariohasProfesion;
import org.university.stcav.evaprocessor.persistence.entities.UsuariohasProfesionPK;

/**
 *
 * @author stcav
 */
public class UsuariohasProfesionJpaController {

    public UsuariohasProfesionJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuariohasProfesion usuariohasProfesion) throws PreexistingEntityException, Exception {
        if (usuariohasProfesion.getUsuariohasProfesionPK() == null) {
            usuariohasProfesion.setUsuariohasProfesionPK(new UsuariohasProfesionPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuariohasProfesion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuariohasProfesion(usuariohasProfesion.getUsuariohasProfesionPK()) != null) {
                throw new PreexistingEntityException("UsuariohasProfesion " + usuariohasProfesion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuariohasProfesion usuariohasProfesion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuariohasProfesion = em.merge(usuariohasProfesion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuariohasProfesionPK id = usuariohasProfesion.getUsuariohasProfesionPK();
                if (findUsuariohasProfesion(id) == null) {
                    throw new NonexistentEntityException("The usuariohasProfesion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuariohasProfesionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuariohasProfesion usuariohasProfesion;
            try {
                usuariohasProfesion = em.getReference(UsuariohasProfesion.class, id);
                usuariohasProfesion.getUsuariohasProfesionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariohasProfesion with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuariohasProfesion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuariohasProfesion> findUsuariohasProfesionEntities() {
        return findUsuariohasProfesionEntities(true, -1, -1);
    }

    public List<UsuariohasProfesion> findUsuariohasProfesionEntities(int maxResults, int firstResult) {
        return findUsuariohasProfesionEntities(false, maxResults, firstResult);
    }

    private List<UsuariohasProfesion> findUsuariohasProfesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuariohasProfesion.class));
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

    public UsuariohasProfesion findUsuariohasProfesion(UsuariohasProfesionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuariohasProfesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariohasProfesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuariohasProfesion> rt = cq.from(UsuariohasProfesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
