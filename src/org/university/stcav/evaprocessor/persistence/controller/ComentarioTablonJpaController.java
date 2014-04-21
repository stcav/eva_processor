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
import org.university.stcav.evaprocessor.persistence.entities.ComentarioTablon;

/**
 *
 * @author stcav
 */
public class ComentarioTablonJpaController {

    public ComentarioTablonJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComentarioTablon comentarioTablon) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comentarioTablon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComentarioTablon comentarioTablon) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comentarioTablon = em.merge(comentarioTablon);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comentarioTablon.getIdComentarioTablon();
                if (findComentarioTablon(id) == null) {
                    throw new NonexistentEntityException("The comentarioTablon with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComentarioTablon comentarioTablon;
            try {
                comentarioTablon = em.getReference(ComentarioTablon.class, id);
                comentarioTablon.getIdComentarioTablon();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentarioTablon with id " + id + " no longer exists.", enfe);
            }
            em.remove(comentarioTablon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComentarioTablon> findComentarioTablonEntities() {
        return findComentarioTablonEntities(true, -1, -1);
    }

    public List<ComentarioTablon> findComentarioTablonEntities(int maxResults, int firstResult) {
        return findComentarioTablonEntities(false, maxResults, firstResult);
    }

    private List<ComentarioTablon> findComentarioTablonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComentarioTablon.class));
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

    public ComentarioTablon findComentarioTablon(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComentarioTablon.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioTablonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComentarioTablon> rt = cq.from(ComentarioTablon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
