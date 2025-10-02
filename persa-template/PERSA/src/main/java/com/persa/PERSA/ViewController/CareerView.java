package com.persa.PERSA.ViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Career;
import com.persa.PERSA.repository.CareerRepository;

@Controller
@RequestMapping("/career")
public class CareerView {
    
    @Autowired CareerRepository careerRepository;

    // Listar todas las sedes
    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("careers", careerRepository.findAll());
        return "career/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("career", new Career());
        return "career/form";
    }

    // Editar
    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Career career = careerRepository.findById(id).orElse(new Career());
        model.addAttribute("career", career);
        return "career/form";
    }

    // Crear
    @PostMapping("/save")
    public String save(@ModelAttribute("career") Career career) {
        careerRepository.save(career);
        return "redirect:/career";
    }

    // Eliminar
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        careerRepository.deleteById(id);
        return "redirect:/career";
    }
}
