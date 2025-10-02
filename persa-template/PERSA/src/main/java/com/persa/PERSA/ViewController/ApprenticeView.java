package com.persa.PERSA.ViewController;

import com.persa.PERSA.models.Course;
import com.persa.PERSA.models.User;
import com.persa.PERSA.repository.CourseRepository;
import com.persa.PERSA.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apprentice")
public class ApprenticeView {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UsersRepository userRepository;

    @GetMapping
    public String index(@RequestParam(value = "course_id", required = false) Long courseId,
                        Model model) {

        List<Course> courses = courseRepository.findByStatus("ACTIVO");
        model.addAttribute("courses", courses);

        List<User> apprentices = null;
        if (courseId != null) {
            apprentices = userRepository.findApprenticesByCourseId(courseId);
            model.addAttribute("selectedCourse", courseRepository.findById(courseId).orElse(null));
        }
        model.addAttribute("apprentices", apprentices);

        return "apprentice/index";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        Optional<User> apprentice = userRepository.findById(id);

        if (apprentice.isPresent() && apprentice.get().getRole().getId() == 3) {
            model.addAttribute("apprentice", apprentice.get());
            return "apprentice/profile";
        } else {
            return "redirect:/apprentice?error=not_apprentice";
        }
    }
}