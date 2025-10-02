package co.edu.sena.persa.controllers;

import co.edu.sena.persa.models.Career;
import co.edu.sena.persa.service.CareerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career")
@AllArgsConstructor
public class CareerController {

    private final CareerService careerService;

    @GetMapping
    public List<Career> showAll(){
        return careerService.showAll();
    }

    @GetMapping("/{id}")
    public Career showById(@PathVariable Long id){
        return careerService.showById(id);
    }

    @PostMapping
    public Career save(@RequestBody Career career){
        return careerService.save(career);
    }

    @PutMapping("/{id}")
    public Career update(@PathVariable Long id, @RequestBody Career career){
        career.setId(id);
        return careerService.save(career);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        careerService.delete(id);
    }
}