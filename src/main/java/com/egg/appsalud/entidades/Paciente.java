/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "User_paciente")
public class Paciente extends User implements Serializable{
    @Column(name = "id_paciente", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_paciente;
//    @Column(name = "obra_social", nullable = true)
//    @OneToOne
//    private ObraSocial obraSocial;
//    @Column(name = "nro_obra_social", nullable = true)
//    private long Nro obraSocial;
//    @Column(name = "img", nullable = true)
//    private Imagen img;
//    @onetomany
//    private List<Turno>listaDeTurnos;
    
    
    

    public Paciente() {
        super();
    }

    public Paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public Paciente(int id_paciente, String mail, String password, String nombre, String apellido, String dni, int edad, long telefono) {
        super(mail, password, nombre, apellido, dni, edad, telefono);
        this.id_paciente = id_paciente;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    
    
    
}
