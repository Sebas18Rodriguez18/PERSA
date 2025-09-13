package co.edu.sena.persa.controllers;

import co.edu.sena.persa.models.Course;
import co.edu.sena.persa.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> showAll(){
        return courseService.showAll();
    }

    @GetMapping("/{id}")
    public Course showById(@PathVariable Long id){
        return courseService.showById(id);
    }

    @PostMapping
    public Course save(@RequestBody Course course, @RequestParam Long career_id){
        return courseService.save(course, career_id);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course, @RequestParam Long career_id){
        course.setId(id);
        return courseService.save(course, career_id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        courseService.delete(id);
    }
}