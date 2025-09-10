package co.edu.sena.persa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.sena.persa.models.Course;
import co.edu.sena.persa.repository.CourseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    CourseRepository courseRepository;

    public List<Course> showAll(){
        return courseRepository.findAll();
    }

    public Course showById(Long id){
        return courseRepository.findById(id).orElseThrow(()->new RuntimeException("Ficha no encontrada"));
    }

    public Course save(Course course){
        return courseRepository.save(course);
    }

    public void delete(Long id){
        courseRepository.deleteById(id);
    }
}