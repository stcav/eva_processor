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
import org.university.stcav.evaprocessor.persistence.entities.Notificado;
import org.university.stcav.evaprocessor.persistence.entities.NotificadoPK;

/**
 *
 * @author stcav
 */
public class NotificadoJpaController {

    public NotificadoJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificado notificado) throws PreexistingEntityException, Exception {
        if (notificado.getNotificadoPK() == null) {
            notificado.setNotificadoPK(new NotificadoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(notificado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotificado(notificado.getNotificadoPK()) != null) {
                throw new PreexistingEntityException("Notificado " + notificado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificado notificado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            notificado = em.merge(notificado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NotificadoPK id = notificado.getNotificadoPK();
                if (findNotificado(id) == null) {
                    throw new NonexistentEntityException("The notificado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NotificadoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificado notificado;
            try {
                notificado = em.getReference(Notificado.class, id);
                notificado.getNotificadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificado with id " + id + " no longer exists.", enfe);
            }
            em.remove(notificado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificado> findNotificadoEntities() {
        return findNotificadoEntities(true, -1, -1);
    }

    public List<Notificado> findNotificadoEntities(int maxResults, int firstResult) {
        return findNotificadoEntities(false, maxResults, firstResult);
    }

    private List<Notificado> findNotificadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificado.class));
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

    public Notificado findNotificado(NotificadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificado.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificado> rt = cq.from(Notificado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
