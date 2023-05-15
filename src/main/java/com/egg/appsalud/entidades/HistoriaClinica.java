package com.egg.appsalud.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    //OneToOne: se indica que será una relación bidireccional indicando en la clase HistoriaClinica
    // el objeto Paciente correspondiente. MappedBy: historiaClinica = es el atributo por el cual mapeas la relacion
    /*
    @OneToOne (mappedBy = "historiaClinica")
    private Paciente paciente;

    @OneToMany (mappedBy = "Historiaclinica")
    private List<NotaMedica> notas;*/


    public HistoriaClinica(Integer id) {
        this.id = id;
    }
}
