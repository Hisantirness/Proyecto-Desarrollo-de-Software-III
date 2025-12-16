package com.barberia.booking.service;

import com.barberia.booking.model.Cita;
import com.barberia.booking.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    public List<Cita> listarPorCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    public java.util.Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }

    public Cita agendar(Cita cita) {
        // Validar disponibilidad (Regla de 30 minutos)
        // Buscamos si existe alguna cita 29 minutos antes o 29 minutos despues
        java.time.LocalDateTime inicio = cita.getFechaHora().minusMinutes(29);
        java.time.LocalDateTime fin = cita.getFechaHora().plusMinutes(29);

        List<Cita> conflictos = citaRepository.findByBarberoIdAndFechaHoraBetween(cita.getBarberoId(), inicio, fin);

        // Filtramos solo las activas (ignoramos CANCELADA)
        boolean hayConflicto = conflictos.stream()
                .anyMatch(c -> c.getEstado().equals("PENDIENTE") || c.getEstado().equals("CONFIRMADA"));

        if (hayConflicto) {
            throw new RuntimeException("El barbero ya tiene una cita en ese rango de tiempo (30 min).");
        }

        if (cita.getEstado() == null) {
            cita.setEstado("PENDIENTE");
        }
        return citaRepository.save(cita);
    }

    public Cita actualizar(Cita cita) {
        return citaRepository.save(cita);
    }
}
