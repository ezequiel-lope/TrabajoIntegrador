/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import com.models.Incidente;
import com.models.Tecnico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ezelc
 */
public class TecnicoJpaController implements Serializable {

    public TecnicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public TecnicoJpaController() {
        emf = Persistence.createEntityManagerFactory("PersistenceJPA");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidente incidenteAsignado = tecnico.getIncidenteAsignado();
            if (incidenteAsignado != null) {
                incidenteAsignado = em.getReference(incidenteAsignado.getClass(), incidenteAsignado.getId());
                tecnico.setIncidenteAsignado(incidenteAsignado);
            }
            Incidente incidente = tecnico.getIncidente();
            if (incidente != null) {
                incidente = em.getReference(incidente.getClass(), incidente.getId());
                tecnico.setIncidente(incidente);
            }
            em.persist(tecnico);
            if (incidenteAsignado != null) {
                Tecnico oldTecnicoAsignadoOfIncidenteAsignado = incidenteAsignado.getTecnicoAsignado();
                if (oldTecnicoAsignadoOfIncidenteAsignado != null) {
                    oldTecnicoAsignadoOfIncidenteAsignado.setIncidenteAsignado(null);
                    oldTecnicoAsignadoOfIncidenteAsignado = em.merge(oldTecnicoAsignadoOfIncidenteAsignado);
                }
                incidenteAsignado.setTecnicoAsignado(tecnico);
                incidenteAsignado = em.merge(incidenteAsignado);
            }
            if (incidente != null) {
                Tecnico oldTecnicoAsignadoOfIncidente = incidente.getTecnicoAsignado();
                if (oldTecnicoAsignadoOfIncidente != null) {
                    oldTecnicoAsignadoOfIncidente.setIncidente(null);
                    oldTecnicoAsignadoOfIncidente = em.merge(oldTecnicoAsignadoOfIncidente);
                }
                incidente.setTecnicoAsignado(tecnico);
                incidente = em.merge(incidente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tecnico tecnico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico persistentTecnico = em.find(Tecnico.class, tecnico.getId());
            Incidente incidenteAsignadoOld = persistentTecnico.getIncidenteAsignado();
            Incidente incidenteAsignadoNew = tecnico.getIncidenteAsignado();
            Incidente incidenteOld = persistentTecnico.getIncidente();
            Incidente incidenteNew = tecnico.getIncidente();
            if (incidenteAsignadoNew != null) {
                incidenteAsignadoNew = em.getReference(incidenteAsignadoNew.getClass(), incidenteAsignadoNew.getId());
                tecnico.setIncidenteAsignado(incidenteAsignadoNew);
            }
            if (incidenteNew != null) {
                incidenteNew = em.getReference(incidenteNew.getClass(), incidenteNew.getId());
                tecnico.setIncidente(incidenteNew);
            }
            tecnico = em.merge(tecnico);
            if (incidenteAsignadoOld != null && !incidenteAsignadoOld.equals(incidenteAsignadoNew)) {
                incidenteAsignadoOld.setTecnicoAsignado(null);
                incidenteAsignadoOld = em.merge(incidenteAsignadoOld);
            }
            if (incidenteAsignadoNew != null && !incidenteAsignadoNew.equals(incidenteAsignadoOld)) {
                Tecnico oldTecnicoAsignadoOfIncidenteAsignado = incidenteAsignadoNew.getTecnicoAsignado();
                if (oldTecnicoAsignadoOfIncidenteAsignado != null) {
                    oldTecnicoAsignadoOfIncidenteAsignado.setIncidenteAsignado(null);
                    oldTecnicoAsignadoOfIncidenteAsignado = em.merge(oldTecnicoAsignadoOfIncidenteAsignado);
                }
                incidenteAsignadoNew.setTecnicoAsignado(tecnico);
                incidenteAsignadoNew = em.merge(incidenteAsignadoNew);
            }
            if (incidenteOld != null && !incidenteOld.equals(incidenteNew)) {
                incidenteOld.setTecnicoAsignado(null);
                incidenteOld = em.merge(incidenteOld);
            }
            if (incidenteNew != null && !incidenteNew.equals(incidenteOld)) {
                Tecnico oldTecnicoAsignadoOfIncidente = incidenteNew.getTecnicoAsignado();
                if (oldTecnicoAsignadoOfIncidente != null) {
                    oldTecnicoAsignadoOfIncidente.setIncidente(null);
                    oldTecnicoAsignadoOfIncidente = em.merge(oldTecnicoAsignadoOfIncidente);
                }
                incidenteNew.setTecnicoAsignado(tecnico);
                incidenteNew = em.merge(incidenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tecnico.getId();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            Incidente incidenteAsignado = tecnico.getIncidenteAsignado();
            if (incidenteAsignado != null) {
                incidenteAsignado.setTecnicoAsignado(null);
                incidenteAsignado = em.merge(incidenteAsignado);
            }
            Incidente incidente = tecnico.getIncidente();
            if (incidente != null) {
                incidente.setTecnicoAsignado(null);
                incidente = em.merge(incidente);
            }
            em.remove(tecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
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

    public Tecnico findTecnico(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
