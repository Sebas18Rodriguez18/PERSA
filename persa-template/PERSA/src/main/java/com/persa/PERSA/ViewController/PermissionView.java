package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.persa.PERSA.models.Permission;
import com.persa.PERSA.models.Career;
import com.persa.PERSA.models.Location;
import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.repository.CareerRepository;
import com.persa.PERSA.repository.LocationRepository;

@Controller
@RequestMapping("/permission")
public class PermissionView {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
        return "permission/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("permission", new Permission());
        model.addAttribute("careers", careerRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        return "permission/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Permission permission = permissionRepository.findById(id).orElse(new Permission());
        model.addAttribute("permission", permission);
        model.addAttribute("careers", careerRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        return "permission/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("permission") Permission permission, RedirectAttributes ra) {
        if (permission.getCareer() != null && permission.getCareer().getId() != null) {
            Career career = careerRepository.findById(permission.getCareer().getId())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            permission.setCareer(career);
        }

        if (permission.getLocation() != null && permission.getLocation().getId() != null) {
            Location location = locationRepository.findById(permission.getLocation().getId())
                    .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
            permission.setLocation(location);
        }

        permissionRepository.save(permission);
        ra.addFlashAttribute("success", "Permiso guardado correctamente");
        return "redirect:/permission";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        permissionRepository.deleteById(id);
        ra.addFlashAttribute("success", "Permiso eliminado correctamente");
        return "redirect:/permission";
    }
}