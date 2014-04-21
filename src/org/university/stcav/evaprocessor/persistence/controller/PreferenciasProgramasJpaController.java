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
import org.university.stcav.evaprocessor.persistence.entities.PreferenciasProgramas;
import org.university.stcav.evaprocessor.persistence.entities.PreferenciasProgramasPK;

/**
 *
 * @author stcav
 */
public class PreferenciasProgramasJpaController {

    public PreferenciasProgramasJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreferenciasProgramas preferenciasProgramas) throws PreexistingEntityException, Exception {
        if (preferenciasProgramas.getPreferenciasProgramasPK() == null) {
            preferenciasProgramas.setPreferenciasProgramasPK(new PreferenciasProgramasPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(preferenciasProgramas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferenciasProgramas(preferenciasProgramas.getPreferenciasProgramasPK()) != null) {
                throw new PreexistingEntityException("PreferenciasProgramas " + preferenciasProgramas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreferenciasProgramas preferenciasProgramas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            preferenciasProgramas = em.merge(preferenciasProgramas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreferenciasProgramasPK id = preferenciasProgramas.getPreferenciasProgramasPK();
                if (findPreferenciasProgramas(id) == null) {
                    throw new NonexistentEntityException("The preferenciasProgramas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreferenciasProgramasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciasProgramas preferenciasProgramas;
            try {
                preferenciasProgramas = em.getReference(PreferenciasProgramas.class, id);
                preferenciasProgramas.getPreferenciasProgramasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferenciasProgramas with id " + id + " no longer exists.", enfe);
            }
            em.remove(preferenciasProgramas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreferenciasProgramas> findPreferenciasProgramasEntities() {
        return findPreferenciasProgramasEntities(true, -1, -1);
    }

    public List<PreferenciasProgramas> findPreferenciasProgramasEntities(int maxResults, int firstResult) {
        return findPreferenciasProgramasEntities(false, maxResults, firstResult);
    }

    private List<PreferenciasProgramas> findPreferenciasProgramasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreferenciasProgramas.class));
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

    public PreferenciasProgramas findPreferenciasProgramas(PreferenciasProgramasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreferenciasProgramas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciasProgramasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreferenciasProgramas> rt = cq.from(PreferenciasProgramas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
