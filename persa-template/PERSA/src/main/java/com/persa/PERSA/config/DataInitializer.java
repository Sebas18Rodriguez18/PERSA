package com.persa.PERSA.config;

import java.time.Instant;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.persa.PERSA.models.Role;
import com.persa.PERSA.repository.RoleRepository;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear roles por defecto si no existen
        String[] defaults = {"APRENDIZ", "INSTRUCTOR", "GUARDIA", "ROLE_CLIENT"};
        for (String name : defaults) {
            roleRepository.findByName(name).orElseGet(() -> {
                Role r = new Role();
                r.setName(name);
                r.setCreatedAt(Instant.now());
                r.setUpdatedAt(Instant.now());
                return roleRepository.save(r);
            });
        }
    }
}
