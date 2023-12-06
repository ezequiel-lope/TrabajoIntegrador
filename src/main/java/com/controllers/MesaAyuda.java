
package com.controllers;

import com.models.Cliente;
import com.models.Estado;
import com.models.Incidente;
import com.models.Servicio;
import com.models.Tecnico;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;



public class MesaAyuda {
     PersistenceControllers control = new  PersistenceControllers();
     RecursosHumanos recursos = new RecursosHumanos();
     AreaComercial controllerAC = new AreaComercial();
    
    public void crearIncidente(Incidente inci)
    {
        control.crearIncidente(inci);
    }
    
    public void eliminarIncidente(int id)
    {
        control.eliminarIncidente(id);
    }
    
    public void editarIncidente(Incidente inci) throws Exception
    {
        control.editarIncidente(inci);
    }
    
    public Incidente buscarIncidente(int id)
    {
        return control.buscarIncidente(id);
        
    }
    
    public ArrayList<Incidente> listaIncidente()
    {
        
        return control.listaIncidente();
         
    }
    
   public List<Tecnico> obtenerTecnicosActivos() {
    return recursos.listaTecnicos().stream()
            .filter(tecnico -> tecnico.getEstado() == Estado.ACTIVO)
            .collect(Collectors.toList());
}
   
   
   
   public void servicioCliente() throws Exception {
    String dniCliente = JOptionPane.showInputDialog("Ingrese su DNI:");
    

    List<Cliente> clientesFiltrados = controllerAC.listaCliente().stream()
            .filter(cliente -> cliente.getDni().equals(dniCliente))
            .collect(Collectors.toList());

    if (!clientesFiltrados.isEmpty()) {
        Cliente clienteEncontrado = clientesFiltrados.get(0);
        List<Servicio> serviciosCliente = clienteEncontrado.getServicios();

        if (!serviciosCliente.isEmpty()) {
            
            DefaultComboBoxModel<Servicio> comboBoxModel = new DefaultComboBoxModel<>(serviciosCliente.toArray(new Servicio[0]));
            
            JComboBox<Servicio> comboBox = new JComboBox<>(comboBoxModel);

            int option = JOptionPane.showConfirmDialog(null, comboBox, "Seleccione el servicio afectado", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
                Servicio servicioSeleccionado = (Servicio) comboBox.getSelectedItem();
                
                String problema;
                String[] partes;

    do {
    problema = JOptionPane.showInputDialog("Ingrese el problema y una descripción separados por ',' (coma)");

    partes = problema.split(",");

    if (partes.length != 2) {
        
        JOptionPane.showMessageDialog(null, "Por favor, ingrese el problema y la descripción separados por ','", "Error", JOptionPane.ERROR_MESSAGE);
    }
    } while (partes.length != 2);

            
            altaIncidente(clienteEncontrado,partes[0],partes[1],servicioSeleccionado);
          
            
        } else {
            JOptionPane.showMessageDialog(null, "Este cliente no tiene servicios asociados.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }
  
    
   }


   
  
public void altaIncidente(Cliente cli, String problema, String descripcion, Servicio servicioSeleccionado) throws Exception {
    Incidente inci = new Incidente(cli, problema, descripcion);
    inci.setCliente(cli);
    inci.setServicio(servicioSeleccionado);
    crearIncidente(inci);

    List<Tecnico> tecnicosActivos = obtenerTecnicosActivos();

    seleccionarTecnico(tecnicosActivos,inci);
    
    editarIncidente(inci);
    
}

public void seleccionarTecnico(List<Tecnico> tecnicosActivos, Incidente inci){
    
    if (!tecnicosActivos.isEmpty()) {
        
        DefaultComboBoxModel<Tecnico> comboBoxModel = new DefaultComboBoxModel<>(tecnicosActivos.toArray(new Tecnico[0]));

        JComboBox<Tecnico> comboBox = new JComboBox<>(comboBoxModel);

        int option = JOptionPane.showConfirmDialog(null, comboBox, "Seleccione un técnico activo", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            
            Tecnico tecnicoSeleccionado = (Tecnico) comboBox.getSelectedItem();

            
            inci.setTecnicoAsignado(tecnicoSeleccionado);
            

            JOptionPane.showMessageDialog(
                    null,
                    "Incidente creado con éxito.\nTécnico asignado:\nNombre: " + tecnicoSeleccionado.getNombre() + "\nID: " + tecnicoSeleccionado.getId() +  "\nHoras estimas de resolucion: " + inci.getColchonHorasEstimadas() + "Hs",
                    "Alta de Incidente",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            notificarTecnico(tecnicoSeleccionado, inci);
            
        } else {
            JOptionPane.showMessageDialog(null, "A cancelado la seleccion.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "No hay técnicos activos.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    

}


public void notificarTecnico(Tecnico tecnicoSeleccionado, Incidente inci){

JOptionPane.showMessageDialog( null, tecnicoSeleccionado.getNombre() +"\n"+ "tiene asignado el insitente :" + "\n" + inci.getTipoProblema()+ "\n" + "ID nro:"+ inci.getId(),  "Nuevo incidente asignado",JOptionPane.INFORMATION_MESSAGE );

tecnicoSeleccionado.setEstado(Estado.INACTIVO);

recursos.editarTecnico(tecnicoSeleccionado);
}


public void marcarProblemaResuelto() throws Exception  {
    
    String idTecnico = JOptionPane.showInputDialog("Ingrese su ID:");
    int idTecnicoEntero = Integer.parseInt(idTecnico);
    Tecnico tecni = recursos.buscarTecnico(idTecnicoEntero);
    Incidente inci = tecni.getIncidenteAsignado();
    tecni.setEstado(Estado.ACTIVO);
    inci.setEstado(Estado.INACTIVO);
    tecni.setProblemasResueltos(tecni.getProblemasResueltos() + 1);
    editarIncidente(inci);
    recursos.editarTecnico(tecni);
    String sugerencia = JOptionPane.showInputDialog("Ingrese su sugerencia al cliente:");
    
    notificacionCliente(inci,sugerencia);
    
    }

public void notificacionCliente(Incidente inci, String sugerencia){
    
JOptionPane.showMessageDialog( null,"El incidente:" + inci.getTipoProblema()+ "\n"+ "Id nro:"+ inci.getId()+ "\n"+ "se resolvio Exitosamente",  "Incidente resuelto",JOptionPane.INFORMATION_MESSAGE );
    
    int option = JOptionPane.showOptionDialog( null,
                "¿Desea ver una sugerencia del técnico?",
                "Ver Sugerencia",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,new Object[]{"Sugerencia", "Cancelar"},
                "Sugerencia"
        );

        
        if (option == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, sugerencia, "Sugerencia del Técnico", JOptionPane.INFORMATION_MESSAGE);
        } else {
            
            JOptionPane.showMessageDialog(null, "Gracias por contratar nuestro servicio.", "Operación Cancelada", JOptionPane.INFORMATION_MESSAGE);
        }

}


}
    
    
       