package co.edu.sena.persa.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import co.edu.sena.persa.models.Location;
import co.edu.sena.persa.service.LocationService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<Location> showAll(){
        return locationService.showAll();
    }

    @GetMapping("/{id}")
    public Location showById(@PathVariable Long id){
        return locationService.showById(id);
    }

    @PostMapping
    public Location save(@RequestBody Location location){
        return locationService.save(location);
    }

    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody Location location){
        location.setId(id);
        return locationService.save(location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        locationService.delete(id);
    }
}
