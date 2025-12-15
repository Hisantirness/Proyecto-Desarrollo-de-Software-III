package com.barberia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CitaFrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.gateway.url}")
    private String gatewayUrl;

    @GetMapping("/citas")
    public String listarCitas(Model model) {
        String urlCitas = gatewayUrl + "/booking-service/citas";
        String urlBarberos = gatewayUrl + "/catalog-service/barberos";
        String urlServicios = gatewayUrl + "/catalog-service/servicios";

        try {
            // Obtenemos citas
            List<Map<String, Object>> citas = restTemplate.getForObject(urlCitas, List.class);
            model.addAttribute("citas", citas);

            // Obtenemos barberos y servicios para el formulario de crear cita
            List<Map<String, Object>> barberos = restTemplate.getForObject(urlBarberos, List.class);
            List<Map<String, Object>> servicios = restTemplate.getForObject(urlServicios, List.class);
            model.addAttribute("barberos", barberos);
            model.addAttribute("servicios", servicios);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al conectar con los microservicios.");
        }
        return "citas";
    }

    @PostMapping("/citas/crear")
    public String crearCita(@RequestParam Long barberoId, 
                            @RequestParam Long servicioId, 
                            @RequestParam(required = false) Long clienteId) {
        
        String url = gatewayUrl + "/booking-service/citas";
        
        // Creamos el objeto JSON a enviar
        Map<String, Object> nuevaCita = new HashMap<>();
        nuevaCita.put("barberoId", barberoId);
        nuevaCita.put("servicioId", servicioId);
        nuevaCita.put("clienteId", clienteId != null ? clienteId : 1L); // Default user ID 1 for simplicity
        nuevaCita.put("fechaHora", LocalDateTime.now().toString()); // Simple current time for proof of concept
        nuevaCita.put("estado", "PENDIENTE");

        try {
            restTemplate.postForObject(url, nuevaCita, Map.class);
        } catch (Exception e) {
            // Log error
            return "redirect:/citas?error=true";
        }

        return "redirect:/citas";
    }

    @PostMapping("/citas/cambiar-estado")
    public String cambiarEstado(@RequestParam Long citaId, @RequestParam String nuevoEstado) {
        String url = gatewayUrl + "/booking-service/citas/" + citaId + "/estado?nuevoEstado=" + nuevoEstado;
        try {
            // Usamos put() pero RestTemplate.put() no retorna nada facil, exchange es mejor, 
            // pero para rapido usaremos postForObject si cambiaramos el back, 
            // pero como el back es PUT, usamos put:
            restTemplate.put(url, null);
        } catch (Exception e) {
            return "redirect:/citas?error=true";
        }
        return "redirect:/citas";
    }
}
