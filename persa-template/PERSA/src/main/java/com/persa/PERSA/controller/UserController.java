package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.User;
import com.persa.PERSA.models.Role;
import com.persa.PERSA.repository.RoleRepository;
import com.persa.PERSA.repository.UsersRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<User> showAll() {
        return usersRepository.findAll();
    }

    @GetMapping("/{id}")
    public User showById(@PathVariable Long id) {
        return usersRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró un usuario con ese id"));
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new RuntimeException("El campo role_id es requerido dentro del objeto role.");
        }

        Role role = roleRepository.findById(user.getRole().getId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setRole(role);
        return usersRepository.save(user);
    }


    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new RuntimeException("El campo 'role_id' es requerido.");
        }

        Role role = roleRepository.findById(user.getRole().getId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setId(id);
        user.setRole(role);
        return usersRepository.save(user);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usersRepository.deleteById(id);
    }
}
