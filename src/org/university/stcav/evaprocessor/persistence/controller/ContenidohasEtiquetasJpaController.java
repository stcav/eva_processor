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
import org.university.stcav.evaprocessor.persistence.entities.ContenidohasEtiquetas;
import org.university.stcav.evaprocessor.persistence.entities.ContenidohasEtiquetasPK;

/**
 *
 * @author stcav
 */
public class ContenidohasEtiquetasJpaController {

    public ContenidohasEtiquetasJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenidohasEtiquetas contenidohasEtiquetas) throws PreexistingEntityException, Exception {
        if (contenidohasEtiquetas.getContenidohasEtiquetasPK() == null) {
            contenidohasEtiquetas.setContenidohasEtiquetasPK(new ContenidohasEtiquetasPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contenidohasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContenidohasEtiquetas(contenidohasEtiquetas.getContenidohasEtiquetasPK()) != null) {
                throw new PreexistingEntityException("ContenidohasEtiquetas " + contenidohasEtiquetas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenidohasEtiquetas contenidohasEtiquetas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contenidohasEtiquetas = em.merge(contenidohasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContenidohasEtiquetasPK id = contenidohasEtiquetas.getContenidohasEtiquetasPK();
                if (findContenidohasEtiquetas(id) == null) {
                    throw new NonexistentEntityException("The contenidohasEtiquetas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ContenidohasEtiquetasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenidohasEtiquetas contenidohasEtiquetas;
            try {
                contenidohasEtiquetas = em.getReference(ContenidohasEtiquetas.class, id);
                contenidohasEtiquetas.getContenidohasEtiquetasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenidohasEtiquetas with id " + id + " no longer exists.", enfe);
            }
            em.remove(contenidohasEtiquetas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities() {
        return findContenidohasEtiquetasEntities(true, -1, -1);
    }

    public List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities(int maxResults, int firstResult) {
        return findContenidohasEtiquetasEntities(false, maxResults, firstResult);
    }

    private List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenidohasEtiquetas.class));
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

    public ContenidohasEtiquetas findContenidohasEtiquetas(ContenidohasEtiquetasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenidohasEtiquetas.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidohasEtiquetasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenidohasEtiquetas> rt = cq.from(ContenidohasEtiquetas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
