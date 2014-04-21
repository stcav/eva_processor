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
import org.university.stcav.evaprocessor.persistence.entities.UsuariohasComunidad;
import org.university.stcav.evaprocessor.persistence.entities.UsuariohasComunidadPK;

/**
 *
 * @author stcav
 */
public class UsuariohasComunidadJpaController {

    public UsuariohasComunidadJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuariohasComunidad usuariohasComunidad) throws PreexistingEntityException, Exception {
        if (usuariohasComunidad.getUsuariohasComunidadPK() == null) {
            usuariohasComunidad.setUsuariohasComunidadPK(new UsuariohasComunidadPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuariohasComunidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuariohasComunidad(usuariohasComunidad.getUsuariohasComunidadPK()) != null) {
                throw new PreexistingEntityException("UsuariohasComunidad " + usuariohasComunidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuariohasComunidad usuariohasComunidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuariohasComunidad = em.merge(usuariohasComunidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuariohasComunidadPK id = usuariohasComunidad.getUsuariohasComunidadPK();
                if (findUsuariohasComunidad(id) == null) {
                    throw new NonexistentEntityException("The usuariohasComunidad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuariohasComunidadPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuariohasComunidad usuariohasComunidad;
            try {
                usuariohasComunidad = em.getReference(UsuariohasComunidad.class, id);
                usuariohasComunidad.getUsuariohasComunidadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariohasComunidad with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuariohasComunidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuariohasComunidad> findUsuariohasComunidadEntities() {
        return findUsuariohasComunidadEntities(true, -1, -1);
    }

    public List<UsuariohasComunidad> findUsuariohasComunidadEntities(int maxResults, int firstResult) {
        return findUsuariohasComunidadEntities(false, maxResults, firstResult);
    }

    private List<UsuariohasComunidad> findUsuariohasComunidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuariohasComunidad.class));
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

    public UsuariohasComunidad findUsuariohasComunidad(UsuariohasComunidadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuariohasComunidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariohasComunidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuariohasComunidad> rt = cq.from(UsuariohasComunidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
