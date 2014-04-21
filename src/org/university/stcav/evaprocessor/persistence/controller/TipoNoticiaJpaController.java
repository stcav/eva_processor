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
import org.university.stcav.evaprocessor.persistence.entities.TipoNoticia;

/**
 *
 * @author stcav
 */
public class TipoNoticiaJpaController {

    public TipoNoticiaJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoNoticia tipoNoticia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoNoticia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoNoticia tipoNoticia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoNoticia = em.merge(tipoNoticia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoNoticia.getIdTipoNoticia();
                if (findTipoNoticia(id) == null) {
                    throw new NonexistentEntityException("The tipoNoticia with id " + id + " no longer exists.");
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
            TipoNoticia tipoNoticia;
            try {
                tipoNoticia = em.getReference(TipoNoticia.class, id);
                tipoNoticia.getIdTipoNoticia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoNoticia with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoNoticia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoNoticia> findTipoNoticiaEntities() {
        return findTipoNoticiaEntities(true, -1, -1);
    }

    public List<TipoNoticia> findTipoNoticiaEntities(int maxResults, int firstResult) {
        return findTipoNoticiaEntities(false, maxResults, firstResult);
    }

    private List<TipoNoticia> findTipoNoticiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoNoticia.class));
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

    public TipoNoticia findTipoNoticia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoNoticia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoNoticiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoNoticia> rt = cq.from(TipoNoticia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
