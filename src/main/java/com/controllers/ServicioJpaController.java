/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import com.models.Incidente;
import com.models.Cliente;
import com.models.Servicio;
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
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ServicioJpaController() {
        emf =Persistence.createEntityManagerFactory("PersistenceJPA");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidente incidente = servicio.getIncidente();
            if (incidente != null) {
                incidente = em.getReference(incidente.getClass(), incidente.getId());
                servicio.setIncidente(incidente);
            }
            Cliente cliente = servicio.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                servicio.setCliente(cliente);
            }
            em.persist(servicio);
            if (incidente != null) {
                Servicio oldServicioOfIncidente = incidente.getServicio();
                if (oldServicioOfIncidente != null) {
                    oldServicioOfIncidente.setIncidente(null);
                    oldServicioOfIncidente = em.merge(oldServicioOfIncidente);
                }
                incidente.setServicio(servicio);
                incidente = em.merge(incidente);
            }
            if (cliente != null) {
                cliente.getServicios().add(servicio);
                cliente = em.merge(cliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            Incidente incidenteOld = persistentServicio.getIncidente();
            Incidente incidenteNew = servicio.getIncidente();
            Cliente clienteOld = persistentServicio.getCliente();
            Cliente clienteNew = servicio.getCliente();
            if (incidenteNew != null) {
                incidenteNew = em.getReference(incidenteNew.getClass(), incidenteNew.getId());
                servicio.setIncidente(incidenteNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                servicio.setCliente(clienteNew);
            }
            servicio = em.merge(servicio);
            if (incidenteOld != null && !incidenteOld.equals(incidenteNew)) {
                incidenteOld.setServicio(null);
                incidenteOld = em.merge(incidenteOld);
            }
            if (incidenteNew != null && !incidenteNew.equals(incidenteOld)) {
                Servicio oldServicioOfIncidente = incidenteNew.getServicio();
                if (oldServicioOfIncidente != null) {
                    oldServicioOfIncidente.setIncidente(null);
                    oldServicioOfIncidente = em.merge(oldServicioOfIncidente);
                }
                incidenteNew.setServicio(servicio);
                incidenteNew = em.merge(incidenteNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getServicios().remove(servicio);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getServicios().add(servicio);
                clienteNew = em.merge(clienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            Incidente incidente = servicio.getIncidente();
            if (incidente != null) {
                incidente.setServicio(null);
                incidente = em.merge(incidente);
            }
            Cliente cliente = servicio.getCliente();
            if (cliente != null) {
                cliente.getServicios().remove(servicio);
                cliente = em.merge(cliente);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
