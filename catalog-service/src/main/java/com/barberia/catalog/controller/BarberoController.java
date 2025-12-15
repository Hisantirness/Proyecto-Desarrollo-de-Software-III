package com.barberia.catalog.controller;

import com.barberia.catalog.model.Barbero;
import com.barberia.catalog.service.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barberos") // Ruta base: http://localhost:8082/barberos
public class BarberoController {

    @Autowired
    private BarberoService barberoService;

    @GetMapping
    public List<Barbero> listarBarberos() {
        return barberoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbero> obtenerBarbero(@PathVariable Long id) {
        return barberoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero) {
        return barberoService.guardar(barbero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbero> actualizarBarbero(@PathVariable Long id, @RequestBody Barbero barberoDetails) {
        return barberoService.obtenerPorId(id)
                .map(barbero -> {
                    barbero.setNombre(barberoDetails.getNombre());
                    barbero.setExperiencia(barberoDetails.getExperiencia());
                    barbero.setFotoUrl(barberoDetails.getFotoUrl());
                    return ResponseEntity.ok(barberoService.guardar(barbero));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBarbero(@PathVariable Long id) {
        return barberoService.obtenerPorId(id)
                .map(barbero -> {
                    barberoService.eliminar(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
