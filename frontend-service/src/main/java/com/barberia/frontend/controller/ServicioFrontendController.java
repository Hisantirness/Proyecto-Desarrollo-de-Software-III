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
            List<Map<String, Object>> servicios = restTemplate.getForObject(url, List.class);
            model.addAttribute("servicios", servicios);
        } catch (Exception e) {
            model.addAttribute("error", "Error al conectar con el servicio de catálogo.");
        }
        return "servicios";
    }
}
