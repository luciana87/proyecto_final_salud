package com.egg.appsalud.entidades;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "obra_social")
public class ObraSocial {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, length = 60)
    private String nombre;


    @OneToMany(mappedBy = "obraSocial") //Una obra social puede tener muchos pacientes, pero un paciente sólo puede tener una OS. MappedBy usa el nombre de la variable en Java.
    private List<Paciente> pacientes;


/*
    @ManyToMany(mappedBy="obraSocial")
    private List<Profesional> medicos; //Tabla intermedia por relacion muchos a muchos entre profesional y obra social
 */

    public ObraSocial() {
    }

    public ObraSocial(Integer id, String nombre, List<Paciente> pacientes) {
        this.id = id;
        this.nombre = nombre;
        this.pacientes = pacientes;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
