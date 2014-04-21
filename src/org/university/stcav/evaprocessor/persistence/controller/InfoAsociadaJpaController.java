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
import org.university.stcav.evaprocessor.persistence.entities.InfoAsociada;

/**
 *
 * @author stcav
 */
public class InfoAsociadaJpaController {

    public InfoAsociadaJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoAsociada infoAsociada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(infoAsociada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoAsociada infoAsociada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            infoAsociada = em.merge(infoAsociada);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = infoAsociada.getIdInfoAsociada();
                if (findInfoAsociada(id) == null) {
                    throw new NonexistentEntityException("The infoAsociada with id " + id + " no longer exists.");
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
            InfoAsociada infoAsociada;
            try {
                infoAsociada = em.getReference(InfoAsociada.class, id);
                infoAsociada.getIdInfoAsociada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoAsociada with id " + id + " no longer exists.", enfe);
            }
            em.remove(infoAsociada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoAsociada> findInfoAsociadaEntities() {
        return findInfoAsociadaEntities(true, -1, -1);
    }

    public List<InfoAsociada> findInfoAsociadaEntities(int maxResults, int firstResult) {
        return findInfoAsociadaEntities(false, maxResults, firstResult);
    }

    private List<InfoAsociada> findInfoAsociadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoAsociada.class));
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

    public InfoAsociada findInfoAsociada(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoAsociada.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoAsociadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoAsociada> rt = cq.from(InfoAsociada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
