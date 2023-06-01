package com.egg.appsalud.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne (mappedBy = "historiaClinica")
    private Paciente paciente;

    @OneToMany (mappedBy = "historiaClinica")
    private List<NotaMedica> notas;


    public HistoriaClinica(Integer id) {
        this.id = id;
    }
    public HistoriaClinica() {
    }

    public HistoriaClinica(Integer id, Paciente paciente, List<NotaMedica> notas) {
        this.id = id;
        this.paciente = paciente;
        this.notas = notas;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<NotaMedica> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaMedica> notas) {
        this.notas = notas;
    }
    
    
}
