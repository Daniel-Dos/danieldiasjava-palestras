package br.com.daniel.nicaragua.modelos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public Laboratorio(String nombre, String direccion, Status status) {
        super();
        this.nombre = nombre;
        this.direccion = direccion;
        this.status = status;
    }

    public Laboratorio() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    @Override
    public String toString() {
        return "Laboratorio [id=" + id + ", nome=" + nombre + ", endereco=" + direccion + ", status=" + status + "]";
    }
}