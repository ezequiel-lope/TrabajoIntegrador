
package com.controllers;

import com.models.Incidente;
import com.models.Tecnico;
import java.util.ArrayList;



public class RecursosHumanos {
    PersistenceControllers control = new  PersistenceControllers();
    MesaAyuda incidentes = new MesaAyuda();
    
    public void crearTecnico(Tecnico tec)
    {
        control.crearTecnico(tec);
    }
    
    public void eliminarTecnico(int id)
    {
        control.eliminarTecnico(id);
    }
    
    public void editarTecnico(Tecnico tec)
    {
        control.editarTecnico(tec);
    }
    
    public Tecnico buscarTecnico(int id)
    {
        Tecnico tec = new Tecnico();
       tec = control.buscarTecnico(id);
        return tec;
    }
    
    public ArrayList<Tecnico> listaTecnicos()
    {
        ArrayList<Tecnico> tecnico = new ArrayList();
        tecnico = control.listaTecnico();
        return tecnico;
    }
    
    
    /*public void EmitirReporte() {
    
    List<Incidente> listaIncidentes = incidentes.listaIncidente();

    listaIncidentes.forEach(inci ->System.out.println(inci) );
}*/
}
