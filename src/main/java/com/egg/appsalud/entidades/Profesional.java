package com.egg.appsalud.entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "profesional")
public class Profesional extends Usuario implements Serializable {

/*
    @OneToMany (mappedBy = "profesional")
    private List<NotaMedica> notas;

 */

    public Profesional() {
    }

    public Profesional(String mail, String password, String nombre, String apellido, String dni, Integer edad, long telefono) {
        super(mail, password, nombre, apellido, dni, edad, telefono);
    }
}
