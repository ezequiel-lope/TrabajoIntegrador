package com.models;

import com.models.Cliente;
import com.models.Estado;
import com.models.Servicio;
import com.models.Tecnico;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-06T04:25:43", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Incidente.class)
public class Incidente_ { 

    public static volatile SingularAttribute<Incidente, String> descripcion;
    public static volatile SingularAttribute<Incidente, Cliente> cliente;
    public static volatile SingularAttribute<Incidente, Estado> estado;
    public static volatile SingularAttribute<Incidente, Servicio> servicio;
    public static volatile ListAttribute<Incidente, String> problemasRelacionados;
    public static volatile SingularAttribute<Incidente, Tecnico> tecnicoAsignado;
    public static volatile SingularAttribute<Incidente, Boolean> complejo;
    public static volatile SingularAttribute<Incidente, String> tipoProblema;
    public static volatile SingularAttribute<Incidente, Integer> colchonHorasEstimadas;
    public static volatile SingularAttribute<Incidente, Integer> id;
    public static volatile SingularAttribute<Incidente, LocalDateTime> fechaReclamo;

}