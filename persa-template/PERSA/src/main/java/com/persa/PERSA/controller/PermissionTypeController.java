package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.PermissionType;


import com.persa.PERSA.repository.PermissionTypeRepository;

@RestController
@RequestMapping("/api/types")
public class PermissionTypeController {
    @Autowired
    private PermissionTypeRepository typeRepository;

    @GetMapping
    public List<PermissionType> showAll(){
        return typeRepository.findAll();
    }

    @GetMapping("/{id}")
    public PermissionType showById(@PathVariable Long id){
        return typeRepository.findById(id).orElseThrow(() ->new RuntimeException("No se encontró un tipo de permiso con ese id"));
    }

    @PostMapping
    public PermissionType create(@PathVariable Long id, @RequestBody PermissionType types){
        return typeRepository.save(types);
    }

    @PutMapping("/{id}")
    public PermissionType update(@PathVariable Long id, @RequestBody PermissionType types){
        types.setId(id);
        return typeRepository.save(types);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        typeRepository.deleteById(id);
    }
}
