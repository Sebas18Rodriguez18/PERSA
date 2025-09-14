package co.edu.sena.persa.service;

import co.edu.sena.persa.models.Course;
import co.edu.sena.persa.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long id, Course course) {
        return courseRepository.findById(id)
                .map(existing -> {
                    existing.setNumberGroup(course.getNumberGroup());
                    existing.setCareer(course.getCareer());
                    return courseRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}