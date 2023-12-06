

package com.mycompany.proyectointegrador;

import com.controllers.AreaComercial;
import com.controllers.Controllers;
import com.controllers.MesaAyuda;
import com.controllers.RecursosHumanos;
import com.models.Cliente;

import com.models.Servicio;
import com.models.Tecnico;
import java.util.ArrayList;




public class ProyectoIntegrador {

    public static void main(String[] args) throws Exception {
       
      AreaComercial controllerAC = new AreaComercial();
      MesaAyuda controllerMA = new MesaAyuda();
      RecursosHumanos controllerRH = new RecursosHumanos();
      Controllers control = new Controllers();
      Servicio windows = new Servicio("windows");
      Servicio avast = new Servicio("avast"); Servicio tango = new Servicio("tango");
      Servicio office365 = new Servicio("office 365");
      Servicio linux = new Servicio("linux");
      System.out.println("Antes de llamar a crear servicio");
      
      /*control.crearServicio(linux);
      control.crearServicio(windows);
      control.crearServicio(avast);
      control.crearServicio(tango);
      control.crearServicio(office365);*/
      System.out.println("despues de llamar a crear servicio");
      System.out.println("Antes de llamar ");
      ArrayList <Servicio> servi1 = new ArrayList();
      servi1.add(windows);
      ArrayList <Servicio> servi2 = new ArrayList();
      servi2.add(linux);
      ArrayList <Servicio> servi3 = new ArrayList();
      servi3.add(avast);
      servi3.add(tango);
      servi3.add(office365);
      System.out.println("despues de llamar");
        System.out.println("antes de llamar crear clientes");
      Cliente cli1 = new Cliente("Brenda Malnis","34290902","3513298468","roma 120",servi1);
      Cliente cli2 = new Cliente("Marcela Macaluso","10552330","3515301980","patria 1205",servi2);
      Cliente cli3 = new Cliente("Carlos Lopez","36143539","3514161379","sarmiento 830",servi3);
      controllerAC.crearCliente(cli1);
      controllerAC.crearCliente(cli2);
      controllerAC.crearCliente(cli3);
        System.out.println("despues de llamar crear cliente");
    
      Tecnico tecnico1 = new Tecnico("Martin Frache",0511, windows );
      Tecnico tecnico2 = new Tecnico("Ezequiel Lopez",2102, linux);
      Tecnico tecnico3 = new Tecnico("Ismael Rodrifuez",1531, office365);
     
       controllerRH.crearTecnico(tecnico1);
       controllerRH.crearTecnico(tecnico2);
       controllerRH.crearTecnico(tecnico3);
      
       
     controllerMA.servicioCliente();
     
     controllerMA.marcarProblemaResuelto();
     
     controllerRH.EmitirReporte();
     
     controllerMA.marcarProblemaResuelto();
     
      
       
       
       
      
       
       
       
       
       
       
        
       
      
      
      
      
      
     
       
       
       
    }
}
