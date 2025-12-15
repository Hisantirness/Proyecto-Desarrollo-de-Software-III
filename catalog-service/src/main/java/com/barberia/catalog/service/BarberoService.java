package com.barberia.catalog.service;

import com.barberia.catalog.model.Barbero;
import com.barberia.catalog.repository.BarberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    public List<Barbero> listarTodos() {
        return barberoRepository.findAll();
    }

    public Barbero guardar(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    public Optional<Barbero> obtenerPorId(Long id) {
        return barberoRepository.findById(id);
    }

    public void eliminar(Long id) {
        barberoRepository.deleteById(id);
    }
}
