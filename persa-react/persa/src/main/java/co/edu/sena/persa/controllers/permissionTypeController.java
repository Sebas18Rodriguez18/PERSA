package co.edu.sena.persa.controllers;

import co.edu.sena.persa.models.PermissionType;
import co.edu.sena.persa.service.PermissionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
@AllArgsConstructor
public class permissionTypeController {

    private final PermissionTypeService typeService;

    @GetMapping
    public List<PermissionType> showAll(){
        return typeService.showAll();
    }

    @GetMapping("/{id}")
    public PermissionType showById(@PathVariable Long id){
        return typeService.showById(id);
    }

    @PostMapping
    public PermissionType save(@RequestBody PermissionType type){
        return typeService.save(type);
    }

    @PutMapping("/{id}")
    public PermissionType update(@PathVariable Long id, @RequestBody PermissionType type){
        type.setId(id);
        return typeService.save(type);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        typeService.delete(id);
    }
}