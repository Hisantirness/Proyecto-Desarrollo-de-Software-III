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
        if (cita.getEstado() == null) {
            cita.setEstado("PENDIENTE");
        }
        return citaRepository.save(cita);
    }

    public Cita actualizar(Cita cita) {
        return citaRepository.save(cita);
    }
}
