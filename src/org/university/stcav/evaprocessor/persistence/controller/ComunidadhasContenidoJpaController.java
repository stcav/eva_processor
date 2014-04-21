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
import org.university.stcav.evaprocessor.persistence.entities.ComunidadhasContenido;
import org.university.stcav.evaprocessor.persistence.entities.ComunidadhasContenidoPK;

/**
 *
 * @author stcav
 */
public class ComunidadhasContenidoJpaController {

    public ComunidadhasContenidoJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComunidadhasContenido comunidadhasContenido) throws PreexistingEntityException, Exception {
        if (comunidadhasContenido.getComunidadhasContenidoPK() == null) {
            comunidadhasContenido.setComunidadhasContenidoPK(new ComunidadhasContenidoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comunidadhasContenido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComunidadhasContenido(comunidadhasContenido.getComunidadhasContenidoPK()) != null) {
                throw new PreexistingEntityException("ComunidadhasContenido " + comunidadhasContenido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComunidadhasContenido comunidadhasContenido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comunidadhasContenido = em.merge(comunidadhasContenido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComunidadhasContenidoPK id = comunidadhasContenido.getComunidadhasContenidoPK();
                if (findComunidadhasContenido(id) == null) {
                    throw new NonexistentEntityException("The comunidadhasContenido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComunidadhasContenidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComunidadhasContenido comunidadhasContenido;
            try {
                comunidadhasContenido = em.getReference(ComunidadhasContenido.class, id);
                comunidadhasContenido.getComunidadhasContenidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunidadhasContenido with id " + id + " no longer exists.", enfe);
            }
            em.remove(comunidadhasContenido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComunidadhasContenido> findComunidadhasContenidoEntities() {
        return findComunidadhasContenidoEntities(true, -1, -1);
    }

    public List<ComunidadhasContenido> findComunidadhasContenidoEntities(int maxResults, int firstResult) {
        return findComunidadhasContenidoEntities(false, maxResults, firstResult);
    }

    private List<ComunidadhasContenido> findComunidadhasContenidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComunidadhasContenido.class));
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

    public ComunidadhasContenido findComunidadhasContenido(ComunidadhasContenidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComunidadhasContenido.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunidadhasContenidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComunidadhasContenido> rt = cq.from(ComunidadhasContenido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
