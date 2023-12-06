
package com.controllers;

import com.models.Servicio;

import java.util.ArrayList;

public class Controllers {
    PersistenceControllers control = new PersistenceControllers();
     public void crearServicio(Servicio servi)
    {
        control.crearServicio(servi);
    }
    
    public void eliminarServicio(int id)
    {
        control.eliminarServicio(id);
    }
    
    public void editarServicio(Servicio servi)
    {
        control.editarServicip(servi);
    }
    
    public Servicio buscarServicio(int id)
    {
        return control.buscarServicio(id);
         
    }
    
    public ArrayList<Servicio> listaServicio()
    {
        ArrayList<Servicio> servi = new ArrayList();
        servi = control.listaServicio();
        return servi;
    }
}
