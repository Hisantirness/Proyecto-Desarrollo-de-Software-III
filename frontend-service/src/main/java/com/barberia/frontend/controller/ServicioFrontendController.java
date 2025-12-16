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
public class ServicioFrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.gateway.url}")
    private String gatewayUrl;

    @GetMapping("/servicios")
    public String listarServicios(Model model) {
        String url = gatewayUrl + "/catalog-service/servicios";
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> servicios = restTemplate.getForObject(url, List.class);
            model.addAttribute("servicios", servicios);
        } catch (Exception e) {
            // FALLBACK: Mock Data
            Map<String, Object> s1 = new java.util.HashMap<>();
            s1.put("id", 1L);
            s1.put("nombre", "Corte Clásico");
            s1.put("precio", 25.0);
            s1.put("duracionMinutos", 45);

            Map<String, Object> s2 = new java.util.HashMap<>();
            s2.put("id", 2L);
            s2.put("nombre", "Afeitado Lujo");
            s2.put("precio", 15.0);
            s2.put("duracionMinutos", 30);

            Map<String, Object> s3 = new java.util.HashMap<>();
            s3.put("id", 3L);
            s3.put("nombre", "Corte + Barba");
            s3.put("precio", 35.0);
            s3.put("duracionMinutos", 60);

            model.addAttribute("servicios", java.util.Arrays.asList(s1, s2, s3));
            model.addAttribute("error", "Nota: Modo Offline (Backend desconectado)");
        }
        return "servicios";
    }
}
