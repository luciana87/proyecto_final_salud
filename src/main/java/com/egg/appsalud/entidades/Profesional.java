package com.egg.appsalud.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table (name = "profesional")
public class Profesional extends Usuario implements Serializable {

/*
    @OneToMany (mappedBy = "profesional")
    private List<NotaMedica> notas;

 */

    public Profesional() {
    }

    public Profesional(String mail, String password, String nombre, String apellido, String dni, LocalDate fechaNacimiento, long telefono) {
        super(mail, password, nombre, apellido, dni, fechaNacimiento, telefono);
    }
}
