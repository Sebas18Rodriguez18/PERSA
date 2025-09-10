package co.edu.sena.persa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.sena.persa.models.Location;
import co.edu.sena.persa.repository.LocationRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> showAll(){
        return locationRepository.findAll();
    }

    public Location showById(Long id){
        return locationRepository.findById(id).orElseThrow(()->new RuntimeException("Sede no encontrada"));
    }

    public Location save(Location location){
        return locationRepository.save(location);
    }

    public void delete(Long id){
        locationRepository.deleteById(id);
    }
}