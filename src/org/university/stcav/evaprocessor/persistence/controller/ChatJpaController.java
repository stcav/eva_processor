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
import org.university.stcav.evaprocessor.persistence.entities.Chat;

/**
 *
 * @author stcav
 */
public class ChatJpaController {

    public ChatJpaController() {
        emf = Persistence.createEntityManagerFactory("EVAProcessorPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Chat chat) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(chat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Chat chat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            chat = em.merge(chat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = chat.getIdChat();
                if (findChat(id) == null) {
                    throw new NonexistentEntityException("The chat with id " + id + " no longer exists.");
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
            Chat chat;
            try {
                chat = em.getReference(Chat.class, id);
                chat.getIdChat();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chat with id " + id + " no longer exists.", enfe);
            }
            em.remove(chat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Chat> findChatEntities() {
        return findChatEntities(true, -1, -1);
    }

    public List<Chat> findChatEntities(int maxResults, int firstResult) {
        return findChatEntities(false, maxResults, firstResult);
    }

    private List<Chat> findChatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Chat.class));
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

    public Chat findChat(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Chat.class, id);
        } finally {
            em.close();
        }
    }

    public int getChatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Chat> rt = cq.from(Chat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
