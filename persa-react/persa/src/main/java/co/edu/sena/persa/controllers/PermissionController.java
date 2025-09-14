package co.edu.sena.persa.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import co.edu.sena.persa.models.Permission;
import co.edu.sena.persa.service.PermissionService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/permission")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public List<Permission> getAll() {
        return permissionService.findAll();
    }

    @GetMapping("/{id}")
    public Permission getById(@PathVariable Long id) {
        return permissionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id " + id));
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionService.update(id, permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }
}