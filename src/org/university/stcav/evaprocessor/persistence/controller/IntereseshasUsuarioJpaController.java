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
import org.university.stcav.evaprocessor.persistence.entities.IntereseshasUsuario;
import org.university.stcav.evaprocessor.persistence.entities.IntereseshasUsuarioPK;

/**
 *
 * @author stcav
 */
public class IntereseshasUsuarioJpaController {

    public IntereseshasUsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntereseshasUsuario intereseshasUsuario) throws PreexistingEntityException, Exception {
        if (intereseshasUsuario.getIntereseshasUsuarioPK() == null) {
            intereseshasUsuario.setIntereseshasUsuarioPK(new IntereseshasUsuarioPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(intereseshasUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntereseshasUsuario(intereseshasUsuario.getIntereseshasUsuarioPK()) != null) {
                throw new PreexistingEntityException("IntereseshasUsuario " + intereseshasUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntereseshasUsuario intereseshasUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            intereseshasUsuario = em.merge(intereseshasUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IntereseshasUsuarioPK id = intereseshasUsuario.getIntereseshasUsuarioPK();
                if (findIntereseshasUsuario(id) == null) {
                    throw new NonexistentEntityException("The intereseshasUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IntereseshasUsuarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntereseshasUsuario intereseshasUsuario;
            try {
                intereseshasUsuario = em.getReference(IntereseshasUsuario.class, id);
                intereseshasUsuario.getIntereseshasUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intereseshasUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(intereseshasUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntereseshasUsuario> findIntereseshasUsuarioEntities() {
        return findIntereseshasUsuarioEntities(true, -1, -1);
    }

    public List<IntereseshasUsuario> findIntereseshasUsuarioEntities(int maxResults, int firstResult) {
        return findIntereseshasUsuarioEntities(false, maxResults, firstResult);
    }

    private List<IntereseshasUsuario> findIntereseshasUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntereseshasUsuario.class));
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

    public IntereseshasUsuario findIntereseshasUsuario(IntereseshasUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntereseshasUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntereseshasUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntereseshasUsuario> rt = cq.from(IntereseshasUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
