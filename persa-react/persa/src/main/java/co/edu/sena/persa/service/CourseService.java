package co.edu.sena.persa.service;

import co.edu.sena.persa.models.Career;
import co.edu.sena.persa.models.Course;
import co.edu.sena.persa.repository.CareerRepository;
import co.edu.sena.persa.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CareerRepository careerRepository;

    public List<Course> showAll(){
        return courseRepository.findAll();
    }

    public Course showById(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha no encontrada"));
    }

    public Course save(Course course, Long careerId){
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
        course.setCareer(career);
        return courseRepository.save(course);
    }

    public void delete(Long id){
        courseRepository.deleteById(id);
    }
}