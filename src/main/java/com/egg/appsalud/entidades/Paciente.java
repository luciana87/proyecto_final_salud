/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.appsalud.entidades;
import com.egg.appsalud.Enumerativos.Rol;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "paciente")
public class Paciente extends Usuario implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

      @OneToOne
      private Imagen imagen;

    @ManyToOne //Muchos pacientes pueden tener una misma OS
    @JoinColumn(name = "obra_social_id") //
    private ObraSocial obraSocial;

    @Column(name = "nro_obra_social")
    private String nroObraSocial;

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

    public Paciente(String mail, String password, String nombre, String apellido, String dni,
                    LocalDate fechaNacimiento, Long telefono, Imagen imagen, ObraSocial obraSocial,
                    String nroObraSocial, List<Turno> listaDeTurnos) {

        super(mail, password, nombre, apellido, dni, fechaNacimiento, telefono);
        this.imagen = imagen;
        this.obraSocial = obraSocial;
        this.nroObraSocial = nroObraSocial;
        this.listaDeTurnos = listaDeTurnos;
    }

    public String getNroObraSocial() {
        return nroObraSocial;
    }

    public void setNroObraSocial(String nroObraSocial) {
        this.nroObraSocial = nroObraSocial;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getId() {
        return id;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public List<Turno> getListaDeTurnos() {
        return listaDeTurnos;
    }

    public void setListaDeTurnos(List<Turno> listaDeTurnos) {
        this.listaDeTurnos = listaDeTurnos;
    }
}
