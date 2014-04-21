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
import org.university.stcav.evaprocessor.persistence.entities.Votacion;

/**
 *
 * @author stcav
 */
public class VotacionJpaController {

    public VotacionJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Votacion votacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(votacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Votacion votacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            votacion = em.merge(votacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = votacion.getIdVotacion();
                if (findVotacion(id) == null) {
                    throw new NonexistentEntityException("The votacion with id " + id + " no longer exists.");
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
            Votacion votacion;
            try {
                votacion = em.getReference(Votacion.class, id);
                votacion.getIdVotacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The votacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(votacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Votacion> findVotacionEntities() {
        return findVotacionEntities(true, -1, -1);
    }

    public List<Votacion> findVotacionEntities(int maxResults, int firstResult) {
        return findVotacionEntities(false, maxResults, firstResult);
    }

    private List<Votacion> findVotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Votacion.class));
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

    public Votacion findVotacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Votacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getVotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Votacion> rt = cq.from(Votacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
