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
    public List<Permission> showAll(){
        return permissionService.showAll();
    }

    @GetMapping("/{id}")
    public Permission showById(@PathVariable Long id){
        return permissionService.showById(id);
    }

    @PostMapping
    public Permission save(@RequestBody Permission permission, @RequestParam Long instructor_id, 
                                                               @RequestParam Long apprentice_id,
                                                               @RequestParam Long guard_id, 
                                                               @RequestParam Long location_id,
                                                               @RequestParam Long permission_type_id){
        return permissionService.save(permission, instructor_id, apprentice_id, guard_id, guard_id, location_id, permission_type_id);
    }

    @PutMapping
    public Permission update(@RequestBody Permission permission, @RequestParam Long instructor_id, 
                                                                 @RequestParam Long apprentice_id,
                                                                 @RequestParam Long guard_id, 
                                                                 @RequestParam Long location_id,
                                                                 @RequestParam Long permission_type_id){
        return permissionService.save(permission, instructor_id, apprentice_id, guard_id, guard_id, location_id, permission_type_id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        permissionService.delete(id);
    }
}