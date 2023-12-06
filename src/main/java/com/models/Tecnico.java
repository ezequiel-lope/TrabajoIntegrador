
package com.models;

import com.controllers.MesaAyuda;
import com.controllers.RecursosHumanos;
import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "tecnico")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Tecnico implements Serializable {
 
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "nombre")
   private String nombre;

   @Column(name = "matricula")
   private int matricula;

  
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "servicio_id")
    private Servicio especialidad;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Incidente incidenteAsignado;

  @Enumerated(EnumType.STRING)
  @JoinColumn(name = "incidente_id")
    private Estado estado;

   @Column(name = "problemas_resueltos")
   private int problemasResueltos;

   @OneToOne(mappedBy = "tecnicoAsignado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Incidente incidente;

    public Tecnico(String nombre, int matricula, Servicio especialidad) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.estado = Estado.ACTIVO;
        this.problemasResueltos = 0;
        
    }
    
    
    


    @Override
    public String toString() {
        return "Tecnico" + "id=" + id + "\n"+ ", nombre=" + nombre + "\n" + ", matricula=" + matricula;
    }
    

    
} 
 

   
