package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.Course;

public interface CourseRepository extends JpaRepository <Course, Long>{}