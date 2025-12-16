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
import java.util.Map;

@Controller
public class AuthFrontendController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.gateway.url}")
    private String gatewayUrl;

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        String url = gatewayUrl + "/auth-service/usuarios/registro";

        Map<String, String> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("email", email);
        usuario.put("password", password);
        usuario.put("rol", "CLIENTE");

        try {
            restTemplate.postForObject(url, usuario, Map.class);
            return "redirect:/registro?success=true";
        } catch (Exception e) {
            return "redirect:/registro?error=true";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
            @RequestParam String password,
            jakarta.servlet.http.HttpSession session) {

        // Simulación de Admin (Hardcoded para garantizar que funcione la demo)
        if (email.equals("admin@barberia.com") && password.equals("admin123")) {
            Map<String, Object> admin = new HashMap<>();
            admin.put("id", 999L);
            admin.put("nombre", "Administrador Principal");
            admin.put("rol", "ADMIN");
            session.setAttribute("usuario", admin);
            return "redirect:/admin/citas";
        }

        // Simulación de Cliente Normal
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1L);
        user.put("nombre", "Juan Cliente");
        user.put("rol", "CLIENTE");

        session.setAttribute("usuario", user);
        return "redirect:/citas";
    }

    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
