package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.persa.PERSA.models.Permission;
import com.persa.PERSA.models.Location;
import com.persa.PERSA.models.PermissionType;
import com.persa.PERSA.models.User;

import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.repository.LocationRepository;
import com.persa.PERSA.repository.PermissionTypeRepository;
import com.persa.PERSA.repository.UsersRepository;

@Controller
@RequestMapping("/permission")
public class PermissionView {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PermissionTypeRepository permissionTypeRepository;

    @Autowired
    private UsersRepository userRepository;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
        return "permission/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("permission", new Permission());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("permissionTypes", permissionTypeRepository.findAll());
        return "permission/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Permission permission = permissionRepository.findById(id).orElse(new Permission());
        model.addAttribute("permission", permission);
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("permissionTypes", permissionTypeRepository.findAll());
        return "permission/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("permission") Permission permission, RedirectAttributes ra) {
        if (permission.getLocation() != null && permission.getLocation().getId() != null) {
            Location location = locationRepository.findById(permission.getLocation().getId())
                    .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
            permission.setLocation(location);
        }

        if (permission.getPermissionType() != null && permission.getPermissionType().getId() != null) {
            PermissionType type = permissionTypeRepository.findById(permission.getPermissionType().getId())
                    .orElseThrow(() -> new RuntimeException("Tipo de permiso no encontrado"));
            permission.setPermissionType(type);
        }

        if (permission.getInstructor() != null && permission.getInstructor().getId() != null) {
            User instructor = userRepository.findById(permission.getInstructor().getId())
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
            permission.setInstructor(instructor);
        }

        if (permission.getApprentice() != null && permission.getApprentice().getId() != null) {
            User apprentice = userRepository.findById(permission.getApprentice().getId())
                    .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));
            permission.setApprentice(apprentice);
        }

        if (permission.getGuard() != null && permission.getGuard().getId() != null) {
            User guard = userRepository.findById(permission.getGuard().getId())
                    .orElseThrow(() -> new RuntimeException("Guardia no encontrado"));
            permission.setGuard(guard);
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
