package co.edu.sena.persa.controllers;

import co.edu.sena.persa.models.Career;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import co.edu.sena.persa.service.CareerService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/career")
@AllArgsConstructor
public class CareerController {
    private final CareerService careerService;

    public List<Career> showAll(){
        return careerService.showAll();
    }

    
}
