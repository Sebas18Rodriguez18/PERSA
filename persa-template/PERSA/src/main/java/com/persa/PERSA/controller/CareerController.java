package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Career;
import com.persa.PERSA.repository.CareerRepository;

@RestController
@RequestMapping("/api/career")
public class CareerController {
    @Autowired
    private CareerRepository careerRepository;

    @GetMapping
    public List<Career> showAll(){
        return careerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Career showById(@PathVariable Long id){
        return careerRepository.findById(id).orElseThrow(() ->new RuntimeException("No se encontró un rol con ese id"));
    }

    @PostMapping
    public Career create(@RequestBody Career Career){
        return careerRepository.save(Career);
    }

    @PutMapping Career update(@PathVariable Long id, @RequestBody Career Career){
        Career.setId(id);
        return careerRepository.save(Career);
    }

    @DeleteMapping 
    public void delete(@PathVariable Long id){
        careerRepository.deleteById(id);
    }
}