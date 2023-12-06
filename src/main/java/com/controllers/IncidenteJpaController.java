/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.models.Cliente;
import com.models.Incidente;
import com.models.Tecnico;
import com.models.Servicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ezelc
 */
public class IncidenteJpaController implements Serializable {

    public IncidenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public IncidenteJpaController() {
        emf = Persistence.createEntityManagerFactory("ProyectoIntegradorJpa");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Incidente incidente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = incidente.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                incidente.setCliente(cliente);
            }
            Tecnico tecnicoAsignado = incidente.getTecnicoAsignado();
            if (tecnicoAsignado != null) {
                tecnicoAsignado = em.getReference(tecnicoAsignado.getClass(), tecnicoAsignado.getId());
                incidente.setTecnicoAsignado(tecnicoAsignado);
            }
            Servicio servicio = incidente.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                incidente.setServicio(servicio);
            }
            em.persist(incidente);
            if (cliente != null) {
                cliente.getIncidentes().add(incidente);
                cliente = em.merge(cliente);
            }
            if (tecnicoAsignado != null) {
                Incidente oldIncidenteAsignadoOfTecnicoAsignado = tecnicoAsignado.getIncidenteAsignado();
                if (oldIncidenteAsignadoOfTecnicoAsignado != null) {
                    oldIncidenteAsignadoOfTecnicoAsignado.setTecnicoAsignado(null);
                    oldIncidenteAsignadoOfTecnicoAsignado = em.merge(oldIncidenteAsignadoOfTecnicoAsignado);
                }
                tecnicoAsignado.setIncidenteAsignado(incidente);
                tecnicoAsignado = em.merge(tecnicoAsignado);
            }
            if (servicio != null) {
                Incidente oldIncidenteOfServicio = servicio.getIncidente();
                if (oldIncidenteOfServicio != null) {
                    oldIncidenteOfServicio.setServicio(null);
                    oldIncidenteOfServicio = em.merge(oldIncidenteOfServicio);
                }
                servicio.setIncidente(incidente);
                servicio = em.merge(servicio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Incidente incidente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidente persistentIncidente = em.find(Incidente.class, incidente.getId());
            Cliente clienteOld = persistentIncidente.getCliente();
            Cliente clienteNew = incidente.getCliente();
            Tecnico tecnicoAsignadoOld = persistentIncidente.getTecnicoAsignado();
            Tecnico tecnicoAsignadoNew = incidente.getTecnicoAsignado();
            Servicio servicioOld = persistentIncidente.getServicio();
            Servicio servicioNew = incidente.getServicio();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                incidente.setCliente(clienteNew);
            }
            if (tecnicoAsignadoNew != null) {
                tecnicoAsignadoNew = em.getReference(tecnicoAsignadoNew.getClass(), tecnicoAsignadoNew.getId());
                incidente.setTecnicoAsignado(tecnicoAsignadoNew);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                incidente.setServicio(servicioNew);
            }
            incidente = em.merge(incidente);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getIncidentes().remove(incidente);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getIncidentes().add(incidente);
                clienteNew = em.merge(clienteNew);
            }
            if (tecnicoAsignadoOld != null && !tecnicoAsignadoOld.equals(tecnicoAsignadoNew)) {
                tecnicoAsignadoOld.setIncidenteAsignado(null);
                tecnicoAsignadoOld = em.merge(tecnicoAsignadoOld);
            }
            if (tecnicoAsignadoNew != null && !tecnicoAsignadoNew.equals(tecnicoAsignadoOld)) {
                Incidente oldIncidenteAsignadoOfTecnicoAsignado = tecnicoAsignadoNew.getIncidenteAsignado();
                if (oldIncidenteAsignadoOfTecnicoAsignado != null) {
                    oldIncidenteAsignadoOfTecnicoAsignado.setTecnicoAsignado(null);
                    oldIncidenteAsignadoOfTecnicoAsignado = em.merge(oldIncidenteAsignadoOfTecnicoAsignado);
                }
                tecnicoAsignadoNew.setIncidenteAsignado(incidente);
                tecnicoAsignadoNew = em.merge(tecnicoAsignadoNew);
            }
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.setIncidente(null);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                Incidente oldIncidenteOfServicio = servicioNew.getIncidente();
                if (oldIncidenteOfServicio != null) {
                    oldIncidenteOfServicio.setServicio(null);
                    oldIncidenteOfServicio = em.merge(oldIncidenteOfServicio);
                }
                servicioNew.setIncidente(incidente);
                servicioNew = em.merge(servicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = incidente.getId();
                if (findIncidente(id) == null) {
                    throw new NonexistentEntityException("The incidente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidente incidente;
            try {
                incidente = em.getReference(Incidente.class, id);
                incidente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incidente with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = incidente.getCliente();
            if (cliente != null) {
                cliente.getIncidentes().remove(incidente);
                cliente = em.merge(cliente);
            }
            Tecnico tecnicoAsignado = incidente.getTecnicoAsignado();
            if (tecnicoAsignado != null) {
                tecnicoAsignado.setIncidenteAsignado(null);
                tecnicoAsignado = em.merge(tecnicoAsignado);
            }
            Servicio servicio = incidente.getServicio();
            if (servicio != null) {
                servicio.setIncidente(null);
                servicio = em.merge(servicio);
            }
            em.remove(incidente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Incidente> findIncidenteEntities() {
        return findIncidenteEntities(true, -1, -1);
    }

    public List<Incidente> findIncidenteEntities(int maxResults, int firstResult) {
        return findIncidenteEntities(false, maxResults, firstResult);
    }

    private List<Incidente> findIncidenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Incidente.class));
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

    public Incidente findIncidente(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Incidente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncidenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Incidente> rt = cq.from(Incidente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
