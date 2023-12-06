
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import com.models.Cliente;
import com.models.Incidente;
import com.models.Servicio;
import com.models.Tecnico;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceControllers{
    ClienteJpaController cliJpa = new ClienteJpaController();
    IncidenteJpaController inciJpa = new IncidenteJpaController();
    ServicioJpaController serviJpa = new ServicioJpaController();
    TecnicoJpaController tecniJpa = new TecnicoJpaController();

    void crearTecnico(Tecnico tec) {
        tecniJpa.create(tec);
    }

    void eliminarTecnico(int id) {
         try {
            tecniJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    Tecnico buscarTecnico(int id) {
         return tecniJpa.findTecnico(id);
    }
    
    void editarTecnico(Tecnico tec) {
         try {
           tecniJpa.edit(tec);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Tecnico> listaTecnico() {
        List <Tecnico> listaTemp = tecniJpa.findTecnicoEntities();
        ArrayList<Tecnico> lista = new ArrayList(listaTemp);
        return lista;
    }

    void crearCliente(Cliente cli) {
        cliJpa.create(cli);
    }
    
    void eliminarCliente(int id) {
         try {
            cliJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Cliente buscarCliente(int id) {
       return cliJpa.findCliente(id);
    }
    
    void editarCliente(Cliente cli) {
        try {
           cliJpa.edit(cli);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Cliente> listaCliente() {
        List <Cliente> listaTemp = cliJpa.findClienteEntities();
        ArrayList<Cliente> lista = new ArrayList(listaTemp);
        return lista;
    }

    

    
    void crearIncidente(Incidente inci) {
        inciJpa.create(inci);
    }
    
    void eliminarIncidente(int id) {
        try {
            inciJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Incidente buscarIncidente(int id) {
        Incidente inci = new Incidente();
       inci = inciJpa.findIncidente(id);
        return inci;
    }
    
    void editarIncidente(Incidente inci) throws Exception {
        try {
           inciJpa.edit(inci);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Incidente> listaIncidente() {
       List <Incidente> listaTemp = inciJpa.findIncidenteEntities();
        ArrayList<Incidente> lista = new ArrayList(listaTemp);
        return lista;
    }

    void crearServicio(Servicio servi) {
         serviJpa.create(servi);
    }

    void eliminarServicio(int id) {
      try {
            serviJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void editarServicip(Servicio servi) {
        try {
           serviJpa.edit(servi);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Servicio buscarServicio(int id) {
        return serviJpa.findServicio(id);
    }

    ArrayList<Servicio> listaServicio() {
       List <Servicio> listaTemp = serviJpa.findServicioEntities();
        ArrayList<Servicio> lista = new ArrayList(listaTemp);
        return lista;
    }

    

    

    

   
    

   
    
    
}
