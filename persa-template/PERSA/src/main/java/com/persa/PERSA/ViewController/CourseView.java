package com.persa.PERSA.ViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.persa.PERSA.models.Course;
import com.persa.PERSA.models.Career;
import com.persa.PERSA.repository.CareerRepository;
import com.persa.PERSA.repository.CourseRepository;

@Controller
@RequestMapping("/course")
public class CourseView {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CareerRepository careerRepository;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "course/index";
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("careers", careerRepository.findAll());
        return "course/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElse(new Course());
        model.addAttribute("course", course);
        model.addAttribute("careers", careerRepository.findAll());
        return "course/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("course") Course course, RedirectAttributes ra) {
        if (course.getCareer() != null && course.getCareer().getId() != null) {
            Career career = careerRepository.findById(course.getCareer().getId())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            course.setCareer(career);
        }

        courseRepository.save(course);
        ra.addFlashAttribute("success", "Curso guardado correctamente");
        return "redirect:/course";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        courseRepository.deleteById(id);
        ra.addFlashAttribute("success", "Curso eliminado correctamente");
        return "redirect:/course";
    }
}