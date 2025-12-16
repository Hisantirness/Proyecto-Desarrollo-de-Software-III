package com.barberia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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
    @SuppressWarnings("unchecked")
    public String listarCitas(Model model, jakarta.servlet.http.HttpSession session) {
        String urlCitas = gatewayUrl + "/booking-service/citas";
        String urlBarberos = gatewayUrl + "/catalog-service/barberos";
        String urlServicios = gatewayUrl + "/catalog-service/servicios";

        try {
            // Obtenemos citas
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> citas = restTemplate.getForObject(urlCitas, List.class);
            model.addAttribute("citas", citas);

            // Obtenemos barberos y servicios para el formulario de crear cita
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> barberos = restTemplate.getForObject(urlBarberos, List.class);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> servicios = restTemplate.getForObject(urlServicios, List.class);
            model.addAttribute("barberos", barberos);
            model.addAttribute("servicios", servicios);

        } catch (Exception e) {
            model.addAttribute("error", "Modo Offline: Usando datos locales.");
            model.addAttribute("citas", java.util.Collections.emptyList());

            // Mock Barberos
            Map<String, Object> b1 = new java.util.HashMap<>();
            b1.put("id", 1L);
            b1.put("nombre", "Santiago Villa");
            Map<String, Object> b2 = new java.util.HashMap<>();
            b2.put("id", 2L);
            b2.put("nombre", "Edgar Rueda");
            Map<String, Object> b3 = new java.util.HashMap<>();
            b3.put("id", 3L);
            b3.put("nombre", "Manuel Serna");
            Map<String, Object> b4 = new java.util.HashMap<>();
            b4.put("id", 4L);
            b4.put("nombre", "Adrian Velasquez");
            model.addAttribute("barberos", java.util.Arrays.asList(b1, b2, b3, b4));

            // Mock Servicios
            Map<String, Object> s1 = new java.util.HashMap<>();
            s1.put("id", 1L);
            s1.put("nombre", "Corte Clásico");
            Map<String, Object> s2 = new java.util.HashMap<>();
            s2.put("id", 2L);
            s2.put("nombre", "Afeitado Lujo");
            Map<String, Object> s3 = new java.util.HashMap<>();
            s3.put("id", 3L);
            s3.put("nombre", "Corte + Barba");
            model.addAttribute("servicios", java.util.Arrays.asList(s1, s2, s3));
        }
        return "citas";
    }

    @PostMapping("/citas/crear")
    @SuppressWarnings("unchecked")
    public String crearCita(@RequestParam Long barberoId,
            @RequestParam Long servicioId,
            @RequestParam String fechaHoraString,
            jakarta.servlet.http.HttpSession session) {

        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");
        if (usuario == null)
            return "redirect:/login";

        String url = gatewayUrl + "/booking-service/citas";

        // Creamos el objeto JSON a enviar
        Map<String, Object> nuevaCita = new HashMap<>();
        nuevaCita.put("barberoId", barberoId);
        nuevaCita.put("servicioId", servicioId);
        nuevaCita.put("clienteId", usuario.get("id")); // Use real session ID
        nuevaCita.put("fechaHora", fechaHoraString);
        nuevaCita.put("estado", "PENDIENTE");

        try {
            restTemplate.postForObject(url, nuevaCita, Map.class);
        } catch (Exception e) {
            // Si el backend falla (ej. validacion de disponibilidad)
            return "redirect:/citas?error=ocupado";
        }

        return "redirect:/citas?success=true";
    }

    @PostMapping("/citas/cambiar-estado")
    public String cambiarEstado(@RequestParam Long citaId, @RequestParam String nuevoEstado) {
        String url = gatewayUrl + "/booking-service/citas/" + citaId + "/estado?nuevoEstado=" + nuevoEstado;
        try {
            // Usamos put() pero RestTemplate.put() no retorna nada facil, exchange es
            // mejor,
            // pero para rapido usaremos postForObject si cambiaramos el back,
            // pero como el back es PUT, usamos put:
            restTemplate.put(url, null);
        } catch (Exception e) {
            return "redirect:/citas?error=true";
        }
        return "redirect:/citas";
    }
}
