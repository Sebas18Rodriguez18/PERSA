package com.persa.PERSA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.persa.PERSA.models.Course;
import com.persa.PERSA.models.Career;
import com.persa.PERSA.repository.CareerRepository;
import com.persa.PERSA.repository.CourseRepository;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CareerRepository careerRepository;

    @GetMapping
    public List<Course> showAll(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course showById(@PathVariable Long id){
        return courseRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No se encontró un grupo con ese id"));
    }

    @PostMapping
    public Course create(@RequestBody Course course){
        if (course.getCareer() == null || course.getCareer().getId() == null) {
            throw new RuntimeException("El campo career_id es requerido dentro del objeto career.");
        }

        Career career = careerRepository.findById(course.getCareer().getId())
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        course.setCareer(career);
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course){
        if (course.getCareer() == null || course.getCareer().getId() == null) {
            throw new RuntimeException("El campo career_id es requerido dentro del objeto career.");
        }

        Career career = careerRepository.findById(course.getCareer().getId())
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        course.setId(id);
        course.setCareer(career);
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        courseRepository.deleteById(id);
    }
}