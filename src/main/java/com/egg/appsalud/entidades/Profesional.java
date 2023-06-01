package com.egg.appsalud.entidades;

import com.egg.appsalud.Enumerativos.Especialidad;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "profesional")
public class Profesional extends Usuario implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    @Column
    private Double reputacion;

    @Column(name = "valor_consulta", nullable = false)
    private Double valorConsulta;

    @Column(name = "descripcion_especialidad", nullable = false)
    private String descripcionEspecialidad;

/*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "profesional_obra_social",
        joinColumns = @JoinColumn(name = "id_profesional", nullable = false),
        inverseJoinColumns = @JoinColumn(name="id_obra_social", nullable = false))
    private List<ObraSocial> atiendePor; //Tabla intermedia: profesional_obra_social por relación muchos a muchos entre profesional y obra social

    @OneToMany(mappedBy = "profesional")
    @Column(name = "jornada_laboral")
    private List<JornadaLaboral> jornadaLaboral;
*/
    @OneToMany(mappedBy = "medico")
    @Column(name = "turnos_asignados")
    private List<Turno> turnosAsignados;


    @OneToMany(mappedBy = "medico")
    private List<Calificacion> calificaciones;


/*
    @OneToMany (mappedBy = "profesional")
    private List<NotaMedica> notas;

     */
    @OneToMany(mappedBy = "profesional")
    @Fetch(FetchMode.JOIN)
    private List<JornadaLaboral> jornadaLaboral;

    public Profesional() {
    }


    public Profesional(String mail, String password, String nombre, String apellido, String dni,
                       LocalDate fechaNacimiento, String telefono, String matricula, Especialidad especialidad,
                       Double reputacion, Double valorConsulta, String descripcionEspecialidad, List<Turno> turnosAsignados) {

        super(mail, password, nombre, apellido, dni, fechaNacimiento, telefono);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.reputacion = reputacion;
        this.valorConsulta = valorConsulta;
        this.descripcionEspecialidad = descripcionEspecialidad;
        this.turnosAsignados = turnosAsignados;
    }

    public void setJornadaLaboral(List<JornadaLaboral> jornadaLaboral) {
        this.jornadaLaboral = jornadaLaboral;
    }

    public List<JornadaLaboral> getJornadaLaboral() {
        return jornadaLaboral;
    }

    public String getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Double getReputacion() {
        return reputacion;
    }

    public void setReputacion(Double reputacion) {
        this.reputacion = reputacion;
    }

    public Double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public String getDescripcionEspecialidad() {
        return descripcionEspecialidad;
    }

    public void setDescripcionEspecialidad(String descripcionEspecialidad) {
        this.descripcionEspecialidad = descripcionEspecialidad;
    }
}
