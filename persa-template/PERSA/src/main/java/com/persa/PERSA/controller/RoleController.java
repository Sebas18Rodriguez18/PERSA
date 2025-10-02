package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Role;
import com.persa.PERSA.repository.RoleRepository;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> showAll(){
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Role showById(@PathVariable Long id){
        return roleRepository.findById(id)
                                .orElseThrow(() ->new RuntimeException("No se encontró un rol con ese id"));
    }

    @PostMapping
    public Role create(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @PutMapping Role update(@PathVariable Long id, @RequestBody Role role){
        role.setId(id);
        return roleRepository.save(role);
    }

    @DeleteMapping 
    public void delete(@PathVariable Long id){
        roleRepository.deleteById(id);
    }
}