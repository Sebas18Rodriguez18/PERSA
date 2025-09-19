package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Location;
import com.persa.PERSA.repository.LocationRepository;

@RestController
@RequestMapping("/api/")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Location> showAll(){
        return locationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Location showById(@PathVariable Long id){
        return locationRepository.findById(id)
                                    .orElseThrow(() ->new RuntimeException("No se encontró una sede con ese id"));
    }

    @PostMapping
    public Location create(@RequestBody Location location){
        return locationRepository.save(location);
    }

    @PutMapping("/{id}")
    public Location update(@RequestBody Location location, @PathVariable Long id){
        location.setId(id);
        return locationRepository.save(location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        locationRepository.deleteById(id);
    }
}
