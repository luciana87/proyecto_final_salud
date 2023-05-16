package com.egg.appsalud.entidades;

import javax.persistence.*;

@Entity
@Table (name = "nota_medica")
public class NotaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String descripcion;

/*
    @ManyToOne
    @JoinColumn (name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    @ManyToOne
    @JoinColumn (name = "profesional_id")
    private Profesional profesional;

 */

    public NotaMedica(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
}
