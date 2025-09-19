package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Permission;
import com.persa.PERSA.models.Career;
import com.persa.PERSA.models.Location;
import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.repository.CareerRepository;
import com.persa.PERSA.repository.LocationRepository;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Permission> showAll() {
        return permissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permission showById(@PathVariable Long id) {
        return permissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró un permiso con ese id"));
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        if (permission.getCareer() == null || permission.getCareer().getId() == null) {
            throw new RuntimeException("El campo career_id es requerido dentro del objeto career.");
        }

        if (permission.getLocation() == null || permission.getLocation().getId() == null) {
            throw new RuntimeException("El campo location_id es requerido dentro del objeto location.");
        }

        Career career = careerRepository.findById(permission.getCareer().getId())
            .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Location location = locationRepository.findById(permission.getLocation().getId())
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        permission.setCareer(career);
        permission.setLocation(location);

        return permissionRepository.save(permission);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable Long id, @RequestBody Permission permission) {
        if (permission.getCareer() == null || permission.getCareer().getId() == null) {
            throw new RuntimeException("El campo career_id es requerido dentro del objeto career.");
        }

        if (permission.getLocation() == null || permission.getLocation().getId() == null) {
            throw new RuntimeException("El campo location_id es requerido dentro del objeto location.");
        }

        Career career = careerRepository.findById(permission.getCareer().getId())
            .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Location location = locationRepository.findById(permission.getLocation().getId())
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        permission.setId(id);
        permission.setCareer(career);
        permission.setLocation(location);

        return permissionRepository.save(permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionRepository.deleteById(id);
    }
}