package co.edu.sena.persa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.sena.persa.models.Career;
import co.edu.sena.persa.repository.CareerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public List<Career> showAll(){
        return careerRepository.findAll();
    }

    public Career showById(Long id){
        return careerRepository.findById(id).orElseThrow(()->new RuntimeException("Programa no encontrado"));
    }

    public Career save(Career career){
        return careerRepository.save(career);
    }

    public void delete(Long id) {
        careerRepository.deleteById(id);
    }
}