package com.persa.PERSA.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.Course;

public interface CourseRepository extends JpaRepository <Course, Long>{
    List<Course> findByStatus(String status);
}