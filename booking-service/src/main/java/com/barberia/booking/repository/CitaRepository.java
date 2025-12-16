package com.barberia.booking.repository;

import com.barberia.booking.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByBarberoId(Long barberoId);

    // Buscar citas en un rango de tiempo para evitar solapamientos
    List<Cita> findByBarberoIdAndFechaHoraBetween(Long barberoId, java.time.LocalDateTime inicio,
            java.time.LocalDateTime fin);

    // Check if a barber has an active appointment at a specific time
    boolean existsByBarberoIdAndFechaHoraAndEstadoIn(Long barberoId, java.time.LocalDateTime fechaHora,
            List<String> estados);
}
