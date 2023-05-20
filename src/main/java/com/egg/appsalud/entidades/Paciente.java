/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "paciente")
public class Paciente extends Usuario implements Serializable{

//    @ManyToOne //Muchos pacientes pueden tener una misma OS
//    @JoinColumn(name = "obra_social_id") //
//    private ObraSocial obraSocial;

//    @Column(name = "nro_obra_social", nullable = true)
//    private String NroObraSocial;

//    @Column(nullable = true)
//    private Imagen img;

    @OneToMany(mappedBy = "paciente") //Usando mappedBy (mappedBy: indica cuál entidad es dueña del uno a muchos de forma única) indicas que la relación es unidireccional. Un ‘Paciente’ tiene muchos ‘Turnos’ pero un ‘Turno’ no tiene muchos pacientes.
    @Column(name = "historial_turnos")
    private List<Turno> listaDeTurnos;

//    @OneToOne //Un paciente tiene una historia clínica
//    @JoinColumn (name = "historia_clinica_id") //Foreign Key: historia_clinica_id
//    private HistoriaClinica historiaClinica ;


    public Paciente() {
    }

    public Paciente(String mail, String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento, Long telefono) {
        super(mail, password, nombre, apellido, dni, fechaNacimiento, telefono);
    }

}
