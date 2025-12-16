package com.barberia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class BarberoFrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.gateway.url}")
    private String gatewayUrl;

    @GetMapping("/barberos")
    @SuppressWarnings("unchecked")
    public String listarBarberos(Model model) {
        String url = gatewayUrl + "/catalog-service/barberos";
        try {
            List<Map<String, Object>> barberos = restTemplate.getForObject(url, List.class);
            model.addAttribute("barberos", barberos);
        } catch (Exception e) {
            // FALLBACK: Mock Data for Academic Demo (Robustness)
            System.err.println("Backend unreachable. Using Mock Data.");

            Map<String, Object> b1 = new java.util.HashMap<>();
            b1.put("id", 1L);
            b1.put("nombre", "Santiago Villa");
            b1.put("experiencia", "Master Barber");
            b1.put("fotoUrl", "https://randomuser.me/api/portraits/men/1.jpg");

            Map<String, Object> b2 = new java.util.HashMap<>();
            b2.put("id", 2L);
            b2.put("nombre", "Edgar Rueda");
            b2.put("experiencia", "Barba Pro");
            b2.put("fotoUrl", "https://randomuser.me/api/portraits/men/2.jpg");

            Map<String, Object> b3 = new java.util.HashMap<>();
            b3.put("id", 3L);
            b3.put("nombre", "Manuel Serna");
            b3.put("experiencia", "Degradados");
            b3.put("fotoUrl", "https://randomuser.me/api/portraits/men/3.jpg");

            Map<String, Object> b4 = new java.util.HashMap<>();
            b4.put("id", 4L);
            b4.put("nombre", "Adrian Velasquez");
            b4.put("experiencia", "Clásico");
            b4.put("fotoUrl", "https://randomuser.me/api/portraits/men/4.jpg");

            model.addAttribute("barberos", java.util.Arrays.asList(b1, b2, b3, b4));
            model.addAttribute("error", "Nota: Modo Offline (Backend desconectado)");
        }
        return "barberos";
    }
}
