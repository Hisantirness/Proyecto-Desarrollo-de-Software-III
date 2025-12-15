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
    public String listarBarberos(Model model) {
        // Llamar al microservicio via Gateway
        String url = gatewayUrl + "/catalog-service/barberos";
        
        // Recibimos una lista de mapas (JSON generico) para no duplicar Entidades
        List<Map<String, Object>> barberos = restTemplate.getForObject(url, List.class);
        
        model.addAttribute("barberos", barberos);
        return "barberos";
    }
}
