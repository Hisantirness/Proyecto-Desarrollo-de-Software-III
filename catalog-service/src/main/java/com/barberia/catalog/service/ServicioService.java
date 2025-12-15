package com.barberia.catalog.service;

import com.barberia.catalog.model.Servicio;
import com.barberia.catalog.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Optional<Servicio> obtenerPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}
