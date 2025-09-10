package com.persa.PERSA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.Course;

public interface CourseRepository extends JpaRepository <Course, Long>{}