package com.models;

import com.models.Estado;
import com.models.Incidente;
import com.models.Servicio;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-06T01:32:04", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Tecnico.class)
public class Tecnico_ { 

    public static volatile SingularAttribute<Tecnico, Incidente> incidenteAsignado;
    public static volatile SingularAttribute<Tecnico, Estado> estado;
    public static volatile SingularAttribute<Tecnico, Integer> problemasResueltos;
    public static volatile SingularAttribute<Tecnico, Integer> matricula;
    public static volatile SingularAttribute<Tecnico, Incidente> incidente;
    public static volatile SingularAttribute<Tecnico, Long> id;
    public static volatile SingularAttribute<Tecnico, String> nombre;
    public static volatile SingularAttribute<Tecnico, Servicio> especialidad;

}