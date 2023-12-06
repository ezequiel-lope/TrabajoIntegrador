
package com.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Cliente implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "nombre")
   private String nombre;

   @Column(name = "dni")
   private String dni;

   @Column(name = "contacto")
   private String contacto;

   @Column(name = "direccion")
   private String direccion;
   
  
   @OneToMany(cascade = CascadeType.PERSIST)
   private List<Servicio> servicios;
  

   @OneToMany
   private List<Incidente> incidentes;

    public Cliente(String nombre, String dni, String contacto, String direccion) {
        this.nombre = nombre;
        this.dni = dni;
        this.contacto = contacto;
        this.direccion = direccion;
    }

   

    public Cliente(String nombre, String dni, String contacto, String direccion, ArrayList<Servicio> servicios) {
        this.nombre = nombre;
        this.dni = dni;
        this.contacto = contacto;
        this.direccion = direccion;
        this.servicios = servicios != null ? servicios : new ArrayList<>();
    }
    
    

   @Override
public String toString() {
    return "Cliente" + " id=" + id + "\n" + "nombre=" + nombre + "\n" + "dni=" + dni + "\n"
            + "contacto=" + contacto + "\n" + "direccion=" + direccion + "\n" +
            "servicios=" + (servicios != null ? servicios.size() + " servicios asociados" + this.nombre : "sin servicios") + "\n" +
            "incidentes=" + incidentes;
}
    
    

    

   
}
