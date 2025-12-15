package com.barberia.booking.controller;

import com.barberia.booking.model.Cita;
import com.barberia.booking.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.listarTodas();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Cita> listarPorCliente(@PathVariable Long clienteId) {
        return citaService.listarPorCliente(clienteId);
    }

    @PostMapping
    public Cita agendarCita(@RequestBody Cita cita) {
        return citaService.agendar(cita);
    }

    @PutMapping("/{id}/estado")
    public Cita cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return citaService.obtenerPorId(id)
                .map(cita -> {
                    cita.setEstado(nuevoEstado);
                    return citaService.actualizar(cita);
                })
                .orElse(null);
    }
}
