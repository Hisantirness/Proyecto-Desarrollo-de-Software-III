package com.barberia.booking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId; // Relacion logica con Auth Service
    private Long barberoId; // Relacion logica con Catalog Service
    private Long servicioId; // Relacion logica con Catalog Service

    private LocalDateTime fechaHora;
    private String estado; // PENDIENTE, CONFIRMADA, CANCELADA

    public Cita() {
    }

    public Cita(Long clienteId, Long barberoId, Long servicioId, LocalDateTime fechaHora, String estado) {
        this.clienteId = clienteId;
        this.barberoId = barberoId;
        this.servicioId = servicioId;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getBarberoId() { return barberoId; }
    public void setBarberoId(Long barberoId) { this.barberoId = barberoId; }
    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
