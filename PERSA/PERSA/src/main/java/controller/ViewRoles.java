package controller;

import model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import repository.RolesRepository;

@Controller
@RequestMapping("/view/roles")
public class ViewRoles {
    
    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", rolesRepository.findAll());
        return "roles";
    }

    @GetMapping("/form")
    public String formulario(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Roles roles = rolesRepository.findById(id).orElse(new Roles());
            model.addAttribute("role", roles);
        } else {
            model.addAttribute("role", new Roles());
        }
        return "role_form";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Roles roles) {
        rolesRepository.save(roles);
        return "redirect:/view/role";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Roles roles = rolesRepository.findById(id).orElse(new Roles());
        model.addAttribute("role", roles);
        return "role_form";
    }

    @PostMapping("/delete/{id}")
    public String eliminar(@PathVariable Long id) {
        rolesRepository.deleteById(id);
        return "redirect:/view/role";
    }


}
