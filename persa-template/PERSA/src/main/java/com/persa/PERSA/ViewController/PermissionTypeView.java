package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.PermissionType;
import com.persa.PERSA.repository.PermissionTypeRepository;

@Controller
@RequestMapping("/type")
public class PermissionTypeView {
    @Autowired
    private PermissionTypeRepository typeRepository;

    @GetMapping
    public String showAll(Model model){
        model.addAttribute("types", typeRepository.findAll());
        return "permissionType/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("type", new PermissionType());
        return "permissionType/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PermissionType type = typeRepository.findById(id).orElse(new PermissionType());
        model.addAttribute("type", type);
        return "permissionType/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("type") PermissionType type) {
        typeRepository.save(type);
        return "redirect:/type";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        typeRepository.deleteById(id);
        return "redirect:/type";
    }
}
