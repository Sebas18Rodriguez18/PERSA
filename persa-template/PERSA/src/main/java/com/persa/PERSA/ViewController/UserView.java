package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.persa.PERSA.models.User;
import com.persa.PERSA.repository.RoleRepository;
import com.persa.PERSA.repository.UsersRepository;

@Controller
@RequestMapping("/user")
public class UserView {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String showAll(Model model){
        model.addAttribute("users", usersRepository.findAll());
        return "user/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        User user= usersRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User users, RedirectAttributes ra) {
        usersRepository.save(users);
        ra.addFlashAttribute("success", "Orden guardada correctamente");
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        usersRepository.deleteById(id);
        ra.addFlashAttribute("success", "Orden eliminada correctamente");
        return "redirect:/user";
    }
}