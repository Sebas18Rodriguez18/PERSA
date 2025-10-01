package com.persa.PERSA.repository;

import com.persa.PERSA.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p WHERE p.apprentice.id = :apprenticeId AND p.apprentice.role.id = 3")
    List<Permission> findByApprenticeId(@Param("apprenticeId") Long apprenticeId);

    @Query("SELECT p FROM Permission p " +
           "WHERE p.apprentice.id IN (" +
           "SELECT ac.user.id FROM ApprenticeCourse ac WHERE ac.course.id = :courseId" +
           ") AND p.apprentice.role.id = 3")
    List<Permission> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT p FROM Permission p " +
           "WHERE p.permissionDate BETWEEN :startDate AND :endDate " +
           "AND p.apprentice.role.id = 3")
    List<Permission> findByPermissionDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}