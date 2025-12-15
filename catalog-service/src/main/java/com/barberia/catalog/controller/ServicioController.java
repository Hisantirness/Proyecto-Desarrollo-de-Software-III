package com.barberia.catalog.controller;

import com.barberia.catalog.model.Servicio;
import com.barberia.catalog.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicio(@PathVariable Long id) {
        return servicioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Servicio guardarServicio(@RequestBody Servicio servicio) {
        return servicioService.guardar(servicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicioDetails) {
        return servicioService.obtenerPorId(id)
                .map(servicio -> {
                    servicio.setNombre(servicioDetails.getNombre());
                    servicio.setPrecio(servicioDetails.getPrecio());
                    servicio.setDuracionMinutos(servicioDetails.getDuracionMinutos());
                    return ResponseEntity.ok(servicioService.guardar(servicio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        return servicioService.obtenerPorId(id)
                .map(servicio -> {
                    servicioService.eliminar(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
