package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Permission;
import com.persa.PERSA.models.Location;
import com.persa.PERSA.models.PermissionType;
import com.persa.PERSA.models.User;

import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.repository.LocationRepository;
import com.persa.PERSA.repository.PermissionTypeRepository;
import com.persa.PERSA.repository.UsersRepository;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PermissionTypeRepository permissionTypeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<Permission> showAll() {
        return permissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permission showById(@PathVariable Long id) {
        return permissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró un permiso con ese ID"));
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        validateRelations(permission);
        return permissionRepository.save(permission);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable Long id, @RequestBody Permission permission) {
        validateRelations(permission);
        permission.setId(id);
        return permissionRepository.save(permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionRepository.deleteById(id);
    }

    private void validateRelations(Permission permission) {
        if (permission.getLocation() == null || permission.getLocation().getId() == null) {
            throw new RuntimeException("El campo location_id es requerido dentro del objeto location.");
        }

        if (permission.getPermissionType() == null || permission.getPermissionType().getId() == null) {
            throw new RuntimeException("El campo permission_type_id es requerido dentro del objeto permissionType.");
        }

        Location location = locationRepository.findById(permission.getLocation().getId())
            .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
        PermissionType type = permissionTypeRepository.findById(permission.getPermissionType().getId())
            .orElseThrow(() -> new RuntimeException("Tipo de permiso no encontrado"));

        permission.setLocation(location);
        permission.setPermissionType(type);

        // Instructor
        if (permission.getInstructor() != null && permission.getInstructor().getId() != null) {
            User instructor = usersRepository.findById(permission.getInstructor().getId())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
            permission.setInstructor(instructor);
        }

        // Aprendiz
        if (permission.getApprentice() != null && permission.getApprentice().getId() != null) {
            User apprentice = usersRepository.findById(permission.getApprentice().getId())
                .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));
            permission.setApprentice(apprentice);
        }

        // Guardia
        if (permission.getGuard() != null && permission.getGuard().getId() != null) {
            User guard = usersRepository.findById(permission.getGuard().getId())
                .orElseThrow(() -> new RuntimeException("Guardia no encontrado"));
            permission.setGuard(guard);
        }
    }
}
