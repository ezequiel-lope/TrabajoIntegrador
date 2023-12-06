
package com.controllers;

import com.models.Cliente;
import java.util.ArrayList;

public class AreaComercial {
    
    PersistenceControllers control = new  PersistenceControllers();
    
     public void crearCliente(Cliente cli)
    {
        control.crearCliente(cli);
    }
    
    public void eliminarCliente(int id)
    {
        control.eliminarCliente(id);
    }
    
    public void editarClinte(Cliente cli)
    {
        control.editarCliente(cli);
    }
    
    public Cliente buscarCliente(int id)
    {
        Cliente cli = new Cliente();
       cli = control.buscarCliente(id);
        return cli;
    }
    
    public ArrayList<Cliente> listaCliente()
    {
        ArrayList<Cliente> cliente= new ArrayList();
        cliente = control.listaCliente();
        return cliente;
    }
    
}
