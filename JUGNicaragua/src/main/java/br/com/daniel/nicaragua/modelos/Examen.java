package br.com.daniel.nicaragua.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private TipoExamen tipo;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @OneToMany()
    @JoinColumn(name = "laboratorio_id")
    private List<Laboratorio> laboratorios = new ArrayList<>();

    public Examen(String nombre, TipoExamen tipo, Status status, List<Laboratorio> Laboratorios) {
        super();
        this.nombre = nombre;
        this.tipo = tipo;
        this.status = status;
        this.laboratorios = Laboratorios;
    }

    public Examen() { }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoExamen getTipo() {
        return tipo;
    }

    public void setTipo(TipoExamen tipo) {
        this.tipo = tipo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Laboratorio> getLaboratorios() {
        return laboratorios;
   }

    public void setLaboratorios(List<Laboratorio> laboratorios) {
        this.laboratorios = laboratorios;
    }

    @Override
    public String toString() {
        return "Exame [id=" + id + ", nome=" + nombre + ", tipo=" + tipo + ", status=" + status + ", laboratorios=" + laboratorios +"]";
    }
}