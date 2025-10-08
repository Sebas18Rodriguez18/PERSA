package com.persa.PERSA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Role;
import com.persa.PERSA.models.User;
import com.persa.PERSA.repository.RoleRepository;
import com.persa.PERSA.repository.UsersRepository;

@Controller
public class AuthController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user) {
        // cifrar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // estado por defecto
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVO");
        }

        // buscar rol por defecto: preferimos "APRENDIZ", si no existe usar cualquier rol disponible
        Role role = roleRepository.findByName("APRENDIZ")
                .orElseGet(() -> roleRepository.findByName("ROLE_CLIENT")
                        .orElseGet(() -> roleRepository.findAll().stream().findFirst()
                                .orElseThrow(() -> new RuntimeException("No hay roles configurados en la base de datos"))));

        user.setRole(role);
        userRepository.save(user);
        return "redirect:/login?registered";
    }

}
