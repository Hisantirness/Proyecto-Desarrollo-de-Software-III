package com.barberia.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "Corte Clásico", "Barba y Toalla"
    private Double precio;
    private Integer duracionMinutos;

    public Servicio() {
    }

    public Servicio(String nombre, Double precio, Integer duracionMinutos) {
        this.nombre = nombre;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }
}
