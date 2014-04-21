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
import org.university.stcav.evaprocessor.persistence.entities.Etiquetas;

/**
 *
 * @author stcav
 */
public class EtiquetasJpaController {

    public EtiquetasJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etiquetas etiquetas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(etiquetas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etiquetas etiquetas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            etiquetas = em.merge(etiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = etiquetas.getIdEtiquetas();
                if (findEtiquetas(id) == null) {
                    throw new NonexistentEntityException("The etiquetas with id " + id + " no longer exists.");
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
            Etiquetas etiquetas;
            try {
                etiquetas = em.getReference(Etiquetas.class, id);
                etiquetas.getIdEtiquetas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etiquetas with id " + id + " no longer exists.", enfe);
            }
            em.remove(etiquetas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etiquetas> findEtiquetasEntities() {
        return findEtiquetasEntities(true, -1, -1);
    }

    public List<Etiquetas> findEtiquetasEntities(int maxResults, int firstResult) {
        return findEtiquetasEntities(false, maxResults, firstResult);
    }

    private List<Etiquetas> findEtiquetasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etiquetas.class));
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

    public Etiquetas findEtiquetas(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etiquetas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtiquetasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etiquetas> rt = cq.from(Etiquetas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
