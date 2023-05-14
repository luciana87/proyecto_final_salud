/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "paciente")
public class Paciente extends User implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

//    @ManyToOne //Muchos pacientes pueden tener una misma OS
//    @JoinColumn(name = "obra_social_id") //
//    private List<ObraSocial> obraSocial;

//    @Column(name = "nro_obra_social", nullable = true)
//    private long NroObraSocial;

//    @Column(nullable = true)
//    private Imagen img;

//    @OneToMany (mappedBy = "paciente") //Usando mappedBy (mappedBy: indica cuál entidad es dueña del uno a muchos de forma única) indicas que la relación es unidireccional. Un ‘Paciente’ tiene muchos ‘Turnos’ pero un ‘Turno’ no tiene muchos pacientes.
//    @Column (name = "historial_turnos")
//    private List<Turno> listaDeTurnos;
    
    
    

    public Paciente() {
        super();
    }

    public Paciente(String id) {
        this.id = id;
    }

    public Paciente(String mail, String password, String nombre, String apellido, String dni, Integer edad, long telefono, String id) {
        super(mail, password, nombre, apellido, dni, edad, telefono);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    
}
