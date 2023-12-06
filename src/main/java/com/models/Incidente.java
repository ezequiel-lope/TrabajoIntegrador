
package com.models;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "incidente")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Incidente implements Serializable {
    
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Enumerated(EnumType.STRING)
   private Estado estado;
   
   @Column(name = "fecha_reclamo")
   private LocalDateTime fechaReclamo;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "cliente_id")
   private Cliente cliente;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "tipo_problema")
   private String tipoProblema;

    @OneToOne
    @JoinColumn(name = "tecnico_id", unique = true)
    private Tecnico tecnicoAsignado;

   @Column(name = "complejo")
   private boolean complejo;

   @Column(name = "colchon_horas_estimadas")
   private int colchonHorasEstimadas;
   
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;


   @ElementCollection
   @CollectionTable(name = "problemas_relacionados", joinColumns = @JoinColumn(name = "incidente_id"))
   @Column(name = "problema_relacionado")
   private List<String> problemasRelacionados = new ArrayList<>();

   
    public Incidente(Cliente cliente, String descripcion, String tipoProblema) {
        this.estado = Estado.ACTIVO;
        this.fechaReclamo = LocalDateTime.now();
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.tipoProblema = tipoProblema;
        this.complejo = false;
        this.colchonHorasEstimadas = 24;
        
    }
    

   

    
    

    
   
   
}
