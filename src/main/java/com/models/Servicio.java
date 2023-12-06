
package com.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "servicio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;
    
    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Incidente incidente;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Servicio(String nombre) {
        this.nombre = nombre;
       
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    
    

    
}