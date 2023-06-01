package com.egg.appsalud.entidades;

import javax.persistence.*;

@Entity
@Table (name = "nota_medica")
public class NotaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 40)
    private String descripcion;

    @ManyToOne
    @JoinColumn (name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    @ManyToOne
    @JoinColumn (name = "profesional_id")
    private Profesional profesional;



    public NotaMedica(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
 public NotaMedica() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public void setProfesional(String IdProfesional) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

