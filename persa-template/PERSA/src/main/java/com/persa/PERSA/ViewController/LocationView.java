package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Location;
import com.persa.PERSA.repository.LocationRepository;

@Controller
@RequestMapping("/location")
public class LocationView {

    @Autowired
    private LocationRepository locationRepository;

    // Listar todas las sedes
    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        return "location/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("location", new Location());
        return "location/form";
    }

    // Editar
    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Location location = locationRepository.findById(id).orElse(new Location());
        model.addAttribute("location", location);
        return "location/form";
    }

    // Crear
    @PostMapping("/save")
    public String save(@ModelAttribute("location") Location location) {
        locationRepository.save(location);
        return "redirect:/location";
    }

    // Eliminar
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        locationRepository.deleteById(id);
        return "redirect:/location";
    }
}