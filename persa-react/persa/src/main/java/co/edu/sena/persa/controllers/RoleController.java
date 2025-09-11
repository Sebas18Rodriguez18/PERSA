package co.edu.sena.persa.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import co.edu.sena.persa.models.Role;
import co.edu.sena.persa.service.RoleService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> ShowAll() {
        return roleService.showAll();
    }

     @GetMapping("/{id}") 
    public Role showById(@PathVariable Long id){
        return roleService.showById(id);
    }

    @PostMapping
    public Role save(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.save(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}