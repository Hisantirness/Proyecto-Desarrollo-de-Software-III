package com.barberia.catalog.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "barberos")
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String experiencia; // Ej: "5 años", "Master Barber"
    private String fotoUrl;

    public Barbero() {
    }

    public Barbero(String nombre, String experiencia, String fotoUrl) {
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.fotoUrl = fotoUrl;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}
