package com.barberia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminFrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.gateway.url}")
    private String gatewayUrl;

    @GetMapping("/citas")
    @SuppressWarnings("unchecked")
    public String adminPanelCitas(HttpSession session, Model model) {
        // 1. Security Check
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.get("rol"))) {
            return "redirect:/login";
        }

        String urlCitas = gatewayUrl + "/booking-service/citas";
        // String urlUsuarios = gatewayUrl + "/auth-service/usuarios"; // Idealmente

        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> citas = restTemplate.getForObject(urlCitas, List.class);

            if (citas != null) {
                for (Map<String, Object> cita : citas) {
                    // Safe parsing
                    Object cIdObj = cita.get("clienteId");
                    Long clienteId = (cIdObj instanceof Number) ? ((Number) cIdObj).longValue() : 0L;

                    if (clienteId == 1L) {
                        cita.put("nombreCliente", "Juan Cliente (Demo)");
                    } else if (clienteId == 2L) {
                        cita.put("nombreCliente", "Maria Tester");
                    } else {
                        cita.put("nombreCliente", "Cliente ID " + clienteId);
                    }
                }
                model.addAttribute("citas", citas);
            } else {
                model.addAttribute("citas", java.util.Collections.emptyList());
            }

            model.addAttribute("usuario", usuario);

        } catch (Exception e) {
            model.addAttribute("error", "Error al conectar con microservicios: " + e.getMessage());
            model.addAttribute("citas", java.util.Collections.emptyList());
        }

        return "admin_citas";
    }

    @PostMapping("/citas/estado")
    @SuppressWarnings("unchecked")
    public String cambiarEstadoCita(@RequestParam Long citaId, @RequestParam String nuevoEstado, HttpSession session) {
        // Security Check
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.get("rol"))) {
            return "redirect:/login";
        }

        String url = gatewayUrl + "/booking-service/citas/" + citaId + "/estado?nuevoEstado=" + nuevoEstado;
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            return "redirect:/admin/citas?error=true";
        }
        return "redirect:/admin/citas?success=true";
    }
}
